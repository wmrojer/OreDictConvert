package com.mattdahepic.hotkeyoredictconv.command;

import com.mattdahepic.hotkeyoredictconv.OreDictConv;

import net.minecraft.command.ICommandSender;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.oredict.OreDictionary;

public class Detect {
    private Detect () {}
    public static void detect (ICommandSender commandSender, ItemStack item) {
        int[] oreDictEntryNumbers = OreDictionary.getOreIDs(item);
        if (oreDictEntryNumbers.length > 0) {
	        commandSender.addChatMessage(new ChatComponentText("Ore dictionary names for item \"" + Item.itemRegistry.getNameForObject(item.getItem()) + OreDictConv.METASEPARATOR + item.getItemDamage() + "\" are:"));
	        for (int i = 0; i < oreDictEntryNumbers.length; i++) { //print names
	            String oreDictEntryName = OreDictionary.getOreName(oreDictEntryNumbers[i]);
	            commandSender.addChatMessage(new ChatComponentText(oreDictEntryName));
	        }
        } else {
	        commandSender.addChatMessage(new ChatComponentText("Item \"" + Item.itemRegistry.getNameForObject(item.getItem()) + OreDictConv.METASEPARATOR + item.getItemDamage() + "\" does not have an ore dictionary entry!"));
        }
    }
}
