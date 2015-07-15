package com.mattdahepic.hotkeyoredictconv.command;

import java.util.Iterator;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import com.mattdahepic.hotkeyoredictconv.config.Config;

public class ListOreDict {
    private ListOreDict () {}
    public static void listOreDict(ICommandSender commandSender) {
		Iterator<String> it = Config.oreMap.keySet().iterator();
		while (it.hasNext()) {
			String k = it.next();
           	commandSender.addChatMessage(new ChatComponentText("OreDictionary:" + k + " -> Item:" + Config.oreMap.get(k)));
		}
    	
    }
}
