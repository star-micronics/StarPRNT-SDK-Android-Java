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

public class SimplifiedChineseReceiptsImpl extends ILocalizeReceipts {

    public SimplifiedChineseReceiptsImpl() {
        mLanguageCode = "zh-CN";

        mCharacterCode = CharacterCode.SimplifiedChinese;
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
                encoding = Charset.forName("GB2312");
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendEmphasis(true);

        builder.appendMultipleHeight("STAR便利店\n".getBytes(encoding), 3);

        builder.appendMultipleHeight("欢迎光临\n".getBytes(encoding), 2);

        builder.appendEmphasis(false);

        builder.append(("Unit 1906-08, 19/F,\n" +
                "Enterprise Square 2,\n" +
                "3 Sheung Yuet Road,\n" +
                "Kowloon Bay, KLN\n" +
                "\n" +
                "Tel : (852) 2795 2335\n" +
                "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "货品名称           数量     价格\n" +
                        "--------------------------------\n" +
                        "\n" +
                        "罐装可乐\n" +
                        "* Coke                 1    7.00\n" +
                        "纸包柠檬茶\n" +
                        "* Lemon Tea            2   10.00\n" +
                        "热狗\n" +
                        "* Hot Dog              1   10.00\n" +
                        "薯片(50克装)\n" +
                        "* Potato Chips(50g)    1   11.00\n" +
                        "--------------------------------\n" +
                        "\n" +
                        "              总数 :       38.00\n" +
                        "              现金 :       38.00\n" +
                        "              找赎 :        0.00\n" +
                        "\n" +
                        "卡号码 Card No.       : 88888888\n" +
                        "卡余额 Remaining Val.    : 88.00\n" +
                        "机号   Device No.       : 1234F1\n" +
                        "\n" +
                        "\n" +
                        "DD/MM/YYYY  HH:MM:SS\n" +
                        "交易编号 : 88888\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "收银机 : 001  收银员 : 180\n" +
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
                encoding = Charset.forName("GB2312");
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendEmphasis(true);

        builder.appendMultipleHeight("STAR便利店\n".getBytes(encoding), 3);

        builder.appendMultipleHeight("欢迎光临\n".getBytes(encoding), 2);

        builder.appendEmphasis(false);

        builder.append(("Unit 1906-08, 19/F, Enterprise Square 2,\n" +
                "　3 Sheung Yuet Road, Kowloon Bay, KLN\n" +
                "\n" +
                "Tel : (852) 2795 2335\n" +
                "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "货品名称   　            数量   　   价格\n" +
                        "------------------------------------------------\n" +
                        "\n" +
                        "罐装可乐\n" +
                        "* Coke                      1        7.00\n" +
                        "纸包柠檬茶\n" +
                        "* Lemon Tea                 2       10.00\n" +
                        "热狗\n" +
                        "* Hot Dog                   1       10.00\n" +
                        "薯片(50克装)\n" +
                        "* Potato Chips(50g)         1       11.00\n" +
                        "------------------------------------------------\n" +
                        "\n" +
                        "                         总数 :     38.00\n" +
                        "                         现金 :     38.00\n" +
                        "                         找赎 :      0.00\n" +
                        "\n" +
                        "卡号码 Card No.       : 88888888\n" +
                        "卡余额 Remaining Val. : 88.00\n" +
                        "机号   Device No.     : 1234F1\n" +
                        "\n" +
                        "\n" +
                        "DD/MM/YYYY  HH:MM:SS  交易编号 : 88888\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append("收银机 : 001  收银员 : 180\n".getBytes(encoding));

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
                encoding = Charset.forName("GB2312");
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

//        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendEmphasis(true);

        builder.appendMultipleHeight("STAR便利店\n".getBytes(encoding), 3);

        builder.appendMultipleHeight("欢迎光临\n".getBytes(encoding), 2);

        builder.appendEmphasis(false);

        builder.append(("Unit 1906-08, 19/F, Enterprise Square 2,\n" +
                "　3 Sheung Yuet Road, Kowloon Bay, KLN\n" +
                "\n" +
                "Tel : (852) 2795 2335\n" +
                "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "货品名称   　                      数量        　         价格\n" +
                        "----------------------------------------------------------------\n" +
                        "\n" +
                        "罐装可乐\n" +
                        "* Coke                               1                    7.00\n" +
                        "纸包柠檬茶\n" +
                        "* Lemon Tea                          2                   10.00\n" +
                        "热狗\n" +
                        "* Hot Dog                            1                   10.00\n" +
                        "薯片(50克装)\n" +
                        "* Potato Chips(50g)                  1                   11.00\n" +
                        "----------------------------------------------------------------\n" +
                        "\n" +
                        "                                   总数 :                38.00\n" +
                        "                                   现金 :                38.00\n" +
                        "                                   找赎 :                 0.00\n" +
                        "\n" +
                        "卡号码 Card No.                   : 88888888\n" +
                        "卡余额 Remaining Val.             : 88.00\n" +
                        "机号   Device No.                 : 1234F1\n" +
                        "\n" +
                        "\n" +
                        "DD/MM/YYYY  HH:MM:SS        交易编号 : 88888\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append("收银机 : 001  收银员 : 180\n".getBytes(encoding));

        builder.appendBarcode("{BStar.".getBytes(Charset.forName("US-ASCII")), BarcodeSymbology.Code128, BarcodeWidth.Mode2, 40, true);
    }

    @Override
    public Bitmap create2inchRasterReceiptImage() {
        String textToPrint =
                "　     　　STAR便利店\n" +
                        "           欢迎光临\n" +
                        "\n" +
                        "Unit 1906-08,19/F,\n" +
                        "  Enterprise Square 2,\n" +
                        "  3 Sheung Yuet Road,\n" +
                        "  Kowloon Bay, KLN\n" +
                        "\n" +
                        "Tel: (852) 2795 2335\n" +
                        "\n" +
                        "货品名称            数量   价格\n" +
                        "-----------------------------\n" +
                        "罐装可乐\n" +
                        "* Coke              1    7.00\n" +
                        "纸包柠檬茶\n" +
                        "* Lemon Tea         2   10.00\n" +
                        "热狗\n" +
                        "* Hot Dog           1   10.00\n" +
                        "薯片(50克装)\n" +
                        "* Potato Chips(50g) 1   11.00\n" +
                        "-----------------------------\n" +
                        "\n" +
                        "               总　数 :  38.00\n" +
                        "               现　金 :  38.00\n" +
                        "               找　赎 :   0.00\n" +
                        "\n" +
                        "卡号码 Card No. :    88888888\n" +
                        "卡余额 Remaining Val. : 88.00\n" +
                        "机号　 Device No. :    1234F1\n" +
                        "\n" +
                        "DD/MM/YYYY HH:MM:SS\n" +
                        "交易编号: 88888\n" +
                        "\n" +
                        "      收银机:001  收银员:180\n";

        int      textSize = 22;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_TWO_INCH, typeface);
    }

    @Override
    public Bitmap create3inchRasterReceiptImage() {
        String textToPrint =
                "　　　　　　  　　STAR便利店\n" +
                        "                欢迎光临\n" +
                        "\n" +
                        "Unit 1906-08,19/F,Enterprise Square 2,\n" +
                        "  3 Sheung Yuet Road, Kowloon Bay, KLN\n" +
                        "\n" +
                        "Tel: (852) 2795 2335\n" +
                        "\n" +
                        "货品名称                 数量   　  价格\n" +
                        "---------------------------------------\n" +
                        "罐装可乐\n" +
                        "* Coke                   1        7.00\n" +
                        "纸包柠檬茶\n" +
                        "* Lemon Tea              2       10.00\n" +
                        "热狗\n" +
                        "* Hot Dog                1       10.00\n" +
                        "薯片(50克装)\n" +
                        "* Potato Chips(50g)      1       11.00\n" +
                        "---------------------------------------\n" +
                        "\n" +
                        "                        总　数 :  38.00\n" +
                        "                        现　金 :  38.00\n" +
                        "                        找　赎 :   0.00\n" +
                        "\n" +
                        "卡号码 Card No.        :       88888888\n" +
                        "卡余额 Remaining Val.  :       88.00\n" +
                        "机号　 Device No.      :       1234F1\n" +
                        "\n" +
                        "DD/MM/YYYY   HH:MM:SS   交易编号: 88888\n" +
                        "\n" +
                        "          收银机:001  收银员:180\n";

        int      textSize = 24;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_THREE_INCH, typeface);
    }

    @Override
    public Bitmap create4inchRasterReceiptImage() {
        String textToPrint =
                "　　　　　　  　　         STAR便利店\n" +
                        "                          欢迎光临\n" +
                        "\n" +
                        "     Unit 1906-08,19/F,Enterprise Square 2,\n" +
                        "                3 Sheung Yuet Road, Kowloon Bay, KLN\n" +
                        "\n" +
                        "Tel: (852) 2795 2335\n" +
                        "\n" +
                        "货品名称                               数量          价格\n" +
                        "---------------------------------------------------------\n" +
                        "罐装可乐\n" +
                        "* Coke                                 1            7.00\n" +
                        "纸包柠檬茶\n" +
                        "* Lemon Tea                            2           10.00\n" +
                        "热狗\n" +
                        "* Hot Dog                              1           10.00\n" +
                        "薯片(50克装)\n" +
                        "* Potato Chips(50g)                    1           11.00\n" +
                        "---------------------------------------------------------\n" +
                        "\n" +
                        "                                          总　数 :  38.00\n" +
                        "                                          现　金 :  38.00\n" +
                        "                                          找　赎 :   0.00\n" +
                        "\n" +
                        "卡号码 Card No.        :       88888888\n" +
                        "卡余额 Remaining Val.  :       88.00\n" +
                        "机号　 Device No.      :       1234F1\n" +
                        "\n" +
                        "DD/MM/YYYY              HH:MM:SS          交易编号: 88888\n" +
                        "\n" +
                        "                   收银机:001  收银员:180\n";

        int      textSize = 24;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_FOUR_INCH, typeface);
    }

    @Override
    public Bitmap createCouponImage(Resources resources) {
        return BitmapFactory.decodeResource(resources, R.drawable.coupon_image_simplified_chinese);
    }

    @Override
    public Bitmap createEscPos3inchRasterReceiptImage() {
        String textToPrint =
                "\n" +
                "　　　　　　　STAR便利店\n" +
                        "              欢迎光临\n" +
                        "\n" +
                        "Unit 1906-08,19/F,\n" +
                        "  Enterprise Square 2,\n" +
                        "  3 Sheung Yuet Road,\n" +
                        "  Kowloon Bay, KLN\n" +
                        "\n" +
                        "Tel: (852) 2795 2335\n" +
                        "\n" +
                        "货品名称              数量   　  价格\n" +
                        "-----------------------------------\n" +
                        "罐装可乐\n" +
                        "* Coke                1        7.00\n" +
                        "纸包柠檬茶\n" +
                        "* Lemon Tea           2       10.00\n" +
                        "热狗\n" +
                        "* Hot Dog             1       10.00\n" +
                        "薯片(50克装)\n" +
                        "* Potato Chips(50g)   1       11.00\n" +
                        "-----------------------------------\n" +
                        "\n" +
                        "                     总　数 :  38.00\n" +
                        "                     现　金 :  38.00\n" +
                        "                     找　赎 :   0.00\n" +
                        "\n" +
                        "卡号码 Card No.        :    88888888\n" +
                        "卡余额 Remaining Val.  :    88.00\n" +
                        "机号　 Device No.      :    1234F1\n" +
                        "\n" +
                        "DD/MM/YYYY HH:MM:SS  交易编号: 88888\n" +
                        "\n" +
                        "          收银机:001  收银员:180\n";

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
                encoding = Charset.forName("GB2312");
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

        builder.appendMultipleHeight("STAR便利店\n".getBytes(encoding), 3);

        builder.appendMultipleHeight("欢迎光临\n".getBytes(encoding), 2);

        builder.appendEmphasis(false);

        builder.append((
                "Unit 1906-08, 19/F, Enterprise Square 2,\n" +
                        "　3 Sheung Yuet Road, Kowloon Bay, KLN\n" +
                        "\n" +
                        "Tel : (852) 2795 2335\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "货品名称   　        数量   　 价格\n" +
                        "------------------------------------------\n" +
                        "\n" +
                        "罐装可乐\n" +
                        "* Coke                  1      7.00\n" +
                        "纸包柠檬茶\n" +
                        "* Lemon Tea             2     10.00\n" +
                        "热狗\n" +
                        "* Hot Dog               1     10.00\n" +
                        "薯片(50克装)\n" +
                        "* Potato Chips(50g)     1     11.00\n" +
                        "------------------------------------------\n" +
                        "\n" +
                        "                   总数 :     38.00\n" +
                        "                   现金 :     38.00\n" +
                        "                   找赎 :      0.00\n" +
                        "\n" +
                        "卡号码 Card No.       : 88888888\n" +
                        "卡余额 Remaining Val. : 88.00\n" +
                        "机号   Device No.     : 1234F1\n" +
                        "\n" +
                        "\n" +
                        "DD/MM/YYYY  HH:MM:SS  交易编号 : 88888\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append("收银机 : 001  收银员 : 180\n".getBytes(encoding));

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
                encoding = Charset.forName("GB2312");
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

//        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendEmphasis(true);

        builder.appendMultipleHeight((
                "STAR便利店\n" +
                "欢迎光临\n").getBytes(encoding), 2);

        builder.appendEmphasis(false);

        builder.append(("Unit 1906-08, 19/F, Enterprise Square 2,\n" +
                "　3 Sheung Yuet Road, Kowloon Bay, KLN\n" +
                "\n" +
                "Tel : (852) 2795 2335\n" +
                "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "货品名称   　          数量  　   价格\n" +
                        "------------------------------------------\n" +
                        "\n" +
                        "罐装可乐\n" +
                        "* Coke                   1        7.00\n" +
                        "纸包柠檬茶\n" +
                        "* Lemon Tea              2       10.00\n" +
                        "热狗\n" +
                        "* Hot Dog                1       10.00\n" +
                        "薯片(50克装)\n" +
                        "* Potato Chips(50g)      1       11.00\n" +
                        "------------------------------------------\n" +
                        "\n" +
                        "                      总数 :     38.00\n" +
                        "                      现金 :     38.00\n" +
                        "                      找赎 :      0.00\n" +
                        "\n" +
                        "卡号码 Card No.       : 88888888\n" +
                        "卡余额 Remaining Val. : 88.00\n" +
                        "机号   Device No.     : 1234F1\n" +
                        "\n" +
                        "\n" +
                        "DD/MM/YYYY  HH:MM:SS  交易编号 : 88888\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append("收银机 : 001  收银员 : 180\n".getBytes(encoding));
    }

    @Override
    public Bitmap createSk12inchRasterReceiptImage() {
        String textToPrint =
                "　　　　　　　STAR便利店\n" +
                        "　　　　　　　　欢迎光临\n" +
                        "\n" +
                        "Unit 1906-08,19/F,\n" +
                        "  Enterprise Square 2,\n" +
                        "  3 Sheung Yuet Road,\n" +
                        "  Kowloon Bay, KLN\n" +
                        "\n" +
                        "Tel: (852) 2795 2335\n" +
                        "\n" +
                        "货品名称              数量     价格\n" +
                        "---------------------------------\n" +
                        "罐装可乐\n" +
                        "* Coke                1      7.00\n" +
                        "纸包柠檬茶\n" +
                        "* Lemon Tea           2     10.00\n" +
                        "热狗\n" +
                        "* Hot Dog             1     10.00\n" +
                        "薯片(50克装)\n" +
                        "* Potato Chips(50g)   1     11.00\n" +
                        "---------------------------------\n" +
                        "\n" +
                        "                   总　数 :  38.00\n" +
                        "                   现　金 :  38.00\n" +
                        "                   找　赎 :   0.00\n" +
                        "\n" +
                        "卡号码 Card No. :        88888888\n" +
                        "卡余额 Remaining Val. :     88.00\n" +
                        "机号　 Device No. :        1234F1\n" +
                        "\n" +
                        "DD/MM/YYYY HH:MM:SS\n" +
                        "交易编号: 88888\n" +
                        "\n" +
                        "        收银机:001  收银员:180\n";

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
