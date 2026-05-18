package com.starmicronics.starprntsdk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.starmicronics.starprntsdk.Communication.CommunicationResult;
import com.starmicronics.starprntsdk.Communication.Result;

import com.starmicronics.starioextension.IPeripheralConnectParser;
import com.starmicronics.starioextension.StarIoExt;
import com.starmicronics.starioextension.StarIoExt.DisplayModel;
import com.starmicronics.starprntsdk.functions.DisplayFunctions;
import com.starmicronics.starioextension.IDisplayCommandBuilder.InternationalType;
import com.starmicronics.starioextension.IDisplayCommandBuilder.CodePageType;
import com.starmicronics.starioextension.IDisplayCommandBuilder.CursorMode;
import com.starmicronics.starioextension.IDisplayCommandBuilder.ContrastMode;

public class DisplayFragment extends ItemListFragment implements CommonAlertDialogFragment.Callback {
    private static final String COMMUNICATE_RESULT_DIALOG                           = "CommunicateResultDialog";
    private static final String DISPLAY_TEXT_SELECT_DIALOG                          = "DisplayTextSelectDialog";
    private static final String DISPLAY_GRAPHIC_SELECT_DIALOG                       = "DisplayGraphicSelectDialog";
    private static final String DISPLAY_TURN_ON_OFF_SELECT_DIALOG                   = "DisplayTurnOnOffSelectDialog";
    private static final String DISPLAY_CURSOR_SELECT_DIALOG                        = "DisplayCursorSelectDialog";
    private static final String DISPLAY_CONTRAST_SELECT_DIALOG                      = "DisplayContrastSelectDialog";
    private static final String DISPLAY_CHARACTER_SET_INTERNATIONAL_SELECT_DIALOG   = "DisplayCharacterSetInternationalSelectDialog";
    private static final String DISPLAY_CHARACTER_SET_CODE_PAGE_SELECT_DIALOG       = "DisplayCharacterSetCodePageSelectDialog";
    private static final String DISPLAY_USER_DEFINED_CHARACTER_SELECT_DIALOG        = "DisplayUserDefinedCharacterSelectDialog";

    private ProgressDialog mProgressDialog;

    private boolean mIsForeground;

    private InternationalType mInternationalType = InternationalType.USA;
    private CodePageType      mCodePageType      = CodePageType.CP437;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Communicating...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);

        addTitle("Contents");
        addMenu("Check Status");
        addMenu("Text");
        addMenu("Graphic");
        addMenu("Back Light (Turn On / Off)");
        addMenu("Cursor");
        addMenu("Contrast");
        addMenu("Character Set (International)");
        addMenu("Character Set (Code Page)");
        addMenu("User Defined Character");

        addTitle("Like a StarIoExtManager Sample");
        addMenu("Sample");
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
    public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
        super.onItemClick(parent, view, position, id);

        CommonAlertDialogFragment dialog = null;

        if (position == 1) {
            mProgressDialog.show();

            PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
            PrinterSettings       settings       = settingManager.getPrinterSettings();

            final IPeripheralConnectParser parser = StarIoExt.createDisplayConnectParser(DisplayModel.SCD222);

            Communication.parseDoNotCheckCondition(DisplayFragment.class, parser, settings.getPortName(), settings.getPortSettings(), 10000, getActivity(), new Communication.SendCallback() {
                @Override
                public void onStatus(CommunicationResult communicationResult) {
                    if (!mIsForeground) {
                        return;
                    }

                    String msg;
                    String title;

                    if (communicationResult.getResult() == Result.Success) {
                        title = "Check Status";

                        if (parser.isConnected()) {
                            msg = "Display Connect";
                        }
                        else {
                            msg = "Display Disconnect";
                        }
                    }
                    else {
                        title = "Communication Result";
                        msg   = Communication.getCommunicationResultMessage(communicationResult);
                    }

                    CommonAlertDialogFragment dialog2 = CommonAlertDialogFragment.newInstance(COMMUNICATE_RESULT_DIALOG);
                    dialog2.setTitle(title);
                    dialog2.setMessage(msg);
                    dialog2.setPositiveButton("OK");
                    dialog2.show(getChildFragmentManager());

                    if (mProgressDialog != null) {
                        mProgressDialog.dismiss();
                    }
                }
            });
        }
        else if (position == 2) {
            dialog = DisplayTextDialogFragment.newInstance(DISPLAY_TEXT_SELECT_DIALOG);
        }
        else if (position == 3) {
            dialog = DisplayGraphicDialogFragment.newInstance(DISPLAY_GRAPHIC_SELECT_DIALOG);
        }
        else if (position == 4) {
            dialog = DisplayTurnOnOffDialogFragment.newInstance(DISPLAY_TURN_ON_OFF_SELECT_DIALOG);
        }
        else if (position == 5) {
            dialog = DisplayCursorDialogFragment.newInstance(DISPLAY_CURSOR_SELECT_DIALOG);
        }
        else if (position == 6) {
            dialog = DisplayContrastDialogFragment.newInstance(DISPLAY_CONTRAST_SELECT_DIALOG);
        }
        else if (position == 7) {
            dialog = DisplayCharacterSetInternationalDialogFragment.newInstance(DISPLAY_CHARACTER_SET_INTERNATIONAL_SELECT_DIALOG);
        }
        else if (position == 8) {
            dialog = DisplayCharacterSetCodePageDialogFragment.newInstance(DISPLAY_CHARACTER_SET_CODE_PAGE_SELECT_DIALOG);
        }
        else if (position == 9) {
            dialog = DisplayUserDefinedCharacterDialogFragment.newInstance(DISPLAY_USER_DEFINED_CHARACTER_SELECT_DIALOG);
        }
        else {
            Intent intent = new Intent(getActivity(), CommonActivity.class);
            intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_display_ext);
            intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "Display Ext");
            intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);
            intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_RELOAD_BUTTON, true);

            startActivity(intent);
        }

        if (dialog != null) {
            dialog.show(getChildFragmentManager());
        }
    }

    @Override
    public void onDialogResult(String tag, Intent data) {
        boolean isCanceled = data.hasExtra(CommonAlertDialogFragment.LABEL_NEGATIVE);

        if (COMMUNICATE_RESULT_DIALOG.compareTo(tag) == 0 || isCanceled) {
            return;
        }

        int index = data.getIntExtra(CommonActivity.BUNDLE_KEY_INDEX, 0);

        final byte[] commandData;

        switch (tag) {
            default:
            case DISPLAY_TEXT_SELECT_DIALOG:

                commandData = DisplayFunctions.createTextPattern(index);
                break;
            case DISPLAY_GRAPHIC_SELECT_DIALOG:

                commandData = DisplayFunctions.createGraphicPattern(index, getResources());
                break;
            case DISPLAY_TURN_ON_OFF_SELECT_DIALOG:
                boolean isTurnOn = (index == 0);

                commandData = DisplayFunctions.createTurnOn(isTurnOn);
                break;
            case DISPLAY_CURSOR_SELECT_DIALOG:
                CursorMode cursorMode;

                switch (index) {
                    default:
                    case 0:     cursorMode = CursorMode.Off;   break;
                    case 1:     cursorMode = CursorMode.Blink; break;
                    case 2:     cursorMode = CursorMode.On;    break;
                }

                commandData = DisplayFunctions.createCursorMode(cursorMode);
                break;
            case DISPLAY_CONTRAST_SELECT_DIALOG:
                ContrastMode contrastMode;

                switch (index) {
                    default:
                    case 0:     contrastMode = ContrastMode.Minus3;    break;
                    case 1:     contrastMode = ContrastMode.Minus2;    break;
                    case 2:     contrastMode = ContrastMode.Minus1;    break;
                    case 3:     contrastMode = ContrastMode.Default;   break;
                    case 4:     contrastMode = ContrastMode.Plus1;     break;
                    case 5:     contrastMode = ContrastMode.Plus2;     break;
                    case 6:     contrastMode = ContrastMode.Plus3;     break;
                }

                commandData = DisplayFunctions.createContrastMode(contrastMode);
                break;
            case DISPLAY_CHARACTER_SET_INTERNATIONAL_SELECT_DIALOG:
                switch (index) {
                    default:
                    case 0:     mInternationalType = InternationalType.USA;            break;
                    case 1:     mInternationalType = InternationalType.France;         break;
                    case 2:     mInternationalType = InternationalType.Germany;        break;
                    case 3:     mInternationalType = InternationalType.UK;             break;
                    case 4:     mInternationalType = InternationalType.Denmark;        break;
                    case 5:     mInternationalType = InternationalType.Sweden;         break;
                    case 6:     mInternationalType = InternationalType.Italy;          break;
                    case 7:     mInternationalType = InternationalType.Spain;          break;
                    case 8:     mInternationalType = InternationalType.Japan;          break;
                    case 9:     mInternationalType = InternationalType.Norway;         break;
                    case 10:    mInternationalType = InternationalType.Denmark2;       break;
                    case 11:    mInternationalType = InternationalType.Spain2;         break;
                    case 12:    mInternationalType = InternationalType.LatinAmerica;   break;
                    case 13:    mInternationalType = InternationalType.Korea;          break;
                }

                commandData = DisplayFunctions.createCharacterSet(mInternationalType, mCodePageType);
                break;
            case DISPLAY_CHARACTER_SET_CODE_PAGE_SELECT_DIALOG:
                switch (index) {
                    default:
                    case 0:     mCodePageType = CodePageType.CP437;                break;
                    case 1:     mCodePageType = CodePageType.Katakana;             break;
                    case 2:     mCodePageType = CodePageType.CP850;                break;
                    case 3:     mCodePageType = CodePageType.CP860;                break;
                    case 4:     mCodePageType = CodePageType.CP863;                break;
                    case 5:     mCodePageType = CodePageType.CP865;                break;
                    case 6:     mCodePageType = CodePageType.CP1252;               break;
                    case 7:     mCodePageType = CodePageType.CP866;                break;
                    case 8:     mCodePageType = CodePageType.CP852;                break;
                    case 9:     mCodePageType = CodePageType.CP858;                break;
                    case 10:    mCodePageType = CodePageType.Japanese;             break;
                    case 11:    mCodePageType = CodePageType.SimplifiedChinese;    break;
                    case 12:    mCodePageType = CodePageType.TraditionalChinese;   break;
                    case 13:    mCodePageType = CodePageType.Hangul;               break;
                }

                commandData = DisplayFunctions.createCharacterSet(mInternationalType, mCodePageType);
                break;
            case DISPLAY_USER_DEFINED_CHARACTER_SELECT_DIALOG:
                boolean isSet = (index == 0);

                commandData = DisplayFunctions.createUserDefinedCharacter(isSet);
                break;
        }

        mProgressDialog.show();

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        final IPeripheralConnectParser parser = StarIoExt.createDisplayConnectParser(DisplayModel.SCD222);

        Communication.parseDoNotCheckCondition(DisplayFragment.class, parser, settings.getPortName(), settings.getPortSettings(), 10000, getActivity(), new Communication.SendCallback() {
            @Override
            public void onStatus(CommunicationResult communicationResult) {
                if (!mIsForeground) {
                    return;
                }

                boolean displayResult = false;
                String  title         = "";
                String  msg           = "";

                if (communicationResult.getResult() == Result.Success) {
                    if (parser.isConnected()) {
                        displayResult = true;
                    }
                    else {
                        title = "Check Status";
                        msg   = "Display Disconnect";
                    }
                }
                else {
                    title = "Communication Result";
                    msg   = Communication.getCommunicationResultMessage(communicationResult);
                }

                if (displayResult) {
                    PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
                    PrinterSettings       settings       = settingManager.getPrinterSettings();

                    Communication.sendCommandsDoNotCheckCondition(DisplayFragment.class, commandData, settings.getPortName(), settings.getPortSettings(), 10000, getActivity(), new Communication.SendCallback() {
                        @Override
                        public void onStatus(CommunicationResult communicationResult) {
                            if (mProgressDialog != null) {
                                mProgressDialog.dismiss();
                            }

                            CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance(COMMUNICATE_RESULT_DIALOG);
                            dialog.setTitle("Communication Result");
                            dialog.setMessage(Communication.getCommunicationResultMessage(communicationResult));
                            dialog.setPositiveButton("OK");
                            dialog.show(getChildFragmentManager());
                        }
                    });
                }
                else {
                    if (mProgressDialog != null) {
                        mProgressDialog.dismiss();
                    }

                    CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance(COMMUNICATE_RESULT_DIALOG);
                    dialog.setTitle(title);
                    dialog.setMessage(msg);
                    dialog.setPositiveButton("OK");
                    dialog.show(getChildFragmentManager());
                }
            }
        });
    }
}


