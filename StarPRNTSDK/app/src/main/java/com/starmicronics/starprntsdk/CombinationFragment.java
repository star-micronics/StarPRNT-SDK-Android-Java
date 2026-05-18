package com.starmicronics.starprntsdk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.starmicronics.starprntsdk.localizereceipts.ILocalizeReceipts;

public class CombinationFragment extends ItemListFragment {

    private int mLanguage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();
        mLanguage = intent.getIntExtra(CommonActivity.BUNDLE_KEY_LANGUAGE, PrinterSettingConstant.LANGUAGE_ENGLISH);

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        int paperSize = settings.getPaperSize();

        ILocalizeReceipts localizeReceipts = ILocalizeReceipts.createLocalizeReceipts(mLanguage, paperSize);
        String languageCode      = localizeReceipts.getLanguageCode();
        String paperSizeStr      = localizeReceipts.getPaperSizeStr();
        String scalePaperSizeStr = localizeReceipts.getScalePaperSizeStr();

        addTitle("StarIoExtManager Sample");
        addMenu(languageCode + " " + paperSizeStr      + " Text Receipt");
        addMenu(languageCode + " " + paperSizeStr      + " Text Receipt (UTF8)");
        addMenu(languageCode + " " + paperSizeStr      + " Raster Receipt");
        addMenu(languageCode + " " + scalePaperSizeStr + " Raster Receipt (Both Scale)");
        addMenu(languageCode + " " + scalePaperSizeStr + " Raster Receipt (Scale)");
        addMenu(languageCode                           + " Raster Coupon");
        addMenu(languageCode                           + " Raster Coupon (Rotation90)");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);

        Intent intent = new Intent(getActivity(), CommonActivity.class);
        intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_combination_ext);
        intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "Combination Ext");
        intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);
        intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_RELOAD_BUTTON, true);
        intent.putExtra(CommonActivity.BUNDLE_KEY_TEST_BUTTON_TEXT, "Print");
        intent.putExtra(CommonActivity.BUNDLE_KEY_SELECTED_INDEX, position);
        intent.putExtra(CommonActivity.BUNDLE_KEY_LANGUAGE, mLanguage);

        startActivity(intent);
    }
}