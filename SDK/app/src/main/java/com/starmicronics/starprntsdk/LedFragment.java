package com.starmicronics.starprntsdk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.starmicronics.starioextension.IBezelCommandBuilder;
import com.starmicronics.starioextension.IBezelCommandBuilder.Mode;
import com.starmicronics.starioextension.ICommandBuilder;
import com.starmicronics.starioextension.ILedCommandBuilder;
import com.starmicronics.starioextension.ILedCommandBuilder.Led;
import com.starmicronics.starioextension.StarIoExt;
import com.starmicronics.starioextension.StarIoExt.LedModel;
import com.starmicronics.starprntsdk.Communication.CommunicationResult;
import com.starmicronics.starprntsdk.functions.PrinterFunctions;
import com.starmicronics.starprntsdk.localizereceipts.ILocalizeReceipts;

import java.util.Arrays;
import java.util.List;

public class LedFragment extends CommonFragment implements CommonAlertDialogFragment.Callback {

    private static final String ERROR_DIALOG = "ErrorDialog";

    static final private String AUTOMATIC_BLINK_LED_CONTROL[] = {
            "Printing, Error",
            "Printing",
            "Error",
            "Disable",
            "Printing, Error, Idle",
    };

    static final private String BLINK_LED_CONTROL[] = {
            "Printing",
            "Error",
    };

    private Spinner mAutomaticBlinkLedControlSpinner;
    private EditText mSetPrintingLedOnTimeEditText;
    private EditText mSetPrintingLedOffTimeEditText;
    private EditText mSetErrorLedOnTimeEditText;
    private EditText mSetErrorLedOffTimeEditText;
    private EditText mSetIdleLedOnTimeEditText;
    private EditText mSetIdleLedOffTimeEditText;

    private Spinner mBlinkLedControlSpinner;
    private EditText mRepeatCountEditText;
    private EditText mBlinkLedOnTimeEditText;
    private EditText mBlinkLedOffTimeEditText;

    private ProgressDialog mProgressDialog;

    private PrinterSettings mPrinterSettings;

    private boolean mIsForeground;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        View rootView = inflater.inflate(R.layout.fragment_led, container, false);

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        mPrinterSettings = settingManager.getPrinterSettings();

        createLedViews(rootView);

        mProgressDialog = new ProgressDialog(getActivity());

        mProgressDialog.setMessage("Communicating...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);

        return rootView;
    }

    private void createLedViews(View rootView) {
        mAutomaticBlinkLedControlSpinner = rootView.findViewById(R.id.automaticBlinkLedControlSpinner);

        ArrayAdapter<String> automaticBlinkArrayAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                AUTOMATIC_BLINK_LED_CONTROL);

        automaticBlinkArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mAutomaticBlinkLedControlSpinner.setAdapter(automaticBlinkArrayAdapter);

        mSetPrintingLedOnTimeEditText = rootView.findViewById(R.id.setPrintingLedOnTimeEditText);
        mSetPrintingLedOffTimeEditText = rootView.findViewById(R.id.setPrintingLedOffTimeEditText);

        mSetErrorLedOnTimeEditText = rootView.findViewById(R.id.setErrorLedOnTimeEditText);
        mSetErrorLedOffTimeEditText = rootView.findViewById(R.id.setErrorLedOffTimeEditText);

        mSetIdleLedOnTimeEditText = rootView.findViewById(R.id.setIdleLedOnTimeEditText);
        mSetIdleLedOffTimeEditText = rootView.findViewById(R.id.setIdleLedOffTimeEditText);

        Button testPrintButton = rootView.findViewById(R.id.testPrintButton);
        testPrintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                print();
            }
        });

        mBlinkLedControlSpinner = rootView.findViewById(R.id.blinkLedControlSpinner);
        mBlinkLedControlSpinner.setEnabled(ModelCapability.canUseBlinkLed(mPrinterSettings.getModelIndex()));

        ArrayAdapter<String> blinkArrayAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                BLINK_LED_CONTROL);

        blinkArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mBlinkLedControlSpinner.setAdapter(blinkArrayAdapter);

        mRepeatCountEditText = rootView.findViewById(R.id.repeatCountEditText);
        mRepeatCountEditText.setEnabled(ModelCapability.canUseBlinkLed(mPrinterSettings.getModelIndex()));
        mBlinkLedOnTimeEditText = rootView.findViewById(R.id.blinkLedOnTimeEditText);
        mBlinkLedOnTimeEditText.setEnabled(ModelCapability.canUseBlinkLed(mPrinterSettings.getModelIndex()));
        mBlinkLedOffTimeEditText = rootView.findViewById(R.id.blinkLedOffTimeEditText);
        mBlinkLedOffTimeEditText.setEnabled(ModelCapability.canUseBlinkLed(mPrinterSettings.getModelIndex()));

        Button blinkButton = rootView.findViewById(R.id.blinkButton);
        blinkButton.setEnabled(ModelCapability.canUseBlinkLed(mPrinterSettings.getModelIndex()));
        blinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blinkLed();
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

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    private void print() {
        mProgressDialog.show();

        StarIoExt.Emulation emulation = ModelCapability.getEmulation(mPrinterSettings.getModelIndex());

        // Create commands to enable bezel.
        // When using the bezel LED, the bezel must be enabled. For other models, it is not necessary to enable the bezel.
        IBezelCommandBuilder bezelBuilder = StarIoExt.createBezelCommandBuilder(emulation);

        bezelBuilder.appendBezelMode(Mode.Valid);

        byte[] bezelCommands = bezelBuilder.getCommands();

        // Create commands for LED setting.
        int printingLedOnTime = getValue(mSetPrintingLedOnTimeEditText, 100);
        int printingLedOffTime = getValue(mSetPrintingLedOffTimeEditText, 100);
        int errorLedOnTime = getValue(mSetErrorLedOnTimeEditText, 100);
        int errorLedOffTime = getValue(mSetErrorLedOffTimeEditText, 100);
        int idleLedOnTime = getValue(mSetIdleLedOnTimeEditText, 100);
        int idleLedOffTime = getValue(mSetIdleLedOffTimeEditText, 100);

        Led[] leds;
        switch (mAutomaticBlinkLedControlSpinner.getSelectedItemPosition()) {
            default:
            case 0:
                leds = new Led[] { Led.Printing, Led.Error };
                break;
            case 1:
                leds = new Led[] { Led.Printing };
                break;
            case 2:
                leds = new Led[] { Led.Error };
                break;
            case 3:
                leds = new Led[] { };
                break;
            case 4:
                leds = new Led[] { Led.Printing, Led.Error, Led.Idle };
                break;
        }

        LedModel model = ModelCapability.getLedModel(mPrinterSettings.getModelIndex());
        ILedCommandBuilder ledBuilder = StarIoExt.createLedCommandBuilder(model);

        try {
            ledBuilder.appendAutomaticBlinkMode(leds);

            List ledList = Arrays.asList(leds);

            if (ledList.contains(Led.Printing)) {
                ledBuilder.appendAutomaticBlinkInterval(Led.Printing, printingLedOnTime, printingLedOffTime);
            }

            if (ledList.contains(Led.Error)) {
                ledBuilder.appendAutomaticBlinkInterval(Led.Error, errorLedOnTime, errorLedOffTime);
            }

            if (ledList.contains(Led.Idle)) {
                ledBuilder.appendAutomaticBlinkInterval(Led.Idle, idleLedOnTime, idleLedOffTime);
            }
        }
        catch (IllegalArgumentException e) {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance(ERROR_DIALOG);
            dialog.setTitle("Error");
            dialog.setMessage(e.getMessage());
            dialog.setPositiveButton("OK");
            dialog.show(getChildFragmentManager());
            return;
        }

        byte[] ledCommands = ledBuilder.getCommands();

        // Create commands for printing.
        byte[] printCommands;

        int paperSize = mPrinterSettings.getPaperSize();

        ILocalizeReceipts localizeReceipts = ILocalizeReceipts.createLocalizeReceipts(PrinterSettingConstant.LANGUAGE_ENGLISH, paperSize);

        printCommands = PrinterFunctions.createCouponData(emulation, localizeReceipts, getResources(), paperSize, ICommandBuilder.BitmapConverterRotation.Right90);

        byte[] commands = new byte[bezelCommands.length + ledCommands.length + printCommands.length];

        System.arraycopy(bezelCommands, 0, commands, 0,                                         bezelCommands.length);
        System.arraycopy(ledCommands,   0, commands, bezelCommands.length,                      ledCommands.length);
        System.arraycopy(printCommands, 0, commands, bezelCommands.length + ledCommands.length, printCommands.length);

        Communication.sendCommands(this, commands, mPrinterSettings.getPortName(), mPrinterSettings.getPortSettings(), 10000, 150000, getActivity(), mSendCallback);     // 10000mS!!!
    }

    private void blinkLed() {
        mProgressDialog.show();

        Led led;

        switch (mBlinkLedControlSpinner.getSelectedItemPosition()) {
            default:
            case 0:
                led = Led.Printing;
                break;
            case 1:
                led = Led.Error;
                break;
        }

        int repeat = getValue(mRepeatCountEditText, 1);
        int onTime = getValue(mBlinkLedOnTimeEditText, 100);
        int offTime = getValue(mBlinkLedOffTimeEditText, 100);

        LedModel model = ModelCapability.getLedModel(mPrinterSettings.getModelIndex());
        ILedCommandBuilder builder = StarIoExt.createLedCommandBuilder(model);

        try {
            builder.appendBlink(led, repeat, onTime, offTime);
        }
        catch (IllegalArgumentException e) {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance(ERROR_DIALOG);
            dialog.setTitle("Error");
            dialog.setMessage(e.getMessage());
            dialog.setPositiveButton("OK");
            dialog.show(getChildFragmentManager());
            return;
        }

        byte[] commands = builder.getCommands();

        Communication.sendCommands(this, commands, mPrinterSettings.getPortName(), mPrinterSettings.getPortSettings(), 10000, 150000, getActivity(), mSendCallback);     // 10000mS!!!
    }

    private final Communication.SendCallback mSendCallback = new Communication.SendCallback() {
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

    @Override
    public void onDialogResult(String tag, Intent data) {
        // do nothing
    }

    private int getValue(EditText editText, int defaultValue) {
        int value;

        try {
            value = Integer.parseInt(editText.getText().toString());
        }
        catch (NumberFormatException e) {
            value = defaultValue;
        }

        return value;
    }
}
