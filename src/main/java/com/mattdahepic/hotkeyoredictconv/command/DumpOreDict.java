package com.mattdahepic.hotkeyoredictconv.command;

import java.util.ArrayList;

import net.minecraft.command.ICommandSender;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.oredict.OreDictionary;

public class DumpOreDict {
    private DumpOreDict () {}
    public static void dump (ICommandSender commandSender) {
        String[] oreDictNames = OreDictionary.getOreNames();
        commandSender.addChatMessage(new ChatComponentText("Dumping all Ore Dictionary entries..."));
        for (int i = 0; i < oreDictNames.length; i++) {
            commandSender.addChatMessage(new ChatComponentText(oreDictNames[i]));
            ArrayList<ItemStack> itemsUnderOreDict = OreDictionary.getOres(oreDictNames[i]);
            if (!itemsUnderOreDict.isEmpty()) {
                for (int j = 0; j < itemsUnderOreDict.size(); j++) {
                    commandSender.addChatMessage(new ChatComponentText("  " + Item.itemRegistry.getNameForObject(itemsUnderOreDict.get(j).getItem()) + ";" + itemsUnderOreDict.get(j).getItemDamage()));
                }
            }
        }
    }
}
