package com.starmicronics.starprntsdk.functions;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.starmicronics.starioextension.IDisplayCommandBuilder;
import com.starmicronics.starioextension.IDisplayCommandBuilder.InternationalType;
import com.starmicronics.starioextension.IDisplayCommandBuilder.CodePageType;
import com.starmicronics.starioextension.IDisplayCommandBuilder.CursorMode;
import com.starmicronics.starioextension.IDisplayCommandBuilder.ContrastMode;
import com.starmicronics.starioextension.StarIoExt;
import com.starmicronics.starioextension.StarIoExt.DisplayModel;

import com.starmicronics.starprntsdk.R;

public class DisplayFunctions {
    public static byte[] createClearScreen() {
        IDisplayCommandBuilder builder = StarIoExt.createDisplayCommandBuilder(DisplayModel.SCD222);

        builder.appendClearScreen();

        return builder.getPassThroughCommands();
    }

    public static byte[] createTextPattern(int number) {
        IDisplayCommandBuilder builder = StarIoExt.createDisplayCommandBuilder(DisplayModel.SCD222);

        builder.appendClearScreen();
        builder.appendCursorMode(CursorMode.Off);
        builder.appendHomePosition();

        byte[] pattern;

        switch (number) {
            default:
            case 0:
                pattern = new byte[]{
                        0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x2a, 0x2b, 0x2c, 0x2d, 0x2e, 0x2f, 0x30, 0x31, 0x32, 0x33,
                        0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x3a, 0x3b, 0x3c, 0x3d, 0x3e, 0x3f, 0x40, 0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47
                };
                break;
            case 1:
                pattern = new byte[]{
                        0x48, 0x49, 0x4a, 0x4b, 0x4c, 0x4d, 0x4e, 0x4f, 0x50, 0x51, 0x52, 0x53, 0x54, 0x55, 0x56, 0x57, 0x58, 0x59, 0x5a, 0x5b,
                        0x5c, 0x5d, 0x5e, 0x5f, 0x60, 0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6a, 0x6b, 0x6c, 0x6d, 0x6e, 0x6f
                };
                break;
            case 2:
                pattern = new byte[]{
                        0x70, 0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7a, 0x7b, 0x7c, 0x7d, 0x7e, 0x7f, (byte) 0x80, (byte) 0x81, (byte) 0x82, (byte) 0x83,
                        (byte) 0x84, (byte) 0x85, (byte) 0x86, (byte) 0x87, (byte) 0x88, (byte) 0x89, (byte) 0x8a, (byte) 0x8b, (byte) 0x8c, (byte) 0x8d, (byte) 0x8e, (byte) 0x8f, (byte) 0x90, (byte) 0x91, (byte) 0x92, (byte) 0x93, (byte) 0x94, (byte) 0x95, (byte) 0x96, (byte) 0x97
                };
                break;
            case 3:
                pattern = new byte[]{
                        (byte) 0x98, (byte) 0x99, (byte) 0x9a, (byte) 0x9b, (byte) 0x9c, (byte) 0x9d, (byte) 0x9e, (byte) 0x9f, (byte) 0xa0, (byte) 0xa1, (byte) 0xa2, (byte) 0xa3, (byte) 0xa4, (byte) 0xa5, (byte) 0xa6, (byte) 0xa7, (byte) 0xa8, (byte) 0xa9, (byte) 0xaa, (byte) 0xab,
                        (byte) 0xac, (byte) 0xad, (byte) 0xae, (byte) 0xaf, (byte) 0xb0, (byte) 0xb1, (byte) 0xb2, (byte) 0xb3, (byte) 0xb4, (byte) 0xb5, (byte) 0xb6, (byte) 0xb7, (byte) 0xb8, (byte) 0xb9, (byte) 0xba, (byte) 0xbb, (byte) 0xbc, (byte) 0xbd, (byte) 0xbe, (byte) 0xbf
                };
                break;
            case 4:
                pattern = new byte[]{
                        (byte) 0xc0, (byte) 0xc1, (byte) 0xc2, (byte) 0xc3, (byte) 0xc4, (byte) 0xc5, (byte) 0xc6, (byte) 0xc7, (byte) 0xc8, (byte) 0xc9, (byte) 0xca, (byte) 0xcb, (byte) 0xcc, (byte) 0xcd, (byte) 0xce, (byte) 0xcf, (byte) 0xd0, (byte) 0xd1, (byte) 0xd2, (byte) 0xd3,
                        (byte) 0xd4, (byte) 0xd5, (byte) 0xd6, (byte) 0xd7, (byte) 0xd8, (byte) 0xd9, (byte) 0xda, (byte) 0xdb, (byte) 0xdc, (byte) 0xdd, (byte) 0xde, (byte) 0xdf, (byte) 0xe0, (byte) 0xe1, (byte) 0xe2, (byte) 0xe3, (byte) 0xe4, (byte) 0xe5, (byte) 0xe6, (byte) 0xe7
                };
                break;
            case 5:
                pattern = new byte[]{
                        (byte) 0xe8, (byte) 0xe9, (byte) 0xea, (byte) 0xeb, (byte) 0xec, (byte) 0xed, (byte) 0xee, (byte) 0xef, (byte) 0xf0, (byte) 0xf1, (byte) 0xf2, (byte) 0xf3, (byte) 0xf4, (byte) 0xf5, (byte) 0xf6, (byte) 0xf7, (byte) 0xf8, (byte) 0xf9, (byte) 0xfa, (byte) 0xfb,
                        (byte) 0xfc, (byte) 0xfd, (byte) 0xfe, (byte) 0xff, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20
                };
                break;
        }

        builder.append(pattern);

        return builder.getPassThroughCommands();
    }

    public static byte[] createGraphicPattern(int number, Resources resources) {
        IDisplayCommandBuilder builder = StarIoExt.createDisplayCommandBuilder(DisplayModel.SCD222);

        builder.appendClearScreen();
        builder.appendCursorMode(CursorMode.Off);
//      builder.appendHomePosition();

        Bitmap bitmap;

        switch (number) {
            default:
            case 0:
                bitmap = BitmapFactory.decodeResource(resources, R.drawable.display_image_1);
                break;
            case 1:
                bitmap = BitmapFactory.decodeResource(resources, R.drawable.display_image_2);
                break;
        }

        builder.appendBitmap(bitmap, false);

        return builder.getPassThroughCommands();
    }

    public static byte[] createCharacterSet(InternationalType internationalType, CodePageType codePageType) {
        IDisplayCommandBuilder builder = StarIoExt.createDisplayCommandBuilder(DisplayModel.SCD222);

        builder.appendClearScreen();
        builder.appendCursorMode(CursorMode.Off);
        builder.appendHomePosition();

        builder.appendInternational(internationalType);
        builder.appendCodePage(codePageType);

        byte[] pattern1 = {
                (byte) 0x2d, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x23, (byte) 0x24, (byte) 0x40, (byte) 0x5b, (byte) 0x5c, (byte) 0x5d, (byte) 0x5e, (byte) 0x60, (byte) 0x7b, (byte) 0x7c, (byte) 0x7d, (byte) 0x7e, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x2d,
                (byte) 0xa0, (byte) 0xa1, (byte) 0xa2, (byte) 0xa3, (byte) 0xa4, (byte) 0xa5, (byte) 0xa6, (byte) 0xa7, (byte) 0xa8, (byte) 0xa9, (byte) 0xaa, (byte) 0xab, (byte) 0xac, (byte) 0xad, (byte) 0xae, (byte) 0xaf, (byte) 0xb0, (byte) 0xb1, (byte) 0xb2, (byte) 0xb3
        };
        byte[] pattern2 = {
                (byte) 0x2d, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x23, (byte) 0x24, (byte) 0x40, (byte) 0x5b, (byte) 0x5c, (byte) 0x5d, (byte) 0x5e, (byte) 0x60, (byte) 0x7b, (byte) 0x7c, (byte) 0x7d, (byte) 0x7e, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x2d,
                (byte) 0x88, (byte) 0xa0, (byte) 0x88, (byte) 0xa1, (byte) 0x88, (byte) 0xa2, (byte) 0x88, (byte) 0xa3, (byte) 0x88, (byte) 0xa4, (byte) 0x88, (byte) 0xa5, (byte) 0x88, (byte) 0xa6, (byte) 0x88, (byte) 0xa7, (byte) 0x88, (byte) 0xa8, (byte) 0x88, (byte) 0xa9
        };
        byte[] pattern3 = {
                (byte) 0x2d, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x23, (byte) 0x24, (byte) 0x40, (byte) 0x5b, (byte) 0x5c, (byte) 0x5d, (byte) 0x5e, (byte) 0x60, (byte) 0x7b, (byte) 0x7c, (byte) 0x7d, (byte) 0x7e, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x2d,
                (byte) 0xb0, (byte) 0xa1, (byte) 0xb0, (byte) 0xa2, (byte) 0xb0, (byte) 0xa3, (byte) 0xb0, (byte) 0xa4, (byte) 0xb0, (byte) 0xa5, (byte) 0xb0, (byte) 0xa6, (byte) 0xb0, (byte) 0xa7, (byte) 0xb0, (byte) 0xa8, (byte) 0xb0, (byte) 0xa9, (byte) 0xb0, (byte) 0xaa
        };
        byte[] pattern4 = {
                (byte) 0x2d, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x23, (byte) 0x24, (byte) 0x40, (byte) 0x5b, (byte) 0x5c, (byte) 0x5d, (byte) 0x5e, (byte) 0x60, (byte) 0x7b, (byte) 0x7c, (byte) 0x7d, (byte) 0x7e, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x2d,
                (byte) 0xa4, (byte) 0x40, (byte) 0xa4, (byte) 0x41, (byte) 0xa4, (byte) 0x42, (byte) 0xa4, (byte) 0x43, (byte) 0xa4, (byte) 0x44, (byte) 0xa4, (byte) 0x45, (byte) 0xa4, (byte) 0x46, (byte) 0xa4, (byte) 0x47, (byte) 0xa4, (byte) 0x48, (byte) 0xa4, (byte) 0x49
        };
        byte[] pattern5 = {
                (byte) 0x2d, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x23, (byte) 0x24, (byte) 0x40, (byte) 0x5b, (byte) 0x5c, (byte) 0x5d, (byte) 0x5e, (byte) 0x60, (byte) 0x7b, (byte) 0x7c, (byte) 0x7d, (byte) 0x7e, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x2d,
                (byte) 0xb0, (byte) 0xa1, (byte) 0xb0, (byte) 0xa2, (byte) 0xb0, (byte) 0xa3, (byte) 0xb0, (byte) 0xa4, (byte) 0xb0, (byte) 0xa5, (byte) 0xb0, (byte) 0xa6, (byte) 0xb0, (byte) 0xa7, (byte) 0xb0, (byte) 0xa8, (byte) 0xb0, (byte) 0xa9, (byte) 0xb0, (byte) 0xaa
        };

        switch (codePageType) {
            default:                    builder.append(pattern1);   break;      // CP437,Katakana,CP850,CP860,CP863,CP865,CP1252,CP866,CP852,CP858
            case Japanese:              builder.append(pattern2);   break;
            case SimplifiedChinese:     builder.append(pattern3);   break;
            case TraditionalChinese:    builder.append(pattern4);   break;
            case Hangul:                builder.append(pattern5);   break;
        }

        return builder.getPassThroughCommands();
    }

    public static byte[] createTurnOn(boolean isTurnOn) {
        IDisplayCommandBuilder builder = StarIoExt.createDisplayCommandBuilder(DisplayModel.SCD222);

//      builder.appendClearScreen();
//      builder.appendCursorMode(CursorMode.Off);
//      builder.appendHomePosition();
//
//      byte[] pattern = {'S', 't', 'a', 'r', ' ', 'M', 'i', 'c', 'r', 'o', 'n', 'i', 'c', 's', ' ', ' ', ' ', ' ', ' ', ' ',
//                        ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'S', 't', 'a', 'r', ' ', 'M', 'i', 'c', 'o', 'n', 'i', 'c', 's'};
//
//      builder.append(pattern);

        builder.appendTurnOn(isTurnOn);

        return builder.getPassThroughCommands();
    }

    public static byte[] createCursorMode(CursorMode mode) {
        IDisplayCommandBuilder builder = StarIoExt.createDisplayCommandBuilder(DisplayModel.SCD222);

        builder.appendClearScreen();
        builder.appendCursorMode(CursorMode.Off);
        builder.appendHomePosition();

        byte[] pattern = {'S', 't', 'a', 'r', ' ', 'M', 'i', 'c', 'r', 'o', 'n', 'i', 'c', 's', ' ', ' ', ' ', ' ', ' ', ' ',
                          'T', 'o', 't', 'a', 'l', ' ', ':', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '1', '2', '3', '4', '5'};

        builder.append(pattern);

        builder.appendSpecifiedPosition(20, 2);

        builder.appendCursorMode(mode);

        return builder.getPassThroughCommands();
    }

    public static byte[] createContrastMode(ContrastMode contrastMode) {
        IDisplayCommandBuilder builder = StarIoExt.createDisplayCommandBuilder(DisplayModel.SCD222);

//      builder.appendClearScreen();
//      builder.appendCursorMode(CursorMode.Off);
//      builder.appendHomePosition();
//
//      byte[] pattern = {'S', 't', 'a', 'r', ' ', 'M', 'i', 'c', 'r', 'o', 'n', 'i', 'c', 's', ' ', ' ', ' ', ' ', ' ', ' ',
//                        ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'S', 't', 'a', 'r', ' ', 'M', 'i', 'c', 'o', 'n', 'i', 'c', 's'};
//
//      builder.append(pattern);

        builder.appendContrastMode(contrastMode);

        return builder.getPassThroughCommands();
    }

    public static byte[] createUserDefinedCharacter(boolean set) {
        IDisplayCommandBuilder builder = StarIoExt.createDisplayCommandBuilder(DisplayModel.SCD222);

        builder.appendClearScreen();
        builder.appendCursorMode(CursorMode.Off);
        builder.appendHomePosition();

        builder.appendInternational(InternationalType.USA);
        builder.appendCodePage(CodePageType.Japanese);

        if (set) {
            byte[] font     = {
                    (byte) 0x00, (byte) 0x00, (byte) 0x32, (byte) 0x00, (byte) 0x49, (byte) 0x00, (byte) 0x49, (byte) 0x7f, (byte) 0x26, (byte) 0x48, (byte) 0x00, (byte) 0x48, (byte) 0x00, (byte) 0x30, (byte) 0x00, (byte) 0x00
            };
            byte[] dbcsFont = {
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x20, (byte) 0x04, (byte) 0x90, (byte) 0x04, (byte) 0x90, (byte) 0x02, (byte) 0x60,
                    (byte) 0x00, (byte) 0x00, (byte) 0x07, (byte) 0xf0, (byte) 0x04, (byte) 0x80, (byte) 0x04, (byte) 0x80, (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
            };
            builder.appendUserDefinedCharacter(0, 0x20, font);
            builder.appendUserDefinedDbcsCharacter(0, 0x8140, dbcsFont);
        }
        else {
            builder.appendUserDefinedCharacter(0, 0x20, null);
            builder.appendUserDefinedDbcsCharacter(0, 0x8140, null);
        }

        byte[] pattern = {
                (byte) 0x5b,(byte) 0x20,(byte) 0x20,(byte) 0x53,(byte) 0x74,(byte) 0x61,(byte) 0x72,(byte) 0x20,(byte) 0x4d,(byte) 0x69,(byte) 0x63,(byte) 0x72,(byte) 0x6f,(byte) 0x6e,(byte) 0x69,(byte) 0x63,(byte) 0x73,(byte) 0x20,(byte) 0x20,(byte) 0x5d,
                (byte) 0x5b,(byte) 0x81,(byte) 0x40,(byte) 0x81,(byte) 0x40,(byte) 0x83,(byte) 0x58,(byte) 0x83,(byte) 0x5e,(byte) 0x81,(byte) 0x5b,(byte) 0x90,(byte) 0xb8,(byte) 0x96,(byte) 0xa7,(byte) 0x81,(byte) 0x40,(byte) 0x81,(byte) 0x40,(byte) 0x5d,
        };
        builder.append(pattern);

        return builder.getPassThroughCommands();
    }
}