package com.starmicronics.starprntsdk;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.starmicronics.stario.StarIOPort;
import com.starmicronics.starioextension.StarIoExt;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends ItemListFragment implements CommonAlertDialogFragment.Callback {

    private static final int PRINTER_SET_REQUEST_CODE = 1;

    private static final String PRINTER_LANG_SELECT_DIALOG           = "PrinterLanguageSelectDialog";

    private static final String BLACK_MARK_LANG_SELECT_DIALOG        = "BlackMarkLanguageSelectDialog";
    private static final String BLACK_MARK_PASTE_LANG_SELECT_DIALOG  = "BlackMarkPasteLanguageSelectDialog";

    private static final String PRINTER_LANG_SELECT_PAGE_MODE_DIALOG = "PrinterLanguageSelectPageModeDialog";

    private static final String PRINT_REDIRECTION_LANG_SELECT_DIALOG = "PrintRedirectionLanguageSelectDialog";

    private static final String MPOP_COMBINATION_LANG_SELECT_DIALOG  = "mPOPCombinationLanguageSelectDialog";

    private static final String SERIAL_NUMBER_DIALOG                 = "SerialNumberDialog";

    private static final String LICENSE_DIALOG                       = "LicenseDialog";

    private static final int BLUETOOTH_REQUEST_CODE = 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        updateList();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // If you are using Android 12 and targetSdkVersion is 31 or later,
        // you have to request Bluetooth permission (Nearby devices permission) to use the Bluetooth printer.
        // https://developer.android.com/about/versions/12/features/bluetooth-permissions
        if (Build.VERSION_CODES.S <= Build.VERSION.SDK_INT) {
            requestBluetoothPermission();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.infoIcon) {
            LicenseDialogFragment dialog = LicenseDialogFragment.newInstance(LICENSE_DIALOG);
            dialog.show(getChildFragmentManager());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PRINTER_SET_REQUEST_CODE) {
            updateList();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);

        final Intent intent = new Intent(getActivity(), CommonActivity.class);

        switch (position) {
            case 1: {   // Tapped Destination Device row
                intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_printer_search);
                intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "Search Port");
                intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);
                intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_RELOAD_BUTTON, true);
                intent.putExtra(CommonActivity.BUNDLE_KEY_PRINTER_SETTING_INDEX, 0);    // Index of the backup printer

                startActivityForResult(intent, PRINTER_SET_REQUEST_CODE);
                break;
            }
            case 3: {   // Tapped Printer row (Sample)
                LanguageSelectDialogFragment dialog = LanguageSelectDialogFragment.newInstance(PRINTER_LANG_SELECT_DIALOG);
                dialog.show(getChildFragmentManager());
                break;
            }
            case 4: {   // Tapped Printer row (Black Mark)
                EnJpLanguageSelectDialogFragment dialog = EnJpLanguageSelectDialogFragment.newInstance(BLACK_MARK_LANG_SELECT_DIALOG);
                dialog.show(getChildFragmentManager());
                break;
            }
            case 5: {   // Tapped Printer row (Black Mark Paste)
                EnJpLanguageSelectDialogFragment dialog = EnJpLanguageSelectDialogFragment.newInstance(BLACK_MARK_PASTE_LANG_SELECT_DIALOG);
                dialog.show(getChildFragmentManager());
                break;
            }
            case 6: {   // Tapped Printer row (Page Mode)
                EnJpLanguageSelectDialogFragment dialog = EnJpLanguageSelectDialogFragment.newInstance(PRINTER_LANG_SELECT_PAGE_MODE_DIALOG);
                dialog.show(getChildFragmentManager());
                break;
            }
            case 7: {   // Tapped Printer row (Presenter)
                intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_presenter);
                intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "Presenter");
                intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);

                startActivity(intent);
                break;
            }
            case 8: {   // Tapped Printer row (LED)
                intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_led);
                intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "LED");
                intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);

                startActivity(intent);
                break;
            }
            case 9: {   // Tapped Printer row (Print Re-Direction Sample)
                CombinationLanguageSelectDialogFragment dialog = CombinationLanguageSelectDialogFragment.newInstance(PRINT_REDIRECTION_LANG_SELECT_DIALOG);
                dialog.show(getChildFragmentManager());
                break;
            }
            case 10: {   // Tapped Printer row (Hold Print Sample)
                intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_hold_print);
                intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "Hold Print");
                intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);

                startActivity(intent);
                break;
            }
            case 11: {   // Tapped AutoSwitch Interface row
                final String[] items = {"StarIO Sample", "StarIoExtManager Sample"};
                new AlertDialog.Builder(getActivity())
                        .setTitle("Select Sample")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int index) {
                                switch (index) {
                                    default:
                                    case 0:
                                        intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_auto_switch_interface);
                                        intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "AutoSwitch Interface");
                                        intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);
                                        break;

                                    case 1:
                                        intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_auto_switch_interface_ext);
                                        intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "AutoSwitch Interface Ext");
                                        intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);
                                        intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_RELOAD_BUTTON, true);
                                        break;
                                }

                                startActivity(intent);

                            }
                        })
                        .show();
                break;
            }
            case 13: {   // Tapped CashDrawer row
                intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_cash_drawer);
                intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "Cash Drawer");
                intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);

                startActivity(intent);
                break;
            }
            case 14: {   // Tapped Barcode Reader row
                intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_barcode_reader_ext);
                intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "Barcode Reader Ext");
                intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);
                intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_RELOAD_BUTTON, true);

                startActivity(intent);
                break;
            }
            case 15: {   // Tapped Display row
                intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_display);
                intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "Display");
                intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);

                startActivity(intent);
                break;
            }
            case 16: {   // Tapped Melody Speaker row
                intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_melody_speaker);
                intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "Melody Speaker");
                intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);

                startActivity(intent);
                break;
            }
            case 18: {   // Tapped Combination row
                CombinationLanguageSelectDialogFragment dialog = CombinationLanguageSelectDialogFragment.newInstance(MPOP_COMBINATION_LANG_SELECT_DIALOG);
                dialog.show(getChildFragmentManager());
                break;
            }
            case 20: {   // Tapped API row
                intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_api);
                intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "API");
                intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);

                startActivity(intent);
                break;
            }
            case 22: {   // Tapped Device Status row (Device Status/Firmware Information)
                intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_device_status);
                intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "Device Status");
                intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);
                intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_RELOAD_BUTTON, true);

                startActivity(intent);
                break;
            }
            case 23: {   // Tapped Device Status row (Product Serial Number)
                SerialNumberDialogFragment dialog = SerialNumberDialogFragment.newInstance(SERIAL_NUMBER_DIALOG);
                dialog.show(getChildFragmentManager());
                break;
            }
            case 25: {   // Tapped Bluetooth Setting row
                intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_bluetooth_setting);
                intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "Bluetooth Setting");
                intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);
                intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_RELOAD_BUTTON, true);

                startActivity(intent);
                break;
            }
            case 26: {   // Tapped USB Serial Number row
                intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_usb_serial_number_setting);
                intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "USB Serial Number");
                intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);

                startActivity(intent);
                break;
            }
            case 28: {   // Tapped Library Version row
                CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance("");
                dialog.setTitle("Library Version");
                dialog.setMessage(
                        "StarIOPort3.1 version " + StarIOPort.getStarIOVersion() + "\n" +
                        StarIoExt.getDescription());
                dialog.setPositiveButton("OK");
                dialog.show(getChildFragmentManager());
                break;
            }
        }
    }

    @Override
    public void onDialogResult(String tag, Intent data) {
        boolean isCanceled = data.hasExtra(CommonAlertDialogFragment.LABEL_NEGATIVE);

        if (isCanceled) return;

        switch (tag) {
            case PRINTER_LANG_SELECT_DIALOG: {
                int language = data.getIntExtra(CommonActivity.BUNDLE_KEY_LANGUAGE, PrinterSettingConstant.LANGUAGE_ENGLISH);

                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_printer);
                intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "Printer");
                intent.putExtra(CommonActivity.BUNDLE_KEY_LANGUAGE, language);
                intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);

                startActivity(intent);

                break;
            }
            case BLACK_MARK_LANG_SELECT_DIALOG: {
                int language = data.getIntExtra(CommonActivity.BUNDLE_KEY_LANGUAGE, PrinterSettingConstant.LANGUAGE_ENGLISH);

                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_blackmark);
                intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "Black Mark");
                intent.putExtra(CommonActivity.BUNDLE_KEY_LANGUAGE, language);
                intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);

                startActivity(intent);

                break;
            }
            case BLACK_MARK_PASTE_LANG_SELECT_DIALOG: {
                int language = data.getIntExtra(CommonActivity.BUNDLE_KEY_LANGUAGE, PrinterSettingConstant.LANGUAGE_ENGLISH);

                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_blackmark_paste);
                intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "Black Mark Paste");
                intent.putExtra(CommonActivity.BUNDLE_KEY_LANGUAGE, language);
                intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);

                startActivity(intent);

                break;
            }
            case PRINTER_LANG_SELECT_PAGE_MODE_DIALOG: {
                int language = data.getIntExtra(CommonActivity.BUNDLE_KEY_LANGUAGE, PrinterSettingConstant.LANGUAGE_ENGLISH);

                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_page_mode);
                intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "Page Mode");
                intent.putExtra(CommonActivity.BUNDLE_KEY_LANGUAGE, language);
                intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);

                startActivity(intent);

                break;
            }
            case PRINT_REDIRECTION_LANG_SELECT_DIALOG: {
                int language = data.getIntExtra(CommonActivity.BUNDLE_KEY_LANGUAGE, PrinterSettingConstant.LANGUAGE_ENGLISH);

                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_print_redirection);
                intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "Print Re-Direction");
                intent.putExtra(CommonActivity.BUNDLE_KEY_LANGUAGE, language);
                intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);

                startActivity(intent);

                break;
            }
            case MPOP_COMBINATION_LANG_SELECT_DIALOG: {
                int language = data.getIntExtra(CommonActivity.BUNDLE_KEY_LANGUAGE, PrinterSettingConstant.LANGUAGE_ENGLISH);

                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_combination);
                intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "Combination");
                intent.putExtra(CommonActivity.BUNDLE_KEY_LANGUAGE, language);
                intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);

                startActivity(intent);

                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == BLUETOOTH_REQUEST_CODE) {
            if (grantResults.length == 2 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Bluetooth permissions are granted.
            } else {
                String text = "You have to allow \"Nearby devices\" to use the Bluetooth printer";
                Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
            }
        }
    }

    @RequiresApi(31)
    private void requestBluetoothPermission() {
        if (getContext().checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED ||
                getContext().checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.BLUETOOTH_CONNECT,
                            Manifest.permission.BLUETOOTH_SCAN,
                    },
                    BLUETOOTH_REQUEST_CODE
            );
        }
    }

    private void updateList() {
        adapter.clear();

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        boolean isDeviceSelected     = false;
        int     modelIndex           = ModelCapability.NONE;
        String  modelName            = "";
        boolean isBluetoothInterface = false;
        boolean isUsbInterface       = false;

        if (settings != null) {
            isDeviceSelected     = true;
            modelIndex           = settings.getModelIndex();
            modelName            = settings.getModelName();
            isBluetoothInterface = settings.getPortName().toUpperCase().startsWith("BT:");
            isUsbInterface       = settings.getPortName().toUpperCase().startsWith("USB:");
        }

        addTitle("Destination Device");
        addPrinterInfo(settingManager.getPrinterSettingsList());

        addTitle("Printer");
        addMenu("Sample",                     isDeviceSelected);
        addMenu("Black Mark Sample",          isDeviceSelected && ModelCapability.canUseBlackMark(modelIndex));
        addMenu("Black Mark Sample (Paste)",  isDeviceSelected && ModelCapability.canUseBlackMark(modelIndex));
        addMenu("Page Mode Sample",           isDeviceSelected && ModelCapability.canUsePageMode(modelIndex));
        addMenu("Presenter Sample",           isDeviceSelected && ModelCapability.canUsePresenter(modelIndex));
        addMenu("LED Sample",                 isDeviceSelected && ModelCapability.canUseLed(modelIndex));
        addMenu("Print Re-Direction Sample",  isDeviceSelected);
        addMenu("Hold Print Sample",          isDeviceSelected && ModelCapability.canUsePaperPresentStatus(modelIndex));
        addMenu("AutoSwitch Interface Sample",      isDeviceSelected && ModelCapability.canUseAutoSwitchInterface(modelIndex));

        addTitle("Peripheral");
        addMenu("Cash Drawer Sample",         isDeviceSelected && ModelCapability.canUseCashDrawer(modelIndex));
        addMenu("Barcode Reader Sample",      isDeviceSelected && ModelCapability.canUseBarcodeReader(modelIndex));
        addMenu("Display Sample",             isDeviceSelected && ModelCapability.canUseCustomerDisplay(modelIndex, modelName));
        addMenu("Melody Speaker Sample",      isDeviceSelected && ModelCapability.canUseMelodySpeaker(modelIndex));

        addTitle("Combination");
        addMenu("StarIoExtManager Sample",    isDeviceSelected && ModelCapability.canUseBarcodeReader(modelIndex));

        addTitle("API");
        addMenu("Sample",                     isDeviceSelected);

        addTitle("Device Status");
        addMenu("Sample",                     isDeviceSelected);
        addMenu("Product Serial Number",      isDeviceSelected && ModelCapability.canGetProductSerialNumber(modelIndex, modelName, isBluetoothInterface));

        addTitle("Interface");
        addMenu("Bluetooth Setting",          isDeviceSelected && isBluetoothInterface);
        addMenu("USB Serial Number",          isDeviceSelected && ModelCapability.settableUsbSerialNumberLength(modelIndex, modelName, isUsbInterface) != 0);

        addTitle("Appendix");
        addMenu("Library Version");
    }

    private void addPrinterInfo(List<PrinterSettings> settingsList) {
        if (settingsList.size() == 0) {
            List<TextInfo> mainTextList = new ArrayList<>();

            mainTextList.add(new TextInfo("Unselected State", R.id.menuTextView, R.anim.blink, Color.RED));

            adapter.add(new ItemList(R.layout.list_main_row, mainTextList, ContextCompat.getColor(getActivity(), R.color.lightskyblue), true));
        }
        else {
            List<TextInfo> textList = new ArrayList<>();

            // Get a port name, MAC address and model name of the destination printer.
            String portName   = settingsList.get(0).getPortName();
            String macAddress = settingsList.get(0).getMacAddress();
            String modelName  = settingsList.get(0).getModelName();

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
}
