package com.starmicronics.starprntsdk;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.starmicronics.stario.StarBluetoothManager;
import com.starmicronics.stario.StarIOPortException;
import com.starmicronics.starioextension.StarBluetoothManagerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BluetoothSettingFragment extends Fragment implements CommonAlertDialogFragment.Callback {

    private enum FwInfoSMSorTSeries {
        V4orEarlier,
        V5orLater
    }

    private static final String SECURITY_SETTING_DIALOG                 = "SecuritySettingDialog";
    private static final String NOT_SUPPORTED_DIALOG                    = "NotSupportedDialog";
    private static final String FAIL_TO_OPEN_PORT_DIALOG                = "FailToOpenPortDialog";
    private static final String FAIL_TO_CREATE_BLUETOOTH_MANAGER_DIALOG = "FailToCreateBluetoothManagerDialog";
    private static final String AUTO_CONNECTION_AND_PIN_DIALOG          = "AutoConnectionAndPinDialog";
    private static final String COMPLETE_TO_APPLY_DIALOG                = "CompleteToApplyDialog";
    private static final String STAR_IO_EXCEPTION_DIALOG                = "StarIoExceptionDialog";

    private StarBluetoothManager mBluetoothManager;

    private EditText mDeviceNameEditText;
    private EditText miOSPortNameEditText;
    private Switch   mAutoConnectSwitch;
    private TextView mSecurityValueTextView;
    private Switch   mChangePinCodeSwitch;
    private EditText mNewPinCodeEditText;
    private Button   mApplyButton;

    private String                                     mDeviceName;
    private String                                     miOSPortName;
    private boolean                                    mAutoConnect;
    private StarBluetoothManager.StarBluetoothSecurity mSecurity;
    private String                                     mNewPinCode;

    private boolean mIsSMSorTSeries = false;
    private FwInfoSMSorTSeries mFwInfoSMSorTSeries = FwInfoSMSorTSeries.V4orEarlier;
    private boolean mIsSMLSeries = false ;

    private Button mGrayOutView;

    private ProgressDialog mProgressDialog;

    private boolean mIsForeground;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        try {
            mBluetoothManager = StarBluetoothManagerFactory.getManager(
                    settings.getPortName(),
                    settings.getPortSettings(),
                    10000,
                    ModelCapability.getEmulation(settings.getModelIndex())
            );
        }
        catch (StarIOPortException e) {
            CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance(FAIL_TO_CREATE_BLUETOOTH_MANAGER_DIALOG);
            dialog.setTitle("Error");
            dialog.setMessage(e.toString());
            dialog.setPositiveButton("OK");
            dialog.setCancelable(false);
            dialog.show(getChildFragmentManager());
        }

        mProgressDialog = new ProgressDialog(getActivity());

        mProgressDialog.setMessage("Communicating...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bluetooth_setting, container, false);

        mApplyButton = rootView.findViewById(R.id.bluetoothSettingApplyButton);

        mDeviceNameEditText = rootView.findViewById(R.id.bluetoothSettingDeviceNameEditText);

        mDeviceNameEditText.setFilters(getDeviceNameAndiOSPortNameFilters());

        mDeviceNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                mDeviceName = s.toString();

                mApplyButton.setEnabled(validateStringSettings());
            }
        });

        mDeviceNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });

        miOSPortNameEditText = rootView.findViewById(R.id.bluetoothSettingiOSPortNameEditText);

        miOSPortNameEditText.setFilters(getDeviceNameAndiOSPortNameFilters());

        miOSPortNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                miOSPortName = s.toString();

                mApplyButton.setEnabled(validateStringSettings());
            }
        });

        miOSPortNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });

        mAutoConnectSwitch = rootView.findViewById(R.id.bluetoothSettingAutoConnectSwitch);

        mAutoConnectSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mAutoConnect = isChecked;

                mApplyButton.setEnabled(validateStringSettings());
            }
        });

        mSecurityValueTextView = rootView.findViewById(R.id.bluetoothSettingSecurityValueTextView);

        mSecurityValueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String>securityList = new ArrayList<>();

                if (mBluetoothManager.getDeviceType() == StarBluetoothManager.StarDeviceType.StarDeviceTypeDesktopPrinter) {
                    securityList.add("SSP");
                    securityList.add("PIN Code");
                }
                else {  // StarDeviceTypePortablePrinter
                    securityList.add("SSP Just Works");
                    securityList.add("SSP Numeric Comparison");
                    securityList.add("PIN Code");
                    //securityList.add("Disable"); // StarBluetoothSecurity.DISABLE is deprecated.
                }

                BluetoothSettingSecuritySelectDialogFragment dialog =
                        BluetoothSettingSecuritySelectDialogFragment.newInstance(SECURITY_SETTING_DIALOG, securityList);
                dialog.show(getChildFragmentManager());
            }
        });

        mChangePinCodeSwitch = rootView.findViewById(R.id.bluetoothSettingChangePinCodeSwitch);

        mChangePinCodeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mNewPinCodeEditText.setEnabled(isChecked);

                if (!isChecked) {
                    mNewPinCodeEditText.setText("");
                }

                mApplyButton.setEnabled(validateStringSettings());
            }
        });

        mNewPinCodeEditText = rootView.findViewById(R.id.bluetoothSettingNewPinCodeEditText);

        mNewPinCodeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                mNewPinCode = s.toString();

                mApplyButton.setEnabled(validateStringSettings());
            }
        });

        mApplyButton = rootView.findViewById(R.id.bluetoothSettingApplyButton);

        mApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmSettings();
            }
        });

        mGrayOutView = rootView.findViewById(R.id.grayoutView);

        // Check firmware version before loading bluetooth settings.
        FirmwareVersionCheckTask task = new FirmwareVersionCheckTask();
        task.execute();

        return rootView;
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
            LoadSettingsTask task = new LoadSettingsTask();
            task.execute();
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
        if (!mIsForeground) {
            return;
        }

        boolean isPressedNegative = data.hasExtra(CommonAlertDialogFragment.LABEL_NEGATIVE);

        switch (tag) {
            case NOT_SUPPORTED_DIALOG:
            case FAIL_TO_OPEN_PORT_DIALOG:
            case FAIL_TO_CREATE_BLUETOOTH_MANAGER_DIALOG:
            case COMPLETE_TO_APPLY_DIALOG:
                getActivity().finish();
                break;
            case SECURITY_SETTING_DIALOG:
                if (isPressedNegative) {
                    return;
                }

                String securityString = data.getStringExtra(CommonActivity.BUNDLE_KEY_BLUETOOTH_SETTING_SECURITY);

                mSecurityValueTextView.setText(securityString);

                if (securityString.equals("SSP") || securityString.equals("SSP Just Works")) {
                    mSecurity = StarBluetoothManager.StarBluetoothSecurity.SSP;
                }
                else if (securityString.equals("SSP Numeric Comparison")) {
                    mSecurity = StarBluetoothManager.StarBluetoothSecurity.SSP_NUMERIC_COMPARISON;
                }
                else if (securityString.equals("PIN Code")) {
                    mSecurity = StarBluetoothManager.StarBluetoothSecurity.PINCODE;
                }
                else {  // Disable
                    mSecurity = StarBluetoothManager.StarBluetoothSecurity.DISABLE;
                }

                break;
            default:
                break;
        }
    }

    private class FirmwareVersionCheckTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            grayOut(true);

            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
            PrinterSettings       settings       = settingManager.getPrinterSettings();

            Communication.getFirmwareInformation(BluetoothSettingFragment.class, settings.getPortName(), settings.getPortSettings(), 10000, getActivity(), mCallback);

            return null;
        }

        private Communication.FirmwareInformationCallback mCallback = new Communication.FirmwareInformationCallback() {
            @Override
            public void onFirmwareInformation(Map<String, String> firmwareInformationMap) {
                if (!mIsForeground) {
                    return;
                }

                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }

                if (firmwareInformationMap != null) {
                    String modelName       = firmwareInformationMap.get("ModelName");
                    String firmwareVersion = firmwareInformationMap.get("FirmwareVersion");

                    // Bluetooth setting feature is supported from Ver3.0 or later (SM-S210i, SM-S220i, SM-T300i/T300 and SM-T400i).
                    // Other models support from Ver1.0.
                    if (modelName.startsWith("SM-S21") || modelName.startsWith("SM-S22") || modelName.startsWith("SM-T30") || modelName.startsWith("SM-T40")) {

                        mIsSMSorTSeries = true;

                        if (Float.valueOf(firmwareVersion) < 3.0) {
                            CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance(NOT_SUPPORTED_DIALOG);
                            dialog.setTitle("Communication Result");
                            dialog.setMessage("This firmware version (" + firmwareVersion + ") of the device does not support bluetooth setting feature.");
                            dialog.setPositiveButton("OK");
                            dialog.setCancelable(false);
                            dialog.show(getChildFragmentManager());
                            return;
                        }

                        if (Float.valueOf(firmwareVersion) < 5.0) {
                            mFwInfoSMSorTSeries = FwInfoSMSorTSeries.V4orEarlier;
                        }
                        else {
                            mFwInfoSMSorTSeries = FwInfoSMSorTSeries.V5orLater;
                        }
                    }

                    grayOut(false);

                    mIsSMLSeries = modelName.startsWith("SM-L");

                    if (mIsSMLSeries) {
                        mNewPinCodeEditText.setFilters(getSMLSeriesPinCodeFilters());
                    }
                    else {
                        mNewPinCodeEditText.setFilters(getPinCodeFilters());
                    }

                    LoadSettingsTask task = new LoadSettingsTask();
                    task.execute();
                }
                else {
                    CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance(FAIL_TO_OPEN_PORT_DIALOG);
                    dialog.setTitle("Communication Result");
                    dialog.setMessage("Fail to Open Port.");
                    dialog.setPositiveButton("OK");
                    dialog.setCancelable(false);
                    dialog.show(getChildFragmentManager());
                }
            }
        };
    }

    private class LoadSettingsTask extends AsyncTask<Void, Void, Boolean> {
        private String mErrorMessage;

        @Override
        protected void onPreExecute() {
            grayOut(true);

            mProgressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean isSuccess = true;

            try {
                mErrorMessage = "Fail to Open Port.";

                mBluetoothManager.open();

                mErrorMessage = "Fail to load setting.";

                mBluetoothManager.loadSetting();
            }
            catch (StarIOPortException e) {
                isSuccess = false;
            }
            finally {
                if (mBluetoothManager.isOpened()) {
                    try {
                        mBluetoothManager.close();
                    }
                    catch (StarIOPortException e) {
                        isSuccess = false;
                    }
                }
            }

            return isSuccess;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            if (!mIsForeground) {
                return;
            }

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            grayOut(!isSuccess);

            if (isSuccess) {
                if (mBluetoothManager.getBluetoothDeviceNameCapability() == StarBluetoothManager.StarBluetoothSettingCapability.SUPPORT) {
                    mDeviceName = mBluetoothManager.getBluetoothDeviceName();

                    mDeviceNameEditText.setText(mDeviceName);
                    mDeviceNameEditText.setEnabled(true);
                }
                else {
                    mDeviceNameEditText.setText("N/A");
                    mDeviceNameEditText.setEnabled(false);
                }

                if (mBluetoothManager.getiOSPortNameCapability() == StarBluetoothManager.StarBluetoothSettingCapability.SUPPORT) {
                    miOSPortName = mBluetoothManager.getiOSPortName();

                    miOSPortNameEditText.setText(miOSPortName);
                    miOSPortNameEditText.setEnabled(true);
                }
                else {
                    miOSPortNameEditText.setText("N/A");
                    miOSPortNameEditText.setEnabled(false);
                }

                if (mBluetoothManager.getAutoConnectCapability() == StarBluetoothManager.StarBluetoothSettingCapability.SUPPORT) {
                    mAutoConnect = mBluetoothManager.getAutoConnect();

                    mAutoConnectSwitch.setChecked(mAutoConnect);
                    mAutoConnectSwitch.setEnabled(true);
                }
                else {
                    mAutoConnectSwitch.setText("N/A");
                    mAutoConnectSwitch.setEnabled(false);
                }

                if (mBluetoothManager.getSecurityTypeCapability() == StarBluetoothManager.StarBluetoothSettingCapability.SUPPORT) {
                    mSecurity = mBluetoothManager.getSecurityType();

                    if (mSecurity == StarBluetoothManager.StarBluetoothSecurity.SSP) {
                        if (mBluetoothManager.getDeviceType() == StarBluetoothManager.StarDeviceType.StarDeviceTypeDesktopPrinter) {
                            mSecurityValueTextView.setText("SSP");
                        }
                        else {
                            mSecurityValueTextView.setText("SSP Just Works");
                        }
                    }
                    else if (mSecurity == StarBluetoothManager.StarBluetoothSecurity.SSP_NUMERIC_COMPARISON) {
                        mSecurityValueTextView.setText("SSP Numeric Comparison");
                    }
                    else if (mSecurity == StarBluetoothManager.StarBluetoothSecurity.PINCODE) {
                        mSecurityValueTextView.setText("PIN Code");
                    }
                    else {  // Disable
                        mSecurityValueTextView.setText("Disable");
                    }

                    if (!mIsSMSorTSeries && !mIsSMLSeries) {
                        mSecurityValueTextView.setEnabled(true);
                    }
                    else if (mIsSMSorTSeries && mFwInfoSMSorTSeries == FwInfoSMSorTSeries.V5orLater) {
                        mSecurityValueTextView.setEnabled(true);
                    }
                    else {
                        mSecurityValueTextView.setEnabled(false);
                    }
                }
                else {
                    mSecurityValueTextView.setText("N/A");
                    mSecurityValueTextView.setEnabled(false);
                }

                if (mBluetoothManager.getPinCodeCapability() == StarBluetoothManager.StarBluetoothSettingCapability.SUPPORT) {
                    mChangePinCodeSwitch.setText("");
                    mChangePinCodeSwitch.setEnabled(true);

                    mNewPinCodeEditText.setText("");
                }
                else {
                    mChangePinCodeSwitch.setText("N/A");
                    mChangePinCodeSwitch.setEnabled(false);

                    mNewPinCodeEditText.setHint("N/A");
                }

                mChangePinCodeSwitch.setChecked(false);

                mNewPinCode = "";
                mNewPinCodeEditText.setEnabled(false);
            }
            else {
                CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance(STAR_IO_EXCEPTION_DIALOG);
                dialog.setTitle("Communication Result");
                dialog.setMessage(mErrorMessage);
                dialog.setPositiveButton("OK");
                dialog.setCancelable(false);
                dialog.show(getChildFragmentManager());
            }

            mApplyButton.setEnabled(validateStringSettings());
        }
    }

    private class ApplySettingsTask extends AsyncTask<Void, Void, Boolean> {
        private String mErrorMessage;

        private boolean mChangePinCode;

        @Override
        protected void onPreExecute() {
            mProgressDialog.show();

            mChangePinCode = mChangePinCodeSwitch.isChecked();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean isSuccess = true;

            try {
                mErrorMessage = "Fail to Open Port.";

                mBluetoothManager.open();

                mErrorMessage = "Fail to apply setting.";

                if (mBluetoothManager.getBluetoothDeviceNameCapability() == StarBluetoothManager.StarBluetoothSettingCapability.SUPPORT) {
                    mBluetoothManager.setBluetoothDeviceName(mDeviceName);
                }

                if (mBluetoothManager.getiOSPortNameCapability() == StarBluetoothManager.StarBluetoothSettingCapability.SUPPORT) {
                    mBluetoothManager.setiOSPortName(miOSPortName);
                }

                if (mBluetoothManager.getSecurityTypeCapability() == StarBluetoothManager.StarBluetoothSettingCapability.SUPPORT) {
                    mBluetoothManager.setSecurityType(mSecurity);
                }

                if (mBluetoothManager.getAutoConnectCapability() == StarBluetoothManager.StarBluetoothSettingCapability.SUPPORT) {
                    mBluetoothManager.setAutoConnect(mAutoConnect);
                }

                if (mBluetoothManager.getSecurityTypeCapability() == StarBluetoothManager.StarBluetoothSettingCapability.SUPPORT &&
                    mBluetoothManager.getSecurityType() == StarBluetoothManager.StarBluetoothSecurity.PINCODE &&
                    mChangePinCode) {
                    mBluetoothManager.setPinCode(mNewPinCode);
                }

                mBluetoothManager.apply();
            }
            catch (StarIOPortException e) {
                isSuccess = false;
            }
            finally {
                if (mBluetoothManager.isOpened()) {
                    try {
                        mBluetoothManager.close();
                    }
                    catch (StarIOPortException e) {
                        isSuccess = false;
                    }
                }
            }

            return isSuccess;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            if (!mIsForeground) {
                return;
            }

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            if (isSuccess) {
                CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance(COMPLETE_TO_APPLY_DIALOG);
                dialog.setTitle("Complete");
                dialog.setMessage("To apply settings, please turn the device power OFF and ON, and establish pairing again.");
                dialog.setPositiveButton("OK");
                dialog.setCancelable(false);
                dialog.show(getChildFragmentManager());
            }
            else {
                CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance(STAR_IO_EXCEPTION_DIALOG);
                dialog.setTitle("Communication Result");
                dialog.setMessage(mErrorMessage);
                dialog.setPositiveButton("OK");
                dialog.setCancelable(false);
                dialog.show(getChildFragmentManager());
            }
        }
    }

    private void confirmSettings() {
        if (mBluetoothManager.getDeviceType() == StarBluetoothManager.StarDeviceType.StarDeviceTypeDesktopPrinter &&
            mAutoConnect &&
            mSecurity == StarBluetoothManager.StarBluetoothSecurity.PINCODE) {
            CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance(AUTO_CONNECTION_AND_PIN_DIALOG);
            dialog.setTitle("Error");
            dialog.setMessage("Auto Connection function is available only when the security setting is \"SSP\".");
            dialog.setPositiveButton("OK");
            dialog.show(getChildFragmentManager());

            return;
        }

        ApplySettingsTask task = new ApplySettingsTask();
        task.execute();
    }

    private InputFilter[] getDeviceNameAndiOSPortNameFilters() {
        InputFilter inputFilter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (validateNameChars(source.toString())) {
                    return source.toString();
                }
                return "";
            }
        };

        InputFilter lengthFilter = new InputFilter.LengthFilter(16);

        return new InputFilter[] { inputFilter, lengthFilter };
    }

    private InputFilter[] getPinCodeFilters() {
        InputFilter inputFilter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (validatePinCodeChars(source.toString())) {
                    return source.toString();
                }
                return "";
            }
        };

        InputFilter lengthFilter = new InputFilter.LengthFilter(16);

        return new InputFilter[] { inputFilter, lengthFilter };
    }

    private InputFilter[] getSMLSeriesPinCodeFilters() {
        InputFilter inputFilter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (validateSMLSeriesPinCodeChars(source.toString())) {
                    return source.toString();
                }
                return "";
            }
        };

        InputFilter lengthFilter = new InputFilter.LengthFilter(4);

        return new InputFilter[] { inputFilter, lengthFilter };
    }

    private boolean validateStringSettings() {
        boolean isValidDeviceName = true;

        // Device name and iOS port name can use alphabetical(A-Z,a-z), numeric(0-9) and some symbol characters(see SDK manual),
        // and its length must be from 1 to 16.
        if (mBluetoothManager.getBluetoothDeviceNameCapability() == StarBluetoothManager.StarBluetoothSettingCapability.SUPPORT) {
            isValidDeviceName = mDeviceName != null &&
                                validateNameChars(mDeviceName) &&
                                validateNameLength(mDeviceName);
        }

        boolean isValidiOSPortName = true;

        if (mBluetoothManager.getiOSPortNameCapability() == StarBluetoothManager.StarBluetoothSettingCapability.SUPPORT) {
            isValidiOSPortName = miOSPortName != null &&
                                 validateNameChars(miOSPortName) &&
                                 validateNameLength(miOSPortName);
        }

        boolean isValidPinCode = true;

        // PIN code for SM-L series must be four numeric(0-9) characters.
        // Other models can use alphabetical(A-Z,a-z) and numeric(0-9) PIN code, and its length must be from 4 to 16.
        if (mBluetoothManager.getPinCodeCapability() == StarBluetoothManager.StarBluetoothSettingCapability.SUPPORT &&
            mChangePinCodeSwitch.isChecked()) {
            if (mIsSMLSeries) {
                isValidPinCode = mNewPinCode != null &&
                                 validateSMLSeriesPinCodeChars(mNewPinCode) &&
                                 validateSMLSeriesPinCodeLength(mNewPinCode);
            }
            else {
                isValidPinCode = mNewPinCode != null &&
                                 validatePinCodeChars(mNewPinCode) &&
                                 validatePinCodeLength(mNewPinCode);
            }
        }

        return isValidDeviceName && isValidiOSPortName && isValidPinCode;
    }

    private boolean validateNameChars(String name) {
        return name.matches("^[0-9a-zA-Z;:!?#$%&,.@_\\-= \\\\/\\*\\+~\\^\\[\\{\\(\\]\\}\\)\\|]+$");
    }

    private boolean validatePinCodeChars(String pinCode) {
        return pinCode.matches("^[0-9a-zA-Z]+$");
    }

    private boolean validateSMLSeriesPinCodeChars(String pinCode) {
        return pinCode.matches("^[0-9]+$");
    }

    private boolean validateNameLength(String name) {
        return name          != null &&
               name.length() >= 1 &&
               name.length() <= 16;
    }

    private boolean validatePinCodeLength(String pinCode) {
        return pinCode          != null &&
               pinCode.length() >= 4 &&
               pinCode.length() <= 16;
    }

    private boolean validateSMLSeriesPinCodeLength(String pinCode) {
        return pinCode          != null &&
               pinCode.length() == 4;
    }

    private void grayOut(boolean grayOut) {
        if (grayOut) {
            mGrayOutView.setVisibility(View.VISIBLE);
        }
        else {
            mGrayOutView.setVisibility(View.GONE);
        }
    }
}
