package com.starmicronics.starprntsdk;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.starmicronics.starioextension.StarIoExt;
import com.starmicronics.starprntsdk.functions.PrinterFunctions;
import com.starmicronics.starprntsdk.localizereceipts.ILocalizeReceipts;

public class AutoSwitchInterfaceFragment extends CommonFragment implements CommonAlertDialogFragment.Callback {

    private ProgressDialog mProgressDialog;

    private boolean mIsForeground;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_auto_switch_interface, container, false);

        Button testPrintButton = rootView.findViewById(R.id.testPrintButton);
        testPrintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                print();
            }
        });

        mProgressDialog = new ProgressDialog(getActivity());

        mProgressDialog.setMessage("Communicating...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);

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

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    private void print() {
        mProgressDialog.show();

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        StarIoExt.Emulation emulation = ModelCapability.getEmulation(settings.getModelIndex());
        int paperSize = settings.getPaperSize();

        ILocalizeReceipts localizeReceipts = ILocalizeReceipts.createLocalizeReceipts(PrinterSettingConstant.LANGUAGE_ENGLISH, paperSize);

        byte[] commands = PrinterFunctions.createRasterReceiptData(emulation, localizeReceipts, getResources());

        Communication.sendCommandsWithAutoInterfaceSelect(this, commands, settings.getPortSettings(), 10000, 30000, getActivity(), mCallback);     // 10000mS!!!
    }

    private final Communication.AutoInterfaceSelectCallback mCallback = new Communication.AutoInterfaceSelectCallback() {
        @Override
        public void onStatus(String portName, Communication.CommunicationResult communicationResult) {
            if (!mIsForeground) {
                return;
            }

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance("CommResultDialog");
            dialog.setTitle("Communication Result");
            dialog.setMessage(Communication.getCommunicationResultMessage(communicationResult) + "\nPort Name: " + portName);
            dialog.setPositiveButton("OK");
            dialog.show(getChildFragmentManager());
        }
    };

    @Override
    public void onDialogResult(String tag, Intent data) {
        // do nothing
    }
}
