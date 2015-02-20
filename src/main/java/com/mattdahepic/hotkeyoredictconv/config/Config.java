package com.mattdahepic.hotkeyoredictconv.config;

import com.mattdahepic.hotkeyoredictconv.OreDictConv;
import com.mattdahepic.hotkeyoredictconv.log.Log;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

/*
Sample Config:
String List:
{"oreDictName=modid:oreDictCompatableItem"}
 */

public class Config {
    public static String[] oreValues = {"oreIron=minecraft:iron_ore|0","oreGold=minecraft:gold_ore|0"};
    public static Configuration config;

    public static void load (FMLPreInitializationEvent event) {
        OreDictConv.configFile = new Configuration(event.getSuggestedConfigurationFile());
        syncConfig();
    }

    public static void syncConfig () {
        try {
            Config.processConfig(OreDictConv.configFile);
        } catch (Exception e) {
            Log.error("Error loading config file!");
            e.printStackTrace();
        } finally {
            if (OreDictConv.configFile.hasChanged()) {
                Log.info("Saving config file!");
                OreDictConv.configFile.save();
            }
        }
    }
    @SubscribeEvent
    public void OnConfigChanged (ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(OreDictConv.MODID)) {
            Log.info("Updating config!");
            syncConfig();
        }
    }

    public static void processConfig (Configuration config) {
        oreValues = config.getStringList("oreValues",Configuration.CATEGORY_GENERAL,oreValues,"Place your ores here in format \"oreDictName=modid:itemName|metaValue\". Use the ingame command /odc to get the ore dict names.");
    }

    public static ItemStack getItem (String oreDictName) {
        for (int i = 0; i < oreValues.length; i++) { //for each entry in the config
            if (oreValues[i].startsWith(oreDictName + "=")) { //if the entry starts with the same value
                if (oreValues[i].length() > oreDictName.length()+1) { //and it presumably includes an entry
                    String temp = oreValues[i];
                    //get the data value
                    int colonLocation = 0;
                    String name = null;
                    for (int j = 0; j < temp.length(); j++) { //get where data value divider is
                        if (temp.charAt(j) == '|') {
                            colonLocation = j;
                            name = temp.substring(oreDictName.length()+1,colonLocation); //get item name if data value exists
                        }
                    }
                    if (name == null) {
                        return null;
                    }
                    Item tempItem = (Item) Item.itemRegistry.getObject(name); //get actual item from name
                    int dataValue;
                    try {
                        dataValue = Integer.parseInt(temp.substring(colonLocation+1,temp.length())); //see if the entry ACTUALLY ends with a number
                    } catch (Exception e) {
                        dataValue = 0;
                    }
                    if (tempItem != null) { //is item illegal?
                        return new ItemStack(tempItem,1,dataValue);
                    } else {
                        Log.error("Config incorrectly set convertable item for entry " + oreDictName + "! FIX IT.");
                        return null;
                    }
                }
            }
        }
        //no entry matched
        return null;
    }
}
