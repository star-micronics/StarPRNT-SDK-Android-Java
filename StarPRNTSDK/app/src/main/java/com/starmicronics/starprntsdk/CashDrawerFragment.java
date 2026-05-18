package com.starmicronics.starprntsdk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.starmicronics.starprntsdk.Communication.CommunicationResult;

import com.starmicronics.starioextension.StarIoExt.Emulation;
import com.starmicronics.starprntsdk.functions.CashDrawerFunctions;

import static com.starmicronics.starioextension.ICommandBuilder.PeripheralChannel;

public class CashDrawerFragment extends ItemListFragment implements CommonAlertDialogFragment.Callback {
    private ProgressDialog mProgressDialog;

    private boolean mIsForeground;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Communicating...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);

        addTitle("Like a StarIO-SDK Sample");
        addMenu("Channel1 (Check condition.)");
        addMenu("Channel1 (Do not check condition.)");
        addMenu("Channel2 (Check condition.)");
        addMenu("Channel2 (Do not check condition.)");

        addTitle("StarIoExtManager Sample");
        addMenu("Channel1 (Check condition.)");
        addMenu("Channel1 (Do not check condition.)");
        addMenu("Channel2 (Check condition.)");
        addMenu("Channel2 (Do not check condition.)");
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

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);

        if (1 <= position && position <= 4) {
            PeripheralChannel channel          = position == 1 || position == 2 ? PeripheralChannel.No1 : PeripheralChannel.No2;
            boolean           doCheckCondition = position == 1 || position == 3;

            openDrawer(channel, doCheckCondition);
        }
        else if (6 <= position && position <= 9) {
            startTestDrawerActivity(position - 5);
        }
    }

    @Override
    public void onDialogResult(String tag, Intent data) {
        // do nothing
    }

    private void openDrawer(PeripheralChannel channel, boolean doCheckCondition) {
        mProgressDialog.show();

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        Emulation emulation = ModelCapability.getEmulation(settings.getModelIndex());

        byte[] data = CashDrawerFunctions.createData(emulation, channel);

        if (doCheckCondition) {
            Communication.sendCommands(this, data, settings.getPortName(), settings.getPortSettings(), 10000, 30000, getActivity(), mCallback);     // 10000mS!!!
        }
        else {
            Communication.sendCommandsDoNotCheckCondition(this, data, settings.getPortName(), settings.getPortSettings(), 10000, getActivity(), mCallback);     // 10000mS!!!
        }
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

    private void startTestDrawerActivity(int index) {
        Intent intent = new Intent(getActivity(), CommonActivity.class);

        intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_cash_drawer_ext);
        intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "CashDrawer Ext");
        intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);
        intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_RELOAD_BUTTON, true);
        intent.putExtra(CommonActivity.BUNDLE_KEY_TEST_BUTTON_TEXT, "Open");
        intent.putExtra(CommonActivity.BUNDLE_KEY_SELECTED_INDEX,   index);

        startActivity(intent);
    }
}
