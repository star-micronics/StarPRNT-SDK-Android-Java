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

public class RussianReceiptsImpl extends ILocalizeReceipts {

    public RussianReceiptsImpl() {
        mLanguageCode = "Ru";

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
                encoding = Charset.forName("Windows-1251");

                builder.appendCodePage(CodePageType.CP1251);
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultiple("Р Е Л А К С\n".getBytes(encoding), 2, 2);

        builder.append(("ООО “РЕЛАКС”\n" +
                "СПб., Малая Балканская, д. 38, лит. А\n" +
                "тел. 307-07-12\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append(("РЕГ №322736     ИНН:123321\n" +
                "01 Белякова И.А.КАССА: 0020 ОТД.01\n").getBytes(encoding));

        builder.appendAlignment("ЧЕК НА ПРОДАЖУ  No 84373\n".getBytes(encoding), AlignmentPosition.Center);

        builder.append((
                "--------------------------------\n" +
                        " 1. Яблоки Айдаред, кг    144.50\n" +
                        " 2. Соус соевый Sen So     36.40\n" +
                        " 3. Соус томатный Клас     19.90\n" +
                        " 4. Ребра свиные в.к м     78.20\n" +
                        " 5. Масло подсол раф д    114.00\n" +
                        " 6. Блокнот 10х14см сп    164.00\n" +
                        " 7. Морс Северная Ягод     99.90\n" +
                        " 8. Активия Биойогурт      43.40\n" +
                        " 9. Бублики Украинские     26.90\n" +
                        "10. Активия Биойогурт      43.40\n" +
                        "11. Сахар-песок 1кг        58.40\n" +
                        "12. Хлопья овсяные Ясн     38.40\n" +
                        "13. Кинза 50г              39.90\n" +
                        "14. Пемза “Сердечко” .Т    37.90\n" +
                        "15. Приправа Santa Mar     47.90\n" +
                        "16. Томаты слива Выбор    162.00\n" +
                        "17. Бонд Стрит Ред Сел     56.90\n" +
                        "--------------------------------\n" +
                        "--------------------------------\n" +
                        "ДИСКОНТНАЯ КАРТА\n" +
                        "                No:2440012489765\n" +
                        "--------------------------------\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Right);

        builder.append((
                "ИТОГО К ОПЛАТЕ = 1212.00\n" +
                        "НАЛИЧНЫЕ = 1212.00\n" +
                        "ВАША СКИДКА : 0.41\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment((
                "ЦЕНЫ УКАЗАНЫ С УЧЕТОМ СКИДКИ\n" +
                        "\n").getBytes(encoding), AlignmentPosition.Center);

        builder.append((
                "08-02-2015 09:49  0254.0130604\n" +
                        "00083213 #060127\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "СПАСИБО ЗА ПОКУПКУ !\n" +
                        "МЫ  ОТКРЫТЫ ЕЖЕДНЕВНО С 9 ДО 23\n" +
                        "СОХРАНЯЙТЕ, ПОЖАЛУЙСТА , ЧЕК\n" +
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
                encoding = Charset.forName("Windows-1251");

                builder.appendCodePage(CodePageType.CP1251);
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultiple("Р Е Л А К С\n".getBytes(encoding), 2, 2);

        builder.append(("ООО “РЕЛАКС”\n" +
                "СПб., Малая Балканская, д. 38, лит. А\n" +
                "тел. 307-07-12\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append(("РЕГ №322736 ИНН : 123321\n" +
                "01  Белякова И.А.  КАССА: 0020 ОТД.01\n").getBytes(encoding));

        builder.appendAlignment("ЧЕК НА ПРОДАЖУ  No 84373\n".getBytes(encoding), AlignmentPosition.Center);

        builder.append((
                "------------------------------------------------\n" +
                        "1.     Яблоки Айдаред, кг                 144.50\n" +
                        "2.     Соус соевый Sen So                  36.40\n" +
                        "3.     Соус томатный Клас                  19.90\n" +
                        "4.     Ребра свиные в.к м                  78.20\n" +
                        "5.     Масло подсол раф д                 114.00\n" +
                        "6.     Блокнот 10х14см сп                 164.00\n" +
                        "7.     Морс Северная Ягод                  99.90\n" +
                        "8.     Активия Биойогурт                   43.40\n" +
                        "9.     Бублики Украинские                  26.90\n" +
                        "10.    Активия Биойогурт                   43.40\n" +
                        "11.    Сахар-песок 1кг                     58.40\n" +
                        "12.    Хлопья овсяные Ясн                  38.40\n" +
                        "13.    Кинза 50г                           39.90\n" +
                        "14.    Пемза “Сердечко” .Т                 37.90\n" +
                        "15.    Приправа Santa Mar                  47.90\n" +
                        "16.    Томаты слива Выбор                 162.00\n" +
                        "17.    Бонд Стрит Ред Сел                  56.90\n" +
                        "------------------------------------------------\n" +
                        "------------------------------------------------\n" +
                        "ДИСКОНТНАЯ КАРТА  No: 2440012489765\n" +
                        "------------------------------------------------\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Right);

        builder.append((
                "ИТОГО  К  ОПЛАТЕ     = 1212.00\n" +
                        "НАЛИЧНЫЕ             = 1212.00\n" +
                        "ВАША СКИДКА : 0.41\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment((
                "ЦЕНЫ УКАЗАНЫ С УЧЕТОМ СКИДКИ\n" +
                        "\n").getBytes(encoding), AlignmentPosition.Center);

        builder.append((
                "08-02-2015 09:49  0254.0130604\n" +
                        "00083213 #060127\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "СПАСИБО ЗА ПОКУПКУ !\n" +
                        "МЫ  ОТКРЫТЫ ЕЖЕДНЕВНО С 9 ДО 23\n" +
                        "СОХРАНЯЙТЕ, ПОЖАЛУЙСТА , ЧЕК\n" +
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
                encoding = Charset.forName("Windows-1251");

                builder.appendCodePage(CodePageType.CP1251);
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultiple("Р Е Л А К С\n".getBytes(encoding), 2, 2);

        builder.append(("ООО “РЕЛАКС”\n" +
                "СПб., Малая Балканская, д. 38, лит. А\n" +
                "тел. 307-07-12\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append(("РЕГ №322736 ИНН : 123321\n" +
                "01  Белякова И.А.  КАССА: 0020 ОТД.01\n").getBytes(encoding));

        builder.appendAlignment("ЧЕК НА ПРОДАЖУ  No 84373\n".getBytes(encoding), AlignmentPosition.Center);

        builder.append((
                "---------------------------------------------------------------------\n" +
                        "1.     Яблоки Айдаред, кг                                      144.50\n" +
                        "2.     Соус соевый Sen So                                       36.40\n" +
                        "3.     Соус томатный Клас                                       19.90\n" +
                        "4.     Ребра свиные в.к м                                       78.20\n" +
                        "5.     Масло подсол раф д                                      114.00\n" +
                        "6.     Блокнот 10х14см сп                                      164.00\n" +
                        "7.     Морс Северная Ягод                                       99.90\n" +
                        "8.     Активия Биойогурт                                        43.40\n" +
                        "9.     Бублики Украинские                                       26.90\n" +
                        "10.    Активия Биойогурт                                        43.40\n" +
                        "11.    Сахар-песок 1кг                                          58.40\n" +
                        "12.    Хлопья овсяные Ясн                                       38.40\n" +
                        "13.    Кинза 50г                                                39.90\n" +
                        "14.    Пемза “Сердечко” .Т                                      37.90\n" +
                        "15.    Приправа Santa Mar                                       47.90\n" +
                        "16.    Томаты слива Выбор                                      162.00\n" +
                        "17.    Бонд Стрит Ред Сел                                       56.90\n" +
                        "---------------------------------------------------------------------\n" +
                        "---------------------------------------------------------------------\n" +
                        "ДИСКОНТНАЯ КАРТА  No: 2440012489765\n" +
                        "---------------------------------------------------------------------\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Right);

        builder.append((
                "ИТОГО  К  ОПЛАТЕ           = 1212.00\n" +
                        "НАЛИЧНЫЕ                   = 1212.00\n" +
                        "ВАША СКИДКА : 0.41\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment((
                "ЦЕНЫ УКАЗАНЫ С УЧЕТОМ СКИДКИ\n" +
                        "\n").getBytes(encoding), AlignmentPosition.Center);

        builder.append((
                "08-02-2015 09:49  0254.0130604\n" +
                        "00083213 #060127\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "СПАСИБО ЗА ПОКУПКУ !\n" +
                        "МЫ  ОТКРЫТЫ ЕЖЕДНЕВНО С 9 ДО 23\n" +
                        "СОХРАНЯЙТЕ, ПОЖАЛУЙСТА , ЧЕК\n" +
                        "\n").getBytes(encoding));

        builder.appendBarcode("{BStar.".getBytes(Charset.forName("US-ASCII")), BarcodeSymbology.Code128, BarcodeWidth.Mode2, 40, true);
    }

    @Override
    public Bitmap create2inchRasterReceiptImage() {
        String textToPrint =
                "          Р Е Л А К С\n" +
                        "          ООО “РЕЛАКС”\n" +
                        "СПб., Малая Балканская, д.\n" +
                        "38, лит. А\n" +
                        "\n" +
                        "тел. 307-07-12\n" +
                        "РЕГ №322736     ИНН:123321\n" +
                        "01 Белякова И.А. КАССА:0020\n" +
                        "ОТД.01\n" +
                        "ЧЕК НА ПРОДАЖУ  No 84373\n" +
                        "----------------------------\n" +
                        " 1.Яблоки Айдаред, кг 144.50\n" +
                        " 2.Соус соевый Sen So  36.40\n" +
                        " 3.Соус томатный Клас  19.90\n" +
                        " 4.Ребра свиные в.к м  78.20\n" +
                        " 5.Масло подсол раф д 114.00\n" +
                        " 6.Блокнот 10х14см сп 164.00\n" +
                        " 7.Морс Северная Ягод  99.90\n" +
                        " 8.Активия Биойогурт   43.40\n" +
                        " 9.Бублики Украинские  26.90\n" +
                        "10.Активия Биойогурт   43.40\n" +
                        "11.Сахар-песок 1кг     58.40\n" +
                        "12.Хлопья овсяные Ясн  38.40\n" +
                        "13.Кинза 50г           39.90\n" +
                        "14.Пемза “Сердечко” .Т 37.90\n" +
                        "15.Приправа Santa Mar  47.90\n" +
                        "16.Томаты слива Выбор 162.00\n" +
                        "17.Бонд Стрит Ред Сел  56.90\n" +
                        "----------------------------\n" +
                        "----------------------------\n" +
                        "ДИСКОНТНАЯ КАРТА\n" +
                        "            No:2440012489765\n" +
                        "----------------------------\n" +
                        "ИТОГО К ОПЛАТЕ = 1212.00\n" +
                        "НАЛИЧНЫЕ = 1212.00\n" +
                        "ВАША СКИДКА : 0.41\n" +
                        "ЦЕНЫ УКАЗАНЫ С УЧЕТОМ СКИДКИ\n" +
                        "\n" +
                        "08-02-2015 09:49\n" +
                        "0254.013060400083213 #060127\n" +
                        "СПАСИБО ЗА ПОКУПКУ !\n" +
                        "\n" +
                        "МЫ  ОТКРЫТЫ ЕЖЕДНЕВНО С 9 ДО\n" +
                        "23 СОХРАНЯЙТЕ, ПОЖАЛУЙСТА ,\n" +
                        "ЧЕК\n";

        int      textSize = 22;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_TWO_INCH, typeface);
    }

    @Override
    public Bitmap create3inchRasterReceiptImage() {
        String textToPrint =
                "      Р Е Л А К С   ООО “РЕЛАКС”\n" +
                        " СПб., Малая Балканская, д. 38, лит. А\n" +
                        "\n" +
                        "тел. 307-07-12\n" +
                        "РЕГ №322736     ИНН:123321\n" +
                        "01 Белякова И.А. КАССА: 0020 ОТД.01\n" +
                        "ЧЕК НА ПРОДАЖУ  No 84373\n" +
                        "--------------------------------------\n" +
                        " 1. Яблоки Айдаред, кг          144.50\n" +
                        " 2. Соус соевый Sen So           36.40\n" +
                        " 3. Соус томатный Клас           19.90\n" +
                        " 4. Ребра свиные в.к м           78.20\n" +
                        " 5. Масло подсол раф д          114.00\n" +
                        " 6. Блокнот 10х14см сп          164.00\n" +
                        " 7. Морс Северная Ягод           99.90\n" +
                        " 8. Активия Биойогурт            43.40\n" +
                        " 9. Бублики Украинские           26.90\n" +
                        "10. Активия Биойогурт            43.40\n" +
                        "11. Сахар-песок 1кг              58.40\n" +
                        "12. Хлопья овсяные Ясн           38.40\n" +
                        "13. Кинза 50г                    39.90\n" +
                        "14. Пемза “Сердечко” .Т          37.90\n" +
                        "15. Приправа Santa Mar           47.90\n" +
                        "16. Томаты слива Выбор          162.00\n" +
                        "17. Бонд Стрит Ред Сел           56.90\n" +
                        "--------------------------------------\n" +
                        "--------------------------------------\n" +
                        "ДИСКОНТНАЯ КАРТА      No:2440012489765\n" +
                        "--------------------------------------\n" +
                        "ИТОГО К ОПЛАТЕ = 1212.00\n" +
                        "НАЛИЧНЫЕ = 1212.00\n" +
                        "ВАША СКИДКА : 0.41\n" +
                        "ЦЕНЫ УКАЗАНЫ С УЧЕТОМ СКИДКИ\n" +
                        "\n" +
                        "08-02-2015 09:49  0254.0130604\n" +
                        "00083213 #060127\n" +
                        "               СПАСИБО ЗА ПОКУПКУ !\n" +
                        "\n" +
                        "    МЫ  ОТКРЫТЫ ЕЖЕДНЕВНО С 9 ДО 23\n" +
                        "        СОХРАНЯЙТЕ, ПОЖАЛУЙСТА , ЧЕК\n";

        int      textSize = 25;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_THREE_INCH, typeface);
    }

    @Override
    public Bitmap create4inchRasterReceiptImage() {
        String textToPrint =
                "               Р Е Л А К С   ООО “РЕЛАКС”\n" +
                        "                СПб., Малая Балканская, д. 38, лит. А\n" +
                        "\n" +
                        "тел. 307-07-12\n" +
                        "РЕГ №322736     ИНН:123321\n" +
                        "01 Белякова И.А. КАССА: 0020 ОТД.01\n" +
                        "ЧЕК НА ПРОДАЖУ  No 84373\n" +
                        "-----------------------------------------------------\n" +
                        " 1.      Яблоки Айдаред, кг                    144.50\n" +
                        " 2.      Соус соевый Sen So                     36.40\n" +
                        " 3.      Соус томатный Клас                     19.90\n" +
                        " 4.      Ребра свиные в.к м                     78.20\n" +
                        " 5.      Масло подсол раф д                    114.00\n" +
                        " 6.      Блокнот 10х14см сп                    164.00\n" +
                        " 7.      Морс Северная Ягод                     99.90\n" +
                        " 8.      Активия Биойогурт                      43.40\n" +
                        " 9.      Бублики Украинские                     26.90\n" +
                        "10.      Активия Биойогурт                      43.40\n" +
                        "11.      Сахар-песок 1кг                        58.40\n" +
                        "12.      Хлопья овсяные Ясн                     38.40\n" +
                        "13.      Кинза 50г                              39.90\n" +
                        "14.      Пемза “Сердечко” .Т                    37.90\n" +
                        "15.      Приправа Santa Mar                     47.90\n" +
                        "16.      Томаты слива Выбор                    162.00\n" +
                        "17.      Бонд Стрит Ред Сел                     56.90\n" +
                        "-----------------------------------------------------\n" +
                        "-----------------------------------------------------\n" +
                        "ДИСКОНТНАЯ КАРТА                     No:2440012489765\n" +
                        "-----------------------------------------------------\n" +
                        "ИТОГО К ОПЛАТЕ = 1212.00\n" +
                        "НАЛИЧНЫЕ = 1212.00\n" +
                        "ВАША СКИДКА : 0.41\n" +
                        "ЦЕНЫ УКАЗАНЫ С УЧЕТОМ СКИДКИ\n" +
                        "\n" +
                        "08-02-2015 09:49  0254.0130604\n" +
                        "00083213 #060127\n" +
                        "                                 СПАСИБО ЗА ПОКУПКУ !\n" +
                        "\n" +
                        "                      МЫ  ОТКРЫТЫ ЕЖЕДНЕВНО С 9 ДО 23\n" +
                        "                         СОХРАНЯЙТЕ, ПОЖАЛУЙСТА , ЧЕК\n";

        int      textSize = 25;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, PrinterSettingConstant.PAPER_SIZE_FOUR_INCH, typeface);
    }

    @Override
    public Bitmap createCouponImage(Resources resources) {
        return BitmapFactory.decodeResource(resources, R.drawable.coupon_image_russian);
    }

    @Override
    public Bitmap createEscPos3inchRasterReceiptImage() {
        String textToPrint =
                "\n" +
                "   Р Е Л А К С    ООО “РЕЛАКС”\n" +
                        "    СПб., Малая Балканская, д.\n" +
                        "           38, лит. А\n" +
                        "\n" +
                        "тел. 307-07-12\n" +
                        "РЕГ №322736     ИНН:123321\n" +
                        "01 Белякова И.А. КАССА: 0020 ОТД.01\n" +
                        "ЧЕК НА ПРОДАЖУ  No 84373\n" +
                        "-----------------------------------\n" +
                        " 1. Яблоки Айдаред, кг       144.50\n" +
                        " 2. Соус соевый Sen So        36.40\n" +
                        " 3. Соус томатный Клас        19.90\n" +
                        " 4. Ребра свиные в.к м        78.20\n" +
                        " 5. Масло подсол раф д       114.00\n" +
                        " 6. Блокнот 10х14см сп       164.00\n" +
                        " 7. Морс Северная Ягод        99.90\n" +
                        " 8. Активия Биойогурт         43.40\n" +
                        " 9. Бублики Украинские        26.90\n" +
                        "10. Активия Биойогурт         43.40\n" +
                        "11. Сахар-песок 1кг           58.40\n" +
                        "12. Хлопья овсяные Ясн        38.40\n" +
                        "13. Кинза 50г                 39.90\n" +
                        "14. Пемза “Сердечко” .Т       37.90\n" +
                        "15. Приправа Santa Mar        47.90\n" +
                        "16. Томаты слива Выбор       162.00\n" +
                        "17. Бонд Стрит Ред Сел        56.90\n" +
                        "-----------------------------------\n" +
                        "-----------------------------------\n" +
                        "ДИСКОНТНАЯ КАРТА   No:2440012489765\n" +
                        "-----------------------------------\n" +
                        "ИТОГО К ОПЛАТЕ = 1212.00\n" +
                        "НАЛИЧНЫЕ = 1212.00\n" +
                        "ВАША СКИДКА : 0.41\n" +
                        "ЦЕНЫ УКАЗАНЫ С УЧЕТОМ СКИДКИ\n" +
                        "\n" +
                        "08-02-2015 09:49  0254.0130604\n" +
                        "00083213 #060127\n" +
                        "               СПАСИБО ЗА ПОКУПКУ !\n" +
                        "\n" +
                        "    МЫ  ОТКРЫТЫ ЕЖЕДНЕВНО С 9 ДО 23\n" +
                        "       СОХРАНЯЙТЕ, ПОЖАЛУЙСТА , ЧЕК\n";

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
                encoding = Charset.forName("Windows-1251");

                builder.appendCodePage(CodePageType.CP1251);
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append("\n".getBytes());

        builder.appendMultiple("Р Е Л А К С\n".getBytes(encoding), 2, 2);

        builder.append(("ООО “РЕЛАКС”\n" +
                "СПб., Малая Балканская, д. 38, лит. А\n" +
                "тел. 307-07-12\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append(("РЕГ №322736 ИНН : 123321\n" +
                "01  Белякова И.А.  КАССА: 0020 ОТД.01\n").getBytes(encoding));

        builder.appendAlignment("ЧЕК НА ПРОДАЖУ  No 84373\n".getBytes(encoding), AlignmentPosition.Center);

        builder.append((
                "------------------------------------------\n" +
                        "1.     Яблоки Айдаред, кг           144.50\n" +
                        "2.     Соус соевый Sen So            36.40\n" +
                        "3.     Соус томатный Клас            19.90\n" +
                        "4.     Ребра свиные в.к м            78.20\n" +
                        "5.     Масло подсол раф д           114.00\n" +
                        "6.     Блокнот 10х14см сп           164.00\n" +
                        "7.     Морс Северная Ягод            99.90\n" +
                        "8.     Активия Биойогурт             43.40\n" +
                        "9.     Бублики Украинские            26.90\n" +
                        "10.    Активия Биойогурт             43.40\n" +
                        "11.    Сахар-песок 1кг               58.40\n" +
                        "12.    Хлопья овсяные Ясн            38.40\n" +
                        "13.    Кинза 50г                     39.90\n" +
                        "14.    Пемза “Сердечко” .Т           37.90\n" +
                        "15.    Приправа Santa Mar            47.90\n" +
                        "16.    Томаты слива Выбор           162.00\n" +
                        "17.    Бонд Стрит Ред Сел            56.90\n" +
                        "------------------------------------------\n" +
                        "------------------------------------------\n" +
                        "ДИСКОНТНАЯ КАРТА  No: 2440012489765\n" +
                        "------------------------------------------\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Right);

        builder.append((
                "ИТОГО  К  ОПЛАТЕ     = 1212.00\n" +
                        "НАЛИЧНЫЕ             = 1212.00\n" +
                        "ВАША СКИДКА : 0.41\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment((
                "ЦЕНЫ УКАЗАНЫ С УЧЕТОМ СКИДКИ\n" +
                        "\n").getBytes(encoding), AlignmentPosition.Center);

        builder.append((
                "08-02-2015 09:49  0254.0130604\n" +
                        "00083213 #060127\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "СПАСИБО ЗА ПОКУПКУ !\n" +
                        "МЫ  ОТКРЫТЫ ЕЖЕДНЕВНО С 9 ДО 23\n" +
                        "СОХРАНЯЙТЕ, ПОЖАЛУЙСТА , ЧЕК\n" +
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
                encoding = Charset.forName("Windows-1251");

                builder.appendCodePage(CodePageType.CP1251);
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultiple("Р Е Л А К С\n".getBytes(encoding), 2, 2);

        builder.append(("ООО “РЕЛАКС”\n" +
                "СПб., Малая Балканская, д. 38, лит. А\n" +
                "тел. 307-07-12\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append(("РЕГ №322736 ИНН : 123321\n" +
                "01  Белякова И.А.  КАССА: 0020 ОТД.01\n").getBytes(encoding));

        builder.appendAlignment("ЧЕК НА ПРОДАЖУ  No 84373\n".getBytes(encoding), AlignmentPosition.Center);

        builder.append((
                "------------------------------------------\n" +
                        "1.     Яблоки Айдаред, кг           144.50\n" +
                        "2.     Соус соевый Sen So            36.40\n" +
                        "3.     Соус томатный Клас            19.90\n" +
                        "4.     Ребра свиные в.к м            78.20\n" +
                        "5.     Масло подсол раф д           114.00\n" +
                        "6.     Блокнот 10х14см сп           164.00\n" +
                        "7.     Морс Северная Ягод            99.90\n" +
                        "8.     Активия Биойогурт             43.40\n" +
                        "9.     Бублики Украинские            26.90\n" +
                        "10.    Активия Биойогурт             43.40\n" +
                        "11.    Сахар-песок 1кг               58.40\n" +
                        "12.    Хлопья овсяные Ясн            38.40\n" +
                        "13.    Кинза 50г                     39.90\n" +
                        "14.    Пемза “Сердечко” .Т           37.90\n" +
                        "15.    Приправа Santa Mar            47.90\n" +
                        "16.    Томаты слива Выбор           162.00\n" +
                        "17.    Бонд Стрит Ред Сел            56.90\n" +
                        "------------------------------------------\n" +
                        "------------------------------------------\n" +
                        "ДИСКОНТНАЯ КАРТА  No: 2440012489765\n" +
                        "------------------------------------------\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Right);

        builder.append((
                "ИТОГО  К  ОПЛАТЕ  = 1212.00\n" +
                        "НАЛИЧНЫЕ          = 1212.00\n" +
                        "ВАША СКИДКА : 0.41\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment((
                "ЦЕНЫ УКАЗАНЫ С УЧЕТОМ СКИДКИ\n" +
                        "\n").getBytes(encoding), AlignmentPosition.Center);

        builder.append((
                "08-02-2015 09:49  0254.0130604\n" +
                        "00083213 #060127\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "СПАСИБО ЗА ПОКУПКУ !\n" +
                        "МЫ  ОТКРЫТЫ ЕЖЕДНЕВНО С 9 ДО 23\n" +
                        "СОХРАНЯЙТЕ, ПОЖАЛУЙСТА , ЧЕК\n").getBytes(encoding));
    }

    @Override
    public Bitmap createSk12inchRasterReceiptImage() {
        String textToPrint =
                "          Р Е Л А К С\n" +
                        "          ООО “РЕЛАКС”\n" +
                        "СПб., Малая Балканская, д.\n" +
                        "38, лит. А\n" +
                        "\n" +
                        "тел. 307-07-12\n" +
                        "РЕГ №322736     ИНН:123321\n" +
                        "01 Белякова И.А. КАССА:0020\n" +
                        "ОТД.01\n" +
                        "ЧЕК НА ПРОДАЖУ  No 84373\n" +
                        "---------------------------------\n" +
                        " 1.Яблоки Айдаред, кг      144.50\n" +
                        " 2.Соус соевый Sen So       36.40\n" +
                        " 3.Соус томатный Клас       19.90\n" +
                        " 4.Ребра свиные в.к м       78.20\n" +
                        " 5.Масло подсол раф д      114.00\n" +
                        " 6.Блокнот 10х14см сп      164.00\n" +
                        " 7.Морс Северная Ягод       99.90\n" +
                        " 8.Активия Биойогурт        43.40\n" +
                        " 9.Бублики Украинские       26.90\n" +
                        "10.Активия Биойогурт        43.40\n" +
                        "11.Сахар-песок 1кг          58.40\n" +
                        "12.Хлопья овсяные Ясн       38.40\n" +
                        "13.Кинза 50г                39.90\n" +
                        "14.Пемза “Сердечко” .Т      37.90\n" +
                        "15.Приправа Santa Mar       47.90\n" +
                        "16.Томаты слива Выбор      162.00\n" +
                        "17.Бонд Стрит Ред Сел       56.90\n" +
                        "---------------------------------\n" +
                        "---------------------------------\n" +
                        "ДИСКОНТНАЯ КАРТА\n" +
                        "                 No:2440012489765\n" +
                        "---------------------------------\n" +
                        "ИТОГО К ОПЛАТЕ = 1212.00\n" +
                        "НАЛИЧНЫЕ = 1212.00\n" +
                        "ВАША СКИДКА : 0.41\n" +
                        "ЦЕНЫ УКАЗАНЫ С УЧЕТОМ СКИДКИ\n" +
                        "\n" +
                        "08-02-2015 09:49\n" +
                        "0254.013060400083213 #060127\n" +
                        "СПАСИБО ЗА ПОКУПКУ !\n" +
                        "\n" +
                        "МЫ  ОТКРЫТЫ ЕЖЕДНЕВНО С 9 ДО 23" +
                        "СОХРАНЯЙТЕ, ПОЖАЛУЙСТА , ЧЕК\n";

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
                encoding = Charset.forName("Windows-1251");

                builder.appendCodePage(CodePageType.CP1251);
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendMultiple("Р Е Л А К С\n".getBytes(encoding), 2, 2);

        builder.append(("ООО “РЕЛАКС”\n" +
                "СПб., Малая Балканская, д. 38, лит. А\n" +
                "тел. 307-07-12\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append(("РЕГ №322736     ИНН:123321\n" +
                "01 Белякова И.А.КАССА: 0020 ОТД.01\n").getBytes(encoding));

        builder.appendAlignment("ЧЕК НА ПРОДАЖУ  No 84373\n".getBytes(encoding), AlignmentPosition.Center);

        builder.append((
                "------------------------------------\n" +
                        " 1. Яблоки Айдаред, кг        144.50\n" +
                        " 2. Соус соевый Sen So         36.40\n" +
                        " 3. Соус томатный Клас         19.90\n" +
                        " 4. Ребра свиные в.к м         78.20\n" +
                        " 5. Масло подсол раф д        114.00\n" +
                        " 6. Блокнот 10х14см сп        164.00\n" +
                        " 7. Морс Северная Ягод         99.90\n" +
                        " 8. Активия Биойогурт          43.40\n" +
                        " 9. Бублики Украинские         26.90\n" +
                        "10. Активия Биойогурт          43.40\n" +
                        "11. Сахар-песок 1кг            58.40\n" +
                        "12. Хлопья овсяные Ясн         38.40\n" +
                        "13. Кинза 50г                  39.90\n" +
                        "14. Пемза “Сердечко” .Т        37.90\n" +
                        "15. Приправа Santa Mar         47.90\n" +
                        "16. Томаты слива Выбор        162.00\n" +
                        "17. Бонд Стрит Ред Сел         56.90\n" +
                        "------------------------------------\n" +
                        "------------------------------------\n" +
                        "ДИСКОНТНАЯ КАРТА\n" +
                        "                    No:2440012489765\n" +
                        "------------------------------------\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Right);

        builder.append((
                "ИТОГО К ОПЛАТЕ = 1212.00\n" +
                        "НАЛИЧНЫЕ = 1212.00\n" +
                        "ВАША СКИДКА : 0.41\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment((
                "ЦЕНЫ УКАЗАНЫ С УЧЕТОМ СКИДКИ\n" +
                        "\n").getBytes(encoding), AlignmentPosition.Center);

        builder.append((
                "08-02-2015 09:49  0254.0130604\n" +
                        "00083213 #060127\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append((
                "СПАСИБО ЗА ПОКУПКУ !\n" +
                        "МЫ  ОТКРЫТЫ ЕЖЕДНЕВНО С 9 ДО 23\n" +
                        "СОХРАНЯЙТЕ, ПОЖАЛУЙСТА , ЧЕК\n" +
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
