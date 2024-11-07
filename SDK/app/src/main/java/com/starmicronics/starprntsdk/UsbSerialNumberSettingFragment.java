package com.starmicronics.starprntsdk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.starmicronics.starprntsdk.Communication.CommunicationResult;
import com.starmicronics.starprntsdk.Communication.Result;

public class UsbSerialNumberSettingFragment extends CommonFragment implements CommonAlertDialogFragment.Callback {

    private ProgressDialog mProgressDialog;

    private boolean mIsForeground;

    private View mSetLayout;
    private View mInitializeLayout;

    private EditText mUsbIdEditText;
    private CheckBox mApplyEnableCheckBox;
    private CheckBox mInitializeEnableCheckBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_usb_serial_number_setting, container, false);

        RadioGroup layoutSelectRadioGroup = rootView.findViewById(R.id.layoutSelectRadioGroup);

        layoutSelectRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.setLayoutRadioButton) {
                    mSetLayout.setVisibility(View.VISIBLE);
                    mInitializeLayout.setVisibility(View.GONE);
                }
                else if (checkedId == R.id.initializeLayoutRadioButton) {
                    mSetLayout.setVisibility(View.GONE);
                    mInitializeLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        // USB Serial Number Setting Layout
        mSetLayout = rootView.findViewById(R.id.setUsbSerialNumberLayout);

        mUsbIdEditText = rootView.findViewById(R.id.usbSerialNumberEditText);

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        int    modelIndex = settings.getModelIndex();
        String modelName  = settings.getModelName();
        String portName   = settings.getPortName();

        boolean isUsbInterface = portName.toUpperCase().startsWith("USB:");

        int maxUsbSerialNumberLength = ModelCapability.settableUsbSerialNumberLength(modelIndex, modelName, isUsbInterface);

        mUsbIdEditText.setFilters(getEditTextFilters(maxUsbSerialNumberLength));

        mUsbIdEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });

        mApplyEnableCheckBox = rootView.findViewById(R.id.applyEnableCheckBox);

        Button applyButton = rootView.findViewById(R.id.applyButton);

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUsbSerialNumber();
            }
        });

        // USB Serial Number Initialization Layout
        mInitializeLayout = rootView.findViewById(R.id.initializeUsbSerialNumberLayout);

        mInitializeEnableCheckBox = rootView.findViewById(R.id.initializeEnableCheckBox);

        Button initializeButton = rootView.findViewById(R.id.initializeButton);

        initializeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeUsbSerialNumber();
            }
        });

        mInitializeEnableCheckBox.setChecked(ModelCapability.isUsbSerialNumberEnabledByDefault(modelIndex));

        mInitializeLayout.setVisibility(View.GONE);

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
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onDialogResult(String tag, Intent data) {
        // do nothing
    }

    private void setUsbSerialNumber() {
        mProgressDialog.show();

        byte[] usbSerialNumber = mUsbIdEditText.getText().toString().getBytes();

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        Communication.setUsbSerialNumber(this, usbSerialNumber, mApplyEnableCheckBox.isChecked(), settings.getPortName(), settings.getPortSettings(), 10000, getActivity(), mCallback);     // 10000mS!!!
    }

    private void initializeUsbSerialNumber() {
        mProgressDialog.show();

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        Communication.initializeUsbSerialNumber(this, mInitializeEnableCheckBox.isChecked(), settings.getPortName(), settings.getPortSettings(), 10000, getActivity(), mCallback);     // 10000mS!!!
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

            String title = "Communication Result";
            String msg;

            if (communicationResult.getResult() == Result.Success) {
                    title = "Complete";

                    PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
                    PrinterSettings       settings       = settingManager.getPrinterSettings();

                    if (settings.getPortName().toUpperCase().startsWith("USB:")) {
                        msg = "To apply settings, please turn the device power OFF and ON.\n\n" +
                              "Please tap the destination device menu and select your printer again.";
                    }
                    else {
                        msg = "To apply settings, please turn the device power OFF and ON.";
                    }
            }
            else {
                msg = Communication.getCommunicationResultMessage(communicationResult);
            }

            CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance("CommResultDialog");
            dialog.setTitle(title);
            dialog.setMessage(msg);
            dialog.setPositiveButton("OK");
            dialog.show(getChildFragmentManager());
        }
    };

    private InputFilter[] getEditTextFilters(int maxChars) {
        InputFilter charFilter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.toString().matches("^[0-9A-Z]+$")) {
                    return source;
                }
                return "";
            }
        };

        InputFilter lengthFilter = new InputFilter.LengthFilter(maxChars);

        return new InputFilter[] { charFilter, lengthFilter };
    }
}
