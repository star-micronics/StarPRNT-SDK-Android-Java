package com.starmicronics.starprntsdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.List;

/**
 * Printer information manager class.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class PrinterSettingManager {

    public static final String PREF_KEY_PRINTER_SETTINGS_JSON = "pref_key_printer_settings_json";

    private Context mContext;
    private List<PrinterSettings> mPrinterSettingsList;

    PrinterSettingManager(Context context) {
        mContext = context;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);

        if (!prefs.contains(PREF_KEY_PRINTER_SETTINGS_JSON)) {
            prefs.edit()
                 .clear()
                 .apply();
        }

        mPrinterSettingsList = JsonUtils.createPrinterSettingListFromJsonString(prefs.getString(PREF_KEY_PRINTER_SETTINGS_JSON, ""));
    }

    public void storePrinterSettings(int index, PrinterSettings settings) {
        if (mPrinterSettingsList.size() > 1) {
            mPrinterSettingsList.remove(index);
        }

        mPrinterSettingsList.add(index, settings);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);

        prefs.edit()
             .putString(PREF_KEY_PRINTER_SETTINGS_JSON, JsonUtils.createJsonStringOfPrinterSettingList(mPrinterSettingsList))
             .apply();
    }

    public PrinterSettings getPrinterSettings() {
        if (mPrinterSettingsList.isEmpty()) {
            return null;
        }

        return mPrinterSettingsList.get(0);
    }

    public PrinterSettings getPrinterSettings(int index) {
        if (mPrinterSettingsList.isEmpty() || (mPrinterSettingsList.size() - 1) < index) {
            return null;
        }

        return mPrinterSettingsList.get(index);
    }

    public List<PrinterSettings> getPrinterSettingsList() {
        return mPrinterSettingsList;
    }
}
