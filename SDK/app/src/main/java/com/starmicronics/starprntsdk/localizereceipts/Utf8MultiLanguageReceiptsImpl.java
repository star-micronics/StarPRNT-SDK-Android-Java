package com.starmicronics.starprntsdk.localizereceipts;

import android.content.res.Resources;
import android.graphics.Bitmap;

import com.starmicronics.starioextension.ICommandBuilder;
import com.starmicronics.starioextension.ICommandBuilder.CodePageType;
import com.starmicronics.starioextension.ICommandBuilder.AlignmentPosition;
import com.starmicronics.starioextension.ICommandBuilder.CjkUnifiedIdeographFont;
import com.starmicronics.starioextension.StarIoExt.CharacterCode;

import java.nio.charset.Charset;

public class Utf8MultiLanguageReceiptsImpl extends ILocalizeReceipts {

    public Utf8MultiLanguageReceiptsImpl() {
        mLanguageCode = "CJK";

        mCharacterCode = CharacterCode.Standard;
    }

    @Override
    public void append2inchTextReceiptData(ICommandBuilder builder, boolean utf8) {
        Charset encoding;

        encoding = Charset.forName("UTF-8");

        builder.appendCodePage(CodePageType.UTF8);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append("2017 / 5 / 15 AM 10:00\n".getBytes(encoding));

        builder.appendMultiple(2, 2);

        // This function is supported by TSP650II(JP2/TW models only) with F/W version 4.0 or later and mC-Print2/3.
        // Switch Kanji/Hangul font by specifying the font for Unicode CJK Unified Ideographs before each word.
        builder.appendCjkUnifiedIdeographFont(CjkUnifiedIdeographFont.Japanese);
        builder.append("受付票 ".getBytes(encoding));

        builder.appendCjkUnifiedIdeographFont(CjkUnifiedIdeographFont.TraditionalChinese);
        builder.append("排號單\n".getBytes(encoding));

        builder.appendCjkUnifiedIdeographFont(CjkUnifiedIdeographFont.SimplifiedChinese);
        builder.append("排号单 ".getBytes(encoding));

        builder.appendCjkUnifiedIdeographFont(CjkUnifiedIdeographFont.Hangul);
        builder.append("접수표\n\n".getBytes(encoding));

        builder.appendMultiple(1, 1);

        builder.appendCjkUnifiedIdeographFont();
        builder.appendMultiple("1\n".getBytes(encoding), 6, 6);
        builder.append("--------------------------------\n".getBytes(encoding));

        builder.appendCjkUnifiedIdeographFont(CjkUnifiedIdeographFont.Japanese);
        builder.append("ご本人がお持ちください。\n".getBytes(encoding));
        builder.append("※紛失しないように\n".getBytes(encoding));
        builder.append("ご注意ください。\n".getBytes(encoding));
    }

    @Override
    public void append3inchTextReceiptData(ICommandBuilder builder, boolean utf8) {
        Charset encoding;

        encoding = Charset.forName("UTF-8");

        builder.appendCodePage(CodePageType.UTF8);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.append("2017 / 5 / 15 AM 10:00\n".getBytes(encoding));

        builder.appendMultiple(2, 2);

        // This function is supported by TSP650II(JP2/TW models only) with F/W version 4.0 or later and mC-Print2/3.
        // Switch Kanji/Hangul font by specifying the font for Unicode CJK Unified Ideographs before each word.
        builder.appendCjkUnifiedIdeographFont(CjkUnifiedIdeographFont.Japanese);
        builder.append("受付票 ".getBytes(encoding));

        builder.appendCjkUnifiedIdeographFont(CjkUnifiedIdeographFont.TraditionalChinese);
        builder.append("排號單\n".getBytes(encoding));

        builder.appendCjkUnifiedIdeographFont(CjkUnifiedIdeographFont.SimplifiedChinese);
        builder.append("排号单 ".getBytes(encoding));

        builder.appendCjkUnifiedIdeographFont(CjkUnifiedIdeographFont.Hangul);
        builder.append("접수표\n\n".getBytes(encoding));

        builder.appendMultiple(1, 1);

        builder.appendCjkUnifiedIdeographFont();
        builder.appendMultiple("1\n".getBytes(encoding), 6, 6);
        builder.append("------------------------------------------\n".getBytes(encoding));

        builder.appendCjkUnifiedIdeographFont(CjkUnifiedIdeographFont.Japanese);
        builder.append("ご本人がお持ちください。\n".getBytes(encoding));
        builder.append("※紛失しないようにご注意ください。\n".getBytes(encoding));
    }

    @Override
    public void append4inchTextReceiptData(ICommandBuilder builder, boolean utf8) {
        // not implemented
    }

    @Override
    public Bitmap create2inchRasterReceiptImage() {
        // not implemented
        return null;
    }

    @Override
    public Bitmap create3inchRasterReceiptImage() {
        // not implemented
        return null;
    }

    @Override
    public Bitmap create4inchRasterReceiptImage() {
        // not implemented
        return null;
    }

    @Override
    public Bitmap createCouponImage(Resources resources) {
        // not implemented
        return null;
    }

    @Override
    public Bitmap createEscPos3inchRasterReceiptImage() {
        // not implemented
        return null;
    }

    @Override
    public void appendEscPos3inchTextReceiptData(ICommandBuilder builder, boolean utf8) {
        // not implemented
    }

    @Override
    public void appendDotImpact3inchTextReceiptData(ICommandBuilder builder, boolean utf8) {
        // not implemented
    }

    @Override
    public Bitmap createSk12inchRasterReceiptImage() {
        // not implemented
        return null;
    }

    @Override
    public void appendSk12inchTextReceiptData(ICommandBuilder builder, boolean utf8) {
        // not implemented
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
