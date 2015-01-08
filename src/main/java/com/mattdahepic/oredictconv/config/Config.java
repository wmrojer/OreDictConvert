package com.mattdahepic.oredictconv.config;

import com.mattdahepic.oredictconv.OreDictConv;
import com.mattdahepic.oredictconv.log.Log;
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
    public static String[] oreValues = {"oreIron=minecraft:iron_ore","oreGold=minecraft:gold_ore"};
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
        oreValues = config.getStringList("oreValues",Configuration.CATEGORY_GENERAL,oreValues,"Place your ores here in format \"oreDictName=modid:itemName\". Use the ingame command /odc to get the ore dict names.");
    }


    public static String getModId (String oreDictName) {
        for (int i = 0; i < oreValues.length; i++) { //for each ore dictionary entry in config, test if the entry is the same
            if (oreValues[i].startsWith(oreDictName)) { //if the beginning of the entry is this ore dictionary
                int equalsLocation = 0;
                int colonLocation = 0;
                for (int j = 0; j < oreValues[i].length(); j++) { //for each character in the string | this finds the equals sign and colon
                    if (oreValues[i].charAt(j) == '=') {
                        equalsLocation = j;
                    } else if (oreValues[i].charAt(j) == ':') {
                        colonLocation = j;
                    }
                }
                String modid = oreValues[i].substring(equalsLocation, colonLocation);
                Log.debug("modid found! returning modid as: " + modid);
                return modid;
            }
        }
        Log.debug("modid not found!");
        return null;
    }
    /*public static String getItem (String oreDictName) {
        if (getModId(oreDictName) != null) {
            for (int i = 0; i < oreValues.length; i++) {
                if (oreValues[i].startsWith(oreDictName)) {
                    int colonLocation = 0;
                    for (int j = 0; j < oreValues[i].length(); j++) {
                        if (oreValues[i].charAt(j) == ':') {
                            colonLocation = j;
                        }
                    }
                    String itemName = oreValues[i].substring(colonLocation, oreValues[i].length());
                    Log.debug("item name found! returning item name as: " + itemName);
                    return itemName;
                }
            }
            Log.debug("item name not found!");
            return null;
        }
        Log.debug("abandoning search for item name as mod name == null.");
        return null;
    }*/
    public static ItemStack getItem (String oreDictName) {
        for (int i = 0; i < oreValues.length; i++) { //for each entry in the config
            if (oreValues[i].startsWith(oreDictName + "=")) { //if the entry starts with the same value
                if (oreValues[i].length() > oreDictName.length()+1) { //and it presumably includes an entry
                    String temp = oreValues[i];
                    temp = temp.substring(oreDictName.length()+1,temp.length()); //TODO: include = sign
                    Item tempItem = (Item) Item.itemRegistry.getObject(temp);
                    if (tempItem != null) { //is item illegal?
                        return new ItemStack(tempItem);
                    } else {
                        Log.playerChat("Config incorrectly set convertable item for entry " + oreDictName + "! FIX IT.");
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
