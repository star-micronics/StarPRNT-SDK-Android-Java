package com.starmicronics.starprntsdk.localizereceipts;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;

import com.starmicronics.starioextension.ICommandBuilder;
import com.starmicronics.starioextension.ICommandBuilder.CodePageType;
import com.starmicronics.starioextension.ICommandBuilder.AlignmentPosition;
import com.starmicronics.starioextension.ICommandBuilder.BarcodeSymbology;
import com.starmicronics.starioextension.ICommandBuilder.BarcodeWidth;
import com.starmicronics.starioextension.StarIoExt.CharacterCode;
import com.starmicronics.starprntsdk.PrinterSettingConstant;
import com.starmicronics.starprntsdk.R;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class PortugueseReceiptsImpl extends ILocalizeReceipts {

    public PortugueseReceiptsImpl() {
        mLanguageCode = "Pt";

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

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultipleHeight((
                "COMERCIAL DE ALIMENTOS\n" +
                        "STAR LTDA.\n").getBytes(encoding), 2);

        builder.append((
                "Avenida Moyses Roysen,\n" +
                        "S/N Vila Guilherme\n" +
                        "Cep: 02049-010 – Sao Paulo – SP\n" +
                        "CNPJ: 62.545.579/0013-69\n" +
                        "IE:110.819.138.118\n" +
                        "IM: 9.041.041-5\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "--------------------------------\n" +
                        "MM/DD/YYYY HH:MM:SS\n" +
                        "CCF:133939 COO:227808\n" +
                        "--------------------------------\n" +
                        "CUPOM FISCAL\n" +
                        "--------------------------------\n" +
                        "001 2505 CAFÉ DO PONTO TRAD A\n" +
                        "                    1un F1 8,15)\n" +
                        "002 2505 CAFÉ DO PONTO TRAD A\n" +
                        "                    1un F1 8,15)\n" +
                        "003 2505 CAFÉ DO PONTO TRAD A\n" +
                        "                    1un F1 8,15)\n" +
                        "004 6129 AGU MIN NESTLE 510ML\n" +
                        "                    1un F1 1,39)\n" +
                        "005 6129 AGU MIN NESTLE 510ML\n" +
                        "                    1un F1 1,39)\n" +
                        "--------------------------------\n").getBytes(encoding));

        builder.appendMultipleWidth("TOTAL  R$  27,23\n".getBytes(encoding), 2);

        builder.append((
                "DINHEIROv                  29,00\n" +
                        "TROCO R$                    1,77\n" +
                        "Valor dos Tributos R$2,15(7,90%)\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "ITEM(S) CINORADIS 5\n" +
                        "OP.:15326  PDV:9  BR,BF:93466\n" +
                        "OBRIGADO PERA PREFERENCIA.\n").getBytes(encoding));

        builder.appendMultipleWidth((
                "VOLTE SEMPRE!\n" +
                        "\n").getBytes(encoding), 2);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "SAC 0800 724 2822\n" +
                        "--------------------------------\n" +
                        "MD5:\n" +
                        "fe028828a532a7dbaf4271155aa4e2db\n" +
                        "Calypso_CA CA.20.c13\n" +
                        " – Unisys Brasil\n" +
                        "--------------------------------\n" +
                        "DARUMA AUTOMAÇÃO   MACH 2\n" +
                        "ECF-IF VERSÃO:01,00,00 ECF:093\n" +
                        "Lj:0204 OPR:ANGELA JORGE\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "DDDDDDDDDAEHFGBFCC\n" +
                        "MM/DD/YYYY HH:MM:SS\n" +
                        "FAB:DR0911BR000000275026\n" +
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

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultipleHeight("COMERCIAL DE ALIMENTOS STAR LTDA.\n".getBytes(encoding), 2);

        builder.append((
                "Avenida Moyses Roysen, S/N  Vila Guilherme\n" +
                        "Cep: 02049-010 – Sao Paulo – SP\n" +
                        "CNPJ: 62.545.579/0013-69\n" +
                        "IE:110.819.138.118  IM: 9.041.041-5\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "------------------------------------------------\n" +
                        "MM/DD/YYYY HH:MM:SS  CCF:133939 COO:227808\n" +
                        "------------------------------------------------\n" +
                        "CUPOM FISCAL\n" +
                        "------------------------------------------------\n" +
                        "001  2505  CAFÉ DO PONTO TRAD A  1un F1  8,15)\n" +
                        "002  2505  CAFÉ DO PONTO TRAD A  1un F1  8,15)\n" +
                        "003  2505  CAFÉ DO PONTO TRAD A  1un F1  8,15)\n" +
                        "004  6129  AGU MIN NESTLE 510ML  1un F1  1,39)\n" +
                        "005  6129  AGU MIN NESTLE 510ML  1un F1  1,39)\n" +
                        "------------------------------------------------\n").getBytes(encoding));

        builder.appendMultipleWidth("TOTAL  R$         27,23\n".getBytes(encoding), 2);

        builder.append((
                "DINHEIROv                                29,00\n" +
                        "TROCO R$                                  1,77\n" +
                        "Valor dos Tributos R$2,15 (7,90%)\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "ITEM(S) CINORADIS 5\n" +
                        "OP.:15326  PDV:9  BR,BF:93466\n" +
                        "OBRIGADO PERA PREFERENCIA.\n").getBytes(encoding));

        builder.appendMultipleWidth((
                "VOLTE SEMPRE!\n" +
                        "\n").getBytes(encoding), 2);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "SAC 0800 724 2822\n" +
                        "------------------------------------------------\n" +
                        "MD5:fe028828a532a7dbaf4271155aa4e2db\n" +
                        "Calypso_CA CA.20.c13 – Unisys Brasil\n" +
                        "------------------------------------------------\n" +
                        "DARUMA AUTOMAÇÃO   MACH 2\n" +
                        "ECF-IF VERSÃO:01,00,00 ECF:093\n" +
                        "Lj:0204 OPR:ANGELA JORGE\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "DDDDDDDDDAEHFGBFCC\n" +
                        "MM/DD/YYYY HH:MM:SS\n" +
                        "FAB:DR0911BR000000275026\n" +
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

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultipleHeight("COMERCIAL DE ALIMENTOS STAR LTDA.\n".getBytes(encoding), 2);

        builder.append((
                "Avenida Moyses Roysen, S/N  Vila Guilherme\n" +
                        "Cep: 02049-010 – Sao Paulo – SP\n" +
                        "CNPJ: 62.545.579/0013-69\n" +
                        "IE:110.819.138.118  IM: 9.041.041-5\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "---------------------------------------------------------------------\n" +
                        "MM/DD/YYYY HH:MM:SS  CCF:133939 COO:227808\n" +
                        "---------------------------------------------------------------------\n" +
                        "CUPOM FISCAL\n" +
                        "---------------------------------------------------------------------\n" +
                        "001  2505        CAFÉ DO PONTO TRAD A    1un F1            8,15)\n" +
                        "002  2505        CAFÉ DO PONTO TRAD A    1un F1            8,15)\n" +
                        "003  2505        CAFÉ DO PONTO TRAD A    1un F1            8,15)\n" +
                        "004  6129        AGU MIN NESTLE 510ML    1un F1            1,39)\n" +
                        "005  6129        AGU MIN NESTLE 510ML    1un F1            1,39)\n" +
                        "---------------------------------------------------------------------\n").getBytes(encoding));

        builder.appendMultipleWidth("TOTAL  R$                  27,23\n".getBytes(encoding), 2);

        builder.append((
                "DINHEIROv                                                  29,00\n" +
                        "TROCO R$                                                    1,77\n" +
                        "Valor dos Tributos R$2,15 (7,90%)\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "ITEM(S) CINORADIS 5\n" +
                        "OP.:15326  PDV:9  BR,BF:93466\n" +
                        "OBRIGADO PERA PREFERENCIA.\n").getBytes(encoding));

        builder.appendMultipleWidth((
                "VOLTE SEMPRE!\n" +
                        "\n").getBytes(encoding), 2);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "SAC 0800 724 2822\n" +
                        "---------------------------------------------------------------------\n" +
                        "MD5:fe028828a532a7dbaf4271155aa4e2db\n" +
                        "Calypso_CA CA.20.c13 – Unisys Brasil\n" +
                        "---------------------------------------------------------------------\n" +
                        "DARUMA AUTOMAÇÃO   MACH 2\n" +
                        "ECF-IF VERSÃO:01,00,00 ECF:093\n" +
                        "Lj:0204 OPR:ANGELA JORGE\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "DDDDDDDDDAEHFGBFCC\n" +
                        "MM/DD/YYYY HH:MM:SS\n" +
                        "FAB:DR0911BR000000275026\n" +
                        "\n").getBytes(encoding));

        builder.appendBarcode("{BStar.".getBytes(Charset.forName("US-ASCII")), BarcodeSymbology.Code128, BarcodeWidth.Mode2, 40, true);

    }

    @Override
    public Bitmap create2inchRasterReceiptImage() {
        String textToPrint =
                "COMERCIAL DE ALIMENTOS\n" +
                        "         STAR LTDA.\n" +
                        "Avenida Moyses Roysen,\n" +
                        "S/N Vila Guilherme\n" +
                        "Cep: 02049-010 – Sao Paulo\n" +
                        "     – SP\n" +
                        "CNPJ: 62.545.579/0013-69\n" +
                        "IE:110.819.138.118\n" +
                        "IM: 9.041.041-5\n" +
                        "--------------------------\n" +
                        "MM/DD/YYYY HH:MM:SS\n" +
                        "CCF:133939 COO:227808\n" +
                        "--------------------------\n" +
                        "CUPOM FISCAL\n" +
                        "--------------------------\n" +
                        "01 CAFÉ DO PONTO TRAD A\n" +
                        "              1un F1 8,15)\n" +
                        "02 CAFÉ DO PONTO TRAD A\n" +
                        "              1un F1 8,15)\n" +
                        "03 CAFÉ DO PONTO TRAD A\n" +
                        "              1un F1 8,15)\n" +
                        "04 AGU MIN NESTLE 510ML\n" +
                        "              1un F1 1,39)\n" +
                        "05 AGU MIN NESTLE 510ML\n" +
                        "              1un F1 1,39)\n" +
                        "--------------------------\n" +
                        "TOTAL  R$            27,23\n" +
                        "DINHEIROv            29,00\n" +
                        "\n" +
                        "TROCO R$              1,77\n" +
                        "Valor dos Tributos\n" +
                        "R$2,15(7,90%)\n" +
                        "ITEM(S) CINORADIS 5\n" +
                        "OP.:15326  PDV:9\n" +
                        "            BR,BF:93466\n" +
                        "OBRIGADO PERA PREFERENCIA.\n" +
                        "VOLTE SEMPRE!\n" +
                        "SAC 0800 724 2822\n" +
                        "--------------------------\n" +
                        "MD5:\n" +
                        "fe028828a532a7dbaf4271155a\n" +
                        "a4e2db\n" +
                        "Calypso_CA CA.20.c13\n" +
                        " – Unisys Brasil\n" +
                        "--------------------------\n" +
                        "DARUMA AUTOMAÇÃO   MACH 2\n" +
                        "ECF-IF VERSÃO:01,00,00\n" +
                        "ECF:093\n" +
                        "Lj:0204 OPR:ANGELA JORGE\n" +
                        "DDDDDDDDDAEHFGBFCC\n" +
                        "MM/DD/YYYY HH:MM:SS\n" +
                        "FAB:DR0911BR000000275026\n";

        int      textSize = 24;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_TWO_INCH, typeface);
    }

    @Override
    public Bitmap create3inchRasterReceiptImage() {
        String textToPrint =
                "         COMERCIAL DE ALIMENTOS\n" +
                        "              STAR LTDA.\n" +
                        "        Avenida Moyses Roysen,\n" +
                        "          S/N Vila Guilherme\n" +
                        "     Cep: 02049-010 – Sao Paulo – SP\n" +
                        "        CNPJ: 62.545.579/0013-69\n" +
                        "  IE:110.819.138.118    IM: 9.041.041-5\n" +
                        "---------------------------------------\n" +
                        "MM/DD/YYYY HH:MM:SS\n" +
                        "CCF:133939   COO:227808\n" +
                        "---------------------------------------\n" +
                        "CUPOM FISCAL\n" +
                        "---------------------------------------\n" +
                        "01  CAFÉ DO PONTO TRAD A  1un F1  8,15)\n" +
                        "02  CAFÉ DO PONTO TRAD A  1un F1  8,15)\n" +
                        "03  CAFÉ DO PONTO TRAD A  1un F1  8,15)\n" +
                        "04  AGU MIN NESTLE 510ML  1un F1  1,39)\n" +
                        "05  AGU MIN NESTLE 510ML  1un F1  1,39)\n" +
                        "---------------------------------------\n" +
                        "TOTAL  R$                         27,23\n" +
                        "DINHEIROv                         29,00\n" +
                        "\n" +
                        "TROCO R$                           1,77\n" +
                        "Valor dos Tributos R$2,15(7,90%)\n" +
                        "ITEM(S) CINORADIS 5\n" +
                        "OP.:15326  PDV:9  BR,BF:93466\n" +
                        "OBRIGADO PERA PREFERENCIA.\n" +
                        "VOLTE SEMPRE!    SAC 0800 724 2822\n" +
                        "---------------------------------------\n" +
                        "MD5:  fe028828a532a7dbaf4271155aa4e2db\n" +
                        "Calypso_CA CA.20.c13 – Unisys Brasil\n" +
                        "---------------------------------------\n" +
                        "DARUMA AUTOMAÇÃO   MACH 2\n" +
                        "ECF-IF VERSÃO:01,00,00 ECF:093\n" +
                        "Lj:0204 OPR:ANGELA JORGE\n" +
                        "DDDDDDDDDAEHFGBFCC\n" +
                        "MM/DD/YYYY HH:MM:SS\n" +
                        "FAB:DR0911BR000000275026\n";

        int      textSize = 24;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_THREE_INCH, typeface);
    }

    @Override
    public Bitmap create4inchRasterReceiptImage() {
        String textToPrint =
                "            COMERCIAL DE ALIMENTOS STAR LTDA.\n" +
                        "         Avenida Moyses Roysen, S/N Vila Guilherme\n" +
                        "              Cep: 02049-010 – Sao Paulo – SP\n" +
                        "                  CNPJ: 62.545.579/0013-69\n" +
                        "                    IE:110.819.138.118    IM: 9.041.041-5\n" +
                        "---------------------------------------------------------\n" +
                        "              MM/DD/YYYY HH:MM:SS CCF:133939   COO:227808\n" +
                        "---------------------------------------------------------\n" +
                        "CUPOM FISCAL\n" +
                        "---------------------------------------------------------\n" +
                        "01   CAFÉ DO PONTO TRAD A    1un F1                 8,15)\n" +
                        "02   CAFÉ DO PONTO TRAD A    1un F1                 8,15)\n" +
                        "03   CAFÉ DO PONTO TRAD A    1un F1                 8,15)\n" +
                        "04   AGU MIN NESTLE 510ML    1un F1                 1,39)\n" +
                        "05   AGU MIN NESTLE 510ML    1un F1                 1,39)\n" +
                        "---------------------------------------------------------\n" +
                        "TOTAL  R$                                           27,23\n" +
                        "DINHEIROv                                           29,00\n" +
                        "\n" +
                        "TROCO R$                                             1,77\n" +
                        "Valor dos Tributos R$2,15(7,90%)\n" +
                        "ITEM(S) CINORADIS 5\n" +
                        "OP.:15326  PDV:9  BR,BF:93466\n" +
                        "OBRIGADO PERA PREFERENCIA.\n" +
                        "                       VOLTE SEMPRE!    SAC 0800 724 2822\n" +
                        "---------------------------------------------------------\n" +
                        "                   MD5:  fe028828a532a7dbaf4271155aa4e2db\n" +
                        "                     Calypso_CA CA.20.c13 – Unisys Brasil\n" +
                        "---------------------------------------------------------\n" +
                        "DARUMA AUTOMAÇÃO   MACH 2\n" +
                        "ECF-IF VERSÃO:01,00,00 ECF:093\n" +
                        "Lj:0204 OPR:ANGELA JORGE\n" +
                        "DDDDDDDDDAEHFGBFCC\n" +
                        "MM/DD/YYYY HH:MM:SS\n" +
                        "FAB:DR0911BR000000275026\n";

        int      textSize = 24;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_FOUR_INCH, typeface);
    }

    @Override
    public Bitmap createCouponImage(Resources resources) {
        return BitmapFactory.decodeResource(resources, R.drawable.coupon_image_portuguese);
    }

    @Override
    public Bitmap createEscPos3inchRasterReceiptImage() {
        String textToPrint =
                "\n" +
                "     COMERCIAL DE ALIMENTOS\n" +
                        "            STAR LTDA.\n" +
                        "      Avenida Moyses Roysen,\n" +
                        "        S/N Vila Guilherme\n" +
                        "  Cep: 02049-010 – Sao Paulo – SP\n" +
                        "      CNPJ: 62.545.579/0013-69\n" +
                        "IE:110.819.138.118  IM: 9.041.041-5\n" +
                        "-----------------------------------\n" +
                        "MM/DD/YYYY HH:MM:SS\n" +
                        "CCF:133939   COO:227808\n" +
                        "-----------------------------------\n" +
                        "CUPOM FISCAL\n" +
                        "-----------------------------------\n" +
                        "01  CAFÉ DO PONTO TRAD A\n" +
                        "                      1un F1  8,15)\n" +
                        "02  CAFÉ DO PONTO TRAD A\n" +
                        "                      1un F1  8,15)\n" +
                        "03  CAFÉ DO PONTO TRAD A\n" +
                        "                      1un F1  8,15)\n" +
                        "04  AGU MIN NESTLE 510ML\n" +
                        "                      1un F1  1,39)\n" +
                        "05  AGU MIN NESTLE 510ML\n" +
                        "                      1un F1  1,39)\n" +
                        "-----------------------------------\n" +
                        "TOTAL  R$                     27,23\n" +
                        "DINHEIROv                     29,00\n" +
                        "\n" +
                        "TROCO R$                       1,77\n" +
                        "Valor dos Tributos R$2,15(7,90%)\n" +
                        "ITEM(S) CINORADIS 5\n" +
                        "OP.:15326  PDV:9  BR,BF:93466\n" +
                        "OBRIGADO PERA PREFERENCIA.\n" +
                        "VOLTE SEMPRE!     SAC 0800 724 2822\n" +
                        "-----------------------------------\n" +
                        "MD5:\n" +
                        "fe028828a532a7dbaf4271155aa4e2db\n" +
                        "Calypso_CA CA.20.c13\n" +
                        " – Unisys Brasil\n" +
                        "-----------------------------------\n" +
                        "DARUMA AUTOMAÇÃO   MACH 2\n" +
                        "ECF-IF VERSÃO:01,00,00 ECF:093\n" +
                        "Lj:0204 OPR:ANGELA JORGE\n" +
                        "DDDDDDDDDAEHFGBFCC\n" +
                        "MM/DD/YYYY HH:MM:SS\n" +
                        "FAB:DR0911BR000000275026\n";

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

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append("\n".getBytes());

        builder.appendMultipleHeight("COMERCIAL DE ALIMENTOS STAR LTDA.\n".getBytes(encoding), 2);

        builder.append((
                "Avenida Moyses Roysen, S/N  Vila Guilherme\n" +
                        "Cep: 02049-010 – Sao Paulo – SP\n" +
                        "CNPJ: 62.545.579/0013-69\n" +
                        "IE:110.819.138.118  IM: 9.041.041-5\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "------------------------------------------\n" +
                        "MM/DD/YYYY HH:MM:SS  CCF:133939 COO:227808\n" +
                        "------------------------------------------\n" +
                        "CUPOM FISCAL\n" +
                        "------------------------------------------\n" +
                        "001   2505    CAFÉ DO PONTO TRAD A\n" +
                        "                            1un F1  8,15)\n" +
                        "002   2505    CAFÉ DO PONTO TRAD A\n" +
                        "                            1un F1  8,15)\n" +
                        "003   2505    CAFÉ DO PONTO TRAD A\n" +
                        "                            1un F1  8,15)\n" +
                        "004   6129    AGU MIN NESTLE 510ML\n" +
                        "                            1un F1  1,39)\n" +
                        "005   6129    AGU MIN NESTLE 510ML\n" +
                        "                            1un F1  1,39)\n" +
                        "------------------------------------------\n").getBytes(encoding));

        builder.appendMultipleWidth("TOTAL  R$      27,23\n".getBytes(encoding), 2);

        builder.append((
                "DINHEIROv                          29,00\n" +
                        "TROCO R$                            1,77\n" +
                        "Valor dos Tributos R$2,15 (7,90%)\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "ITEM(S) CINORADIS 5\n" +
                        "OP.:15326  PDV:9  BR,BF:93466\n" +
                        "OBRIGADO PERA PREFERENCIA.\n").getBytes(encoding));

        builder.appendMultipleWidth((
                "VOLTE SEMPRE!\n" +
                        "\n").getBytes(encoding), 2);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "SAC 0800 724 2822\n" +
                        "------------------------------------------\n" +
                        "MD5:fe028828a532a7dbaf4271155aa4e2db\n" +
                        "Calypso_CA CA.20.c13 – Unisys Brasil\n" +
                        "------------------------------------------\n" +
                        "DARUMA AUTOMAÇÃO   MACH 2\n" +
                        "ECF-IF VERSÃO:01,00,00 ECF:093\n" +
                        "Lj:0204 OPR:ANGELA JORGE\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "DDDDDDDDDAEHFGBFCC\n" +
                        "MM/DD/YYYY HH:MM:SS\n" +
                        "FAB:DR0911BR000000275026\n" +
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

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultipleHeight("\nCOMERCIAL DE ALIMENTOS STAR LTDA.\n".getBytes(encoding), 2);

        builder.append((
                "Avenida Moyses Roysen, S/N Vila Guilherme\n" +
                        "Cep: 02049-010 – Sao Paulo – SP\n" +
                        "CNPJ: 62.545.579/0013-69\n" +
                        "IE:110.819.138.118  IM: 9.041.041-5\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "------------------------------------------\n" +
                        "MM/DD/YYYY HH:MM:SS  CCF:133939 COO:227808\n" +
                        "------------------------------------------\n" +
                        "CUPOM FISCAL\n" +
                        "------------------------------------------\n" +
                        "01 2505 CAFÉ DO PONTO TRAD A  1un F1 8,15)\n" +
                        "02 2505 CAFÉ DO PONTO TRAD A  1un F1 8,15)\n" +
                        "03 2505 CAFÉ DO PONTO TRAD A  1un F1 8,15)\n" +
                        "04 6129 AGU MIN NESTLE 510ML  1un F1 1,39)\n" +
                        "05 6129 AGU MIN NESTLE 510ML  1un F1 1,39)\n" +
                        "------------------------------------------\n" +
                        "TOTAL  R$                            27,23\n" +
                        "DINHEIROv                            29,00\n" +
                        "TROCO R$                              1,77\n" +
                        "Valor dos Tributos R$2,15 (7,90%)\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultipleWidth("TOTAL  R$      27,23\n".getBytes(encoding), 2);

        builder.append((
                "ITEM(S) CINORADIS 5\n" +
                        "OP.:15326  PDV:9  BR,BF:93466\n" +
                        "OBRIGADO PERA PREFERENCIA.\n").getBytes(encoding));

        builder.appendMultipleWidth((
                "VOLTE SEMPRE!\n" +
                        "\n").getBytes(encoding), 2);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "SAC 0800 724 2822\n" +
                        "------------------------------------------\n" +
                        "MD5:  fe028828a532a7dbaf4271155aa4e2db\n" +
                        "Calypso_CA CA.20.c13 – Unisys Brasil\n" +
                        "------------------------------------------\n" +
                        "DARUMA AUTOMAÇÃO   MACH 2\n" +
                        "ECF-IF VERSÃO:01,00,00 ECF:093\n" +
                        "Lj:0204 OPR:ANGELA JORGE\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "DDDDDDDDDAEHFGBFCC\n" +
                        "MM/DD/YYYY HH:MM:SS\n" +
                        "FAB:DR0911BR000000275026\n").getBytes(encoding));
    }

    @Override
    public Bitmap createSk12inchRasterReceiptImage() {
        String textToPrint =
                "    COMERCIAL DE ALIMENTOS\n" +
                        "           STAR LTDA.\n" +
                        "Avenida Moyses Roysen,\n" +
                        "S/N Vila Guilherme\n" +
                        "Cep: 02049-010 – Sao Paulo\n" +
                        "     – SP\n" +
                        "CNPJ: 62.545.579/0013-69\n" +
                        "IE:110.819.138.118\n" +
                        "IM: 9.041.041-5\n" +
                        "------------------------------\n" +
                        "MM/DD/YYYY HH:MM:SS\n" +
                        "CCF:133939 COO:227808\n" +
                        "------------------------------\n" +
                        "CUPOM FISCAL\n" +
                        "------------------------------\n" +
                        "01 CAFÉ DO PONTO TRAD A\n" +
                        "                  1un F1 8,15)\n" +
                        "02 CAFÉ DO PONTO TRAD A\n" +
                        "                  1un F1 8,15)\n" +
                        "03 CAFÉ DO PONTO TRAD A\n" +
                        "                  1un F1 8,15)\n" +
                        "04 AGU MIN NESTLE 510ML\n" +
                        "                  1un F1 1,39)\n" +
                        "05 AGU MIN NESTLE 510ML\n" +
                        "                  1un F1 1,39)\n" +
                        "------------------------------\n" +
                        "TOTAL  R$                27,23\n" +
                        "DINHEIROv                29,00\n" +
                        "\n" +
                        "TROCO R$                  1,77\n" +
                        "Valor dos Tributos\n" +
                        "R$2,15(7,90%)\n" +
                        "ITEM(S) CINORADIS 5\n" +
                        "OP.:15326  PDV:9\n" +
                        "            BR,BF:93466\n" +
                        "OBRIGADO PERA PREFERENCIA.\n" +
                        "VOLTE SEMPRE!\n" +
                        "SAC 0800 724 2822\n" +
                        "------------------------------\n" +
                        "MD5:\n" +
                        "fe028828a532a7dbaf4271155a\n" +
                        "a4e2db\n" +
                        "Calypso_CA CA.20.c13\n" +
                        " – Unisys Brasil\n" +
                        "------------------------------\n" +
                        "DARUMA AUTOMAÇÃO   MACH 2\n" +
                        "ECF-IF VERSÃO:01,00,00\n" +
                        "ECF:093\n" +
                        "Lj:0204 OPR:ANGELA JORGE\n" +
                        "DDDDDDDDDAEHFGBFCC\n" +
                        "MM/DD/YYYY HH:MM:SS\n" +
                        "FAB:DR0911BR000000275026\n";

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

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultipleHeight((
                "COMERCIAL DE ALIMENTOS\n" +
                        "STAR LTDA.\n").getBytes(encoding), 2);

        builder.append((
                "Avenida Moyses Roysen,\n" +
                        "S/N Vila Guilherme\n" +
                        "Cep: 02049-010 – Sao Paulo – SP\n" +
                        "CNPJ: 62.545.579/0013-69\n" +
                        "IE:110.819.138.118\n" +
                        "IM: 9.041.041-5\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "------------------------------------\n" +
                        "MM/DD/YYYY HH:MM:SS\n" +
                        "CCF:133939 COO:227808\n" +
                        "------------------------------------\n" +
                        "CUPOM FISCAL\n" +
                        "------------------------------------\n" +
                        "001 2505 CAFÉ DO PONTO TRAD A\n" +
                        "                        1un F1 8,15)\n" +
                        "002 2505 CAFÉ DO PONTO TRAD A\n" +
                        "                        1un F1 8,15)\n" +
                        "003 2505 CAFÉ DO PONTO TRAD A\n" +
                        "                        1un F1 8,15)\n" +
                        "004 6129 AGU MIN NESTLE 510ML\n" +
                        "                        1un F1 1,39)\n" +
                        "005 6129 AGU MIN NESTLE 510ML\n" +
                        "                        1un F1 1,39)\n" +
                        "------------------------------------\n").getBytes(encoding));

        builder.appendMultipleWidth("TOTAL  R$    27,23\n".getBytes(encoding), 2);

        builder.append((
                "DINHEIROv                      29,00\n" +
                        "TROCO R$                        1,77\n" +
                        "Valor dos Tributos R$2,15(7,90%)\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "ITEM(S) CINORADIS 5\n" +
                        "OP.:15326  PDV:9  BR,BF:93466\n" +
                        "OBRIGADO PERA PREFERENCIA.\n").getBytes(encoding));

        builder.appendMultipleWidth((
                "VOLTE SEMPRE!\n" +
                        "\n").getBytes(encoding), 2);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "SAC 0800 724 2822\n" +
                        "------------------------------------\n" +
                        "MD5:\n" +
                        "fe028828a532a7dbaf4271155aa4e2db\n" +
                        "Calypso_CA CA.20.c13\n" +
                        " – Unisys Brasil\n" +
                        "------------------------------------\n" +
                        "DARUMA AUTOMAÇÃO   MACH 2\n" +
                        "ECF-IF VERSÃO:01,00,00 ECF:093\n" +
                        "Lj:0204 OPR:ANGELA JORGE\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "DDDDDDDDDAEHFGBFCC\n" +
                        "MM/DD/YYYY HH:MM:SS\n" +
                        "FAB:DR0911BR000000275026\n" +
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
