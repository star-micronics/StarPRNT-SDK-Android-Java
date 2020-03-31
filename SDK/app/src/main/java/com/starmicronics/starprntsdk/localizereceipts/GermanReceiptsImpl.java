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

public class GermanReceiptsImpl extends ILocalizeReceipts {

    public GermanReceiptsImpl() {
        mLanguageCode = "De";

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

        builder.appendInternational(InternationalType.Germany);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultiple((
                "STAR\n" +
                        "Supermarkt\n").getBytes(encoding), 2, 2);

        builder.append((
                "\n" +
                        "Das Internet von seiner\n" +
                        "genussvollsten Seite\n" +
                        "\n").getBytes(encoding));

        builder.appendMultipleHeight(("www.Star-EMEM.com\n").getBytes(encoding), 2);

        builder.append(("Gebührenfrei Rufnummer:\n").getBytes(encoding));

        builder.appendEmphasis(("08006646701\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append(("--------------------------------\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Right);

        builder.appendEmphasis(("EUR\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
            "Schmand 24%                 0.42\n" +
            "Kefir                       0.79\n" +
            "Haarspray                   1.79\n" +
            "Gurken ST                   0.59\n" +
            "Mandelknacker               1.59\n" +
            "Mandelknacker               1.59\n" +
            "Nussecken                   1.69\n" +
            "Nussecken                   1.69\n" +
            "Clemen.1kg NZ               1.49\n" +
            "2X\n" +
            "Zitronen ST                 1.18\n" +
            "4X\n" +
            "Grapefruit                  3.16\n" +
            "Party Garnelen              9.79\n" +
            "Apfelsaft                   1.39\n" +
            "Lauchzw./Schl.B             0.49\n" +
            "Butter                      1.19\n" +
            "Profi-Haartrockner         27.99\n" +
            "Mozarella 45%               0.59\n" +
            "Mozarella 45%               0.59\n" +
            "Bruschetta Brot             0.59\n" +
            "Weizenmehl                  0.39\n" +
            "Jodsalz                     0.19\n" +
            "Eier M braun Bod            1.79\n" +
            "Schlagsahne                 1.69\n" +
            "Schlagsahne                 1.69\n" +
            "\n" +
            "Rueckgeld              EUR  0.00\n" +
            "\n" +
            "19.00% MwSt.               13.14\n" +
            "NETTO-UMSATZ               82.33\n" +
            "--------------------------------\n" +
            "KontoNr:  0551716000 / 0 / 0512\n" +
            "BLZ:      58862159\n" +
            "Trace-Nr: 027929\n" +
            "Beleg:    7238\n" +
            "--------------------------------\n" +
            "Kas: 003/006    Bon  0377 PC01 P\n" +
            "Dat: 30.03.2015 Zeit 18:06:01 43\n" +
            "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
            "USt–ID:    DE125580123\n" +
            "\n").getBytes(encoding));

        builder.appendEmphasis((
            "Vielen dank\n" +
            "für Ihren Einkauf!\n" +
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

        builder.appendInternational(InternationalType.Germany);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultiple((
            "STAR\n" +
            "Supermarkt\n").getBytes(encoding), 2, 2);

        builder.append((
            "\n" +
            "Das Internet von seiner genussvollsten Seite\n" +
            "\n").getBytes(encoding));
        
        builder.appendMultipleHeight(("www.Star-EMEM.com\n").getBytes(encoding), 2);
        
        builder.append(("Gebührenfrei Rufnummer:\n").getBytes(encoding));
        
        builder.appendEmphasis(("08006646701\n").getBytes(encoding));
        
        builder.appendAlignment(AlignmentPosition.Left);
        
        builder.append(("------------------------------------------------\n").getBytes(encoding));
        
        builder.appendAlignment(AlignmentPosition.Right);
        
        builder.appendEmphasis(("EUR\n").getBytes(encoding));
        
        builder.appendAlignment(AlignmentPosition.Left);
        
        builder.append((
            "Schmand 24%                                 0.42\n" +
            "Kefir                                       0.79\n" +
            "Haarspray                                   1.79\n" +
            "Gurken ST                                   0.59\n" +
            "Mandelknacker                               1.59\n" +
            "Mandelknacker                               1.59\n" +
            "Nussecken                                   1.69\n" +
            "Nussecken                                   1.69\n" +
            "Clemen.1kg NZ                               1.49\n" +
            "2X\n" +
            "Zitronen ST                                 1.18\n" +
            "4X\n" +
            "Grapefruit                                  3.16\n" +
            "Party Garnelen                              9.79\n" +
            "Apfelsaft                                   1.39\n" +
            "Lauchzw./Schl.B                             0.49\n" +
            "Butter                                      1.19\n" +
            "Profi-Haartrockner                         27.99\n" +
            "Mozarella 45%                               0.59\n" +
            "Mozarella 45%                               0.59\n" +
            "Bruschetta Brot                             0.59\n" +
            "Weizenmehl                                  0.39\n" +
            "Jodsalz                                     0.19\n" +
            "Eier M braun Bod                            1.79\n" +
            "Schlagsahne                                 1.69\n" +
            "Schlagsahne                                 1.69\n" +
            "\n" +
            "Rueckgeld                              EUR  0.00\n" +
            "\n" +
            "19.00% MwSt.                               13.14\n" +
            "NETTO-UMSATZ                               82.33\n" +
            "------------------------------------------------\n" +
            "KontoNr:  0551716000 / 0 / 0512\n" +
            "BLZ:      58862159\n" +
            "Trace-Nr: 027929\n" +
            "Beleg:    7238\n" +
            "------------------------------------------------\n" +
            "Kas: 003/006    Bon  0377 PC01 P\n" +
            "Dat: 30.03.2015 Zeit 18:06:01 43\n" +
            "\n").getBytes(encoding));
        
        builder.appendAlignment(AlignmentPosition.Center);
        
        builder.append((
            "USt–ID:    DE125580123\n" +
            "\n").getBytes(encoding));
        
        builder.appendEmphasis((
            "Vielen dank\n" +
            "für Ihren Einkauf!\n" +
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

        builder.appendInternational(InternationalType.Germany);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultiple((
            "STAR\n" +
            "Supermarkt\n").getBytes(encoding), 2, 2);

        builder.append((
            "\n" +
            "Das Internet von seiner genussvollsten Seite\n" +
            "\n").getBytes(encoding));
        
        builder.appendMultipleHeight(("www.Star-EMEM.com\n").getBytes(encoding), 2);
        
        builder.append(("Gebührenfrei Rufnummer:\n").getBytes(encoding));
        
        builder.appendEmphasis(("08006646701\n").getBytes(encoding));
        
        builder.appendAlignment(AlignmentPosition.Left);
        
        builder.append(("---------------------------------------------------------------------\n").getBytes(encoding));
        
        builder.appendAlignment(AlignmentPosition.Right);
        
        builder.appendEmphasis(("EUR\n").getBytes(encoding));
        
        builder.appendAlignment(AlignmentPosition.Left);
        
        builder.append((
            "Schmand 24%                                                      0.42\n" +
            "Kefir                                                            0.79\n" +
            "Haarspray                                                        1.79\n" +
            "Gurken ST                                                        0.59\n" +
            "Mandelknacker                                                    1.59\n" +
            "Mandelknacker                                                    1.59\n" +
            "Nussecken                                                        1.69\n" +
            "Nussecken                                                        1.69\n" +
            "Clemen.1kg NZ                                                    1.49\n" +
            "2X\n" +
            "Zitronen ST                                                      1.18\n" +
            "4X\n" +
            "Grapefruit                                                       3.16\n" +
            "Party Garnelen                                                   9.79\n" +
            "Apfelsaft                                                        1.39\n" +
            "Lauchzw./Schl.B                                                  0.49\n" +
            "Butter                                                           1.19\n" +
            "Profi-Haartrockner                                              27.99\n" +
            "Mozarella 45%                                                    0.59\n" +
            "Mozarella 45%                                                    0.59\n" +
            "Bruschetta Brot                                                  0.59\n" +
            "Weizenmehl                                                       0.39\n" +
            "Jodsalz                                                          0.19\n" +
            "Eier M braun Bod                                                 1.79\n" +
            "Schlagsahne                                                      1.69\n" +
            "Schlagsahne                                                      1.69\n" +
            "\n" +
            "Rueckgeld                                                   EUR  0.00\n" +
            "\n" +
            "19.00% MwSt.                                                    13.14\n" +
            "NETTO-UMSATZ                                                    82.33\n" +
            "---------------------------------------------------------------------\n" +
            "KontoNr:  0551716000 / 0 / 0512\n" +
            "BLZ:      58862159\n" +
            "Trace-Nr: 027929\n" +
            "Beleg:    7238\n" +
            "---------------------------------------------------------------------\n" +
            "Kas: 003/006    Bon  0377 PC01 P\n" +
            "Dat: 30.03.2015 Zeit 18:06:01 43\n" +
            "\n").getBytes(encoding));
        
        builder.appendAlignment(AlignmentPosition.Center);
        
        builder.append((
            "USt–ID:    DE125580123\n" +
            "\n").getBytes(encoding));
        
        builder.appendEmphasis((
            "Vielen dank\n" +
            "für Ihren Einkauf!\n" +
            "\n").getBytes(encoding));

        builder.appendBarcode("{BStar.".getBytes(Charset.forName("US-ASCII")), BarcodeSymbology.Code128, BarcodeWidth.Mode2, 40, true);
    }

    @Override
    public Bitmap create2inchRasterReceiptImage() {
        String textToPrint =
                "           STAR\n" +
                "        Supermarkt\n" +
                "\n" +
                " Das Internet von seiner\n" +
                "   genussvollsten Seite\n" +
                "\n" +
                "    www.Star-EMEM.com\n" +
                " Gebührenfrei Rufnummer:\n" +
                "       08006646701\n" +
                "--------------------------\n" +
                "                       EUR\n" +
                "Schmand 24%           0.42\n" +
                "Kefir                 0.79\n" +
                "Haarspray             1.79\n" +
                "Gurken ST             0.59\n" +
                "Mandelknacker         1.59\n" +
                "Mandelknacker         1.59\n" +
                "Nussecken             1.69\n" +
                "Nussecken             1.69\n" +
                "Clemen.1kg NZ         1.49\n" +
                "2X\n" +
                "Zitronen ST           1.18\n" +
                "4X\n" +
                "Grapefruit            3.16\n" +
                "Party Garnelen        9.79\n" +
                "Apfelsaft             1.39\n" +
                "Lauchzw./Schl.B       0.49\n" +
                "Butter                1.19\n" +
                "Profi-Haartrockner   27.99\n" +
                "Mozarella 45%         0.59\n" +
                "Mozarella 45%         0.59\n" +
                "Bruschetta Brot       0.59\n" +
                "Weizenmehl            0.39\n" +
                "Jodsalz               0.19\n" +
                "Eier M braun Bod      1.79\n" +
                "Schlagsahne           1.69\n" +
                "Schlagsahne           1.69\n" +
                "\n" +
                "Rueckgeld        EUR  0.00\n" +
                "\n" +
                "19.00% MwSt.         13.14\n" +
                "NETTO-UMSATZ         82.33\n" +
                "--------------------------\n" +
                "KontoNr: 0551716000/0/0512\n" +
                "BLZ:     58862159\n" +
                "Trace-Nr:027929\n" +
                "Beleg:   7238\n" +
                "--------------------------\n" +
                "Kas:003/006 Bon0377 PC01 P\n" +
                "Dat:30.03.2015\n" +
                "Zeit18:06:01            43\n" +
                "\n" +
                "  USt–ID:    DE125580123\n" +
                "\n" +
                "       Vielen dank\n" +
                "    für Ihren Einkauf!\n";

        int      textSize = 24;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_TWO_INCH, typeface);
    }

    @Override
    public Bitmap create3inchRasterReceiptImage() {
        String textToPrint =
                "                 STAR\n" +
                "              Supermarkt\n" +
                "\n" +
                "       Das Internet von seiner\n" +
                "         genussvollsten Seite\n" +
                "\n" +
                "          www.Star-EMEM.com\n" +
                "       Gebührenfrei Rufnummer:\n" +
                "             08006646701\n" +
                "--------------------------------------\n" +
                "                                   EUR\n" +
                "Schmand 24%                       0.42\n" +
                "Kefir                             0.79\n" +
                "Haarspray                         1.79\n" +
                "Gurken ST                         0.59\n" +
                "Mandelknacker                     1.59\n" +
                "Mandelknacker                     1.59\n" +
                "Nussecken                         1.69\n" +
                "Nussecken                         1.69\n" +
                "Clemen.1kg NZ                     1.49\n" +
                "2X\n" +
                "Zitronen ST                       1.18\n" +
                "4X\n" +
                "Grapefruit                        3.16\n" +
                "Party Garnelen                    9.79\n" +
                "Apfelsaft                         1.39\n" +
                "Lauchzw./Schl.B                   0.49\n" +
                "Butter                            1.19\n" +
                "Profi-Haartrockner               27.99\n" +
                "Mozarella 45%                     0.59\n" +
                "Mozarella 45%                     0.59\n" +
                "Bruschetta Brot                   0.59\n" +
                "Weizenmehl                        0.39\n" +
                "Jodsalz                           0.19\n" +
                "Eier M braun Bod                  1.79\n" +
                "Schlagsahne                       1.69\n" +
                "Schlagsahne                       1.69\n" +
                "\n" +
                "Rueckgeld                    EUR  0.00\n" +
                "\n" +
                "19.00% MwSt.                     13.14\n" +
                "NETTO-UMSATZ                     82.33\n" +
                "--------------------------------------\n" +
                "KontoNr:  0551716000 / 0 / 0512\n" +
                "BLZ:      58862159\n" +
                "Trace-Nr: 027929\n" +
                "Beleg:    7238\n" +
                "--------------------------------------\n" +
                "Kas: 003/006    Bon  0377 PC01 P\n" +
                "Dat: 30.03.2015 Zeit 18:06:01 43\n" +
                "\n" +
                "        USt–ID:    DE125580123\n" +
                "\n" +
                "             Vielen dank\n" +
                "          für Ihren Einkauf!\n";

        int      textSize = 24;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_THREE_INCH, typeface);
    }

    @Override
    public Bitmap create4inchRasterReceiptImage() {
        String textToPrint =
                "                          STAR\n" +
                "                       Supermarkt\n" +
                "\n" +
                "                Das Internet von seiner\n" +
                "                  genussvollsten Seite\n" +
                "\n" +
                "                   www.Star-EMEM.com\n" +
                "                Gebührenfrei Rufnummer:\n" +
                "                      08006646701\n" +
                "---------------------------------------------------------\n" +
                "                                                      EUR\n" +
                "Schmand 24%                                          0.42\n" +
                "Kefir                                                0.79\n" +
                "Haarspray                                            1.79\n" +
                "Gurken ST                                            0.59\n" +
                "Mandelknacker                                        1.59\n" +
                "Mandelknacker                                        1.59\n" +
                "Nussecken                                            1.69\n" +
                "Nussecken                                            1.69\n" +
                "Clemen.1kg NZ                                        1.49\n" +
                "2X\n" +
                "Zitronen ST                                          1.18\n" +
                "4X\n" +
                "Grapefruit                                           3.16\n" +
                "Party Garnelen                                       9.79\n" +
                "Apfelsaft                                            1.39\n" +
                "Lauchzw./Schl.B                                      0.49\n" +
                "Butter                                               1.19\n" +
                "Profi-Haartrockner                                  27.99\n" +
                "Mozarella 45%                                        0.59\n" +
                "Mozarella 45%                                        0.59\n" +
                "Bruschetta Brot                                      0.59\n" +
                "Weizenmehl                                           0.39\n" +
                "Jodsalz                                              0.19\n" +
                "Eier M braun Bod                                     1.79\n" +
                "Schlagsahne                                          1.69\n" +
                "Schlagsahne                                          1.69\n" +
                "\n" +
                "Rueckgeld                                       EUR  0.00\n" +
                "\n" +
                "19.00% MwSt.                                        13.14\n" +
                "NETTO-UMSATZ                                        82.33\n" +
                "---------------------------------------------------------\n" +
                "KontoNr:  0551716000 / 0 / 0512\n" +
                "BLZ:      58862159\n" +
                "Trace-Nr: 027929\n" +
                "Beleg:    7238\n" +
                "---------------------------------------------------------\n" +
                "Kas: 003/006    Bon  0377 PC01 P\n" +
                "Dat: 30.03.2015 Zeit 18:06:01 43\n" +
                "\n" +
                "                 USt–ID:    DE125580123\n" +
                "\n" +
                "                      Vielen dank\n" +
                "                   für Ihren Einkauf!\n";

        int      textSize = 24;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_FOUR_INCH, typeface);
    }

    @Override
    public Bitmap createCouponImage(Resources resources) {
        return BitmapFactory.decodeResource(resources, R.drawable.coupon_image_german);
    }

    @Override
    public Bitmap createEscPos3inchRasterReceiptImage() {
        String textToPrint =
                "\n" +
                "               STAR\n" +
                "            Supermarkt\n" +
                "\n" +
                "      Das Internet von seiner\n" +
                "       genussvollsten Seite\n" +
                "\n" +
                "         www.Star-EMEM.com\n" +
                "      Gebührenfrei Rufnummer:\n" +
                "            08006646701\n" +
                "-----------------------------------\n" +
                "                                EUR\n" +
                "Schmand 24%                    0.42\n" +
                "Kefir                          0.79\n" +
                "Haarspray                      1.79\n" +
                "Gurken ST                      0.59\n" +
                "Mandelknacker                  1.59\n" +
                "Mandelknacker                  1.59\n" +
                "Nussecken                      1.69\n" +
                "Nussecken                      1.69\n" +
                "Clemen.1kg NZ                  1.49\n" +
                "2X\n" +
                "Zitronen ST                    1.18\n" +
                "4X\n" +
                "Grapefruit                     3.16\n" +
                "Party Garnelen                 9.79\n" +
                "Apfelsaft                      1.39\n" +
                "Lauchzw./Schl.B                0.49\n" +
                "Butter                         1.19\n" +
                "Profi-Haartrockner            27.99\n" +
                "Mozarella 45%                  0.59\n" +
                "Mozarella 45%                  0.59\n" +
                "Bruschetta Brot                0.59\n" +
                "Weizenmehl                     0.39\n" +
                "Jodsalz                        0.19\n" +
                "Eier M braun Bod               1.79\n" +
                "Schlagsahne                    1.69\n" +
                "Schlagsahne                    1.69\n" +
                "\n" +
                "Rueckgeld                 EUR  0.00\n" +
                "\n" +
                "19.00% MwSt.                  13.14\n" +
                "NETTO-UMSATZ                  82.33\n" +
                "-----------------------------------\n" +
                "KontoNr:  0551716000 / 0 / 0512\n" +
                "BLZ:      58862159\n" +
                "Trace-Nr: 027929\n" +
                "Beleg:    7238\n" +
                "-----------------------------------\n" +
                "Kas: 003/006    Bon  0377 PC01 P\n" +
                "Dat: 30.03.2015 Zeit 18:06:01 43\n" +
                "\n" +
                "      USt–ID:    DE125580123\n" +
                "\n" +
                "            Vielen dank\n" +
                "        für Ihren Einkauf!\n";

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

        builder.appendInternational(InternationalType.Germany);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append("\n".getBytes());

        builder.appendMultiple((
            "STAR\n" +
            "Supermarkt\n").getBytes(encoding), 2, 2);
        
        builder.append((
            "\n" +
            "Das Internet von seiner\n" +
            "genussvollsten Seite\n" +
            "\n").getBytes(encoding));
        
        builder.appendMultipleHeight(("www.Star-EMEM.com\n").getBytes(encoding), 2);
        
        builder.append(("Gebührenfrei Rufnummer:\n").getBytes(encoding));
        
        builder.appendEmphasis(("08006646701\n").getBytes(encoding));
        
        builder.appendAlignment(AlignmentPosition.Left);
        
        builder.append(("------------------------------------------\n").getBytes(encoding));
        
        builder.appendEmphasis(("                                       EUR\n").getBytes(encoding));
        
        builder.append((
            "Schmand 24%                           0.42\n" +
            "Kefir                                 0.79\n" +
            "Haarspray                             1.79\n" +
            "Gurken ST                             0.59\n" +
            "Mandelknacker                         1.59\n" +
            "Mandelknacker                         1.59\n" +
            "Nussecken                             1.69\n" +
            "Nussecken                             1.69\n" +
            "Clemen.1kg NZ                         1.49\n" +
            "2X\n" +
            "Zitronen ST                           1.18\n" +
            "4X\n" +
            "Grapefruit                            3.16\n" +
            "Party Garnelen                        9.79\n" +
            "Apfelsaft                             1.39\n" +
            "Lauchzw./Schl.B                       0.49\n" +
            "Butter                                1.19\n" +
            "Profi-Haartrockner                   27.99\n" +
            "Mozarella 45%                         0.59\n" +
            "Mozarella 45%                         0.59\n" +
            "Bruschetta Brot                       0.59\n" +
            "Weizenmehl                            0.39\n" +
            "Jodsalz                               0.19\n" +
            "Eier M braun Bod                      1.79\n" +
            "Schlagsahne                           1.69\n" +
            "Schlagsahne                           1.69\n" +
            "\n" +
            "Rueckgeld                        EUR  0.00\n" +
            "\n" +
            "19.00% MwSt.                         13.14\n" +
            "NETTO-UMSATZ                         82.33\n" +
            "------------------------------------------\n" +
            "KontoNr:  0551716000 / 0 / 0512\n" +
            "BLZ:      58862159\n" +
            "Trace-Nr: 027929\n" +
            "Beleg:    7238\n" +
            "------------------------------------------\n" +
            "Kas: 003/006    Bon  0377 PC01 P\n" +
            "Dat: 30.03.2015 Zeit 18:06:01 43\n" +
            "\n").getBytes(encoding));
        
        builder.appendAlignment(AlignmentPosition.Center);
        
        builder.append((
            "USt–ID:    DE125580123\n" +
            "\n").getBytes(encoding));
        
        builder.appendEmphasis((
            "Vielen dank\n" +
            "für Ihren Einkauf!\n" +
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

        builder.appendInternational(InternationalType.Germany);
        
        builder.appendAlignment(AlignmentPosition.Center);
        
        builder.appendMultiple((
            "STAR\n" +
            "Supermarkt\n").getBytes(encoding), 2, 2);
        
        builder.append((
            "\n" +
            "Das Internet von seiner\n" +
            "genussvollsten Seite\n" +
            "\n").getBytes(encoding));
        
        builder.appendMultipleHeight(("www.Star-EMEM.com\n").getBytes(encoding), 2);
        
        builder.append(("Gebührenfrei Rufnummer:\n").getBytes(encoding));
        
        builder.appendEmphasis(("08006646701\n").getBytes(encoding));
        
        builder.appendAlignment(AlignmentPosition.Left);
        
        builder.append(("------------------------------------------\n").getBytes(encoding));
        
        builder.appendAlignment(AlignmentPosition.Right);
        
        builder.appendEmphasis(("EUR\n").getBytes(encoding));
        
        builder.appendAlignment(AlignmentPosition.Left);
        
        builder.append((
            "Schmand 24%                           0.42\n" +
            "Kefir                                 0.79\n" +
            "Haarspray                             1.79\n" +
            "Gurken ST                             0.59\n" +
            "Mandelknacker                         1.59\n" +
            "Mandelknacker                         1.59\n" +
            "Nussecken                             1.69\n" +
            "Nussecken                             1.69\n" +
            "Clemen.1kg NZ                         1.49\n" +
            "2X\n" +
            "Zitronen ST                           1.18\n" +
            "4X\n" +
            "Grapefruit                            3.16\n" +
            "Party Garnelen                        9.79\n" +
            "Apfelsaft                             1.39\n" +
            "Lauchzw./Schl.B                       0.49\n" +
            "Butter                                1.19\n" +
            "Profi-Haartrockner                   27.99\n" +
            "Mozarella 45%                         0.59\n" +
            "Mozarella 45%                         0.59\n" +
            "Bruschetta Brot                       0.59\n" +
            "Weizenmehl                            0.39\n" +
            "Jodsalz                               0.19\n" +
            "Eier M braun Bod                      1.79\n" +
            "Schlagsahne                           1.69\n" +
            "Schlagsahne                           1.69\n" +
            "\n" +
            "Rueckgeld                        EUR  0.00\n" +
            "\n" +
            "19.00% MwSt.                         13.14\n" +
            "NETTO-UMSATZ                         82.33\n" +
            "------------------------------------------\n" +
            "KontoNr:  0551716000 / 0 / 0512\n" +
            "BLZ:      58862159\n" +
            "Trace-Nr: 027929\n" +
            "Beleg:    7238\n" +
            "------------------------------------------\n" +
            "Kas: 003/006    Bon  0377 PC01 P\n" +
            "Dat: 30.03.2015 Zeit 18:06:01 43\n" +
            "\n").getBytes(encoding));
        
        builder.appendAlignment(AlignmentPosition.Center);
        
        builder.append((
            "USt–ID:    DE125580123\n" +
            "\n").getBytes(encoding));
        
        builder.appendEmphasis((
            "Vielen dank\n" +
            "für Ihren Einkauf!\n").getBytes(encoding));
    }

    @Override
    public Bitmap createSk12inchRasterReceiptImage() {
        String textToPrint =
                "             STAR\n" +
                        "          Supermarkt\n" +
                        "\n" +
                        " Das Internet von seiner\n" +
                        "   genussvollsten Seite\n" +
                        "\n" +
                        "      www.Star-EMEM.com\n" +
                        " Gebührenfrei Rufnummer:\n" +
                        "         08006646701\n" +
                        "------------------------------\n" +
                        "                           EUR\n" +
                        "Schmand 24%               0.42\n" +
                        "Kefir                     0.79\n" +
                        "Haarspray                 1.79\n" +
                        "Gurken ST                 0.59\n" +
                        "Mandelknacker             1.59\n" +
                        "Mandelknacker             1.59\n" +
                        "Nussecken                 1.69\n" +
                        "Nussecken                 1.69\n" +
                        "Clemen.1kg NZ             1.49\n" +
                        "2X\n" +
                        "Zitronen ST               1.18\n" +
                        "4X\n" +
                        "Grapefruit                3.16\n" +
                        "Party Garnelen            9.79\n" +
                        "Apfelsaft                 1.39\n" +
                        "Lauchzw./Schl.B           0.49\n" +
                        "Butter                    1.19\n" +
                        "Profi-Haartrockner       27.99\n" +
                        "Mozarella 45%             0.59\n" +
                        "Mozarella 45%             0.59\n" +
                        "Bruschetta Brot           0.59\n" +
                        "Weizenmehl                0.39\n" +
                        "Jodsalz                   0.19\n" +
                        "Eier M braun Bod          1.79\n" +
                        "Schlagsahne               1.69\n" +
                        "Schlagsahne               1.69\n" +
                        "\n" +
                        "Rueckgeld            EUR  0.00\n" +
                        "\n" +
                        "19.00% MwSt.             13.14\n" +
                        "NETTO-UMSATZ             82.33\n" +
                        "------------------------------\n" +
                        "KontoNr: 0551716000/0/0512\n" +
                        "BLZ:     58862159\n" +
                        "Trace-Nr:027929\n" +
                        "Beleg:   7238\n" +
                        "------------------------------\n" +
                        "Kas:003/006 Bon0377 PC01 P\n" +
                        "Dat:30.03.2015\n" +
                        "Zeit18:06:01                43\n" +
                        "\n" +
                        "  USt–ID:    DE125580123\n" +
                        "\n" +
                        "         Vielen dank\n" +
                        "      für Ihren Einkauf!\n";

        int      textSize = 24;
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

        builder.appendInternational(InternationalType.Germany);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultiple((
                "STAR\n" +
                        "Supermarkt\n").getBytes(encoding), 2, 2);

        builder.append((
                "\n" +
                        "Das Internet von seiner\n" +
                        "genussvollsten Seite\n" +
                        "\n").getBytes(encoding));

        builder.appendMultipleHeight(("www.Star-EMEM.com\n").getBytes(encoding), 2);

        builder.append(("Gebührenfrei Rufnummer:\n").getBytes(encoding));

        builder.appendEmphasis(("08006646701\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append(("------------------------------------\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Right);

        builder.appendEmphasis(("EUR\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "Schmand 24%                     0.42\n" +
                        "Kefir                           0.79\n" +
                        "Haarspray                       1.79\n" +
                        "Gurken ST                       0.59\n" +
                        "Mandelknacker                   1.59\n" +
                        "Mandelknacker                   1.59\n" +
                        "Nussecken                       1.69\n" +
                        "Nussecken                       1.69\n" +
                        "Clemen.1kg NZ                   1.49\n" +
                        "2X\n" +
                        "Zitronen ST                     1.18\n" +
                        "4X\n" +
                        "Grapefruit                      3.16\n" +
                        "Party Garnelen                  9.79\n" +
                        "Apfelsaft                       1.39\n" +
                        "Lauchzw./Schl.B                 0.49\n" +
                        "Butter                          1.19\n" +
                        "Profi-Haartrockner             27.99\n" +
                        "Mozarella 45%                   0.59\n" +
                        "Mozarella 45%                   0.59\n" +
                        "Bruschetta Brot                 0.59\n" +
                        "Weizenmehl                      0.39\n" +
                        "Jodsalz                         0.19\n" +
                        "Eier M braun Bod                1.79\n" +
                        "Schlagsahne                     1.69\n" +
                        "Schlagsahne                     1.69\n" +
                        "\n" +
                        "Rueckgeld                  EUR  0.00\n" +
                        "\n" +
                        "19.00% MwSt.                   13.14\n" +
                        "NETTO-UMSATZ                   82.33\n" +
                        "------------------------------------\n" +
                        "KontoNr:  0551716000 / 0 / 0512\n" +
                        "BLZ:      58862159\n" +
                        "Trace-Nr: 027929\n" +
                        "Beleg:    7238\n" +
                        "------------------------------------\n" +
                        "Kas: 003/006    Bon  0377 PC01 P\n" +
                        "Dat: 30.03.2015 Zeit 18:06:01 43\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "USt–ID:    DE125580123\n" +
                        "\n").getBytes(encoding));

        builder.appendEmphasis((
                "Vielen dank\n" +
                        "für Ihren Einkauf!\n" +
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
