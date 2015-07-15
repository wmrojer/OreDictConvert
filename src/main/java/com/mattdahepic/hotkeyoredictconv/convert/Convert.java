package com.mattdahepic.hotkeyoredictconv.convert;

import com.mattdahepic.hotkeyoredictconv.OreDictConv;
import com.mattdahepic.hotkeyoredictconv.config.Config;
import com.mattdahepic.hotkeyoredictconv.log.Log;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.oredict.OreDictionary;

public class Convert {
    private Convert () {}

    //@SideOnly(Side.SERVER)
    public static void convert (EntityPlayerMP player) {
        if (!player.worldObj.isRemote) { //if on server
        	int numConverted = 0;
            if (Config.oreMap != null && Config.oreMap.size() > 0) { //if config is empty: OH NO! GOD NO! WHY! WHY WOULD SOMEONE DO THAT!
                InventoryPlayer inventoryPlayer = player.inventory;
                for (int i = 0; i < inventoryPlayer.getSizeInventory(); i++) { //i = inventory slot of player
                	ItemStack curStack = inventoryPlayer.getStackInSlot(i);
                    if (curStack != null) {
                        int[] oreIds = OreDictionary.getOreIDs(curStack);
                        for (int k = 0; k < oreIds.length; k++) { //k = how many ore dict entries this item is registered to
                            String oreName = OreDictionary.getOreName(oreIds[k]);
                            if (Config.debug) 
                            	Log.info("Checking ore dictionary entry " + oreName + " for " + Item.itemRegistry.getNameForObject(curStack.getItem()) + OreDictConv.METASEPARATOR + curStack.getItemDamage() + "in slot " + i );
                            int tempSize = curStack.stackSize;
                            ItemStack tempItem = Config.getItem(oreName, tempSize);
                            if (tempItem != null) {
                            	if (Config.debug)
                            		Log.info("Got " + Item.itemRegistry.getNameForObject(tempItem.getItem()) + OreDictConv.METASEPARATOR + tempItem.getItemDamage() + " for ore dictionary entry " + oreName);
                            	if (!tempItem.isItemEqual(curStack)) {
                            		if (Config.debug)
                            			Log.info("Converting " + tempSize + " " + Item.itemRegistry.getNameForObject(curStack.getItem()) + OreDictConv.METASEPARATOR + curStack.getItemDamage() + " in slot " + i +  
                            					 " to " + Item.itemRegistry.getNameForObject(tempItem.getItem()) + OreDictConv.METASEPARATOR + tempItem.getItemDamage());
                                    inventoryPlayer.decrStackSize(i, tempSize);
                                    inventoryPlayer.addItemStackToInventory(tempItem);
                                    numConverted += tempSize; 
                                    break;
                            	}
                            }
                        }
                    }
                }
            }
            if (Config.debug) {
            	player.addChatMessage(new ChatComponentText("Converted " + numConverted + " items!"));
            }
        }
    }
}
