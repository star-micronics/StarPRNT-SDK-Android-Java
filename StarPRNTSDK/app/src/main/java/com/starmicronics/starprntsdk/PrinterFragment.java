package com.starmicronics.starprntsdk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;

import com.starmicronics.starprntsdk.Communication.CommunicationResult;

import com.starmicronics.starprntsdk.functions.PrinterFunctions;
import com.starmicronics.starprntsdk.localizereceipts.ILocalizeReceipts;

import java.io.IOException;

import static com.starmicronics.starioextension.StarIoExt.Emulation;
import static com.starmicronics.starioextension.ICommandBuilder.BitmapConverterRotation;

public class PrinterFragment extends ItemListFragment implements CommonAlertDialogFragment.Callback {
    private static final int CAMERA_REQUEST_CODE  = 0x0001;
    private static final int LIBRARY_REQUEST_CODE = 0x0002;

    private ProgressDialog mProgressDialog;

    private int mLanguage;

    private boolean mIsForeground;

    private Bitmap mBitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressDialog = new ProgressDialog(getActivity());

        mProgressDialog.setMessage("Communicating...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);

        Intent intent = getActivity().getIntent();
        mLanguage = intent.getIntExtra(CommonActivity.BUNDLE_KEY_LANGUAGE, PrinterSettingConstant.LANGUAGE_ENGLISH);

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        int modelIndex = settings.getModelIndex();
        int paperSize  = settings.getPaperSize();

        ILocalizeReceipts localizeReceipts = ILocalizeReceipts.createLocalizeReceipts(mLanguage, paperSize);
        String languageCode      = localizeReceipts.getLanguageCode();
        String paperSizeStr      = localizeReceipts.getPaperSizeStr();
        String scalePaperSizeStr = localizeReceipts.getScalePaperSizeStr();

        if (mLanguage != PrinterSettingConstant.LANGUAGE_CJK_UNIFIED_IDEOGRAPH) {
            addTitle("Like a StarIO-SDK Sample");
            addMenu(languageCode + " " + paperSizeStr      + " Text Receipt",                ModelCapability.canPrintTextReceiptSample(modelIndex));
            addMenu(languageCode + " " + paperSizeStr      + " Text Receipt (UTF8)",         ModelCapability.canPrintUtf8EncodedText(modelIndex));
            addMenu(languageCode + " " + paperSizeStr      + " Raster Receipt",              ModelCapability.canPrintRasterReceiptSample(modelIndex));
            addMenu(languageCode + " " + scalePaperSizeStr + " Raster Receipt (Both Scale)", ModelCapability.canPrintRasterReceiptSample(modelIndex));
            addMenu(languageCode + " " + scalePaperSizeStr + " Raster Receipt (Scale)",      ModelCapability.canPrintRasterReceiptSample(modelIndex));
            addMenu(languageCode                           + " Raster Coupon");
            addMenu(languageCode                           + " Raster Coupon (Rotation90)");

            addTitle("StarIoExtManager Sample");
            addMenu(languageCode + " " + paperSizeStr      + " Text Receipt",                ModelCapability.canPrintTextReceiptSample(modelIndex));
            addMenu(languageCode + " " + paperSizeStr      + " Text Receipt (UTF8)",         ModelCapability.canPrintUtf8EncodedText(modelIndex));
            addMenu(languageCode + " " + paperSizeStr      + " Raster Receipt",              ModelCapability.canPrintRasterReceiptSample(modelIndex));
            addMenu(languageCode + " " + scalePaperSizeStr + " Raster Receipt (Both Scale)", ModelCapability.canPrintRasterReceiptSample(modelIndex));
            addMenu(languageCode + " " + scalePaperSizeStr + " Raster Receipt (Scale)",      ModelCapability.canPrintRasterReceiptSample(modelIndex));
            addMenu(languageCode                           + " Raster Coupon");
            addMenu(languageCode                           + " Raster Coupon (Rotation90)");

            addTitle("Appendix");
            addMenu(                                         " Print Photo from Library");
            addMenu(                                         " Print Photo by Camera");
        }
        else {
            addTitle("Like a StarIO-SDK Sample");
            addMenu(languageCode + " " + paperSizeStr      + " Text Receipt (UTF8)",         ModelCapability.canPrintCjk(modelIndex));

            addTitle("StarIoExtManager Sample");
            addMenu(languageCode + " " + paperSizeStr      + " Text Receipt (UTF8)",         ModelCapability.canPrintCjk(modelIndex));
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);

        if (mLanguage != PrinterSettingConstant.LANGUAGE_CJK_UNIFIED_IDEOGRAPH) {
            if (1 <= position && position <= 7) {
                print(position);
            }
            else if (9 <= position && position <= 15) {
                startTestPrintActivity(position - 8);
            }
            else if (position == 17) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");

                try {
                    startActivityForResult(intent, LIBRARY_REQUEST_CODE);
                }
                catch (ActivityNotFoundException e) {
                    // nothing;
                }
            }
            else if (position == 18) {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

                try {
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                }
                catch (ActivityNotFoundException e) {
                    // nothing;
                }
            }
        }
        else {
            if (position == 1) {
                print(position);
            }
            else if (position == 3) {
                startTestPrintActivity(position - 2);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode != Activity.RESULT_OK) {
                // Canceled
                return;
            }

            mBitmap = (Bitmap) data.getExtras().get("data");

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
                    try {
                        mBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                        print(8);
                    }
                    catch (IOException e) {
                        // Nothing
                    }
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

        switch (selectedIndex) {
            default:
            case 1:
                commands = PrinterFunctions.createTextReceiptData(emulation, localizeReceipts, false);
                break;
            case 2:
                commands = PrinterFunctions.createTextReceiptData(emulation, localizeReceipts, true);
                break;
            case 3:
                commands = PrinterFunctions.createRasterReceiptData(emulation, localizeReceipts, getResources());
                break;
            case 4:
                commands = PrinterFunctions.createScaleRasterReceiptData(emulation, localizeReceipts, getResources(), paperSize, true);
                break;
            case 5:
                commands = PrinterFunctions.createScaleRasterReceiptData(emulation, localizeReceipts, getResources(), paperSize, false);
                break;
            case 6:
                commands = PrinterFunctions.createCouponData(emulation, localizeReceipts, getResources(), paperSize, BitmapConverterRotation.Normal);
                break;
            case 7:
                commands = PrinterFunctions.createCouponData(emulation, localizeReceipts, getResources(), paperSize, BitmapConverterRotation.Right90);
                break;
            case 8:
                if (mBitmap != null) {
                    commands = PrinterFunctions.createRasterData(emulation, mBitmap, paperSize, true);
                }
                else {
                    commands = new byte[0];
                }
                break;
        }

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

    private void startTestPrintActivity(int position) {
        Intent intent = new Intent(getActivity(), CommonActivity.class);

        intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_printer_ext);
        intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "Printer Ext");
        intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);
        intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_RELOAD_BUTTON, true);
        intent.putExtra(CommonActivity.BUNDLE_KEY_TEST_BUTTON_TEXT, "Print");
        intent.putExtra(CommonActivity.BUNDLE_KEY_LANGUAGE,         mLanguage);
        intent.putExtra(CommonActivity.BUNDLE_KEY_SELECTED_INDEX,   position);

        startActivity(intent);
    }
}
