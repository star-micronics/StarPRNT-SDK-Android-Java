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

import com.starmicronics.starioextension.ICommandBuilder.QrCodeModel;
import com.starmicronics.starioextension.ICommandBuilder.QrCodeLevel;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class TraditionalChineseReceiptsImpl extends ILocalizeReceipts {

    public TraditionalChineseReceiptsImpl() {
        mLanguageCode = "zh-TW";

        mCharacterCode = CharacterCode.TraditionalChinese;
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
                encoding = Charset.forName("Big5");
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendEmphasis(true);

        builder.appendMultipleHeight("Star Micronics\n".getBytes(encoding), 3);

        builder.appendEmphasis(false);

        builder.append("--------------------------------\n".getBytes(encoding));

        builder.appendMultiple((
                "電子發票證明聯\n" +
                        "103年01-02月\n" +
                        "EV-99999999\n").getBytes(encoding), 2, 2);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append(("2014/01/15 13:00\n" +
                "隨機碼 : 9999    總計 : 999\n" +
                "賣方 : 99999999\n" +
                "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendQrCode("http://www.star-m.jp/eng/index.html".getBytes(), QrCodeModel.No2, QrCodeLevel.Q, 5);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "\n" +
                        "商品退換請持本聯及銷貨明細表。\n" +
                        "9999999-9999999 999999-999999 9999\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment("銷貨明細表 　(銷售)\n".getBytes(encoding), AlignmentPosition.Center);

        builder.appendAlignment("2014-01-15 13:00:02\n".getBytes(encoding), AlignmentPosition.Right);

        builder.append((
                "\n" +
                        "烏龍袋茶2g20入       55 x2 110TX\n" +
                        "茉莉烏龍茶2g20入     55 x2 110TX\n" +
                        "天仁觀音茶2g*20      55 x2 110TX\n").getBytes(encoding));

        builder.appendEmphasis((
                "      小　 計 :              330\n" +
                        "      總   計 :              330\n").getBytes(encoding));

        builder.append((
                "--------------------------------\n" +
                        "現 金                        400\n" +
                        "      找　 零 :               70\n").getBytes(encoding));

        builder.appendEmphasis(" 101 發票金額 :              330\n".getBytes(encoding));

        builder.append((
                "2014-01-15 13:00\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendBarcode("{BStar.".getBytes(Charset.forName("US-ASCII")), BarcodeSymbology.Code128, BarcodeWidth.Mode2, 40, true);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "\n" +
                        "商品退換、贈品及停車兌換請持本聯。\n" +
                        "9999999-9999999 999999-999999 9999\n").getBytes(encoding));
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
                encoding = Charset.forName("Big5");
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendEmphasis(true);

        builder.appendMultipleHeight("Star Micronics\n".getBytes(encoding), 3);

        builder.appendEmphasis(false);

        builder.append("------------------------------------------------\n".getBytes(encoding));

        builder.appendMultiple((
                "電子發票證明聯\n" +
                        "103年01-02月\n" +
                        "EV-99999999\n").getBytes(encoding), 2, 2);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append(("2014/01/15 13:00\n" +
                "隨機碼 : 9999    總計 : 999\n" +
                "賣方 : 99999999\n" +
                "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendQrCode("http://www.star-m.jp/eng/index.html".getBytes(), QrCodeModel.No2, QrCodeLevel.Q, 5);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "\n" +
                        "商品退換請持本聯及銷貨明細表。\n" +
                        "9999999-9999999 999999-999999 9999\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment("銷貨明細表 　(銷售)\n".getBytes(encoding), AlignmentPosition.Center);

        builder.appendAlignment("2014-01-15 13:00:02\n".getBytes(encoding), AlignmentPosition.Right);

        builder.append((
                "\n" +
                        "烏龍袋茶2g20入                55 x2 110TX\n" +
                        "茉莉烏龍茶2g20入              55 x2 110TX\n" +
                        "天仁觀音茶2g*20               55 x2 110TX\n").getBytes(encoding));

        builder.appendEmphasis((
                "      小　 計 :                330\n" +
                        "      總   計 :                330\n").getBytes(encoding));

        builder.append((
                "------------------------------------------------\n" +
                        "現 金                          400\n" +
                        "      找　 零 :                 70\n").getBytes(encoding));

        builder.appendEmphasis(" 101 發票金額 :                330\n".getBytes(encoding));

        builder.append((
                "2014-01-15 13:00\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendBarcode("{BStar.".getBytes(Charset.forName("US-ASCII")), BarcodeSymbology.Code128, BarcodeWidth.Mode2, 40, true);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "\n" +
                        "商品退換、贈品及停車兌換請持本聯。\n" +
                        "9999999-9999999 999999-999999 9999\n").getBytes(encoding));
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
                encoding = Charset.forName("Big5");
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendEmphasis(true);

        builder.appendMultipleHeight("Star Micronics\n".getBytes(encoding), 3);

        builder.appendEmphasis(false);

        builder.append("------------------------------------------------\n".getBytes(encoding));

        builder.appendMultiple((
                "電子發票證明聯\n" +
                        "103年01-02月\n" +
                        "EV-99999999\n").getBytes(encoding), 2, 2);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append(("2014/01/15 13:00\n" +
                "隨機碼 : 9999    總計 : 999\n" +
                "賣方 : 99999999\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendQrCode("http://www.star-m.jp/eng/index.html".getBytes(), QrCodeModel.No2, QrCodeLevel.Q, 5);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "\n" +
                        "商品退換請持本聯及銷貨明細表。\n" +
                        "9999999-9999999 999999-999999 9999\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment("銷貨明細表 　(銷售)\n".getBytes(encoding), AlignmentPosition.Center);

        builder.appendAlignment("2014-01-15 13:00:02\n".getBytes(encoding), AlignmentPosition.Right);

        builder.append((
                "\n" +
                        "烏龍袋茶2g20入                                    55 x2 110TX\n" +
                        "茉莉烏龍茶2g20入                                  55 x2 110TX\n" +
                        "天仁觀音茶2g*20                                   55 x2 110TX\n").getBytes(encoding));

        builder.appendEmphasis((
                "      小　 計 :                                    330\n" +
                        "      總   計 :                                    330\n").getBytes(encoding));

        builder.append((
                "---------------------------------------------------------------------\n" +
                        "現 金                                              400\n" +
                        "      找　 零 :                                     70\n").getBytes(encoding));

        builder.appendEmphasis(" 101 發票金額 :                                    330\n".getBytes(encoding));

        builder.append((
                "2014-01-15 13:00\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendBarcode("{BStar.".getBytes(Charset.forName("US-ASCII")), BarcodeSymbology.Code128, BarcodeWidth.Mode2, 40, true);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "\n" +
                        "商品退換、贈品及停車兌換請持本聯。\n" +
                        "9999999-9999999 999999-999999 9999\n").getBytes(encoding));
    }

    @Override
    public Bitmap create2inchRasterReceiptImage() {
        String textToPrint =
                "　　　　Star Micronics\n" +
                        "-----------------------------\n" +
                        "        電子發票證明聯\n" +
                        "        103年01-02月\n" +
                        "        EV-99999999\n" +
                        "2014/01/15 13:00\n" +
                        "隨機碼 : 9999      總計 : 999\n" +
                        "賣　方 : 99999999\n" +
                        "\n" +
                        "商品退換請持本聯及銷貨明細表。\n" +
                        "9999999-9999999 999999-999999 9999\n" +
                        "\n" +
                        "\n" +
                        "        銷貨明細表 　(銷售)\n" +
                        "     2014-01-15 13:00:02\n" +
                        "\n" +
                        "烏龍袋茶2g20入　   55 x2  110TX\n" +
                        "茉莉烏龍茶2g20入   55 x2  110TX\n" +
                        "天仁觀音茶2g*20　  55 x2  110TX\n" +
                        "     小　　計 :　　         330\n" +
                        "     總　　計 :　　         330\n" +
                        "-----------------------------\n" +
                        "現　金　　　                400\n" +
                        "     找　　零 :　　          70\n" +
                        " 101 發票金額 :　　         330\n" +
                        "2014-01-15 13:00\n" +
                        "\n" +
                        "商品退換、贈品及停車兌換請持本聯。\n" +
                        "9999999-9999999 999999-999999 9999\n";

        int      textSize = 22;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_TWO_INCH, typeface);
    }

    @Override
    public Bitmap create3inchRasterReceiptImage() {
        String textToPrint =
                "　 　　　  　　Star Micronics\n" +
                        "---------------------------------------\n" +
                        "              電子發票證明聯\n" +
                        "              103年01-02月\n" +
                        "              EV-99999999\n" +
                        "2014/01/15 13:00\n" +
                        "隨機碼 : 9999      總計 : 999\n" +
                        "賣　方 : 99999999\n" +
                        "\n" +
                        "商品退換請持本聯及銷貨明細表。\n" +
                        "9999999-9999999 999999-999999 9999\n" +
                        "\n" +
                        "\n" +
                        "         銷貨明細表 　(銷售)\n" +
                        "                    2014-01-15 13:00:02\n" +
                        "\n" +
                        "烏龍袋茶2g20入　         55 x2    110TX\n" +
                        "茉莉烏龍茶2g20入         55 x2    110TX\n" +
                        "天仁觀音茶2g*20　        55 x2    110TX\n" +
                        "     小　　計 :　　        330\n" +
                        "     總　　計 :　　        330\n" +
                        "---------------------------------------\n" +
                        "現　金　　　               400\n" +
                        "     找　　零 :　　         70\n" +
                        " 101 發票金額 :　　        330\n" +
                        "2014-01-15 13:00\n" +
                        "\n" +
                        "商品退換、贈品及停車兌換請持本聯。\n" +
                        "9999999-9999999 999999-999999 9999\n";

        int      textSize = 24;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_THREE_INCH, typeface);
    }

    @Override
    public Bitmap create4inchRasterReceiptImage() {
        String textToPrint =
                "　 　　　  　  　       Star Micronics\n" +
                        "---------------------------------------------------------\n" +
                        "                       電子發票證明聯\n" +
                        "                       103年01-02月\n" +
                        "                       EV-99999999\n" +
                        "2014/01/15 13:00\n" +
                        "隨機碼 : 9999      總計 : 999\n" +
                        "賣　方 : 99999999\n" +
                        "\n" +
                        "商品退換請持本聯及銷貨明細表。\n" +
                        "9999999-9999999 999999-999999 9999\n" +
                        "\n" +
                        "\n" +
                        "                      銷貨明細表 　(銷售)\n" +
                        "                                      2014-01-15 13:00:02\n" +
                        "\n" +
                        "烏龍袋茶2g20入　                   55 x2        110TX\n" +
                        "茉莉烏龍茶2g20入                   55 x2        110TX\n" +
                        "天仁觀音茶2g*20　                  55 x2        110TX\n" +
                        "     小　　計 :　　                  330\n" +
                        "     總　　計 :　　                  330\n" +
                        "---------------------------------------------------------\n" +
                        "現　金　　　                         400\n" +
                        "     找　　零 :　　                   70\n" +
                        " 101 發票金額 :　　                  330\n" +
                        "2014-01-15 13:00\n" +
                        "\n" +
                        "商品退換、贈品及停車兌換請持本聯。\n" +
                        "9999999-9999999 999999-999999 9999\n";

        int      textSize = 24;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_FOUR_INCH, typeface);
    }

    @Override
    public Bitmap createCouponImage(Resources resources) {
        return BitmapFactory.decodeResource(resources, R.drawable.coupon_image_traditional_chinese);
    }

    @Override
    public Bitmap createEscPos3inchRasterReceiptImage() {
        String textToPrint =
                "\n" +
                "　 　  　　Star Micronics\n" +
                        "-----------------------------------\n" +
                        "           電子發票證明聯\n" +
                        "           103年01-02月\n" +
                        "           EV-99999999\n" +
                        "2014/01/15 13:00\n" +
                        "隨機碼 : 9999      總計 : 999\n" +
                        "賣　方 : 99999999\n" +
                        "\n" +
                        "商品退換請持本聯及銷貨明細表。\n" +
                        "9999999-9999999 999999-999999 9999\n" +
                        "\n" +
                        "\n" +
                        "         銷貨明細表 　(銷售)\n" +
                        "            2014-01-15 13:00:02\n" +
                        "\n" +
                        "烏龍袋茶2g20入　     55 x2    110TX\n" +
                        "茉莉烏龍茶2g20入     55 x2    110TX\n" +
                        "天仁觀音茶2g*20　    55 x2    110TX\n" +
                        "     小　　計 :　　        330\n" +
                        "     總　　計 :　　        330\n" +
                        "-----------------------------------\n" +
                        "現　金　　　               400\n" +
                        "     找　　零 :　　         70\n" +
                        " 101 發票金額 :　　        330\n" +
                        "2014-01-15 13:00\n" +
                        "\n" +
                        "商品退換、贈品及停車兌換請持本聯。\n" +
                        "9999999-9999999 999999-999999 9999\n";

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
                encoding = Charset.forName("Big5");
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendEmphasis(true);

        builder.append("\n".getBytes());

        builder.appendMultipleHeight("Star Micronics\n".getBytes(encoding), 3);

        builder.appendEmphasis(false);

        builder.append("------------------------------------------\n".getBytes(encoding));

        builder.appendMultiple((
                "電子發票證明聯\n" +
                        "103年01-02月\n" +
                        "EV-99999999\n").getBytes(encoding), 2, 2);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append(("2014/01/15 13:00\n" +
                "隨機碼 : 9999    總計 : 999\n" +
                "賣方 : 99999999\n" +
                "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendQrCode("http://www.star-m.jp/eng/index.html".getBytes(), QrCodeModel.No2, QrCodeLevel.Q, 5);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "\n" +
                        "商品退換請持本聯及銷貨明細表。\n" +
                        "9999999-9999999 999999-999999 9999\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment("銷貨明細表 　(銷售)\n".getBytes(encoding), AlignmentPosition.Center);

        builder.appendAlignment("2014-01-15 13:00:02\n".getBytes(encoding), AlignmentPosition.Right);

        builder.append((
                "\n" +
                        "烏龍袋茶2g20入                55 x2 110TX\n" +
                        "茉莉烏龍茶2g20入              55 x2 110TX\n" +
                        "天仁觀音茶2g*20               55 x2 110TX\n").getBytes(encoding));

        builder.appendEmphasis((
                "      小　 計 :                330\n" +
                        "      總   計 :                330\n").getBytes(encoding));

        builder.append((
                "------------------------------------------\n" +
                        "現 金                          400\n" +
                        "      找　 零 :                 70\n").getBytes(encoding));

        builder.appendEmphasis(" 101 發票金額 :                330\n".getBytes(encoding));

        builder.append((
                "2014-01-15 13:00\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendBarcode("{BStar.".getBytes(Charset.forName("US-ASCII")), BarcodeSymbology.Code128, BarcodeWidth.Mode2, 40, true);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "\n" +
                        "商品退換、贈品及停車兌換請持本聯。\n" +
                        "9999999-9999999 999999-999999 9999\n").getBytes(encoding));
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
                encoding = Charset.forName("Big5");
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendEmphasis(true);

        builder.appendMultipleHeight("Star Micronics\n".getBytes(encoding), 3);

        builder.appendEmphasis(false);

        builder.append("------------------------------------------\n".getBytes(encoding));

        builder.appendMultiple((
                "電子發票證明聯\n" +
                        "103年01-02月\n" +
                        "EV-99999999\n").getBytes(encoding), 2, 2);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append(("2014/01/15 13:00\n" +
                "隨機碼 : 9999    總計 : 999\n" +
                "賣方 : 99999999\n" +
                "\n" +
                "商品退換請持本聯及銷貨明細表。\n" +
                "9999999-9999999 999999-999999 9999\n" +
                "\n").getBytes(encoding));

        builder.appendAlignment("銷貨明細表 　(銷售)\n".getBytes(encoding), AlignmentPosition.Center);

        builder.appendAlignment("2014-01-15 13:00:02\n".getBytes(encoding), AlignmentPosition.Right);

        builder.append((
                "\n" +
                        "烏龍袋茶2g20入             55 x2 110TX\n" +
                        "茉莉烏龍茶2g20入           55 x2 110TX\n" +
                        "天仁觀音茶2g*20            55 x2 110TX\n").getBytes(encoding));

        builder.appendEmphasis((
                "      小　 計 :             330\n" +
                        "      總   計 :             330\n").getBytes(encoding));

        builder.append((
                "------------------------------------------\n" +
                        "現 金                       400\n" +
                        "      找　 零 :              70\n").getBytes(encoding));

        builder.appendEmphasis(" 101 發票金額 :             330\n".getBytes(encoding));

        builder.append((
                "2014-01-15 13:00\n" +
                        "\n" +
                        "商品退換、贈品及停車兌換請持本聯。\n" +
                        "9999999-9999999 999999-999999 9999\n").getBytes(encoding));
    }

    @Override
    public Bitmap createSk12inchRasterReceiptImage() {
        String textToPrint =
                "　　　　　Star Micronics\n" +
                        "---------------------------------\n" +
                        "  　      電子發票證明聯\n" +
                        "          103年01-02月\n" +
                        "          EV-99999999\n" +
                        "2014/01/15 13:00\n" +
                        "隨機碼 : 9999        總計 : 999\n" +
                        "賣　方 : 99999999\n" +
                        "\n" +
                        "商品退換請持本聯及銷貨明細表。\n" +
                        "9999999-9999999 999999-999999 9999\n" +
                        "\n" +
                        "\n" +
                        "        銷貨明細表 　(銷售)\n" +
                        "       2014-01-15 13:00:02\n" +
                        "\n" +
                        "烏龍袋茶2g20入　     55 x2   110TX\n" +
                        "茉莉烏龍茶2g20入     55 x2   110TX\n" +
                        "天仁觀音茶2g*20　    55 x2   110TX\n" +
                        "     小　　計 :　　            330\n" +
                        "     總　　計 :　　            330\n" +
                        "---------------------------------\n" +
                        "現　金　　　                   400\n" +
                        "     找　　零 :　　             70\n" +
                        " 101 發票金額 :　　            330\n" +
                        "2014-01-15 13:00\n" +
                        "\n" +
                        "商品退換、贈品及停車兌換請持本聯。\n" +
                        "9999999-9999999 999999-999999 9999\n";

        int      textSize = 22;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_SK1_TWO_INCH, typeface);
    }

    @Override
    public void appendSk12inchTextReceiptData(ICommandBuilder builder, boolean utf8) {
        // SK1 series does not support Chinese font (DBCS).
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