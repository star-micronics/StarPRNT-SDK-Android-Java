package com.starmicronics.starprntsdk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import android.view.View;
import android.widget.AdapterView;

import com.starmicronics.starprntsdk.Communication.CommunicationResult;

import com.starmicronics.starprntsdk.functions.PrinterFunctions;
import com.starmicronics.starprntsdk.localizereceipts.ILocalizeReceipts;

import java.util.ArrayList;
import java.util.List;

import static com.starmicronics.starioextension.StarIoExt.Emulation;
import static com.starmicronics.starioextension.ICommandBuilder.BitmapConverterRotation;

public class PrintRedirectionFragment extends ItemListFragment implements CommonAlertDialogFragment.Callback {

    private static final int PRINTER_SET_REQUEST_CODE = 1;

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
        mLanguage = intent.getIntExtra(CommonActivity.BUNDLE_KEY_LANGUAGE, PrinterSettingConstant.LANGUAGE_ENGLISH);

        updateList();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PRINTER_SET_REQUEST_CODE) {
            updateList();
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

        if (position == 1) {
            Intent intent = new Intent(getActivity(), CommonActivity.class);
            intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_printer_search);
            intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "Search Port");
            intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);
            intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_RELOAD_BUTTON, true);
            intent.putExtra(CommonActivity.BUNDLE_KEY_PRINTER_SETTING_INDEX, 1);    // Index of the backup printer

            startActivityForResult(intent, PRINTER_SET_REQUEST_CODE);
        }
        else if (3 <= position && position <= 9) {
            print(position);
        }
    }

    @Override
    public void onDialogResult(String tag, Intent data) {
        // do nothing
    }

    private void updateList() {
        adapter.clear();

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        boolean isDeviceSelected     = 1 < settingManager.getPrinterSettingsList().size();
        int     modelIndex           = settings.getModelIndex();
        int     paperSize            = settings.getPaperSize();

        ILocalizeReceipts localizeReceipts = ILocalizeReceipts.createLocalizeReceipts(mLanguage, paperSize);
        String languageCode      = localizeReceipts.getLanguageCode();
        String paperSizeStr      = localizeReceipts.getPaperSizeStr();
        String scalePaperSizeStr = localizeReceipts.getScalePaperSizeStr();

        addTitle("Backup Device");
        addPrinterInfo(settingManager.getPrinterSettingsList());

        addTitle("Sample");
        addMenu(languageCode + " " + paperSizeStr      + " Text Receipt",                isDeviceSelected && ModelCapability.canPrintTextReceiptSample(modelIndex));
        addMenu(languageCode + " " + paperSizeStr      + " Text Receipt (UTF8)",         isDeviceSelected && ModelCapability.canPrintUtf8EncodedText(modelIndex));
        addMenu(languageCode + " " + paperSizeStr      + " Raster Receipt",              isDeviceSelected && ModelCapability.canPrintRasterReceiptSample(modelIndex));
        addMenu(languageCode + " " + scalePaperSizeStr + " Raster Receipt (Both Scale)", isDeviceSelected && ModelCapability.canPrintRasterReceiptSample(modelIndex));
        addMenu(languageCode + " " + scalePaperSizeStr + " Raster Receipt (Scale)",      isDeviceSelected && ModelCapability.canPrintRasterReceiptSample(modelIndex));
        addMenu(languageCode                           + " Raster Coupon",               isDeviceSelected);
        addMenu(languageCode                           + " Raster Coupon (Rotation90)",  isDeviceSelected);
    }

    private void addPrinterInfo(List<PrinterSettings> settingsList) {
        if (settingsList.size() <= 1) {
            List<TextInfo> mainTextList = new ArrayList<>();

            mainTextList.add(new TextInfo("Unselected State", R.id.menuTextView, R.anim.blink, Color.RED));

            adapter.add(new ItemList(R.layout.list_main_row, mainTextList, ContextCompat.getColor(getActivity(), R.color.lightskyblue), true));
        }
        else {
            List<TextInfo> textList = new ArrayList<>();

            // Get a port name, MAC address and model name of the backup printer.
            String portName   = settingsList.get(1).getPortName();
            String macAddress = settingsList.get(1).getMacAddress();
            String modelName  = settingsList.get(1).getModelName();

            if (portName.startsWith(PrinterSettingConstant.IF_TYPE_ETHERNET) ||
                portName.startsWith(PrinterSettingConstant.IF_TYPE_BLUETOOTH)) {
                textList.add(new TextInfo(modelName, R.id.deviceTextView));
                if (macAddress.isEmpty()) {
                    textList.add(new TextInfo(portName,                           R.id.deviceDetailTextView));
                }
                else {
                    textList.add(new TextInfo(portName + " (" + macAddress + ")", R.id.deviceDetailTextView));
                }
            }
            else {  // USB interface
                textList.add(new TextInfo(modelName, R.id.deviceTextView));
                textList.add(new TextInfo(portName,  R.id.deviceDetailTextView));
            }

            adapter.add(new ItemList(R.layout.list_destination_device_row, textList, ContextCompat.getColor(getActivity(), R.color.lightskyblue), true));
        }
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
            case 3:
                commands = PrinterFunctions.createTextReceiptData(emulation, localizeReceipts, false);
                break;
            case 4:
                commands = PrinterFunctions.createTextReceiptData(emulation, localizeReceipts, true);
                break;
            case 5:
                commands = PrinterFunctions.createRasterReceiptData(emulation, localizeReceipts, getResources());
                break;
            case 6:
                commands = PrinterFunctions.createScaleRasterReceiptData(emulation, localizeReceipts, getResources(), paperSize, true);
                break;
            case 7:
                commands = PrinterFunctions.createScaleRasterReceiptData(emulation, localizeReceipts, getResources(), paperSize, false);
                break;
            case 8:
                commands = PrinterFunctions.createCouponData(emulation, localizeReceipts, getResources(), paperSize, BitmapConverterRotation.Normal);
                break;
            case 9:
                commands = PrinterFunctions.createCouponData(emulation, localizeReceipts, getResources(), paperSize, BitmapConverterRotation.Right90);
                break;
        }

        PrinterSettingManager printerSettingManager = new PrinterSettingManager(getActivity());
        List<PrinterSettings> printerSettingsList = printerSettingManager.getPrinterSettingsList();

        Communication.sendCommandsForRedirection(this, commands, printerSettingsList, 10000, 30000, getActivity(), mCallback);     // 10000mS!!!
    }

    private final Communication.PrintRedirectionCallback mCallback = new Communication.PrintRedirectionCallback() {
        @Override
        public void onStatus(List<Pair<String, CommunicationResult>> communicationResultList) {
            if (!mIsForeground) {
                return;
            }

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            String msg = "";

            for (int i = 0; i < communicationResultList.size(); i++) {
                Pair<String, CommunicationResult> pair = communicationResultList.get(i);

                boolean isMainPrinter = i == 0;

                if (isMainPrinter) {
                    msg += "[Destination]\n";
                }
                else {
                    msg += "[Backup]\n";
                }

                msg += ("Port Name: " + pair.first + "\n");

                switch (pair.second.getResult()) {
                    case Success:
                        msg += "----> Success!\n\n";
                        break;
                    case ErrorOpenPort:
                        msg += "----> Fail to openPort\n\n";
                        break;
                    case ErrorBeginCheckedBlock:
                        msg += "----> Printer is offline (beginCheckedBlock)\n\n";
                        break;
                    case ErrorEndCheckedBlock:
                        msg += "----> Printer is offline (endCheckedBlock)\n\n";
                        break;
                    case ErrorReadPort:
                        msg += "----> Read port error (readPort)\n\n";
                        break;
                    case ErrorWritePort:
                        msg += "----> Write port error (writePort)\n\n";
                        break;
                    default:
                        msg += "----> Unknown error\n\n";
                        break;
                }
            }

            CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance("CommResultDialog");
            dialog.setTitle("Communication Result");
            dialog.setMessage(msg);
            dialog.setPositiveButton("OK");
            dialog.show(getChildFragmentManager());
        }
    };
}
