package com.starmicronics.starprntsdk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class JsonUtils {
    static String createJsonStringOfPrinterSettingList(List<PrinterSettings> printerSettingList) {
        JSONArray jsonArray = new JSONArray();

        try {
            for (PrinterSettings printerSettings : printerSettingList) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("ModelIndex",               printerSettings.getModelIndex());
                jsonObject.put("PortName",                 printerSettings.getPortName());
                jsonObject.put("PortSettings",             printerSettings.getPortSettings());
                jsonObject.put("MACAddress",               printerSettings.getMacAddress());
                jsonObject.put("ModelName",                printerSettings.getModelName());
                jsonObject.put("CashDrawerOpenActiveHigh", printerSettings.getCashDrawerOpenActiveHigh());
                jsonObject.put("PaperSize",                printerSettings.getPaperSize());

                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            // do nothing
        }

        return jsonArray.toString();
    }

    static List<PrinterSettings> createPrinterSettingListFromJsonString(String jsonString) {
        List<PrinterSettings> printerSettingList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                PrinterSettings printerSettings = new PrinterSettings(
                        jsonObject.getInt("ModelIndex"),
                        jsonObject.getString("PortName"),
                        jsonObject.getString("PortSettings"),
                        jsonObject.getString("MACAddress"),
                        jsonObject.getString("ModelName"),
                        jsonObject.getBoolean("CashDrawerOpenActiveHigh"),
                        jsonObject.getInt("PaperSize"));

                printerSettingList.add(printerSettings);
            }
        }
        catch (JSONException e) {
            // do nothing
        }

        return printerSettingList;
    }
}
