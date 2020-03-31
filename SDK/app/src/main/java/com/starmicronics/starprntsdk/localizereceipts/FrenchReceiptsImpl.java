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

public class FrenchReceiptsImpl extends ILocalizeReceipts {

    public FrenchReceiptsImpl() {
        mLanguageCode = "Fr";

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

        builder.appendInternational(InternationalType.France);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultipleHeight("Star Micronics Communications\n".getBytes(encoding), 2);

        builder.append((
                "AVENUE LA MOTTE PICQUET\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "--------------------------------\n" +
                        "Date   : MM/DD/YYYY\n" +
                        "Heure  : HH:MM\n" +
                        "Boutique: OLUA23    Caisse: 0001\n" +
                        "Conseiller: 002970  Ticket: 3881\n" +
                        "--------------------------------\n" +
                        "\n" +
                        "Vous avez été servi par : Souad\n" +
                        "\n" +
                        "CAC IPHONE\n" +
                        "3700615033581 1 X 19.99€  19.99€\n" +
                        "\n" +
                        "dont contribution\n" +
                        " environnementale :\n" +
                        "CAC IPHONE                 0.01€\n" +
                        "--------------------------------\n" +
                        "1 Piéce(s) Total :        19.99€\n" +
                        "Mastercard Visa  :        19.99€\n" +
                        "\n" +
                        "Taux TVA    Montant H.T.   T.V.A\n" +
                        "  20%          16.66€      3.33€\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "Merci de votre visite et.\n" +
                        "à bientôt.\n" +
                        "Conservez votre ticket il\n" +
                        "vous sera demandé pour\n" +
                        "tout échange.\n" +
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

        builder.appendInternational(InternationalType.France);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultipleHeight("Star Micronics Communications\n".getBytes(encoding), 2);

        builder.append((
                "AVENUE LA MOTTE PICQUET\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "------------------------------------------------\n" +
                        "Date: MM/DD/YYYY    Heure: HH:MM\n" +
                        "Boutique: OLUA23    Caisse: 0001\n" +
                        "Conseiller: 002970  Ticket: 3881\n" +
                        "------------------------------------------------\n" +
                        "\n" +
                        "Vous avez été servi par : Souad\n" +
                        "\n" +
                        "CAC IPHONE\n" +
                        "3700615033581   1    X     19.99€         19.99€\n" +
                        "\n" +
                        "dont contribution environnementale :\n" +
                        "CAC IPHONE                                 0.01€\n" +
                        "------------------------------------------------\n" +
                        "1 Piéce(s) Total :                        19.99€\n" +
                        "Mastercard Visa  :                        19.99€\n" +
                        "\n" +
                        "Taux TVA    Montant H.T.   T.V.A\n" +
                        "  20%          16.66€      3.33€\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "Merci de votre visite et. à bientôt.\n" +
                        "Conservez votre ticket il\n" +
                        "vous sera demandé pour tout échange.\n").getBytes(encoding));

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

        builder.appendInternational(InternationalType.France);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultipleHeight("Star Micronics Communications\n".getBytes(encoding), 2);

        builder.append((
                "AVENUE LA MOTTE PICQUET\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "---------------------------------------------------------------------\n" +
                        "Date: MM/DD/YYYY    Heure: HH:MM\n" +
                        "Boutique: OLUA23    Caisse: 0001\n" +
                        "Conseiller: 002970  Ticket: 3881\n" +
                        "---------------------------------------------------------------------\n" +
                        "\n" +
                        "Vous avez été servi par : Souad\n" +
                        "\n" +
                        "CAC IPHONE\n" +
                        "3700615033581   1    X     19.99€                              19.99€\n" +
                        "\n" +
                        "dont contribution environnementale :\n" +
                        "CAC IPHONE                                                      0.01€\n" +
                        "---------------------------------------------------------------------\n" +
                        "1 Piéce(s) Total :                                             19.99€\n" +
                        "Mastercard Visa  :                                             19.99€\n" +
                        "\n" +
                        "Taux TVA    Montant H.T.   T.V.A\n" +
                        "  20%          16.66€      3.33€\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "Merci de votre visite et. à bientôt.\n" +
                        "Conservez votre ticket il\n" +
                        "vous sera demandé pour tout échange.\n").getBytes(encoding));

        builder.appendBarcode("{BStar.".getBytes(Charset.forName("US-ASCII")), BarcodeSymbology.Code128, BarcodeWidth.Mode2, 40, true);
    }

    @Override
    public Bitmap create2inchRasterReceiptImage() {
        String textToPrint =
                "      Star Micronics\n" +
                        "      Communications\n" +
                        "      AVENUE LA MOTTE\n" +
                        " PICQUET City, State 12345\n" +
                        "\n" +
                        "--------------------------\n" +
                        "Date: MM/DD/YYYY\n" +
                        "Time:HH:MM PM\n" +
                        "Boutique: OLUA23\n" +
                        "Caisse: 0001\n" +
                        "Conseiller: 002970\n" +
                        "Ticket: 3881\n" +
                        "--------------------------\n" +
                        "Vous avez été servi par :\n" +
                        "                     Souad\n" +
                        "CAC IPHONE\n" +
                        "3700615033581 1 X   19.99€\n" +
                        "                    19.99€\n" +
                        "dont contribution\n" +
                        " environnementale :\n" +
                        "CAC IPHONE           0.01€\n" +
                        "--------------------------\n" +
                        " 1 Piéce(s) Total : 19.99€\n" +
                        "\n" +
                        "  Mastercard Visa : 19.99€\n" +
                        "Taux TVA Montant H.T.\n" +
                        "     20%       16.66€\n" +
                        "T.V.A\n" +
                        "3.33€\n" +
                        "Merci de votre visite et.\n" +
                        "à bientôt.\n" +
                        "Conservez votre ticket il\n" +
                        "vous sera demandé pour\n" +
                        "tout échange.\n";

        int      textSize = 24;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_TWO_INCH, typeface);
    }

    @Override
    public Bitmap create3inchRasterReceiptImage() {
        String textToPrint =
                "      Star Micronics Communications\n" +
                        "             AVENUE LA MOTTE\n" +
                        "        PICQUET City, State 12345\n" +
                        "\n" +
                        "--------------------------------------\n" +
                        "     Date: MM/DD/YYYY    Time:HH:MM PM\n" +
                        "        Boutique: OLUA23  Caisse: 0001\n" +
                        "      Conseiller: 002970  Ticket: 3881\n" +
                        "--------------------------------------\n" +
                        "Vous avez été servi par : Souad\n" +
                        "CAC IPHONE\n" +
                        "3700615033581   1 X 19.99€      19.99€\n" +
                        "dont contribution environnementale :\n" +
                        "CAC IPHONE                       0.01€\n" +
                        "--------------------------------------\n" +
                        "  1 Piéce(s)    Total :         19.99€\n" +
                        "\n" +
                        "        Mastercard Visa  :      19.99€\n" +
                        "          Taux TVA  Montant H.T. T.V.A\n" +
                        "               20%       16.66€  3.33€\n" +
                        "  Merci de votre visite et. à bientôt.\n" +
                        "   Conservez votre ticket il vous sera\n" +
                        "            demandé pour tout échange.\n";

        int      textSize = 25;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_THREE_INCH, typeface);
    }

    @Override
    public Bitmap create4inchRasterReceiptImage() {
        String textToPrint =
                "             Star Micronics Communications\n" +
                        "       AVENUE LA MOTTE PICQUET City, State 12345\n" +
                        "\n" +
                        "-------------------------------------------------------\n" +
                        "                      Date: MM/DD/YYYY    Time:HH:MM PM\n" +
                        "                  Boutique: OLUA23         Caisse: 0001\n" +
                        "                Conseiller: 002970         Ticket: 3881\n" +
                        "-------------------------------------------------------\n" +
                        "Vous avez été servi par : Souad\n" +
                        "CAC IPHONE\n" +
                        "3700615033581      1  X  19.99€                  19.99€\n" +
                        "dont contribution environnementale :\n" +
                        "CAC IPHONE                                        0.01€\n" +
                        "-------------------------------------------------------\n" +
                        "        1 Piéce(s)    Total :                    19.99€\n" +
                        "\n" +
                        "        Mastercard Visa  :                       19.99€\n" +
                        "                           Taux TVA  Montant H.T. T.V.A\n" +
                        "                              20%         16.66€  3.33€\n" +
                        "                   Merci de votre visite et. à bientôt.\n" +
                        " Conservez votre ticket il vous sera demandé pour\n" +
                        " tout échange.\n";

        int      textSize = 25;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_FOUR_INCH, typeface);
    }

    @Override
    public Bitmap createCouponImage(Resources resources) {
        return BitmapFactory.decodeResource(resources, R.drawable.coupon_image_french);
    }

    @Override
    public Bitmap createEscPos3inchRasterReceiptImage() {
        String textToPrint =
                "\n" +
                        "   Star Micronics Communications\n" +
                        "           AVENUE LA MOTTE\n" +
                        "      PICQUET City, State 12345\n" +
                        "\n" +
                        "-----------------------------------\n" +
                        "  Date: MM/DD/YYYY    Time:HH:MM PM\n" +
                        "     Boutique: OLUA23  Caisse: 0001\n" +
                        "   Conseiller: 002970  Ticket: 3881\n" +
                        "-----------------------------------\n" +
                        "Vous avez été servi par : Souad\n" +
                        "CAC IPHONE\n" +
                        "3700615033581  1 X 19.99€    19.99€\n" +
                        "dont contribution environnementale:\n" +
                        "CAC IPHONE                    0.01€\n" +
                        "-----------------------------------\n" +
                        "  1 Piéce(s)    Total :      19.99€\n" +
                        "\n" +
                        "     Mastercard Visa  :      19.99€\n" +
                        "       Taux TVA  Montant H.T. T.V.A\n" +
                        "            20%       16.66€  3.33€\n" +
                        "Merci de votre visite et.\n" +
                        "à bientôt.\n" +
                        "Conservez votre ticket il vous sera\n" +
                        "demandé pour tout échange.\n";

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

        builder.appendInternational(InternationalType.France);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append("\n".getBytes(encoding));

        builder.appendMultipleHeight("Star Micronics Communications\n".getBytes(encoding), 2);

        builder.append((
                "AVENUE LA MOTTE PICQUET\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "------------------------------------------\n" +
                        "Date: MM/DD/YYYY    Heure: HH:MM\n" +
                        "Boutique: OLUA23    Caisse: 0001\n" +
                        "Conseiller: 002970  Ticket: 3881\n" +
                        "------------------------------------------\n" +
                        "\n" +
                        "Vous avez été servi par : Souad\n" +
                        "\n" +
                        "CAC IPHONE\n" +
                        "3700615033581   1    X   19.99€     19.99€\n" +
                        "\n" +
                        "dont contribution environnementale :\n" +
                        "CAC IPHONE                           0.01€\n" +
                        "------------------------------------------\n" +
                        "1 Piéce(s) Total :                  19.99€\n" +
                        "Mastercard Visa  :                  19.99€\n" +
                        "\n" +
                        "Taux TVA    Montant H.T.   T.V.A\n" +
                        "  20%          16.66€      3.33€\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "Merci de votre visite et. à bientôt.\n" +
                        "Conservez votre ticket il\n" +
                        "vous sera demandé pour tout échange.\n").getBytes(encoding));

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

        builder.appendInternational(InternationalType.France);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultipleHeight("Star Micronics Communications\n".getBytes(encoding), 2);

        builder.append((
                "AVENUE LA MOTTE PICQUET\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "------------------------------------------\n" +
                        "Date: MM/DD/YYYY    Heure: HH:MM\n" +
                        "Boutique: OLUA23    Caisse: 0001\n" +
                        "Conseiller: 002970  Ticket: 3881\n" +
                        "------------------------------------------\n" +
                        "\n" +
                        "Vous avez été servi par : Souad\n" +
                        "\n" +
                        "CAC IPHONE\n" +
                        "3700615033581 1 X 19.99€            19.99€\n" +
                        "\n" +
                        "dont contribution environnementale :\n" +
                        "CAC IPHONE                           0.01€\n" +
                        "------------------------------------------\n" +
                        "1 Piéce(s) Total :                  19.99€\n" +
                        "Mastercard Visa  :                  19.99€\n" +
                        "\n" +
                        "Taux TVA    Montant H.T.   T.V.A\n" +
                        "  20%          16.66€      3.33€\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "Merci de votre visite et. à bientôt.\n" +
                        "Conservez votre ticket il\n" +
                        "vous sera demandé pour tout échange.\n").getBytes(encoding));
    }

    @Override
    public Bitmap createSk12inchRasterReceiptImage() {
        String textToPrint =
                "        Star Micronics\n" +
                        "        Communications\n" +
                        "        AVENUE LA MOTTE\n" +
                        "   PICQUET City, State 12345\n" +
                        "\n" +
                        "------------------------------\n" +
                        "Date: MM/DD/YYYY\n" +
                        "Time:HH:MM PM\n" +
                        "Boutique: OLUA23\n" +
                        "Caisse: 0001\n" +
                        "Conseiller: 002970\n" +
                        "Ticket: 3881\n" +
                        "------------------------------\n" +
                        "Vous avez été servi par :\n" +
                        "                         Souad\n" +
                        "CAC IPHONE\n" +
                        "3700615033581 1 X       19.99€\n" +
                        "                        19.99€\n" +
                        "dont contribution\n" +
                        " environnementale :\n" +
                        "CAC IPHONE               0.01€\n" +
                        "------------------------------\n" +
                        " 1 Piéce(s) Total :     19.99€\n" +
                        "\n" +
                        "  Mastercard Visa :     19.99€\n" +
                        "Taux TVA Montant H.T.  T.V.A\n" +
                        "     20%       16.66€  3.33€\n" +
                        "\n" +
                        "\n" +
                        "Merci de votre visite et.\n" +
                        "à bientôt.\n" +
                        "Conservez votre ticket il vous\n" +
                        "sera demandé pour tout" +
                        "échange.\n";

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

        builder.appendInternational(InternationalType.France);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultipleHeight("Star Micronics Communications\n".getBytes(encoding), 2);

        builder.append((
                "AVENUE LA MOTTE PICQUET\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "------------------------------------\n" +
                        "Date   : MM/DD/YYYY\n" +
                        "Heure  : HH:MM\n" +
                        "Boutique: OLUA23        Caisse: 0001\n" +
                        "Conseiller: 002970      Ticket: 3881\n" +
                        "------------------------------------\n" +
                        "\n" +
                        "Vous avez été servi par : Souad\n" +
                        "\n" +
                        "CAC IPHONE\n" +
                        "3700615033581 1 X 19.99€      19.99€\n" +
                        "\n" +
                        "dont contribution environnementale :\n" +
                        "CAC IPHONE                     0.01€\n" +
                        "------------------------------------\n" +
                        "1 Piéce(s) Total :            19.99€\n" +
                        "Mastercard Visa  :            19.99€\n" +
                        "\n" +
                        "Taux TVA      Montant H.T.     T.V.A\n" +
                        "  20%            16.66€        3.33€\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "Merci de votre visite et.\n" +
                        "à bientôt.\n" +
                        "Conservez votre ticket il\n" +
                        "vous sera demandé pour\n" +
                        "tout échange.\n" +
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
