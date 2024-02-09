package com.starmicronics.starprntsdk.functions;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.starmicronics.starioextension.IBezelCommandBuilder;
import com.starmicronics.starioextension.ICommandBuilder;
import com.starmicronics.starioextension.StarIoExt;
import com.starmicronics.starprntsdk.ModelCapability;
import com.starmicronics.starprntsdk.R;

import java.io.UnsupportedEncodingException;

import static com.starmicronics.starioextension.StarIoExt.Emulation;
import static com.starmicronics.starioextension.ICommandBuilder.CodePageType;
import static com.starmicronics.starioextension.ICommandBuilder.AlignmentPosition;
import static com.starmicronics.starioextension.ICommandBuilder.BitmapConverterRotation;
import static com.starmicronics.starioextension.ICommandBuilder.CutPaperAction;
import static com.starmicronics.starioextension.ICommandBuilder.FontStyleType;
import static com.starmicronics.starioextension.ICommandBuilder.InitializationType;
import static com.starmicronics.starioextension.ICommandBuilder.InternationalType;
import static com.starmicronics.starioextension.ICommandBuilder.LogoSize;
import static com.starmicronics.starioextension.ICommandBuilder.PeripheralChannel;
import static com.starmicronics.starioextension.ICommandBuilder.QrCodeLevel;
import static com.starmicronics.starioextension.ICommandBuilder.QrCodeModel;
import static com.starmicronics.starioextension.ICommandBuilder.SoundChannel;
import static com.starmicronics.starioextension.ICommandBuilder.Pdf417Level;
import static com.starmicronics.starioextension.ICommandBuilder.BarcodeSymbology;
import static com.starmicronics.starioextension.ICommandBuilder.BarcodeWidth;
import static com.starmicronics.starioextension.ICommandBuilder.BlackMarkType;
import static com.starmicronics.starioextension.ICommandBuilder.PrintableAreaType;
import static com.starmicronics.starioextension.ICommandBuilder.LabelType;
import static com.starmicronics.starioextension.IBezelCommandBuilder.AutomaticPageLengthType;
import static com.starmicronics.starioextension.IBezelCommandBuilder.Mode;

public class ApiFunctions {

    public static byte[] createGenericData(Emulation emulation) {
        byte[] data = "Hello World.".getBytes();

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.append(data);
        builder.append((byte) 0x0a);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createFontStyleData(Emulation emulation) {
        byte[] data = "Hello World.\n".getBytes();

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.append(data);
        builder.appendFontStyle(FontStyleType.B);
        builder.append(data);
        builder.appendFontStyle(FontStyleType.A);
        builder.append(data);
        builder.appendFontStyle(FontStyleType.B);
        builder.append(data);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createInitializationData(Emulation emulation) {
        byte[] data = "Hello World.\n".getBytes();

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.append(data);
        builder.appendMultiple(2, 2);
        builder.append(data);
        builder.appendFontStyle(FontStyleType.B);
        builder.append(data);
        builder.appendInitialization(InitializationType.Command);
        builder.append(data);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createCodePageData(Emulation emulation) {
        byte[] bytes2 = new byte[]{ 0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x2a, 0x2b, 0x2c, 0x2d, 0x2e, 0x2f, 0x0a };
        byte[] bytes3 = new byte[]{ 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x3a, 0x3b, 0x3c, 0x3d, 0x3e, 0x3f, 0x0a };
        byte[] bytes4 = new byte[]{ 0x40, 0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48, 0x49, 0x4a, 0x4b, 0x4c, 0x4d, 0x4e, 0x4f, 0x0a };
        byte[] bytes5 = new byte[]{ 0x50, 0x51, 0x52, 0x53, 0x54, 0x55, 0x56, 0x57, 0x58, 0x59, 0x5a, 0x5b, 0x5c, 0x5d, 0x5e, 0x5f, 0x0a };
        byte[] bytes6 = new byte[]{ 0x60, 0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6a, 0x6b, 0x6c, 0x6d, 0x6e, 0x6f, 0x0a };
        byte[] bytes7 = new byte[]{ 0x70, 0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7a, 0x7b, 0x7c, 0x7d, 0x7e, 0x7f, 0x0a };
        byte[] bytes8 = new byte[]{ (byte) 0x80, (byte) 0x81, (byte) 0x82, (byte) 0x83, (byte) 0x84, (byte) 0x85, (byte) 0x86, (byte) 0x87, (byte) 0x88, (byte) 0x89, (byte) 0x8a, (byte) 0x8b, (byte) 0x8c, (byte) 0x8d, (byte) 0x8e, (byte) 0x8f, 0x0a };
        byte[] bytes9 = new byte[]{ (byte) 0x90, (byte) 0x91, (byte) 0x92, (byte) 0x93, (byte) 0x94, (byte) 0x95, (byte) 0x96, (byte) 0x97, (byte) 0x98, (byte) 0x99, (byte) 0x9a, (byte) 0x9b, (byte) 0x9c, (byte) 0x9d, (byte) 0x9e, (byte) 0x9f, 0x0a };
        byte[] bytesA = new byte[]{ (byte) 0xa0, (byte) 0xa1, (byte) 0xa2, (byte) 0xa3, (byte) 0xa4, (byte) 0xa5, (byte) 0xa6, (byte) 0xa7, (byte) 0xa8, (byte) 0xa9, (byte) 0xaa, (byte) 0xab, (byte) 0xac, (byte) 0xad, (byte) 0xae, (byte) 0xaf, 0x0a };
        byte[] bytesB = new byte[]{ (byte) 0xb0, (byte) 0xb1, (byte) 0xb2, (byte) 0xb3, (byte) 0xb4, (byte) 0xb5, (byte) 0xb6, (byte) 0xb7, (byte) 0xb8, (byte) 0xb9, (byte) 0xba, (byte) 0xbb, (byte) 0xbc, (byte) 0xbd, (byte) 0xbe, (byte) 0xbf, 0x0a };
        byte[] bytesC = new byte[]{ (byte) 0xc0, (byte) 0xc1, (byte) 0xc2, (byte) 0xc3, (byte) 0xc4, (byte) 0xc5, (byte) 0xc6, (byte) 0xc7, (byte) 0xc8, (byte) 0xc9, (byte) 0xca, (byte) 0xcb, (byte) 0xcc, (byte) 0xcd, (byte) 0xce, (byte) 0xcf, 0x0a };
        byte[] bytesD = new byte[]{ (byte) 0xd0, (byte) 0xd1, (byte) 0xd2, (byte) 0xd3, (byte) 0xd4, (byte) 0xd5, (byte) 0xd6, (byte) 0xd7, (byte) 0xd8, (byte) 0xd9, (byte) 0xda, (byte) 0xdb, (byte) 0xdc, (byte) 0xdd, (byte) 0xde, (byte) 0xdf, 0x0a };
        byte[] bytesE = new byte[]{ (byte) 0xe0, (byte) 0xe1, (byte) 0xe2, (byte) 0xe3, (byte) 0xe4, (byte) 0xe5, (byte) 0xe6, (byte) 0xe7, (byte) 0xe8, (byte) 0xe9, (byte) 0xea, (byte) 0xeb, (byte) 0xec, (byte) 0xed, (byte) 0xee, (byte) 0xef, 0x0a };
        byte[] bytesF = new byte[]{ (byte) 0xf0, (byte) 0xf1, (byte) 0xf2, (byte) 0xf3, (byte) 0xf4, (byte) 0xf5, (byte) 0xf6, (byte) 0xf7, (byte) 0xf8, (byte) 0xf9, (byte) 0xfa, (byte) 0xfb, (byte) 0xfc, (byte) 0xfd, (byte) 0xfe, (byte) 0xff, 0x0a };

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.appendCodePage(CodePageType.CP998); builder.append("*CP998*\n".getBytes());

        builder.append(bytes2);
        builder.append(bytes3);
        builder.append(bytes4);
        builder.append(bytes5);
        builder.append(bytes6);
        builder.append(bytes7);
        builder.append(bytes8);
        builder.append(bytes9);
        builder.append(bytesA);
        builder.append(bytesB);
        builder.append(bytesC);
        builder.append(bytesD);
        builder.append(bytesE);
        builder.append(bytesF);

        builder.append("\n".getBytes());

//        builder.appendCodePage(CodePageType.CP437); builder.append("*CP437*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP737); builder.append("*CP737*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP772); builder.append("*CP774*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP774); builder.append("*CP774*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP851); builder.append("*CP851*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP852); builder.append("*CP852*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP855); builder.append("*CP855*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP857); builder.append("*CP857*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP858); builder.append("*CP858*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP860); builder.append("*CP860*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP861); builder.append("*CP861*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP862); builder.append("*CP862*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP863); builder.append("*CP863*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP864); builder.append("*CP864*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP865); builder.append("*CP865*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP866); builder.append("*CP866*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP869); builder.append("*CP869*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP874); builder.append("*CP874*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP928); builder.append("*CP928*\n".getBytes());
        builder.appendCodePage(CodePageType.CP932); builder.append("*CP932*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP998); builder.append("*CP998*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP999); builder.append("*CP999*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP1001); builder.append("*CP1001*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP1250); builder.append("*CP1250*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP1251); builder.append("*CP1251*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP1252); builder.append("*CP1252*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP2001); builder.append("*CP2001*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP3001); builder.append("*CP3001*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP3002); builder.append("*CP3002*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP3011); builder.append("*CP3011*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP3012); builder.append("*CP3012*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP3021); builder.append("*CP3021*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP3041); builder.append("*CP3041*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP3840); builder.append("*CP3840*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP3841); builder.append("*CP3841*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP3843); builder.append("*CP3843*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP3844); builder.append("*CP3844*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP3845); builder.append("*CP3845*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP3846); builder.append("*CP3846*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP3847); builder.append("*CP3847*\n".getBytes());
//        builder.appendCodePage(CodePageType.CP3848); builder.append("*CP3848*\n".getBytes());
//        builder.appendCodePage(CodePageType.UTF8); builder.append("*UTF8*\n".getBytes());
//        builder.appendCodePage(CodePageType.Blank); builder.append("*Blank*\n".getBytes());

        builder.append(bytes2);
        builder.append(bytes3);
        builder.append(bytes4);
        builder.append(bytes5);
        builder.append(bytes6);
        builder.append(bytes7);
        builder.append(bytes8);
        builder.append(bytes9);
        builder.append(bytesA);
        builder.append(bytesB);
        builder.append(bytesC);
        builder.append(bytesD);
        builder.append(bytesE);
        builder.append(bytesF);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createInternationalData(Emulation emulation) {
        byte[] bytes = new byte[]{ 0x23, 0x24, 0x40, 0x58, 0x5a, 0x5b, 0x5c, 0x5d, 0x5e, 0x60, 0x7b, 0x7c, 0x7d, 0x7e, 0x0a };

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.append("*USA*\n".getBytes());
        builder.appendInternational(InternationalType.USA);
        builder.append(bytes);

        builder.append("*France*\n".getBytes());
        builder.appendInternational(InternationalType.France);
        builder.append(bytes);

        builder.append("*Germany*\n".getBytes());
        builder.appendInternational(InternationalType.Germany);
        builder.append(bytes);

        builder.append("*UK*\n".getBytes());
        builder.appendInternational(InternationalType.UK);
        builder.append(bytes);

        builder.append("*Denmark*\n".getBytes());
        builder.appendInternational(InternationalType.Denmark);
        builder.append(bytes);

        builder.append("*Sweden*\n".getBytes());
        builder.appendInternational(InternationalType.Sweden);
        builder.append(bytes);

        builder.append("*Italy*\n".getBytes());
        builder.appendInternational(InternationalType.Italy);
        builder.append(bytes);

        builder.append("*Spain*\n".getBytes());
        builder.appendInternational(InternationalType.Spain);
        builder.append(bytes);

        builder.append("*Japan*\n".getBytes());
        builder.appendInternational(InternationalType.Japan);
        builder.append(bytes);

        builder.append("*Norway*\n".getBytes());
        builder.appendInternational(InternationalType.Norway);
        builder.append(bytes);

        builder.append("*Denmark2*\n".getBytes());
        builder.appendInternational(InternationalType.Denmark2);
        builder.append(bytes);

        builder.append("*Spain2*\n".getBytes());
        builder.appendInternational(InternationalType.Spain2);
        builder.append(bytes);

        builder.append("*LatinAmerica*\n".getBytes());
        builder.appendInternational(InternationalType.LatinAmerica);
        builder.append(bytes);

        builder.append("*Korea*\n".getBytes());
        builder.appendInternational(InternationalType.Korea);
        builder.append(bytes);

        builder.append("*Ireland*\n".getBytes());
        builder.appendInternational(InternationalType.Ireland);
        builder.append(bytes);

        builder.append("*Legal*\n".getBytes());
        builder.appendInternational(InternationalType.Legal);
        builder.append(bytes);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createFeedData(Emulation emulation) {
        byte[] data       = "Hello World.".getBytes();
        byte[] dataWithLf = "Hello World.\n".getBytes();

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.append(data);
        builder.appendLineFeed();

        builder.appendLineFeed(data);

        builder.append(data);
        builder.appendLineFeed(2);

        builder.appendLineFeed(data, 2);

        builder.append(data);
        builder.appendUnitFeed(64);

        builder.appendUnitFeed(data, 64);

        builder.append(dataWithLf);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createCharacterSpaceData(Emulation emulation) {
        byte[] data = "Hello World.".getBytes();

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.appendCharacterSpace(0);
        builder.appendLineFeed(data);
        builder.appendCharacterSpace(1);
        builder.appendLineFeed(data);
        builder.appendCharacterSpace(2);
        builder.appendLineFeed(data);
        builder.appendCharacterSpace(3);
        builder.appendLineFeed(data);
        builder.appendCharacterSpace(4);
        builder.appendLineFeed(data);
        builder.appendCharacterSpace(5);
        builder.appendLineFeed(data);
        builder.appendCharacterSpace(6);
        builder.appendLineFeed(data);
        builder.appendCharacterSpace(7);
        builder.appendLineFeed(data);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createLineSpaceData(Emulation emulation) {
        byte[] data = "Hello World.".getBytes();

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.appendLineSpace(32);
        builder.appendLineFeed(data);
        builder.appendLineFeed(data);
        builder.appendLineFeed(data);
        builder.appendLineSpace(24);
        builder.appendLineFeed(data);
        builder.appendLineFeed(data);
        builder.appendLineFeed(data);
        builder.appendLineSpace(32);
        builder.appendLineFeed(data);
        builder.appendLineFeed(data);
        builder.appendLineFeed(data);
        builder.appendLineSpace(24);
        builder.appendLineFeed(data);
        builder.appendLineFeed(data);
        builder.appendLineFeed(data);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createTopMarginData(int modelIndex, Emulation emulation) {
        byte[] data = "Hello World.\n".getBytes();

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        if (modelIndex == ModelCapability.MC_LABEL3) {
            builder.appendTopMargin(3);
            builder.append("*Top margin:3mm*\n".getBytes());
            builder.append(data);
            builder.append(data);
            builder.append(data);

            builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

            builder.appendTopMargin(11);    // Default
            builder.append("*Top margin:11mm*\n".getBytes());
            builder.append(data);
            builder.append(data);
            builder.append(data);

            builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);
        }
        else {
            builder.appendTopMargin(2);
            builder.append("*Top margin:2mm*\n".getBytes());
            builder.append(data);
            builder.append(data);
            builder.append(data);

            builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

            builder.appendTopMargin(5);
            builder.append("*Top margin:5mm*\n".getBytes());
            builder.append(data);
            builder.append(data);
            builder.append(data);

            builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

            builder.appendTopMargin(11);    // Default
            builder.append("*Top margin:11mm*\n".getBytes());
            builder.append(data);
            builder.append(data);
            builder.append(data);

            builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);
        }

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createEmphasisData(Emulation emulation) {
        byte[] data      = "Hello World.\n".getBytes();
        byte[] dataHalf0 = "Hello ".getBytes();
        byte[] dataHalf1 = "World.\n".getBytes();

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.append(data);

        builder.appendEmphasis(true);
        builder.append(data);
        builder.appendEmphasis(false);
        builder.append(data);

        builder.appendEmphasis(data);
        builder.append(data);

        builder.appendEmphasis(dataHalf0);
        builder.append(dataHalf1);

        builder.append(dataHalf0);
        builder.appendEmphasis(dataHalf1);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createInvertData(Emulation emulation) {
        byte[] data      = "Hello World.\n".getBytes();
        byte[] dataHalf0 = "Hello ".getBytes();
        byte[] dataHalf1 = "World.\n".getBytes();

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.append(data);

        builder.appendInvert(true);
        builder.append(data);
        builder.appendInvert(false);
        builder.append(data);

        builder.appendInvert(data);
        builder.append(data);

        builder.appendInvert(dataHalf0);
        builder.append(dataHalf1);

        builder.append(dataHalf0);
        builder.appendInvert(dataHalf1);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createUnderLineData(Emulation emulation) {
        byte[] data      = "Hello World.\n".getBytes();
        byte[] dataHalf0 = "Hello ".getBytes();
        byte[] dataHalf1 = "World.\n".getBytes();

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.append(data);

        builder.appendUnderLine(true);
        builder.append(data);
        builder.appendUnderLine(false);
        builder.append(data);

        builder.appendUnderLine(data);
        builder.append(data);

        builder.appendUnderLine(dataHalf0);
        builder.append(dataHalf1);

        builder.append(dataHalf0);
        builder.appendUnderLine(dataHalf1);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createMultipleData(Emulation emulation) {
        byte[] data      = "Hello World.\n".getBytes();
        byte[] dataHalf0 = "Hello ".getBytes();
        byte[] dataHalf1 = "World.\n".getBytes();

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.append(data);

        builder.appendMultiple(2, 2);
        builder.append(data);
        builder.appendMultiple(1, 1);
        builder.append(data);

        builder.appendMultiple(data, 2, 2);
        builder.append(data);

        builder.appendMultiple(dataHalf0, 2, 2);
        builder.append(dataHalf1);
        builder.append(dataHalf0);
        builder.appendMultiple(dataHalf1, 2, 2);

        builder.appendMultipleHeight(2);
        builder.append(data);
        builder.appendMultipleHeight(1);
        builder.append(data);

        builder.appendMultipleHeight(dataHalf0, 2);
        builder.append(dataHalf1);
        builder.append(dataHalf0);
        builder.appendMultipleHeight(dataHalf1, 2);

        builder.appendMultipleWidth(2);
        builder.append(data);
        builder.appendMultipleWidth(1);
        builder.append(data);

        builder.appendMultipleWidth(dataHalf0, 2);
        builder.append(dataHalf1);
        builder.append(dataHalf0);
        builder.appendMultipleWidth(dataHalf1, 2);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createAbsolutePositionData(Emulation emulation) {
        byte[] data = "Hello World.\n".getBytes();

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.append(data);

        builder.appendAbsolutePosition(40);
        builder.append(data);
        builder.append(data);

        builder.appendAbsolutePosition(data, 40);
        builder.append(data);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createAlignmentData(Emulation emulation) {
        byte[] data = "Hello World.\n".getBytes();

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.append(data);

        builder.appendAlignment(AlignmentPosition.Center);
        builder.append(data);
        builder.appendAlignment(AlignmentPosition.Right);
        builder.append(data);
        builder.appendAlignment(AlignmentPosition.Left);
        builder.append(data);

        builder.appendAlignment(data, AlignmentPosition.Center);
        builder.appendAlignment(data, AlignmentPosition.Right);
        builder.append(data);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createHorizontalTabPositionData(Emulation emulation) {
        byte[] data1 = "QTY\tITEM\tTOTAL\n".getBytes();
        byte[] data2 = "1\tApple\t1.50\n".getBytes();
        byte[] data3 = "2\tOrange\t2.00\n".getBytes();
        byte[] data4 = "5\tBanana\t3.00\n".getBytes();

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.appendHorizontalTabPosition(new int[]{ 5, 24 });

        builder.append("*Tab Position:5, 24*\n".getBytes());
        builder.append(data1);
        builder.append(data2);
        builder.append(data3);
        builder.append(data4);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createLogoData(Emulation emulation) {
        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.append("*Normal*\n".getBytes());
        builder.appendLogo(LogoSize.Normal, 1);

        builder.append("\n*Double Width*\n".getBytes());
        builder.appendLogo(LogoSize.DoubleWidth, 1);

        builder.append("\n*Double Height*\n".getBytes());
        builder.appendLogo(LogoSize.DoubleHeight, 1);

        builder.append("\n*Double Width and Double Height*\n".getBytes());
        builder.appendLogo(LogoSize.DoubleWidthDoubleHeight, 1);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createCutPaperData(Emulation emulation) {
        byte[] data = "Hello World.\n".getBytes();

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);

        builder.appendCutPaper(CutPaperAction.FullCut);

        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);

        builder.appendCutPaper(CutPaperAction.PartialCut);

        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);

        builder.appendCutPaper(CutPaperAction.FullCutWithFeed);

        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);
        builder.append(data);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createPeripheralData(Emulation emulation) {
        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.appendPeripheral(PeripheralChannel.No1);
        builder.appendPeripheral(PeripheralChannel.No2);
        builder.appendPeripheral(PeripheralChannel.No1, 2000);
        builder.appendPeripheral(PeripheralChannel.No2, 2000);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createSoundData(Emulation emulation) {
        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.appendSound(SoundChannel.No1);
        builder.appendSound(SoundChannel.No2);
        builder.appendSound(SoundChannel.No1, 3);
        builder.appendSound(SoundChannel.No2, 3);
        builder.appendSound(SoundChannel.No1, 1, 1000, 1000);
        builder.appendSound(SoundChannel.No2, 1, 1000, 1000);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createBitmapData(Emulation emulation, int width, Context context) {
        Bitmap sphereImage   = BitmapFactory.decodeResource(context.getResources(), R.drawable.sphere_image);
        Bitmap starLogoImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.star_logo_image);

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.append("*diffusion:true*\n".getBytes());
        builder.appendBitmap(sphereImage, true);
        builder.append("\n*diffusion:false*\n".getBytes());
        builder.appendBitmap(sphereImage, false);

        builder.append("\n*Normal*\n".getBytes());
        builder.appendBitmap(starLogoImage, true);

        builder.append("\n*width:Full, bothScale:true*\n".getBytes());
        builder.appendBitmap(starLogoImage, true, width, true);
        builder.append("\n*width:Full, bothScale:false*\n".getBytes());
        builder.appendBitmap(starLogoImage, true, width, false);

        builder.append("\n*Right90*\n".getBytes());
        builder.appendBitmap(starLogoImage, true, BitmapConverterRotation.Right90);
        builder.append("\n*Rotate180*\n".getBytes());
        builder.appendBitmap(starLogoImage, true, BitmapConverterRotation.Rotate180);
//      builder.append("\n*Left90*\n".getBytes());
//      builder.appendBitmap(starLogoImage, true, BitmapConverterRotation.Left90);

        builder.append("\n*Normal,    AbsolutePosition:40*\n".getBytes());
        builder.appendBitmapWithAbsolutePosition(starLogoImage, true, 40);
//      builder.append("\n*Right90,   AbsolutePosition:40*\n".getBytes());
//      builder.appendBitmapWithAbsolutePosition(starLogoImage, true, BitmapConverterRotation.Right90, 40);
//      builder.append("\n*Rotate180, AbsolutePosition:40*\n".getBytes());
//      builder.appendBitmapWithAbsolutePosition(starLogoImage, true, BitmapConverterRotation.Rotate180, 40);
//      builder.append("\n*Left90,    AbsolutePosition:40*\n".getBytes());
//      builder.appendBitmapWithAbsolutePosition(starLogoImage, true, BitmapConverterRotation.Left90, 40);

        builder.append("\n*Normal,    Alignment:Center*\n".getBytes());
        builder.appendBitmapWithAlignment(starLogoImage, true, AlignmentPosition.Center);
//      builder.append("\n*Right90,   Alignment:Center*\n".getBytes());
//      builder.appendBitmapWithAlignment(starLogoImage, true, BitmapConverterRotation.Right90, AlignmentPosition.Center);
//      builder.append("\n*Rotate180, Alignment:Center*\n".getBytes());
//      builder.appendBitmapWithAlignment(starLogoImage, true, BitmapConverterRotation.Rotate180, AlignmentPosition.Center);
//      builder.append("\n*Left90,    Alignment:Center*\n".getBytes());
//      builder.appendBitmapWithAlignment(starLogoImage, true, BitmapConverterRotation.Left90, AlignmentPosition.Center);

        builder.append("\n*Normal,    Alignment:Right*\n".getBytes());
        builder.appendBitmapWithAlignment(starLogoImage, true, AlignmentPosition.Right);
//      builder.append("\n*Right90,   Alignment:Right*\n".getBytes());
//      builder.appendBitmapWithAlignment(starLogoImage, true, BitmapConverterRotation.Right90, AlignmentPosition.Right);
//      builder.append("\n*Rotate180, Alignment:Right*\n".getBytes());
//      builder.appendBitmapWithAlignment(starLogoImage, true, BitmapConverterRotation.Rotate180, AlignmentPosition.Right);
//      builder.append("\n*Left90,    Alignment:Right*\n".getBytes());
//      builder.appendBitmapWithAlignment(starLogoImage, true, BitmapConverterRotation.Left90, AlignmentPosition.Right);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createBarcodeData(Emulation emulation) {
        byte[] dataUpcE    = "01234500006" .getBytes();
        byte[] dataUpcA    = "01234567890" .getBytes();
        byte[] dataJan8    = "0123456"     .getBytes();
        byte[] dataJan13   = "012345678901".getBytes();
        byte[] dataCode39  = "0123456789"  .getBytes();
        byte[] dataItf     = "0123456789"  .getBytes();
        byte[] dataCode128 = "{B0123456789".getBytes();
        byte[] dataCode93  = "0123456789"  .getBytes();
        byte[] dataNw7     = "A0123456789B".getBytes();

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.append("*UPCE*\n".getBytes());
        builder.appendBarcode(dataUpcE, BarcodeSymbology.UPCE, BarcodeWidth.Mode1, 40, true);
        builder.appendUnitFeed(32);
        builder.append("\n*UPCA*\n".getBytes());
        builder.appendBarcode(dataUpcA, BarcodeSymbology.UPCA, BarcodeWidth.Mode1, 40, true);
        builder.appendUnitFeed(32);
        builder.append("\n*JAN8*\n".getBytes());
        builder.appendBarcode(dataJan8, BarcodeSymbology.JAN8, BarcodeWidth.Mode1, 40, true);
        builder.appendUnitFeed(32);
        builder.append("\n*JAN13*\n".getBytes());
        builder.appendBarcode(dataJan13, BarcodeSymbology.JAN13, BarcodeWidth.Mode1, 40, true);
        builder.appendUnitFeed(32);
        builder.append("\n*Code39*\n".getBytes());
        builder.appendBarcode(dataCode39, BarcodeSymbology.Code39, BarcodeWidth.Mode1, 40, true);
        builder.appendUnitFeed(32);
        builder.append("\n*ITF*\n".getBytes());
        builder.appendBarcode(dataItf, BarcodeSymbology.ITF, BarcodeWidth.Mode1, 40, true);
        builder.appendUnitFeed(32);
        builder.append("\n*Code128*\n".getBytes());
        builder.appendBarcode(dataCode128, BarcodeSymbology.Code128, BarcodeWidth.Mode1, 40, true);
        builder.appendUnitFeed(32);
        builder.append("\n*Code93*\n".getBytes());
        builder.appendBarcode(dataCode93, BarcodeSymbology.Code93, BarcodeWidth.Mode1, 40, true);
        builder.appendUnitFeed(32);
        builder.append("\n*NW7*\n".getBytes());
        builder.appendBarcode(dataNw7, BarcodeSymbology.NW7, BarcodeWidth.Mode1, 40, true);
        builder.appendUnitFeed(32);

        builder.append("*HRI:NO*\n".getBytes());
        builder.appendBarcode(dataUpcE, BarcodeSymbology.UPCE, BarcodeWidth.Mode1, 40, false);
        builder.appendUnitFeed(32);
        builder.append("*Mode:1*\n".getBytes());
        builder.appendBarcode(dataUpcE, BarcodeSymbology.UPCE, BarcodeWidth.Mode1, 40, true);
        builder.appendUnitFeed(32);
        builder.append("*Mode:2*\n".getBytes());
        builder.appendBarcode(dataUpcE, BarcodeSymbology.UPCE, BarcodeWidth.Mode2, 40, true);
        builder.appendUnitFeed(32);
        builder.append("*Mode:3*\n".getBytes());
        builder.appendBarcode(dataUpcE, BarcodeSymbology.UPCE, BarcodeWidth.Mode3, 40, true);
        builder.appendUnitFeed(32);

        builder.append("\n*AbsolutePosition:40*\n".getBytes());
        builder.appendBarcodeWithAbsolutePosition(dataUpcE, BarcodeSymbology.UPCE, BarcodeWidth.Mode1, 40, true, 40);
        builder.appendUnitFeed(32);

        builder.append("\n*Alignment:Center*\n".getBytes());
        builder.appendBarcodeWithAlignment(dataUpcE, BarcodeSymbology.UPCE, BarcodeWidth.Mode1, 40, true, AlignmentPosition.Center);
        builder.appendUnitFeed(32);
        builder.append("\n*Alignment:Right*\n" .getBytes());
        builder.appendBarcodeWithAlignment(dataUpcE, BarcodeSymbology.UPCE, BarcodeWidth.Mode1, 40, true, AlignmentPosition.Right);
        builder.appendUnitFeed(32);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createPdf417Data(Emulation emulation) {
        byte[] data;

        try {
            data = "Hello World.\n".getBytes("UTF-8");      // Use UTF-8 encoded text data for PDF417.
        }
        catch (UnsupportedEncodingException e) {
            data = "Hello World.\n".getBytes();
        }

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.append("\n*Module:2*\n".getBytes());
        builder.appendPdf417(data, 0, 1, Pdf417Level.ECC0, 2, 2);
        builder.appendUnitFeed(32);
        builder.append("\n*Module:4*\n".getBytes());
        builder.appendPdf417(data, 0, 1, Pdf417Level.ECC0, 4, 2);
        builder.appendUnitFeed(32);

        builder.append("\n*Column:2*\n".getBytes());
        builder.appendPdf417(data, 0, 2, Pdf417Level.ECC0, 2, 2);
        builder.appendUnitFeed(32);
        builder.append("\n*Column:4*\n".getBytes());
        builder.appendPdf417(data, 0, 4, Pdf417Level.ECC0, 2, 2);
        builder.appendUnitFeed(32);

        builder.append("\n*Line:10*\n".getBytes());
        builder.appendPdf417(data, 10, 0, Pdf417Level.ECC0, 2, 2);
        builder.appendUnitFeed(32);
        builder.append("\n*Line:40*\n".getBytes());
        builder.appendPdf417(data, 40, 0, Pdf417Level.ECC0, 2, 2);
        builder.appendUnitFeed(32);

        builder.append("\n*Level:ECC0*\n".getBytes());
        builder.appendPdf417(data, 0, 7, Pdf417Level.ECC0, 2, 2);
        builder.appendUnitFeed(32);
        builder.append("\n*Level:ECC8*\n".getBytes());
        builder.appendPdf417(data, 0, 7, Pdf417Level.ECC8, 2, 2);
        builder.appendUnitFeed(32);

        builder.append("\n*AbsolutePosition:40*\n".getBytes());
        builder.appendPdf417WithAbsolutePosition(data, 0, 1, Pdf417Level.ECC0, 2, 2, 40);
        builder.appendUnitFeed(32);

        builder.append("\n*Alignment:Center*\n".getBytes());
        builder.appendPdf417WithAlignment(data, 0, 1, Pdf417Level.ECC0, 2, 2, AlignmentPosition.Center);
        builder.appendUnitFeed(32);
        builder.append("\n*Alignment:Right*\n".getBytes());
        builder.appendPdf417WithAlignment(data, 0, 1, Pdf417Level.ECC0, 2, 2, AlignmentPosition.Right);
        builder.appendUnitFeed(32);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createQrCodeData(Emulation emulation) {
        byte[] data;

        try {
            data = "Hello World.\n".getBytes("UTF-8");      // Use UTF-8 encoded text data for QR Code.
        }
        catch (UnsupportedEncodingException e) {
            data = "Hello World.\n".getBytes();
        }

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.append("*Cell:2*\n".getBytes());
        builder.appendQrCode(data, QrCodeModel.No2, QrCodeLevel.L, 2);
        builder.appendUnitFeed(32);
        builder.append("*Cell:8*\n".getBytes());
        builder.appendQrCode(data, QrCodeModel.No2, QrCodeLevel.L, 8);
        builder.appendUnitFeed(32);

        builder.append("*Level:L*\n".getBytes());
        builder.appendQrCode(data, QrCodeModel.No2, QrCodeLevel.L, 4);
        builder.appendUnitFeed(32);
        builder.append("*Level:M*\n".getBytes());
        builder.appendQrCode(data, QrCodeModel.No2, QrCodeLevel.M, 4);
        builder.appendUnitFeed(32);
        builder.append("*Level:Q*\n".getBytes());
        builder.appendQrCode(data, QrCodeModel.No2, QrCodeLevel.Q, 4);
        builder.appendUnitFeed(32);
        builder.append("*Level:H*\n".getBytes());
        builder.appendQrCode(data, QrCodeModel.No2, QrCodeLevel.H, 4);
        builder.appendUnitFeed(32);

        builder.append("\n*AbsolutePosition:40*\n".getBytes());
        builder.appendQrCodeWithAbsolutePosition(data, QrCodeModel.No2, QrCodeLevel.L, 4, 40);
        builder.appendUnitFeed(32);

        builder.append("\n*Alignment:Center*\n".getBytes());
        builder.appendQrCodeWithAlignment(data, QrCodeModel.No2, QrCodeLevel.L, 4, AlignmentPosition.Center);
        builder.appendUnitFeed(32);
        builder.append("\n*Alignment:Right*\n".getBytes());
        builder.appendQrCodeWithAlignment(data, QrCodeModel.No2, QrCodeLevel.L, 4, AlignmentPosition.Right);
        builder.appendUnitFeed(32);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createBlackMarkData(Emulation emulation, BlackMarkType type) {
        byte[] data = "Hello World.\n".getBytes();

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.appendBlackMark(type);

        builder.append(data);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

//      builder.appendBlackMark(BlackMarkType.Invalid);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createPageModeData(Emulation emulation, int width, Context context) {
        byte[] data;

        try {
            data = "Hello World.\n".getBytes("UTF-8");      // Use UTF-8 encoded text data for QR Code.
        }
        catch (UnsupportedEncodingException e) {
            data = "Hello World.\n".getBytes();
        }

        Bitmap starLogoImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.star_logo_image);

        int height = 30 * 8;        // 30mm!!

        Rect rect;

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.append("\n*Normal*\n".getBytes());

        rect = new Rect(0, 0, width, height);

        builder.beginPageMode(rect, BitmapConverterRotation.Normal);

        builder.append(data);

        builder.appendPageModeVerticalAbsolutePosition(160);

        builder.append(data);

        builder.appendPageModeVerticalAbsolutePosition(80);

        builder.appendAbsolutePosition(data, 40);

        builder.endPageMode();

        builder.append("\n*Right90*\n".getBytes());

//      rect = new Rect(0, 0, width,  height);
//      rect = new Rect(0, 0, height, width);
        rect = new Rect(0, 0, width,  width);

        builder.beginPageMode(rect, BitmapConverterRotation.Right90);

        builder.append(data);

        builder.appendPageModeVerticalAbsolutePosition(160);

        builder.append(data);

        builder.appendPageModeVerticalAbsolutePosition(80);

        builder.appendAbsolutePosition(data, 40);

        builder.endPageMode();

//        builder.append("\n*Rotate180*\n".getBytes());
//
//        rect = new Rect(0, 0, width,  height);
//
//        builder.beginPageMode(rect, BitmapConverterRotation.Rotate180);
//
//        builder.append(data);
//
//        builder.appendPageModeVerticalAbsolutePosition(160);
//
//        builder.append(data);
//
//        builder.appendPageModeVerticalAbsolutePosition(80);
//
//        builder.appendAbsolutePosition(data, 40);
//
//        builder.endPageMode();
//
//        builder.append("\n*Left90*\n".getBytes());
//
////      rect = new Rect(0, 0, width,   height);
//        rect = new Rect(0, 0, height,  width);
//
//        builder.beginPageMode(rect, BitmapConverterRotation.Left90);
//
//        builder.append(data);
//
//        builder.appendPageModeVerticalAbsolutePosition(160);
//
//        builder.append(data);
//
//        builder.appendPageModeVerticalAbsolutePosition(80);
//
//        builder.appendAbsolutePosition(data, 40);
//
//        builder.endPageMode();
//
        builder.append("\n*Mixed Text*\n".getBytes());

//      rect = new Rect(0, 0, width,  height);
        rect = new Rect(0, 0, width,  width);

        builder.beginPageMode(rect, BitmapConverterRotation.Normal);

        builder.appendPageModeVerticalAbsolutePosition(width / 2);

        builder.appendAbsolutePosition(data, width / 2);

        builder.appendPageModeRotation(BitmapConverterRotation.Right90);

        builder.appendPageModeVerticalAbsolutePosition(width / 2);

        builder.appendAbsolutePosition(data, width / 2);

        builder.appendPageModeRotation(BitmapConverterRotation.Rotate180);

        builder.appendPageModeVerticalAbsolutePosition(width / 2);

        builder.appendAbsolutePosition(data, width / 2);

        builder.appendPageModeRotation(BitmapConverterRotation.Left90);

        builder.appendPageModeVerticalAbsolutePosition(width / 2);

        builder.appendAbsolutePosition(data, width / 2);

        builder.endPageMode();

        builder.append("\n*Mixed Bitmap*\n".getBytes());

//      rect = new Rect(0, 0, width,  height);
        rect = new Rect(0, 0, width,  width);

        builder.beginPageMode(rect, BitmapConverterRotation.Normal);

        builder.appendPageModeVerticalAbsolutePosition(width / 2);

        builder.appendBitmapWithAbsolutePosition(starLogoImage, false, width / 2);

        builder.appendPageModeRotation(BitmapConverterRotation.Right90);

        builder.appendPageModeVerticalAbsolutePosition(width / 2);

        builder.appendBitmapWithAbsolutePosition(starLogoImage, true, width / 2);

        builder.appendPageModeRotation(BitmapConverterRotation.Rotate180);

        builder.appendPageModeVerticalAbsolutePosition(width / 2);

        builder.appendBitmapWithAbsolutePosition(starLogoImage, true, width / 2);

        builder.appendPageModeRotation(BitmapConverterRotation.Left90);

        builder.appendPageModeVerticalAbsolutePosition(width / 2);

        builder.appendBitmapWithAbsolutePosition(starLogoImage, true, width / 2);

        builder.endPageMode();

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createPrintableAreaData(Emulation emulation, PrintableAreaType type, Context context) {
        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        Bitmap printableAreaImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.printable_area_image);

        byte[] data1 = "123456789".getBytes();
        byte[] data2 = "0".getBytes();

        builder.beginDocument();

        builder.appendPrintableArea(type);

        switch (type) {
//          case Standard:
            default:
                builder.append("*Standard*\n".getBytes());
                break;
            case Type1:
                builder.append("*Type1*\n".getBytes());
                break;
            case Type2:
                builder.append("*Type2*\n".getBytes());
                break;
            case Type3:
                builder.append("*Type3*\n".getBytes());
                break;
            case Type4:
                builder.append("*Type4*\n".getBytes());
                break;
        }

        builder.appendBitmap(printableAreaImage, true);

        builder.append(data1);
        builder.appendInvert(data2);
        builder.append(data1);
        builder.appendInvert(data2);
        builder.append(data1);
        builder.appendInvert(data2);
        builder.append(data1);
        builder.appendInvert(data2);
        builder.append(data1);
        builder.appendInvert(data2);
        builder.append(data1);
        builder.appendInvert(data2);
        builder.append(data1);
        builder.appendInvert(data2);
        builder.append(data1);
        builder.appendInvert(data2);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }

    public static byte[] createBezelAutomaticPageLengthData(Emulation emulation, AutomaticPageLengthType type) {
        IBezelCommandBuilder bezelCommandBuilder = StarIoExt.createBezelCommandBuilder(emulation);
        bezelCommandBuilder.appendBezelMode(Mode.Valid);
        bezelCommandBuilder.appendAutomaticPageLength(type);

        ICommandBuilder printerCommandBuilder = StarIoExt.createCommandBuilder(emulation);
        printerCommandBuilder.beginDocument();
        printerCommandBuilder.append("Hello world".getBytes());
        printerCommandBuilder.appendCutPaper(CutPaperAction.PartialCutWithFeed);
        printerCommandBuilder.endDocument();

        byte[] commands = new byte[bezelCommandBuilder.getCommands().length + printerCommandBuilder.getCommands().length];
        System.arraycopy(bezelCommandBuilder.getCommands(), 0, commands, 0, bezelCommandBuilder.getCommands().length);
        System.arraycopy(printerCommandBuilder.getCommands(), 0, commands, bezelCommandBuilder.getCommands().length, printerCommandBuilder.getCommands().length);

        return commands;
    }

    public static byte[] createLabelData(Emulation emulation, LabelType type) {
        byte[] data = "Hello World.\n".getBytes();

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.appendLabel(type);

        builder.append(data);

        builder.appendCutPaper(CutPaperAction.PartialCutWithFeed);

//      builder.appendLabel(LabelType.Invalid);

        builder.endDocument();

        return builder.getCommands();
    }
}
