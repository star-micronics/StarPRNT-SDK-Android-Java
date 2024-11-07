package com.starmicronics.starprntsdk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.starmicronics.starioextension.StarIoExt.Emulation;
import com.starmicronics.starprntsdk.functions.PrinterFunctions;

import com.starmicronics.starprntsdk.Communication.CommunicationResult;

import java.util.List;

public class HoldPrintFragment extends CommonFragment implements CommonAlertDialogFragment.Callback {
    private Spinner mHoldingControlSpinner;

    private boolean[] mIsHoldArray;

    private ProgressDialog mProgressDialog;

    private Emulation mEmulation;
    private PrinterSettings mPrinterSettings;

    private boolean mIsForeground;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hold_print, container, false);

        createViews(rootView);

        mProgressDialog = new ProgressDialog(getActivity());

        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        mPrinterSettings = settingManager.getPrinterSettings();
        mEmulation = ModelCapability.getEmulation(mPrinterSettings.getModelIndex());

        return rootView;
    }

    private void createViews(View rootView) {
        mHoldingControlSpinner = rootView.findViewById(R.id.printPagesSpinner);
        ArrayAdapter<String> modeArrayAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                new String[] {
                        "Always hold",
                        "Hold before printing the last page",
                        "Do not hold"
                });

        modeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mHoldingControlSpinner.setAdapter(modeArrayAdapter);
        mHoldingControlSpinner.setSelection(0);

        Button testPrintButton = rootView.findViewById(R.id.testPrintButton);
        testPrintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (mHoldingControlSpinner.getSelectedItemPosition()) {
                    default:
                    case 0:
                        mIsHoldArray = new boolean[] { true, true, true };       // Always hold
                        break;
                    case 1:
                        mIsHoldArray = new boolean[] { false, true, false };     // Hold before printing the last page
                        break;
                    case 2:
                        mIsHoldArray = new boolean[] { false, false, false };    // Do not Hold
                        break;
                }

                print();
            }
        });
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
    public void onStop() {
        super.onStop();

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    private void print() {
        mProgressDialog.setMessage("Communicating...");
        mProgressDialog.show();

        List<byte[]> commandList = PrinterFunctions.createHoldPrintData(mEmulation, mIsHoldArray);

        Communication.sendCommandsMultiplePages(this, commandList, mPrinterSettings.getPortName(), mPrinterSettings.getPortSettings(), 10000, 30000, 10000, getActivity(), mSendCallback);
    }

    private final Communication.SendMultiplePagesCallback mSendCallback = new Communication.SendMultiplePagesCallback() {
        @Override
        public void onStart(int index) {
            if (!mIsForeground) {
                return;
            }

            if (mProgressDialog != null) {
                mProgressDialog.setMessage("Communicating...");
            }
        }

        @Override
        public void onFinish(int index) {
            if (!mIsForeground || !mIsHoldArray[index]) {
                return;
            }

            if (mProgressDialog != null) {
                mProgressDialog.setMessage("Please remove paper from printer.");
            }
        }

        @Override
        public void onAllFinish(CommunicationResult communicationResult) {
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

    @Override
    public void onDialogResult(String tag, Intent data) {
        // do nothing
    }
}
