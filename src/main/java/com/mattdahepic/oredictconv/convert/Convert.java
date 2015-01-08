package com.mattdahepic.oredictconv.convert;

import com.mattdahepic.oredictconv.config.Config;
import com.mattdahepic.oredictconv.log.Log;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class Convert {
    public static String[] oreValues = Config.oreValues;
    public static InventoryPlayer inventoryPlayer = Minecraft.getMinecraft().thePlayer.inventory;
    private Convert () {}
    public static void register () {}
    public static void convert () {
        if (oreValues != null) { //if config is empty: OH NO! GOD NO! WHY! WHY WOULD SOMEONE DO THAT!
            for (int i = 0; i < inventoryPlayer.getSizeInventory(); i++) { //i = inventory slot of player
                Log.info("Checking inventory slot " + i);
                if (inventoryPlayer.getStackInSlot(i) != null) {
                    Log.info("Slot " + i + " contains item.");
                    for (int j = 0; j < oreValues.length; j++) { //j = how many ore values in config
                        Log.info(" Checking slot for config entry " + j);
                        int[] oreIds = OreDictionary.getOreIDs(inventoryPlayer.getStackInSlot(i));
                        for (int k = 0; k < oreIds.length; k++) { //k = how many ore dict entries this item is registered to
                            Log.info("  Checking ore dictionary entry " + k + " for this item.");
                            String oreName = OreDictionary.getOreName(oreIds[k]);
                            if (oreName == oreValues[j]) {
                                Log.info("   This slot matches this config entry! Converting!");
                                for (int l = 0; l < inventoryPlayer.getStackInSlot(i).stackSize; l++) { //l = amount of items in slot that matches the ore value
                                    Log.info("Converted item!");
                                    inventoryPlayer.decrStackSize(i, 1);
                                    inventoryPlayer.addItemStackToInventory(new ItemStack(GameRegistry.findItem(Config.getModId(oreName), Config.getItem(oreName))));
                                }
                            } else {
                                Log.info("  Ore dictionary entry " + k + " for this item does not match config entry " + j + ".");
                            }
                        }
                    }
                } else {
                    Log.info("Slot " + i + " empty!");
                }
            }
        } else {
            Log.playerChat("No ore dictionary convertable items defined in config!");
            Log.warn("No ore dictionary convertable items defined in config!");
        }
    }
}
