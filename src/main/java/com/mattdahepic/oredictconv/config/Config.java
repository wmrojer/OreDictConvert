package com.mattdahepic.oredictconv.config;

import com.mattdahepic.oredictconv.log.Log;
import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;

/*
Sample Config:
String List:
{"oreDictName=modid:oreDictCompatableItem"}
 */

public class Config {
    public static String[] oreValues = null;
    public static Configuration config;
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
    public static String getItem (String oreDictName) {
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
    }
}
