package com.mattdahepic.hotkeyoredictconv.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.mattdahepic.hotkeyoredictconv.OreDictConv;
import com.mattdahepic.hotkeyoredictconv.log.Log;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

/*
Sample Config:
String List:
{"oreDictName=modid:oreDictCompatableItem"}
 */

public class Config {
    static Configuration config;
    public static HashMap<String, String> oreMap = new HashMap<String, String>();
    public static Boolean debug = false;
    private static String[] oreValues = {"oreIron=minecraft:iron_ore;0","oreGold=minecraft:gold_ore;0"};
    private static Property oreValuesProp;
    private static List<Pattern> whitelist;
    private static List<Pattern> blacklist;

    private static final String REGEX_COMMENT = "Supports multiple expressions separated by commas.\n"
        + "Uses Java's Pattern class regex syntax. See the following page for more info about Pattern regex syntax:\n"
        + "http://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html";
    
    private static final String WHITELIST_COMMENT = "Only ore dictionary names that match any of these regexes can be added. " + REGEX_COMMENT;
    private static final String BLACKLIST_COMMENT = "Ore dictionary names that match any of these regexes can not be added. " + REGEX_COMMENT;

    public static void load(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        syncConfig();
    }

    public static void syncConfig() {
        try {
            Config.loadConfig(config);
        } catch (Exception e) {
            Log.error("Error loading config file!");
            e.printStackTrace();
        } finally {
            if (config.hasChanged()) {
            	if (debug)
            		Log.info("Saving config file!");
                config.save();
            }
        }
    }
    @SubscribeEvent
    public void OnConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(OreDictConv.MODID)) {
        	if (debug)
        		Log.info("Updating config!");
            syncConfig();
        }
    }

	public static void loadConfig(Configuration config) {

		debug = config.get(Configuration.CATEGORY_GENERAL, "debug", false, "Enable debug output in the log").getBoolean(); // Load this first
		
		String whitelist_line = config.get(Configuration.CATEGORY_GENERAL, "whitelist", "^ore.*,^ingot.*,^dust.*,^block.*,^nugget.*",WHITELIST_COMMENT).getString();
	    String blacklist_line = config.get(Configuration.CATEGORY_GENERAL, "blacklist", "",BLACKLIST_COMMENT).getString();
	    whitelist = CompilePatterns(whitelist_line);
	    blacklist = CompilePatterns(blacklist_line);
	    
		oreValuesProp = config.get(Configuration.CATEGORY_GENERAL, "oreValues", oreValues, "Place your ores here in format \"oreDictName=modid:itemName;metaValue\". Use the ingame command /odc to get the ore dict names.");
        String[] tempArray = oreValuesProp.getStringList();
        oreMap = new HashMap<String, String>();
        for (int i=0; i<tempArray.length; i++) {
        	String parts[] = tempArray[i].split("=", 2);
        	if (parts.length==2) {
        		if (debug)
        			Log.info("load config: "+parts[0]+"="+parts[1]);
        		if (isValidOreName(parts[0])) {
        			oreMap.put(parts[0], parts[1]);
        		} else {
        			Log.error("Illeagal oreName in config ignored. "+parts[0]);
        		}
        		
        	} else {
        		Log.error("Invalid config entry \""+tempArray[i]+"\"!");
        	}
        }
    }

    public static void saveConfig() {
    	try {
    		String [] tempArray = new String[oreMap.size()];
    		Iterator<String> it = oreMap.keySet().iterator();
    		int i = 0;
    		while (it.hasNext()) {
    			String k = it.next();
    			tempArray[i++] = k + "=" + oreMap.get(k);
    		}
	        oreValuesProp.set(tempArray);
        } catch (Exception e) {
            Log.error("Error saving config file!");
            e.printStackTrace();
        } finally {
            if (config.hasChanged()) {
            	config.save();
            }
        }
    }

    static private List<Pattern> CompilePatterns(String line) {
	    List<Pattern> list = new ArrayList<Pattern>();
	    String[] tokens = line.split(",");
	    for(String t: tokens) {
		    t = t.trim();
		    if(t == null || t.isEmpty()) {
		    	continue;
		    }
		    try {
		    	list.add(Pattern.compile(t));
		    } catch(PatternSyntaxException e) {
		    	Log.warn("Pattern '" + t + "' has invalid syntax.");
		    }
	    }
	    return list;
    }
    
    static private boolean MatchesAnyPattern(String str, List<Pattern> patterns) {
      for(Pattern p:patterns) {
        if(p.matcher(str).matches()) {
          return true;
        }
      }
      return false;
    }
    
    public static boolean isValidOreName(String oreName) {
    	return MatchesAnyPattern(oreName, whitelist) && !MatchesAnyPattern(oreName, blacklist);
    }

    public static ItemStack getItem(String oreDictName) {
    	return getItem(oreDictName, 1);
    }
    
    public static ItemStack getItem(String oreDictName, int num) {
    	if (!oreMap.containsKey(oreDictName))
    		return null;
    	String[] parts = oreMap.get(oreDictName).split(";", 2);
    	int meta = 0;
    	if ( parts.length > 1 ) {
    		try {
    			meta = Integer.parseInt(parts[1]);
    		} catch (Exception e) {
    		}
    	}
    	Item tempItem = (Item) Item.itemRegistry.getObject(parts[0]);
    	if ( tempItem != null ) {
    		return new ItemStack(tempItem, num, meta);
        } else {
            Log.error("Config incorrectly set convertable item for entry " + oreDictName + "! FIX IT.");
        }
    	return null;
    }
}
