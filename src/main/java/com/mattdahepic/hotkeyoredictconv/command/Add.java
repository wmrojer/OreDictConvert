package com.mattdahepic.hotkeyoredictconv.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.oredict.OreDictionary;

import com.mattdahepic.hotkeyoredictconv.config.Config;

public class Add {
    private Add () {}
    public static void add(ICommandSender commandSender, ItemStack item) {
        int[] oreDictEntryNumbers = OreDictionary.getOreIDs(item);
        String[] oreDictEntryNames = new String[oreDictEntryNumbers.length];
        for (int i = 0; i < oreDictEntryNumbers.length; i++) { //add names to array
            oreDictEntryNames[i] = OreDictionary.getOreName(oreDictEntryNumbers[i]);
        }
        if (oreDictEntryNumbers.length==1) {
        	if (Config.isValidOreName(oreDictEntryNames[0])) {
	        	if (Config.oreMap.containsKey(oreDictEntryNames[0])) {
	               	commandSender.addChatMessage(new ChatComponentText("Changed to default item for Ore Dictionary " + oreDictEntryNames[0]));
	            } else {
	            	commandSender.addChatMessage(new ChatComponentText("Set to default item for Ore Dictionary " + oreDictEntryNames[0]));
	            }
	        	Config.oreMap.put(oreDictEntryNames[0], Item.itemRegistry.getNameForObject(item.getItem()) + ";" + item.getItemDamage());
	        	Config.saveConfig();
        	} else {
        		commandSender.addChatMessage(new ChatComponentText("Ore Dictionary " + oreDictEntryNames[0] + " is not allowed!"));
        	}
        	
        } else if (oreDictEntryNumbers.length>1) {
	        commandSender.addChatMessage(new ChatComponentText("Can only add items that have ONE Ore dictionary name!"));
        } else {
        	commandSender.addChatMessage(new ChatComponentText("This item does not have an Ore dictionary name!"));
        }
    }
}
