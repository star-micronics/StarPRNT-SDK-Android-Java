package com.starmicronics.starprntsdk.functions;

import android.content.Context;
import android.net.Uri;

import com.starmicronics.starioextension.IMelodySpeakerCommandBuilder;
import com.starmicronics.starioextension.IMelodySpeakerCommandBuilder.SoundStorageArea;
import com.starmicronics.starioextension.SoundSetting;
import com.starmicronics.starioextension.StarIoExt;
import com.starmicronics.starioextension.StarIoExt.MelodySpeakerModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MelodySpeakerFunctions {
    public static byte[] createPlayingRegisteredSound(MelodySpeakerModel model, SoundStorageArea soundStorageArea, boolean specifySound, int soundNumber, boolean specifyVolume, int volume) {
        IMelodySpeakerCommandBuilder builder = StarIoExt.createMelodySpeakerCommandBuilder(model);

        SoundSetting setting = new SoundSetting();

        if (specifySound) {
            setting.setSoundStorageArea(soundStorageArea);
            setting.setSoundNumber(soundNumber);
        }

        if (specifyVolume) {
            setting.setVolume(volume);
        }

        builder.appendSound(setting);

        return builder.getCommands();
    }

    public static byte[] createPlayingReceivedData(MelodySpeakerModel model, Uri uri, boolean specifyVolume, int volume, Context context) {
        InputStream inputStream = null;
        byte[] soundData;

        try {
            inputStream = context.getContentResolver().openInputStream(uri);

            byte[] buffer = new byte[1024];

            int bytesRead;

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            soundData = outputStream.toByteArray();

            inputStream.close();
        }
        catch (IOException e) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e1) {
                    // do nothing
                }
            }

            return null;
        }

        IMelodySpeakerCommandBuilder builder = StarIoExt.createMelodySpeakerCommandBuilder(model);

        SoundSetting setting = new SoundSetting();

        if (specifyVolume) {
            setting.setVolume(volume);
        }

        builder.appendSoundData(soundData, setting);

        return builder.getCommands();
    }
}
