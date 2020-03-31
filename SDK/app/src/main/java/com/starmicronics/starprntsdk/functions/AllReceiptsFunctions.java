package com.starmicronics.starprntsdk.functions;

import android.content.Context;
import android.graphics.Bitmap;

import com.starmicronics.cloudservices.AllReceipts;
import com.starmicronics.cloudservices.RequestCallback;
import com.starmicronics.starioextension.ICommandBuilder;
import com.starmicronics.starioextension.StarIoExt;
import com.starmicronics.starioextension.StarIoExt.Emulation;
import com.starmicronics.starprntsdk.localizereceipts.ILocalizeReceipts;

import com.starmicronics.starioextension.ICommandBuilder.CutPaperAction;

public class AllReceiptsFunctions {
    public static byte[] createRasterReceiptData(Context context, Emulation emulation, ILocalizeReceipts localizeReceipts, int width, boolean receipt, boolean info, boolean qrCode, RequestCallback callback) {
        Bitmap image = localizeReceipts.createRasterReceiptImage(context.getResources());

        String urlString = AllReceipts.uploadBitmap(context, image, callback);

        if (receipt || info || qrCode) {
            ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

            builder.beginDocument();

            if (receipt) {
                builder.appendBitmap(image, false);
            }

            if (info || qrCode) {
                byte[] data;

                if (emulation == Emulation.StarGraphic) {
                    data = AllReceipts.generateAllReceipts(context, urlString, emulation, info, qrCode, width); // Support to centering in Star Graphic.
                }
                else {
                    data = AllReceipts.generateAllReceipts(context, urlString, emulation, info, qrCode);        // Non support to centering in Star Graphic.
                }

                builder.appendRaw(data);
            }

            builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

            builder.endDocument();

            return builder.getCommands();
        }

        return null;
    }

    public static byte[] createScaleRasterReceiptData(Context context, Emulation emulation, ILocalizeReceipts localizeReceipts, int width, boolean bothScale, boolean receipt, boolean info, boolean qrCode, RequestCallback callback) {
        Bitmap image = localizeReceipts.createScaleRasterReceiptImage(context.getResources());

        String urlString = AllReceipts.uploadBitmap(context, image, callback);

        if (receipt || info || qrCode) {
            ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

            builder.beginDocument();

            if (receipt) {
                builder.appendBitmap(image, false, width, bothScale);
            }

            if (info || qrCode) {
                byte[] data;

                if (emulation == Emulation.StarGraphic) {
                    data = AllReceipts.generateAllReceipts(context, urlString, emulation, info, qrCode, width); // Support to centering in Star Graphic.
                }
                else {
                    data = AllReceipts.generateAllReceipts(context, urlString, emulation, info, qrCode);        // Non support to centering in Star Graphic.
                }

                builder.appendRaw(data);
            }

            builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

            builder.endDocument();

            return builder.getCommands();
        }

        return null;
    }

    public static byte[] createTextReceiptData(Context context, Emulation emulation, ILocalizeReceipts localizeReceipts, int width, boolean utf8, boolean receipt, boolean info, boolean qrCode, RequestCallback callback) {
        ICommandBuilder uploadDataBuilder = StarIoExt.createCommandBuilder(emulation);

        uploadDataBuilder.beginDocument();

        localizeReceipts.appendTextReceiptData(uploadDataBuilder, utf8);

//        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);  // No need.

        uploadDataBuilder.endDocument();

        byte[] receiptsData = uploadDataBuilder.getCommands();

        String urlString = AllReceipts.uploadData(context, receiptsData, emulation, localizeReceipts.getCharacterCode(), width, callback);

        if (receipt || info || qrCode) {
            ICommandBuilder printDataBuilder = StarIoExt.createCommandBuilder(emulation);

            printDataBuilder.beginDocument();

            if (receipt) {
                localizeReceipts.appendTextReceiptData(printDataBuilder, utf8);
            }

            if (info || qrCode) {
                byte[] allReceiptsData = AllReceipts.generateAllReceipts(context, urlString, emulation, info, qrCode);

                printDataBuilder.appendRaw(allReceiptsData);
            }

            printDataBuilder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

            printDataBuilder.endDocument();

            return printDataBuilder.getCommands();
        }

        return null;
    }
}
