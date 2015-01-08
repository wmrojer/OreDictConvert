package com.mattdahepic.oredictconv.command;

import com.mattdahepic.oredictconv.log.Log;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class Find {
    public static void find (String oreDictName) {
        ArrayList<ItemStack> itemsUnderOreDict = OreDictionary.getOres(oreDictName);
        if (!itemsUnderOreDict.isEmpty()) {
            Log.playerChat("Ore names under entry " + oreDictName + " are:");
            Log.info("Ore names under entry " + oreDictName + " are:");
            for (int i = 0; i < itemsUnderOreDict.size(); i++) {
                Log.playerChat(Item.itemRegistry.getNameForObject(itemsUnderOreDict.get(i).getItem()));
                Log.info(Item.itemRegistry.getNameForObject(itemsUnderOreDict.get(i).getItem()));
            }
            return;
        } else {
            Log.playerChat("There are no items under this name or this name is not registered!");
            return;
        }
    }
}
