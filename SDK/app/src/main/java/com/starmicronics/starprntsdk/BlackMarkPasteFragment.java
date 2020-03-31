package com.starmicronics.starprntsdk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.starmicronics.starprntsdk.Communication.CommunicationResult;

import com.starmicronics.starprntsdk.functions.PrinterFunctions;
import com.starmicronics.starprntsdk.localizereceipts.ILocalizeReceipts;

import static com.starmicronics.starioextension.StarIoExt.Emulation;
import static com.starmicronics.starioextension.ICommandBuilder.BlackMarkType;

public class BlackMarkPasteFragment extends Fragment implements CommonAlertDialogFragment.Callback {

    private ProgressDialog mProgressDialog;

    private int mLanguage;

    private boolean mIsForeground;

    private EditText mEditTextView;
    private Switch   mDoubleHeightSwitch;
    private Switch   mDetectionSwitch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressDialog = new ProgressDialog(getActivity());

        mProgressDialog.setMessage("Communicating...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);

        Intent intent = getActivity().getIntent();
        mLanguage = intent.getIntExtra(CommonActivity.BUNDLE_KEY_LANGUAGE, PrinterSettingConstant.LANGUAGE_ENGLISH);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_black_mark_paste, container, false);

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        int paperSize = settings.getPaperSize();

        ILocalizeReceipts localizeReceipts = ILocalizeReceipts.createLocalizeReceipts(mLanguage, paperSize);

        mEditTextView = rootView.findViewById(R.id.blackMarkEditTextView);

        mEditTextView.setText(localizeReceipts.createPasteTextLabelString());

        mDoubleHeightSwitch = rootView.findViewById(R.id.blackMarkHeightX2Switch);

        mDetectionSwitch = rootView.findViewById(R.id.blackMarkDetectionSwitch);

        int modelIndex = settings.getModelIndex();

        if (ModelCapability.canUseBlackMarkDetection(modelIndex)) {
            mDetectionSwitch.setEnabled(true);
            mDetectionSwitch.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
        }
        else {
            mDetectionSwitch.setEnabled(false);
            mDetectionSwitch.setTextColor(ContextCompat.getColor(getActivity(), R.color.lightgrey));
        }

        Button clearButton = rootView.findViewById(R.id.blackMarkPasteClearButton);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditTextView.setText("");
            }
        });

        Button printButton = rootView.findViewById(R.id.blackMarkPastePrintButton);

        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print();
            }
        });

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
    public void onDialogResult(String tag, Intent data) {
        // do nothing
    }

    private void print() {
        mProgressDialog.show();

        String  pasteText      = mEditTextView.getText().toString();
        boolean isDoubleHeight = mDoubleHeightSwitch.isChecked();
        boolean isDetection    = mDetectionSwitch.isChecked();

        byte[] commands;

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        Emulation emulation = ModelCapability.getEmulation(settings.getModelIndex());
        int paperSize = settings.getPaperSize();

        ILocalizeReceipts localizeReceipts = ILocalizeReceipts.createLocalizeReceipts(mLanguage, paperSize);

        BlackMarkType type;

        if (isDetection) {
            type = BlackMarkType.ValidWithDetection;
        }
        else {
            type = BlackMarkType.Valid;
        }

        commands = PrinterFunctions.createPasteTextBlackMarkData(emulation, localizeReceipts, pasteText, isDoubleHeight, type, false);

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
