package com.starmicronics.starprntsdk.localizereceipts;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;

import com.starmicronics.starioextension.ICommandBuilder;
import com.starmicronics.starioextension.ICommandBuilder.CodePageType;
import com.starmicronics.starioextension.ICommandBuilder.InternationalType;
import com.starmicronics.starioextension.ICommandBuilder.AlignmentPosition;
import com.starmicronics.starioextension.ICommandBuilder.BarcodeSymbology;
import com.starmicronics.starioextension.ICommandBuilder.BarcodeWidth;
import com.starmicronics.starioextension.StarIoExt.CharacterCode;
import com.starmicronics.starprntsdk.PrinterSettingConstant;
import com.starmicronics.starprntsdk.R;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;


public class SpanishReceiptsImpl extends ILocalizeReceipts {

    public SpanishReceiptsImpl() {
        mLanguageCode = "Es";

        mCharacterCode = CharacterCode.Standard;
    }

    @Override
    public void append2inchTextReceiptData(ICommandBuilder builder, boolean utf8) {
        Charset encoding;

        if (utf8) {
            encoding = Charset.forName("UTF-8");

            builder.appendCodePage(CodePageType.UTF8);
        }
        else {
            try {
                encoding = Charset.forName("Windows-1252");

                builder.appendCodePage(CodePageType.CP1252);
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendInternational(InternationalType.Spain);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultiple(("BAR RESTAURANT\n" +
                "EL POZO\n").getBytes(encoding), 2, 2);

        builder.append((
                "C/.ROCAFORT 187\n" +
                        "08029 BARCELONA\n" +
                        "\n" +
                        "NIF :X-3856907Z\n" +
                        "TEL :934199465\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "--------------------------------\n" +
                        "MESA: 100 P: - FECHA: YYYY-MM-DD\n" +
                        "CAN P/U DESCRIPCION  SUMA\n" +
                        "--------------------------------\n" +
                        " 4  3,00  JARRA  CERVEZA   12,00\n" +
                        " 1  1,60  COPA DE CERVEZA   1,60\n" +
                        "--------------------------------\n" +
                        "               SUB TOTAL : 13,60\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Right);

        builder.appendMultipleHeight("TOTAL:     13,60 EUROS\n".getBytes(encoding), 2);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "NO: 000018851     IVA INCLUIDO\n" +
                        "--------------------------------\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append(("**** GRACIAS POR SU VISITA! ****\n" +
                "\n").getBytes(encoding));

        builder.appendBarcode("{BStar.".getBytes(Charset.forName("US-ASCII")), BarcodeSymbology.Code128, BarcodeWidth.Mode2, 40, true);
    }

    @Override
    public void append3inchTextReceiptData(ICommandBuilder builder, boolean utf8) {
        Charset encoding;

        if (utf8) {
            encoding = Charset.forName("UTF-8");

            builder.appendCodePage(CodePageType.UTF8);
        }
        else {
            try {
                encoding = Charset.forName("Windows-1252");

                builder.appendCodePage(CodePageType.CP1252);
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendInternational(InternationalType.Spain);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultiple("BAR RESTAURANT EL POZO\n".getBytes(encoding), 2, 2);

        builder.append((
                "C/.ROCAFORT 187 08029 BARCELONA\n" +
                        "NIF :X-3856907Z  TEL :934199465\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "------------------------------------------------\n" +
                        "MESA: 100 P: - FECHA: YYYY-MM-DD\n" +
                        "CAN P/U DESCRIPCION  SUMA\n" +
                        "------------------------------------------------\n" +
                        " 4     3,00      JARRA  CERVEZA            12,00\n" +
                        " 1     1,60      COPA DE CERVEZA            1,60\n" +
                        "------------------------------------------------\n" +
                        "                           SUB TOTAL :     13,60\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Right);

        builder.appendMultipleHeight("TOTAL:     13,60 EUROS\n".getBytes(encoding), 2);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "NO: 000018851  IVA INCLUIDO\n" +
                        "------------------------------------------------\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append(("**** GRACIAS POR SU VISITA! ****\n" +
                "\n").getBytes(encoding));

        builder.appendBarcode("{BStar.".getBytes(Charset.forName("US-ASCII")), BarcodeSymbology.Code128, BarcodeWidth.Mode2, 40, true);
    }

    @Override
    public void append4inchTextReceiptData(ICommandBuilder builder, boolean utf8) {
        Charset encoding;

        if (utf8) {
            encoding = Charset.forName("UTF-8");

            builder.appendCodePage(CodePageType.UTF8);
        }
        else {
            try {
                encoding = Charset.forName("Windows-1252");

                builder.appendCodePage(CodePageType.CP1252);
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendInternational(InternationalType.Spain);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultiple("BAR RESTAURANT EL POZO\n".getBytes(encoding), 2, 2);

        builder.append((
                "C/.ROCAFORT 187 08029 BARCELONA\n" +
                        "NIF :X-3856907Z  TEL :934199465\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "---------------------------------------------------------------------\n" +
                        "MESA: 100 P: - FECHA: YYYY-MM-DD\n" +
                        "CAN P/U DESCRIPCION  SUMA\n" +
                        "---------------------------------------------------------------------\n" +
                        " 4     3,00          JARRA  CERVEZA                             12,00\n" +
                        " 1     1,60          COPA DE CERVEZA                             1,60\n" +
                        "---------------------------------------------------------------------\n" +
                        "                                         SUB TOTAL :            13,60\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Right);

        builder.appendMultipleHeight("TOTAL:     13,60 EUROS\n".getBytes(encoding), 2);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "NO: 000018851  IVA INCLUIDO\n" +
                        "---------------------------------------------------------------------\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append(("**** GRACIAS POR SU VISITA! ****\n" +
                "\n").getBytes(encoding));

        builder.appendBarcode("{BStar.".getBytes(Charset.forName("US-ASCII")), BarcodeSymbology.Code128, BarcodeWidth.Mode2, 40, true);
    }

    @Override
    public Bitmap create2inchRasterReceiptImage() {
        String textToPrint =
                "     BAR RESTAURANT\n" +
                        "                   EL POZO\n" +
                        "C/.ROCAFORT 187\n" +
                        "08029 BARCELONA\n" +
                        "NIF :X-3856907Z\n" +
                        "TEL :934199465\n" +
                        "--------------------------\n" +
                        "MESA: 100 P: -\n" +
                        "    FECHA: YYYY-MM-DD\n" +
                        "CAN P/U DESCRIPCION  SUMA\n" +
                        "--------------------------\n" +
                        "3,00 JARRA CERVEZA   12,00\n" +
                        "1,60 COPA DE CERVEZA  1,60\n" +
                        "--------------------------\n" +
                        "         SUB TOTAL : 13,60\n" +
                        "TOTAL:         13,60 EUROS\n" +
                        " NO:000018851 IVA INCLUIDO\n" +
                        "\n" +
                        "--------------------------\n" +
                        "**GRACIAS POR SU VISITA!**\n";

        int      textSize = 22;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_TWO_INCH, typeface);
    }

    @Override
    public Bitmap create3inchRasterReceiptImage() {
        String textToPrint =
                "                        BAR RESTAURANT\n" +
                        "                               EL POZO\n" +
                        "C/.ROCAFORT 187\n" +
                        "08029 BARCELONA\n" +
                        "NIF :X-3856907Z\n" +
                        "TEL :934199465\n" +
                        "--------------------------------------\n" +
                        "MESA: 100 P: - FECHA: YYYY-MM-DD\n" +
                        "CAN P/U DESCRIPCION  SUMA\n" +
                        "--------------------------------------\n" +
                        "4 3,00 JARRA  CERVEZA   12,00\n" +
                        "1 1,60 COPA DE CERVEZA  1,60\n" +
                        "--------------------------------------\n" +
                        "                     SUB TOTAL : 13,60\n" +
                        "TOTAL:               13,60 EUROS\n" +
                        "NO: 000018851 IVA INCLUIDO\n" +
                        "\n" +
                        "--------------------------------------\n" +
                        "            **GRACIAS POR SU VISITA!**\n";

        int      textSize = 24;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_THREE_INCH, typeface);
    }

    @Override
    public Bitmap create4inchRasterReceiptImage() {
        String textToPrint =
                "                                   BAR RESTAURANT EL POZO\n" +
                        "                          C/.ROCAFORT 187 08029 BARCELONA\n" +
                        "                          NIF :X-3856907Z  TEL :934199465\n" +
                        "---------------------------------------------------------\n" +
                        "MESA: 100 P: - FECHA: YYYY-MM-DD\n" +
                        "CAN P/U DESCRIPCION  SUMA\n" +
                        "---------------------------------------------------------\n" +
                        "4    3,00    JARRA  CERVEZA                         12,00\n" +
                        "1    1,60    COPA DE CERVEZA                         1,60\n" +
                        "---------------------------------------------------------\n" +
                        "                                  SUB TOTAL :       13,60\n" +
                        "                                 TOTAL :      13,60 EUROS\n" +
                        "NO: 000018851 IVA INCLUIDO\n" +
                        "\n" +
                        "---------------------------------------------------------\n" +
                        "                             ***GRACIAS POR SU VISITA!***\n";

        int      textSize = 24;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_FOUR_INCH, typeface);
    }

    @Override
    public Bitmap createCouponImage(Resources resources) {
        return BitmapFactory.decodeResource(resources, R.drawable.coupon_image_spanish);
    }

    @Override
    public Bitmap createEscPos3inchRasterReceiptImage() {
        String textToPrint =
                "\n" +
                "                     BAR RESTAURANT\n" +
                        "                            EL POZO\n" +
                        "C/.ROCAFORT 187\n" +
                        "08029 BARCELONA\n" +
                        "NIF :X-3856907Z\n" +
                        "TEL :934199465\n" +
                        "-----------------------------------\n" +
                        "MESA: 100 P: - FECHA: YYYY-MM-DD\n" +
                        "CAN P/U DESCRIPCION  SUMA\n" +
                        "-----------------------------------\n" +
                        "4 3,00 JARRA  CERVEZA   12,00\n" +
                        "1 1,60 COPA DE CERVEZA  1,60\n" +
                        "-----------------------------------\n" +
                        "                  SUB TOTAL : 13,60\n" +
                        "TOTAL:               13,60 EUROS\n" +
                        "NO: 000018851 IVA INCLUIDO\n" +
                        "\n" +
                        "-----------------------------------\n" +
                        "         **GRACIAS POR SU VISITA!**\n";

        int      textSize = 24;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_ESCPOS_THREE_INCH, typeface);
    }

    @Override
    public void appendEscPos3inchTextReceiptData(ICommandBuilder builder, boolean utf8) {
        Charset encoding;

        if (utf8) {
            encoding = Charset.forName("UTF-8");

            builder.appendCodePage(CodePageType.UTF8);
        }
        else {
            try {
                encoding = Charset.forName("Windows-1252");

                builder.appendCodePage(CodePageType.CP1252);
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendInternational(InternationalType.Spain);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append("\n".getBytes());

        builder.appendMultipleHeight("BAR RESTAURANT EL POZO\n".getBytes(encoding), 2);

        builder.append((
                "C/.ROCAFORT 187 08029 BARCELONA\n" +
                        "NIF :X-3856907Z  TEL :934199465\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "------------------------------------------\n" +
                        "MESA: 100 P: - FECHA: YYYY-MM-DD\n" +
                        "CAN P/U DESCRIPCION  SUMA\n" +
                        "------------------------------------------\n" +
                        " 4    3,00    JARRA  CERVEZA         12,00\n" +
                        " 1    1,60    COPA DE CERVEZA         1,60\n" +
                        "------------------------------------------\n" +
                        "                     SUB TOTAL :     13,60\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Right);

        builder.appendMultipleHeight("TOTAL:     13,60 EUROS\n".getBytes(encoding), 2);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "NO: 000018851  IVA INCLUIDO\n" +
                        "------------------------------------------\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append(("**** GRACIAS POR SU VISITA! ****\n" +
                "\n").getBytes(encoding));

        builder.appendBarcode("{BStar.".getBytes(Charset.forName("US-ASCII")), BarcodeSymbology.Code128, BarcodeWidth.Mode2, 40, true);
    }

    @Override
    public void appendDotImpact3inchTextReceiptData(ICommandBuilder builder, boolean utf8) {
        Charset encoding;

        if (utf8) {
            encoding = Charset.forName("UTF-8");

            builder.appendCodePage(CodePageType.UTF8);
        }
        else {
            try {
                encoding = Charset.forName("Windows-1252");

                builder.appendCodePage(CodePageType.CP1252);
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendInternational(InternationalType.Spain);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultipleHeight("BAR RESTAURANT EL POZO\n".getBytes(encoding), 2);

        builder.append((
                "C/.ROCAFORT 187 08029 BARCELONA\n" +
                        "NIF :X-3856907Z  TEL :934199465\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "------------------------------------------\n" +
                        "MESA: 100 P: - FECHA: YYYY-MM-DD\n" +
                        "CAN P/U DESCRIPCION  SUMA\n" +
                        "------------------------------------------\n" +
                        " 4 3,00 JARRA  CERVEZA               12,00\n" +
                        " 1 1,60 COPA DE CERVEZA               1,60\n" +
                        "------------------------------------------\n" +
                        " SUB TOTAL :                         13,60\n" +
                        "                     TOTAL:    13,60 EUROS\n" +
                        "NO: 000018851  IVA INCLUIDO\n" +
                        "------------------------------------------\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append("**** GRACIAS POR SU VISITA! ****\n".getBytes(encoding));
    }

    @Override
    public Bitmap createSk12inchRasterReceiptImage() {
        String textToPrint =
                "         BAR RESTAURANT\n" +
                        "                          EL POZO\n" +
                        "C/.ROCAFORT 187\n" +
                        "08029 BARCELONA\n" +
                        "NIF :X-3856907Z\n" +
                        "TEL :934199465\n" +
                        "---------------------------------\n" +
                        "MESA: 100 P: -\n" +
                        "    FECHA: YYYY-MM-DD\n" +
                        "CAN P/U DESCRIPCION  SUMA\n" +
                        "---------------------------------\n" +
                        "3,00 JARRA CERVEZA          12,00\n" +
                        "1,60 COPA DE CERVEZA         1,60\n" +
                        "---------------------------------\n" +
                        "                SUB TOTAL : 13,60\n" +
                        "TOTAL:                13,60 EUROS\n" +
                        " NO:000018851 IVA INCLUIDO\n" +
                        "\n" +
                        "---------------------------------\n" +
                        "   **GRACIAS POR SU VISITA!**\n";

        int      textSize = 22;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_SK1_TWO_INCH, typeface);
    }

    @Override
    public void appendSk12inchTextReceiptData(ICommandBuilder builder, boolean utf8) {
        Charset encoding;

        if (utf8) {
            encoding = Charset.forName("UTF-8");

            builder.appendCodePage(CodePageType.UTF8);
        }
        else {
            try {
                encoding = Charset.forName("Windows-1252");

                builder.appendCodePage(CodePageType.CP1252);
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendInternational(InternationalType.Spain);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultiple(("BAR RESTAURANT\n" +
                "EL POZO\n").getBytes(encoding), 2, 2);

        builder.append((
                "C/.ROCAFORT 187\n" +
                        "08029 BARCELONA\n" +
                        "\n" +
                        "NIF :X-3856907Z\n" +
                        "TEL :934199465\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "------------------------------------\n" +
                        "MESA: 100 P: - FECHA: YYYY-MM-DD\n" +
                        "CAN P/U DESCRIPCION  SUMA\n" +
                        "------------------------------------\n" +
                        " 4  3,00  JARRA  CERVEZA       12,00\n" +
                        " 1  1,60  COPA DE CERVEZA       1,60\n" +
                        "------------------------------------\n" +
                        "               SUB TOTAL :     13,60\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Right);

        builder.appendMultipleHeight("TOTAL:     13,60 EUROS\n".getBytes(encoding), 2);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "NO: 000018851     IVA INCLUIDO\n" +
                        "------------------------------------\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append(("**** GRACIAS POR SU VISITA! ****\n" +
                "\n").getBytes(encoding));

        builder.appendBarcode("{BStar.".getBytes(Charset.forName("US-ASCII")), BarcodeSymbology.Code128, BarcodeWidth.Mode2, 40, true);
    }

    @Override
    public void appendTextLabelData(ICommandBuilder builder, boolean utf8) {
        // not implemented
    }

    @Override
    public String createPasteTextLabelString() {
        // not implemented
        return null;
    }

    @Override
    public void appendPasteTextLabelData(ICommandBuilder builder, String pasteText, boolean utf8) {
        // not implemented
    }
}
