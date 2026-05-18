package com.starmicronics.starprntsdk.functions;


import com.starmicronics.starioextension.ICommandBuilder;
import com.starmicronics.starioextension.StarIoExt;
import com.starmicronics.starioextension.StarIoExt.Emulation;

import static com.starmicronics.starioextension.ICommandBuilder.PeripheralChannel;

public class CashDrawerFunctions {
    public static byte[] createData(Emulation emulation, PeripheralChannel channel) {
        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.appendPeripheral(channel);

        builder.endDocument();

        return builder.getCommands();
    }
}
