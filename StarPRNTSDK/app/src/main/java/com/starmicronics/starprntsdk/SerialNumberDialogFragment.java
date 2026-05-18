package com.starmicronics.starprntsdk;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.starmicronics.starprntsdk.Communication.CommunicationResult;
import com.starmicronics.starprntsdk.Communication.Result;

public class SerialNumberDialogFragment extends CommonAlertDialogFragment {

    private TextView mSerialNumberTextView;

    private ProgressDialog mProgressDialog;

    private boolean mIsFirst = true;
    private boolean mIsForeground;

    public static SerialNumberDialogFragment newInstance(String tag) {
        SerialNumberDialogFragment dialogFragment = new SerialNumberDialogFragment();

        Bundle args = new Bundle();
        args.putString(DIALOG_TAG, tag);
        args.putBoolean(CANCEL, false);
        args.putBoolean(CALLBACK, false);

        dialogFragment.setArguments(args);
        dialogFragment.setCancelable(false);

        dialogFragment.setPositiveButton("OK");

        return dialogFragment;
    }

    @Override
    public @NonNull
    Dialog onCreateDialog(Bundle savedInstanceState) {
        mProgressDialog = new ProgressDialog(getActivity());

        mProgressDialog.setMessage("Communicating...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View dialogView = View.inflate(getActivity(), R.layout.dialog_serial_number, null);

        builder.setTitle("Product Serial Number");

        builder.setView(dialogView);

        mSerialNumberTextView = dialogView.findViewById(R.id.serialNumberTextView);

        setupPositiveButton(builder);

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mIsFirst) {
            mProgressDialog.show();

            PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
            PrinterSettings       settings       = settingManager.getPrinterSettings();

            Communication.getSerialNumber(SerialNumberDialogFragment.class, settings.getPortName(), settings.getPortSettings(), 10000, getActivity(), mCallback);
        }
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

    final Communication.SerialNumberCallback mCallback = new Communication.SerialNumberCallback() {
        @Override
        public void onSerialNumber(CommunicationResult communicationResult, String serialNumber) {
            if (!mIsForeground) {
                return;
            }

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            mIsFirst = false;

            if (communicationResult.getResult() == Result.Success) {
                mSerialNumberTextView.setText(serialNumber);
            }
            else {
                mSerialNumberTextView.setText(Communication.getCommunicationResultMessage(communicationResult));
            }
        }
    };
}
