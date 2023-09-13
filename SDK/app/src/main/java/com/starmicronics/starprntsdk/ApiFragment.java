package com.starmicronics.starprntsdk;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.starmicronics.starioextension.IBezelCommandBuilder.AutomaticPageLengthType;
import com.starmicronics.starioextension.ICommandBuilder.PrintableAreaType;
import com.starmicronics.starprntsdk.Communication.CommunicationResult;
import com.starmicronics.starprntsdk.functions.ApiFunctions;

import static com.starmicronics.starioextension.ICommandBuilder.BlackMarkType;
import static com.starmicronics.starioextension.ICommandBuilder.LabelType;
import static com.starmicronics.starioextension.StarIoExt.Emulation;

public class ApiFragment extends ItemListFragment implements CommonAlertDialogFragment.Callback {

    private static final String BLACK_MARK_TYPE_SELECT_DIALOG     = "BlackMarkTypeSelectDialog";
    private static final String PRINTABLE_AREA_TYPE_SELECT_DIALOG = "PrintableAreaTypeSelectDialog";
    private static final String AUTOMATIC_PAGE_LENGTH_TYPE_SELECT_DIALOG = "AutomaticPageLengthTypeSelectDialog";
    private static final String LABEL_TYPE_SELECT_DIALOG = "LabelTypeSelectDialog";

    private static final int BLACK_MARK_MENU_INDEX     = 25;
    private static final int PRINTABLE_AREA_MENU_INDEX = 27;
    private static final int AUTOMATIC_PAGE_LENGTH_MENU_INDEX = 28;
    private static final int LABEL_MENU_INDEX = 29;

    private ProgressDialog mProgressDialog;

    private BlackMarkType     mBlackMarkType;
    private PrintableAreaType mPrintableAreaType;
    private AutomaticPageLengthType mAutomaticPageLengthType;
    private LabelType mLabelType;

    private boolean mIsForeground;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Communicating...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);

        addTitle("Sample");
        addMenu("Generic");
        addMenu("Font Style");
        addMenu("Initialization");
        addMenu("Code Page");
        addMenu("International");
        addMenu("Feed");
        addMenu("Character Space");
        addMenu("Line Space");
        addMenu("Top Margin");
        addMenu("Emphasis");
        addMenu("Invert");
        addMenu("Under Line");
        addMenu("Multiple");
        addMenu("Absolute Position");
        addMenu("Alignment");
        addMenu("Horizontal Tab Position");
        addMenu("Logo");
        addMenu("Cut Paper");
        addMenu("Peripheral");
        addMenu("Sound");
        addMenu("Bitmap");
        addMenu("Barcode");
        addMenu("PDF417");
        addMenu("QR Code");
        addMenu("Black Mark");
        addMenu("Page Mode");
        addMenu("Printable Area");
        addMenu("Automatic Page Length (Bezel)");
        addMenu("Label");
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

        if (position == BLACK_MARK_MENU_INDEX) {            // Black Mark
            BlackMarkTypeSelectDialogFragment dialog = BlackMarkTypeSelectDialogFragment.newInstance(BLACK_MARK_TYPE_SELECT_DIALOG);
            dialog.show(getChildFragmentManager());
        }
        else if (position == PRINTABLE_AREA_MENU_INDEX) {   // Printable Area
            PrintableAreaTypeSelectDialogFragment dialog = PrintableAreaTypeSelectDialogFragment.newInstance(PRINTABLE_AREA_TYPE_SELECT_DIALOG);
            dialog.show(getChildFragmentManager());
        }
        else if (position == AUTOMATIC_PAGE_LENGTH_MENU_INDEX) {   // Automatic page length
            AutomaticPageLengthTypeSelectDialogFragment dialog = AutomaticPageLengthTypeSelectDialogFragment.newInstance(AUTOMATIC_PAGE_LENGTH_TYPE_SELECT_DIALOG);
            dialog.show(getChildFragmentManager());
        }
        else if (position == LABEL_MENU_INDEX) {   // Label
            LabelTypeSelectDialogFragment dialog = LabelTypeSelectDialogFragment.newInstance(LABEL_TYPE_SELECT_DIALOG);
            dialog.show(getChildFragmentManager());
        }
        else {                                              // Others
            print(position);
        }
    }

    @Override
    public void onDialogResult(String tag, Intent data) {
        boolean isCanceled = data.hasExtra(CommonAlertDialogFragment.LABEL_NEGATIVE);

        if (isCanceled) return;

        switch (tag) {
            case BLACK_MARK_TYPE_SELECT_DIALOG : {
                int index = data.getIntExtra(CommonActivity.BUNDLE_KEY_BLACK_MARK_TYPE_INDEX, 0);

                switch (index) {
                    default:
                    case 0: mBlackMarkType = BlackMarkType.Invalid;            break;
                    case 1: mBlackMarkType = BlackMarkType.Valid;              break;
                    case 2: mBlackMarkType = BlackMarkType.ValidWithDetection; break;
                }

                print(BLACK_MARK_MENU_INDEX);   // Black Mark

                break;
            }
            case PRINTABLE_AREA_TYPE_SELECT_DIALOG : {
                int index = data.getIntExtra(CommonActivity.BUNDLE_KEY_PRINTABLE_AREA_TYPE_INDEX, 0);

                switch (index) {
                    default:
                    case 0: mPrintableAreaType = PrintableAreaType.Standard; break;
                    case 1: mPrintableAreaType = PrintableAreaType.Type1;    break;
                    case 2: mPrintableAreaType = PrintableAreaType.Type2;    break;
                    case 3: mPrintableAreaType = PrintableAreaType.Type3;    break;
                    case 4: mPrintableAreaType = PrintableAreaType.Type4;    break;
                }

                print(PRINTABLE_AREA_MENU_INDEX);   // Printable Area

                break;
            }
            case AUTOMATIC_PAGE_LENGTH_TYPE_SELECT_DIALOG : {
                int index = data.getIntExtra(CommonActivity.BUNDLE_KEY_AUTOMATIC_PAGE_LENGTH_TYPE_INDEX, 0);

                switch (index) {
                    default:
                    case 0: mAutomaticPageLengthType = AutomaticPageLengthType.Invalid; break;
                    case 1: mAutomaticPageLengthType = AutomaticPageLengthType.Valid;    break;
                    case 2: mAutomaticPageLengthType = AutomaticPageLengthType.Default;    break;
                }

                print(AUTOMATIC_PAGE_LENGTH_MENU_INDEX);   // Automatic page length

                break;
            }
            case LABEL_TYPE_SELECT_DIALOG : {
                int index = data.getIntExtra(CommonActivity.BUNDLE_KEY_LABEL_TYPE_INDEX, 0);

                switch (index) {
                    default:
                    case 0: mLabelType = LabelType.Invalid; break;
                    case 1: mLabelType = LabelType.Valid;    break;
                    case 2: mLabelType = LabelType.ValidWithDetection;    break;
                }

                print(LABEL_MENU_INDEX);   // Label

                break;
            }
        }
    }

    private void print(int selectedIndex) {
        mProgressDialog.show();

        byte[] data;

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        Emulation emulation  = ModelCapability.getEmulation(settings.getModelIndex());
        int       modelIndex = settings.getModelIndex();

        int paperSize = settings.getPaperSize();

        switch (selectedIndex) {
            default:
            case 1:                                data = ApiFunctions.createGenericData(emulation);                                            break;
            case 2:                                data = ApiFunctions.createFontStyleData(emulation);                                          break;
            case 3:                                data = ApiFunctions.createInitializationData(emulation);                                     break;
            case 4:                                data = ApiFunctions.createCodePageData(emulation);                                           break;
            case 5:                                data = ApiFunctions.createInternationalData(emulation);                                      break;
            case 6:                                data = ApiFunctions.createFeedData(emulation);                                               break;
            case 7:                                data = ApiFunctions.createCharacterSpaceData(emulation);                                     break;
            case 8:                                data = ApiFunctions.createLineSpaceData(emulation);                                          break;
            case 9:                                data = ApiFunctions.createTopMarginData(modelIndex, emulation);                               break;
            case 10:                               data = ApiFunctions.createEmphasisData(emulation);                                           break;
            case 11:                               data = ApiFunctions.createInvertData(emulation);                                             break;
            case 12:                               data = ApiFunctions.createUnderLineData(emulation);                                          break;
            case 13:                               data = ApiFunctions.createMultipleData(emulation);                                           break;
            case 14:                               data = ApiFunctions.createAbsolutePositionData(emulation);                                   break;
            case 15:                               data = ApiFunctions.createAlignmentData(emulation);                                          break;
            case 16:                               data = ApiFunctions.createHorizontalTabPositionData(emulation);                              break;
            case 17:                               data = ApiFunctions.createLogoData(emulation);                                               break;
            case 18:                               data = ApiFunctions.createCutPaperData(emulation);                                           break;
            case 19:                               data = ApiFunctions.createPeripheralData(emulation);                                         break;
            case 20:                               data = ApiFunctions.createSoundData(emulation);                                              break;
            case 21:                               data = ApiFunctions.createBitmapData(emulation, paperSize, getActivity());                   break;
            case 22:                               data = ApiFunctions.createBarcodeData(emulation);                                            break;
            case 23:                               data = ApiFunctions.createPdf417Data(emulation);                                             break;
            case 24:                               data = ApiFunctions.createQrCodeData(emulation);                                             break;
            case BLACK_MARK_MENU_INDEX:            data = ApiFunctions.createBlackMarkData(emulation, mBlackMarkType);                          break;
            case 26:                               data = ApiFunctions.createPageModeData(emulation, paperSize, getActivity());                 break;
            case PRINTABLE_AREA_MENU_INDEX:        data = ApiFunctions.createPrintableAreaData(emulation, mPrintableAreaType, getActivity());   break;
            case AUTOMATIC_PAGE_LENGTH_MENU_INDEX: data = ApiFunctions.createBezelAutomaticPageLengthData(emulation, mAutomaticPageLengthType); break;
            case LABEL_MENU_INDEX:                 data = ApiFunctions.createLabelData(emulation, mLabelType);                                  break;
        }

        Communication.sendCommands(this, data, settings.getPortName(), settings.getPortSettings(), 10000, 30000, getActivity(), mCallback);     // 10000mS!!!
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
