package com.starmicronics.starprntsdk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.starmicronics.starprntsdk.Communication.CommunicationResult;
import com.starmicronics.starprntsdk.Communication.Result;

import com.starmicronics.stario.StarPrinterStatus;
import com.starmicronics.starioextension.ICommandBuilder;

import com.starmicronics.starioextension.IPresenterPaperCounterParser;
import com.starmicronics.starioextension.IPresenterPaperCounterParser.Counter;
import com.starmicronics.starioextension.PresenterSetting.Mode;
import com.starmicronics.starioextension.PresenterSetting.PaperRetractFunction;
import com.starmicronics.starioextension.StarIoExt;
import com.starmicronics.starioextension.StarIoExt.Emulation;
import com.starmicronics.starprntsdk.functions.PresenterFunctions;
import com.starmicronics.starprntsdk.functions.PrinterFunctions;
import com.starmicronics.starprntsdk.localizereceipts.ILocalizeReceipts;

public class PresenterFragment extends CommonFragment implements CommonAlertDialogFragment.Callback {

    private static final String ERROR_DIALOG = "ErrorDialog";

    static final private String PRESENTER_MODE[] = {
            "Loop - Hold",
            "NoLoop - Hold",
            "NoLoop - NoHold",
    };

    static final private String PRESENTER_PAPER_RETRACT_FUNCTION[] = {
            "Retract",
            "Eject",
            "Disable",
    };

    private Spinner mPresenterModeSpinner;
    private Spinner mPresenterPaperRetractFunctionSpinner;
    private EditText mPaperHoldTimeEditText;

    private CheckBox mCheckPresenterStatusCheckBox;

    private ProgressDialog mProgressDialog;
    private Toast mToast;

    private Emulation mEmulation;
    private PrinterSettings mPrinterSettings;

    private boolean mIsForeground;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        View rootView = inflater.inflate(R.layout.fragment_presenter, container, false);

        createPresenterViews(rootView);

        mProgressDialog = new ProgressDialog(getActivity());

        mProgressDialog.setMessage("Communicating...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        mPrinterSettings = settingManager.getPrinterSettings();
        mEmulation = ModelCapability.getEmulation(mPrinterSettings.getModelIndex());

        return rootView;
    }

    private void createPresenterViews(View rootView) {
        mPresenterModeSpinner = rootView.findViewById(R.id.presenterModeSpinner);

        ArrayAdapter<String> modeArrayAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                PRESENTER_MODE);

        modeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mPresenterModeSpinner.setAdapter(modeArrayAdapter);

        mPresenterPaperRetractFunctionSpinner = rootView.findViewById(R.id.presenterPaperRetractFunctionSpinner);

        ArrayAdapter<String> paperRetractFunctionArrayAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                PRESENTER_PAPER_RETRACT_FUNCTION);

        paperRetractFunctionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mPresenterPaperRetractFunctionSpinner.setAdapter(paperRetractFunctionArrayAdapter);

        mPresenterPaperRetractFunctionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPaperHoldTimeEditText.setEnabled(position != 2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mPaperHoldTimeEditText = rootView.findViewById(R.id.paperHoldTimeEditText);

        mCheckPresenterStatusCheckBox = rootView.findViewById(R.id.checkPresenterStatusCheckBox);

        Button testPrintButton = rootView.findViewById(R.id.testPrintButton);
        testPrintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                print(mCheckPresenterStatusCheckBox.isChecked());
            }
        });

        Button getPrintedPaperCounterButton = rootView.findViewById(R.id.getPrintedPaperCounterButton);
        getPrintedPaperCounterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPaperCounter(Counter.Print);
            }
        });

        Button getAutoRetractedPaperCounterButton = rootView.findViewById(R.id.getAutoRetractedPaperCounterButton);
        getAutoRetractedPaperCounterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPaperCounter(Counter.Retract);
            }
        });

        Button clearCounterButton = rootView.findViewById(R.id.clearCounterButton);
        clearCounterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCounter();
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

    private void print(boolean checkPresenterState) {
        mProgressDialog.show();

        // Create commands for presenter setting.
        Mode mode;

        switch (mPresenterModeSpinner.getSelectedItemPosition()) {
            default:
            case 0:
                mode = Mode.LoopHold;
                break;
            case 1:
                mode = Mode.NoLoopHold;
                break;
            case 2:
                mode = Mode.NoLoopNoHold;
                break;
        }

        PaperRetractFunction function;

        switch (mPresenterPaperRetractFunctionSpinner.getSelectedItemPosition()) {
            default:
            case 0:
                function = PaperRetractFunction.Retract;
                break;
            case 1:
                function = PaperRetractFunction.Eject;
                break;
            case 2:
                function = PaperRetractFunction.Disable;
                break;
        }

        int holdTime;

        try {
            holdTime = Integer.parseInt(mPaperHoldTimeEditText.getText().toString());
        }
        catch (NumberFormatException e) {
            holdTime = 5000;
        }

        byte[] settingCommands;

        try {
            settingCommands = PresenterFunctions.createSetPresenterOperation(mEmulation, mode, function, holdTime);
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

        //  Create commands for printing.
        byte[] printCommands;

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        StarIoExt.Emulation emulation = ModelCapability.getEmulation(settings.getModelIndex());
        int paperSize = settings.getPaperSize();

        ILocalizeReceipts localizeReceipts = ILocalizeReceipts.createLocalizeReceipts(PrinterSettingConstant.LANGUAGE_ENGLISH, paperSize);

        printCommands = PrinterFunctions.createCouponData(emulation, localizeReceipts, getResources(), paperSize, ICommandBuilder.BitmapConverterRotation.Right90);

        byte[] commands = new byte[settingCommands.length + printCommands.length];

        System.arraycopy(settingCommands, 0, commands, 0, settingCommands.length);
        System.arraycopy(printCommands, 0, commands, settingCommands.length, printCommands.length);

        // Show the paper position of the presenter during printing if the checkPresenterState flag is true.
        // If using this function, please make "ASB Paper Position" valid with memory switch (refer to the product specification manual for detail)
        // and see the implementation of sendCommandsWithPresenterStateCheck method.
        if (checkPresenterState) {
            Communication.sendCommandsWithPresenterStateCheck(this, commands, settings.getPortName(), settings.getPortSettings(), 10000, 150000, getActivity(), mSendCallback, mPresenterStateCallback);     // 10000mS!!!
        }
        else {
            Communication.sendCommands(this, commands, settings.getPortName(), settings.getPortSettings(), 10000, 150000, getActivity(), mSendCallback);     // 10000mS!!!
        }
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

    private final Communication.PresenterStateCheckCallback mPresenterStateCallback = new Communication.PresenterStateCheckCallback() {
        @Override
        public void onStatus(Communication.PresenterStatus presenterStatus, StarPrinterStatus status) {
            if (!mIsForeground) {
                return;
            }

            String msg;

            switch (presenterStatus) {
                default:
                case NoPaper:
                    msg = "No paper.\n" + "Presenter State = " + status.presenterState;
                    break;
                case Loop:
                    msg = "Loop.\n" + "Presenter State = " + status.presenterState;
                    break;
                case Hold:
                    msg = "Paper can be pulled out.\n" + "Presenter State = " + status.presenterState;
                    break;
                case Retract:
                    msg = "Retract.\n" + "Presenter State = " + status.presenterState;
                    break;
                case Eject:
                    msg = "Eject.\n" + "Presenter State = " + status.presenterState;
                    break;
            }

            if(mToast != null) {
                mToast.cancel();
            }

            mToast = Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG);
            mToast.show();
        }
    };

    private void getPaperCounter(final Counter counter) {
        mProgressDialog.show();

        final IPresenterPaperCounterParser parser = StarIoExt.createPresenterPaperCounterParser(mEmulation, counter);

        Communication.parseDoNotCheckCondition(PresenterFragment.class, parser, mPrinterSettings.getPortName(), mPrinterSettings.getPortSettings(), 10000, getActivity(), new Communication.SendCallback() {
            @Override
            public void onStatus(CommunicationResult communicationResult) {
                if (!mIsForeground) {
                    return;
                }

                String msg;
                String title;

                if (communicationResult.getResult() == Result.Success) {
                    if (parser.getCounter() == Counter.Print) {
                        title = "Printed paper counter";
                    }
                    else {  // Counter.Retract
                        title = "Auto retracted/ejected paper counter";
                    }

                    msg   = String.valueOf(parser.getCount());
                }
                else {
                    title = "Communication Result";
                    msg   = Communication.getCommunicationResultMessage(communicationResult);
                }

                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }

                CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance("CommResultDialog");
                dialog.setTitle(title);
                dialog.setMessage(msg);
                dialog.setPositiveButton("OK");
                dialog.show(getChildFragmentManager());
            }
        });
    }

    private void clearCounter() {
        mProgressDialog.show();

        byte[] commands = PresenterFunctions.createClearCounter(mEmulation);

        Communication.sendCommands(this, commands, mPrinterSettings.getPortName(), mPrinterSettings.getPortSettings(), 10000, 30000, getActivity(), mSendCallback);    // 10000mS!!!
    }

    @Override
    public void onDialogResult(String tag, Intent data) {
        // do nothing
    }
}
