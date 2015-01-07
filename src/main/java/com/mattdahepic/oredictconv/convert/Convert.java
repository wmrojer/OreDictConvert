package com.mattdahepic.oredictconv.convert;

import com.mattdahepic.oredictconv.config.Config;
import com.mattdahepic.oredictconv.log.Log;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class Convert {
    public static String[] oreValues = Config.oreValues;
    public static InventoryPlayer inventoryPlayer = Minecraft.getMinecraft().thePlayer.inventory;
    private Convert () {}
    public static void register () {}
    public static void convert () {
        if (oreValues != null) { //if config is empty: OH NO!
            for (int i = 0; i < inventoryPlayer.getSizeInventory(); i++) { //i = inventory slot of player
                for (int j = 0; j < oreValues.length; j++) { //j = how many ore values in config
                    int[] oreIds = OreDictionary.getOreIDs(inventoryPlayer.getStackInSlot(i));
                    for (int k = 0; k < oreIds.length; k++) { //k = how many ore dict entries this item is registered to
                        String oreName = OreDictionary.getOreName(oreIds[k]);
                        if (oreName == oreValues[j]) {
                            for (int l = 0; l < inventoryPlayer.getStackInSlot(i).stackSize; l++) { //l = amount of items in slot that matches the ore value                                    `   ```1        ``
                                inventoryPlayer.decrStackSize(i, 1);
                                inventoryPlayer.addItemStackToInventory(new ItemStack(GameRegistry.findItem(Config.getModId(oreName), Config.getItem(oreName))));
                            }
                        }
                    }
                }
            }
        } else {
            Log.playerChat("No ore dictionary convertable items defined in config!");
        }
    }
}
