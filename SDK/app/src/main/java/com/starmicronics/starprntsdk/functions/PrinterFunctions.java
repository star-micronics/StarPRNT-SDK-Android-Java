package com.starmicronics.starprntsdk.functions;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;

import com.starmicronics.starioextension.IBezelCommandBuilder;
import com.starmicronics.starioextension.ICommandBuilder;
import com.starmicronics.starioextension.StarIoExt;
import com.starmicronics.starprntsdk.localizereceipts.ILocalizeReceipts;

import java.util.ArrayList;
import java.util.List;

import com.starmicronics.starioextension.StarIoExt.Emulation;
import com.starmicronics.starioextension.ICommandBuilder.CutPaperAction;
import com.starmicronics.starioextension.ICommandBuilder.BlackMarkType;
import com.starmicronics.starioextension.ICommandBuilder.BitmapConverterRotation;
import com.starmicronics.starioextension.ICommandBuilder.AlignmentPosition;
import com.starmicronics.starioextension.ICommandBuilder.HoldPrintType;
import com.starmicronics.starioextension.ICommandBuilder.PaperPresentStatusType;


import com.starmicronics.starioextension.IBezelCommandBuilder.Mode;

public class PrinterFunctions {

    public static byte[] createTextReceiptData(Emulation emulation, ILocalizeReceipts localizeReceipts, boolean utf8) {
        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        localizeReceipts.appendTextReceiptData(builder, utf8);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createRasterReceiptData(Emulation emulation, ILocalizeReceipts localizeReceipts, Resources resources) {
        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        Bitmap image = localizeReceipts.createRasterReceiptImage(resources);

        builder.appendBitmap(image, false);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createScaleRasterReceiptData(Emulation emulation, ILocalizeReceipts localizeReceipts, Resources resources, int width, boolean bothScale) {
        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        Bitmap image = localizeReceipts.createScaleRasterReceiptImage(resources);

        builder.appendBitmap(image, false, width, bothScale);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createCouponData(Emulation emulation, ILocalizeReceipts localizeReceipts, Resources resources, int width, BitmapConverterRotation rotation) {
        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        Bitmap image = localizeReceipts.createCouponImage(resources);

        builder.appendBitmap(image, false, width, true, rotation);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createRasterData(Emulation emulation, Bitmap bitmap, int width, boolean bothScale) {
        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.appendBitmap(bitmap, true, width, bothScale);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createTextBlackMarkData(Emulation emulation, ILocalizeReceipts localizeReceipts, BlackMarkType type, boolean utf8) {
        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.appendBlackMark(type);

        localizeReceipts.appendTextLabelData(builder, utf8);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

//      builder.appendBlackMark(BlackMarkType.Invalid);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createPasteTextBlackMarkData(Emulation emulation, ILocalizeReceipts localizeReceipts, String pasteText, boolean doubleHeight, BlackMarkType type, boolean utf8) {
        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.appendBlackMark(type);

        if (doubleHeight) {
            builder.appendMultipleHeight(2);

            localizeReceipts.appendPasteTextLabelData(builder, pasteText, utf8);

            builder.appendMultipleHeight(1);
        }
        else {
            localizeReceipts.appendPasteTextLabelData(builder, pasteText, utf8);
        }

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

//      builder.appendBlackMark(BlackMarkType.Invalid);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createTextPageModeData(Emulation emulation, ILocalizeReceipts localizeReceipts, Rect rect, BitmapConverterRotation rotation, boolean utf8) {
        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.beginPageMode(rect, rotation);

        localizeReceipts.appendTextLabelData(builder, utf8);

        builder.endPageMode();

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static List<byte[]> createHoldPrintData(Emulation emulation, boolean[] isHoldArray) {
        List<byte[]> commandList = new ArrayList<>();

        for (int i = 0; i < isHoldArray.length; i++) {
            ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

            builder.beginDocument();

            IBezelCommandBuilder bezelBuilder = StarIoExt.createBezelCommandBuilder(emulation);

            // Enable bezel.
            bezelBuilder.appendBezelMode(Mode.Valid);

            builder.append(bezelBuilder.getCommands());

            // Disable hold print controlled by printer firmware.
            builder.appendHoldPrint(HoldPrintType.Invalid);

            if (isHoldArray[i]) {
                // Enable paper present status if wait paper removal before next printing.
                builder.appendPaperPresentStatus(PaperPresentStatusType.Valid);
            }
            else {
                // Disable paper present status if do not wait paper removal before next printing.
                builder.appendPaperPresentStatus(PaperPresentStatusType.Invalid);
            }

            // Create commands for printing.
            builder.appendAlignment(AlignmentPosition.Center);

            builder.append("\n------------------------------------\n\n\n\n\n\n".getBytes());

            builder.appendMultiple(3, 3);

            builder.append("Page ".getBytes());
            builder.append(String.valueOf(i + 1).getBytes());

            builder.appendMultiple(1, 1);

            builder.append("\n\n\n\n\n----------------------------------\n".getBytes());

            builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

            builder.endDocument();

            commandList.add(builder.getCommands());
        }

        return commandList;
    }
}
