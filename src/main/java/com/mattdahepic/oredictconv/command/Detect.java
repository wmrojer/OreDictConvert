package com.mattdahepic.oredictconv.command;

import com.mattdahepic.oredictconv.log.Log;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class Detect {
    private Detect () {}
    public static void detect (ItemStack item) {
        int[] oreDictEntryNumbers = OreDictionary.getOreIDs(item);
        String[] oreDictEntryNames = new String[oreDictEntryNumbers.length];
        for (int i = 0; i < oreDictEntryNumbers.length; i++) { //add names to array
            oreDictEntryNames[i] = OreDictionary.getOreName(oreDictEntryNumbers[i]);
        }
        Log.playerChat("Ore dictionary names for item \"" + Item.itemRegistry.getNameForObject(item.getItem()) + "\" are:");
        for (int i = 0; i < oreDictEntryNames.length; i++) { //print names
            Log.playerChat(oreDictEntryNames[i]);
        }
    }
}
