package com.mattdahepic.hotkeyoredictconv.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class Find {
    public static void find (ICommandSender commandSender, String oreDictName) {
        ArrayList<ItemStack> itemsUnderOreDict = OreDictionary.getOres(oreDictName);
        if (!itemsUnderOreDict.isEmpty()) {
            commandSender.addChatMessage(new ChatComponentText("Ore names under entry " + oreDictName + " are:"));
            for (int i = 0; i < itemsUnderOreDict.size(); i++) {
                commandSender.addChatMessage(new ChatComponentText(Item.itemRegistry.getNameForObject(itemsUnderOreDict.get(i).getItem()) + "|" + itemsUnderOreDict.get(i).getItemDamage()));
            }
            return;
        } else {
            commandSender.addChatMessage(new ChatComponentText("There are no items under this name or this name is not registered!"));
            return;
        }
    }
}
