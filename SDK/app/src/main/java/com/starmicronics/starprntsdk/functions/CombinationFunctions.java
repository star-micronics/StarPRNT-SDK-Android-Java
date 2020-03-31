package com.starmicronics.starprntsdk.functions;

import android.content.res.Resources;
import android.graphics.Bitmap;

import com.starmicronics.starioextension.ICommandBuilder;
import com.starmicronics.starioextension.StarIoExt;
import com.starmicronics.starprntsdk.localizereceipts.ILocalizeReceipts;

import static com.starmicronics.starioextension.StarIoExt.Emulation;
import static com.starmicronics.starioextension.ICommandBuilder.BitmapConverterRotation;
import static com.starmicronics.starioextension.ICommandBuilder.CutPaperAction;
import static com.starmicronics.starioextension.ICommandBuilder.PeripheralChannel;

public class CombinationFunctions {

    public static byte[] createTextReceiptData(Emulation emulation, ILocalizeReceipts localizeReceipts, boolean utf8) {
        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        localizeReceipts.appendTextReceiptData(builder, utf8);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.appendPeripheral(PeripheralChannel.No1);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createRasterReceiptData(Emulation emulation, ILocalizeReceipts localizeReceipts, Resources resources) {
        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        Bitmap image = localizeReceipts.createRasterReceiptImage(resources);

        builder.appendBitmap(image, false);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.appendPeripheral(PeripheralChannel.No1);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createScaleRasterReceiptData(Emulation emulation, ILocalizeReceipts localizeReceipts, Resources resources, int width, boolean bothScale) {
        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        Bitmap image = localizeReceipts.createScaleRasterReceiptImage(resources);

        builder.appendBitmap(image, false, width, bothScale);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.appendPeripheral(PeripheralChannel.No1);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createCouponData(Emulation emulation, ILocalizeReceipts localizeReceipts, Resources resources, int width, BitmapConverterRotation rotation) {
        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        Bitmap image = localizeReceipts.createCouponImage(resources);

        builder.appendBitmap(image, false, width, true, rotation);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.appendPeripheral(PeripheralChannel.No1);

        builder.endDocument();

        return builder.getCommands();
    }
}
