package com.starmicronics.starprntsdk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;

import com.starmicronics.starprntsdk.Communication.CommunicationResult;

import com.starmicronics.starioextension.StarIoExt.Emulation;
import com.starmicronics.starprntsdk.functions.PrinterFunctions;
import com.starmicronics.starprntsdk.localizereceipts.ILocalizeReceipts;

import static com.starmicronics.starioextension.ICommandBuilder.BlackMarkType;

import java.util.ArrayList;
import java.util.List;

public class BlackMarkFragment extends Fragment implements ListView.OnItemClickListener, CommonAlertDialogFragment.Callback {

    private ProgressDialog mProgressDialog;

    private int mLanguage;

    private boolean mIsForeground;

    private ArrayAdapter<ItemList> mAdapter;

    private Switch mDetectionSwitch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressDialog = new ProgressDialog(getActivity());

        mProgressDialog.setMessage("Communicating...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);

        mAdapter = new ItemListAdapter(getActivity(), new ArrayList<ItemList>());

        Intent intent = getActivity().getIntent();
        mLanguage  = intent.getIntExtra(CommonActivity.BUNDLE_KEY_LANGUAGE, PrinterSettingConstant.LANGUAGE_ENGLISH);

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        int paperSize = settings.getPaperSize();

        ILocalizeReceipts localizeReceipts = ILocalizeReceipts.createLocalizeReceipts(mLanguage, paperSize);
        String            languageCode     = localizeReceipts.getLanguageCode();

        addTitle("Like a StarIO-SDK Sample");

        addMenu(languageCode + " Text Label");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_black_mark, container, false);

        ListView listView = rootView.findViewById(R.id.blackMarkListView);

        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
        listView.setDivider(null);

        mDetectionSwitch = rootView.findViewById(R.id.blackMarkDetectionSwitch);

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        int modelIndex = settings.getModelIndex();

        if (ModelCapability.canUseBlackMarkDetection(modelIndex)) {
            mDetectionSwitch.setEnabled(true);
            mDetectionSwitch.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
        }
        else {
            mDetectionSwitch.setEnabled(false);
            mDetectionSwitch.setTextColor(ContextCompat.getColor(getActivity(), R.color.lightgrey));
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mIsForeground = true;
    }

    @Override
    public void onPause() {
        super.onPause();

        mIsForeground = false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        print();
    }

    @Override
    public void onDialogResult(String tag, Intent data) {
        // do nothing
    }

    private void addTitle(String title) {
        List<TextInfo> textList = new ArrayList<>();

        textList.add(new TextInfo(title, R.id.menuTextView));

        mAdapter.add(new ItemList(R.layout.list_main_title_row, textList, false));
    }

    private void addMenu(String menu) {
        List<TextInfo> textList = new ArrayList<>();

        textList.add(new TextInfo(menu, R.id.menuTextView));

        mAdapter.add(new ItemList(R.layout.list_main_row, textList, true));
    }

    private void print() {
        mProgressDialog.show();

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        Emulation emulation = ModelCapability.getEmulation(settings.getModelIndex());
        int paperSize = settings.getPaperSize();

        ILocalizeReceipts localizeReceipts = ILocalizeReceipts.createLocalizeReceipts(mLanguage, paperSize);

        BlackMarkType type;

        if (mDetectionSwitch.isChecked()) {
            type = BlackMarkType.ValidWithDetection;
        }
        else {
            type = BlackMarkType.Valid;
        }

        byte[] commands = PrinterFunctions.createTextBlackMarkData(emulation, localizeReceipts, type, false);

        Communication.sendCommands(this, commands, settings.getPortName(), settings.getPortSettings(), 10000, 30000, getActivity(), mCallback);     // 10000mS!!!
    }

    private final Communication.SendCallback mCallback = new Communication.SendCallback() {
        @Override
        public void onStatus(CommunicationResult communicationResult) {
            if (!mIsForeground) {
                return;
            }

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance("CommResultDialog");
            dialog.setTitle("Communication Result");
            dialog.setMessage(Communication.getCommunicationResultMessage(communicationResult));
            dialog.setPositiveButton("OK");
            dialog.show(getChildFragmentManager());
        }
    };
}
