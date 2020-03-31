package com.starmicronics.starprntsdk.functions;

import com.starmicronics.starioextension.IPresenterCommandBuilder;

import com.starmicronics.starioextension.PresenterSetting;
import com.starmicronics.starioextension.PresenterSetting.Mode;
import com.starmicronics.starioextension.PresenterSetting.PaperRetractFunction;
import com.starmicronics.starioextension.StarIoExt;
import com.starmicronics.starioextension.StarIoExt.Emulation;

public class PresenterFunctions {
    public static byte[] createSetPresenterOperation(Emulation emulation, Mode mode, PaperRetractFunction retractFunction, int holdTime) {
        IPresenterCommandBuilder builder = StarIoExt.createPresenterCommandBuilder(emulation);

        PresenterSetting setting = new PresenterSetting();

        setting.setMode(mode);

        setting.setPaperRetractFunction(retractFunction);

        if (retractFunction == PaperRetractFunction.Retract ||
            retractFunction == PaperRetractFunction.Eject) {
            setting.setPaperHoldTime(holdTime);
        }

        builder.appendOperation(setting);

        return builder.getCommands();
    }

    public static byte[] createClearCounter(Emulation emulation) {
        IPresenterCommandBuilder builder = StarIoExt.createPresenterCommandBuilder(emulation);

        builder.appendClearPaperCounter();

        return builder.getCommands();
    }
}
