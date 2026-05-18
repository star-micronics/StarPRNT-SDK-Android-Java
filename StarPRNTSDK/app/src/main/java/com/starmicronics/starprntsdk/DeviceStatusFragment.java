package com.starmicronics.starprntsdk;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.starmicronics.stario.StarPrinterStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public final class DeviceStatusFragment extends ItemListFragment implements CommonAlertDialogFragment.Callback {

    private static final String OPEN_FAILURE_DIALOG = "OpenFailureDialog";

    private ProgressDialog mProgressDialog;

    private boolean mIsForeground;

    public DeviceStatusFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mProgressDialog = new ProgressDialog(getActivity());

        mProgressDialog.setMessage("Communicating...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        for (int i = 0; i < menu.size(); ++i) {
            MenuItem menuItem = menu.getItem(i);
            if (menuItem.getItemId() == R.id.reloadIcon) {
                menuItem.setVisible(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.reloadIcon) {
            updateList();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
    public void onDialogResult(String tag, Intent data) {
        // do nothing
    }

    private void updateList() {
        retrieveStatus();
    }

    private void createStatusItems(StarPrinterStatus status) {
        adapter.clear();

        if (status == null || status.rawLength == 0) {
            return;
        }

        addTitle("Device Status");

        addItem("Online",
                status.offline ? "Offline" : "Online",
                status.offline ? Color.RED : Color.BLUE);
        addItem("Cover",
                status.coverOpen ? "Open"    : "Closed",
                status.coverOpen ? Color.RED : Color.BLUE);

        if (status.receiptPaperEmpty) {
            addItem("Paper", "Empty", Color.RED);
        }
        else if (status.receiptPaperNearEmptyInner || status.receiptPaperNearEmptyOuter) {
            addItem("Paper", "Near Empty", ContextCompat.getColor(getActivity(), R.color.orange));
        }
        else {
            addItem("Paper", "Ready", Color.BLUE);
        }

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        if (settings.getCashDrawerOpenActiveHigh()) {
            addItem("Cash Drawer",
                    status.compulsionSwitch ? "Open" : "Closed",
                    status.compulsionSwitch ? Color.RED : Color.BLUE);
        }
        else {
            addItem("Cash Drawer",
                    status.compulsionSwitch ? "Closed" : "Open",
                    status.compulsionSwitch ? Color.BLUE : Color.RED);
        }

        addItem("Head Temperature",
                status.overTemp            ? "High"     : "Normal",
                status.overTemp            ? Color.RED  : Color.BLUE);
        addItem("Non Recoverable Error",
                status.unrecoverableError  ? "Error"    : "Ready",
                status.unrecoverableError  ? Color.RED  : Color.BLUE);
        addItem("Paper Cutter",
                status.cutterError         ? "Error"    : "Ready",
                status.cutterError         ? Color.RED  : Color.BLUE);
        addItem("Head Thermistor",
                status.headThermistorError ? "Abnormal" : "Normal",
                status.headThermistorError ? Color.RED  : Color.BLUE);
        addItem("Voltage",
                status.voltageError        ? "Abnormal" : "Normal",
                status.voltageError        ? Color.RED  : Color.BLUE);

        if (status.etbAvailable) {
            addItem("ETB Counter", String.valueOf(status.etbCounter), Color.BLUE);
        }
    }

    private void createFirmwareInformationItems(Map<String, String> firmwareInformationMap) {
        if (firmwareInformationMap == null) {
            return;
        }

        addTitle("Firmware Information");

        addItem("Model Name",       firmwareInformationMap.get("ModelName"),       Color.BLUE);
        addItem("Firmware Version", firmwareInformationMap.get("FirmwareVersion"), Color.BLUE);
    }

    private void createUnableToGetFirmwareInformationItems() {
        addTitle("Firmware Information");

        addItem("Unable to get F/W info. from an error.", "", Color.RED);
    }

    public void addItem(String kindOfStatus, String status, int statusTextColor) {
        List<ImgInfo>  imgList  = new ArrayList<>();
        List<TextInfo> textList = new ArrayList<>();

        textList.add(new TextInfo(kindOfStatus, R.id.statusListItem));
        textList.add(new TextInfo(status,       R.id.statusListStatus, statusTextColor));

        adapter.add(new ItemList(R.layout.list_device_status_row, textList, imgList));
    }

    private void retrieveStatus() {
        mProgressDialog.show();

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        Communication.retrieveStatus(DeviceStatusFragment.class, settings.getPortName(), settings.getPortSettings(), 10000, getActivity(), mStatusCallback);
    }

    private final Communication.StatusCallback mStatusCallback = new Communication.StatusCallback() {
        @Override
        public void onStatus(StarPrinterStatus status) {
            if (!mIsForeground) {
                return;
            }

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            if (status == null || status.rawLength == 0) {
                adapter.clear();

                CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance(OPEN_FAILURE_DIALOG);
                dialog.setTitle("Communication Result");
                dialog.setMessage("Fail to Open Port.");
                dialog.setPositiveButton("OK");
                dialog.show(getChildFragmentManager());
            }
            else {
                createStatusItems(status);

                if (status.offline) {
                    createUnableToGetFirmwareInformationItems();
                }
                else {
                    getFirmwareInformation();
                }
            }
        }
    };

    private void getFirmwareInformation() {
        mProgressDialog.show();

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        Communication.getFirmwareInformation(DeviceStatusFragment.class, settings.getPortName(), settings.getPortSettings(), 10000, getActivity(), mFirmwareInformationCallback);
    }

    private final Communication.FirmwareInformationCallback mFirmwareInformationCallback = new Communication.FirmwareInformationCallback() {
        @Override
        public void onFirmwareInformation(Map<String, String> firmwareInformationMap) {
            if (!mIsForeground) {
                return;
            }

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            if (firmwareInformationMap == null) {
                CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance(OPEN_FAILURE_DIALOG);
                dialog.setTitle("Communication Result");
                dialog.setMessage("Fail to Open Port.");
                dialog.setPositiveButton("OK");
                dialog.show(getChildFragmentManager());
            }
            else {
                createFirmwareInformationItems(firmwareInformationMap);
            }
        }
    };
}
