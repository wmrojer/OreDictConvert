package com.mattdahepic.hotkeyoredictconv.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.oredict.OreDictionary;

public class DumpOreDict {
    private DumpOreDict () {}
    public static void dump (ICommandSender commandSender) {
        String[] oreDictNames = OreDictionary.getOreNames();
        commandSender.addChatMessage(new ChatComponentText("Dumping all Ore Dictionary entries..."));
        for (int i = 0; i < oreDictNames.length; i++) {
            commandSender.addChatMessage(new ChatComponentText(oreDictNames[i]));
        }
    }
}
