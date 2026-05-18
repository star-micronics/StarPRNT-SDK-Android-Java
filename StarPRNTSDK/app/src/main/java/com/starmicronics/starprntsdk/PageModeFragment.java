package com.starmicronics.starprntsdk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.starmicronics.starprntsdk.functions.PrinterFunctions;
import com.starmicronics.starprntsdk.localizereceipts.ILocalizeReceipts;

import com.starmicronics.starprntsdk.Communication.CommunicationResult;

import static com.starmicronics.starioextension.ICommandBuilder.BitmapConverterRotation;
import static com.starmicronics.starioextension.StarIoExt.Emulation;

public class PageModeFragment extends ItemListFragment implements CommonAlertDialogFragment.Callback {
    private static final int CAMERA_REQUEST_CODE  = 0x0001;
    private static final int LIBRARY_REQUEST_CODE = 0x0002;

    private ProgressDialog mProgressDialog;

    private int mLanguage;

    private boolean mIsForeground;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressDialog = new ProgressDialog(getActivity());

        mProgressDialog.setMessage("Communicating...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);

        Intent intent = getActivity().getIntent();
        mLanguage  = intent.getIntExtra(CommonActivity.BUNDLE_KEY_LANGUAGE,   PrinterSettingConstant.LANGUAGE_ENGLISH);

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        Emulation emulation = ModelCapability.getEmulation(settings.getModelIndex());
        int paperSize = settings.getPaperSize();

        ILocalizeReceipts localizeReceipts = ILocalizeReceipts.createLocalizeReceipts(mLanguage, paperSize);
        String languageCode      = localizeReceipts.getLanguageCode();
        String paperSizeStr      = localizeReceipts.getPaperSizeStr();

        boolean canPrintTextReceipt     = emulation != Emulation.StarGraphic;

        addTitle("Like a StarIO-SDK Sample");
        addMenu(languageCode + " " + paperSizeStr      + " Text Label (Rotation0)",    canPrintTextReceipt);
        addMenu(languageCode + " " + paperSizeStr      + " Text Label (Rotation90)",   canPrintTextReceipt);
        addMenu(languageCode + " " + paperSizeStr      + " Text Label (Rotation180)",  canPrintTextReceipt);
        addMenu(languageCode + " " + paperSizeStr      + " Text Label (Rotation270)",  canPrintTextReceipt);
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

        print(position);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode != Activity.RESULT_OK) {
                // Canceled
                return;
            }

            print(8);
        }
        else if (requestCode == LIBRARY_REQUEST_CODE) {
            if (resultCode != Activity.RESULT_OK) {
                // Canceled
                return;
            }

            if (data != null) {
                Uri uri = data.getData();

                if (uri != null) {
                    print(8);
                }
            }
        }
    }

    @Override
    public void onDialogResult(String tag, Intent data) {
        // do nothing
    }

    private void print(int selectedIndex) {
        mProgressDialog.show();

        byte[] commands;

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        Emulation emulation = ModelCapability.getEmulation(settings.getModelIndex());
        int paperSize = settings.getPaperSize();

        ILocalizeReceipts localizeReceipts = ILocalizeReceipts.createLocalizeReceipts(mLanguage, paperSize);

        Rect                    rect;
        BitmapConverterRotation rotation;

        int width  = paperSize;
        int height = 30 * 8;        // 30mm

        switch (selectedIndex) {
            default:
            case 1:
                rect     = new Rect(0, 0, width, height);
                rotation = BitmapConverterRotation.Normal;
                break;
            case 2:
////            rect     = new Rect(0, 0, width, height);
//              rect     = new Rect(0, 0, height, width);
                //noinspection SuspiciousNameCombination
                rect     = new Rect(0, 0, width, width);
                rotation = BitmapConverterRotation.Right90;
                break;
            case 3:
                rect     = new Rect(0, 0, width, height);
                rotation = BitmapConverterRotation.Rotate180;
                break;
            case 4:
//              rect     = new Rect(0, 0, width, height);
                //noinspection SuspiciousNameCombination
                rect     = new Rect(0, 0, height, width);
                rotation = BitmapConverterRotation.Left90;
                break;
        }

        commands = PrinterFunctions.createTextPageModeData(emulation, localizeReceipts, rect, rotation, false);

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
