package com.mattdahepic.hotkeyoredictconv;

import net.minecraftforge.oredict.OreDictionary;

import com.mattdahepic.hotkeyoredictconv.command.CommandConfig;
import com.mattdahepic.hotkeyoredictconv.config.Config;
import com.mattdahepic.hotkeyoredictconv.network.PacketHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = OreDictConv.MODID,name = OreDictConv.NAME,version = OreDictConv.VERSION, guiFactory = "com.mattdahepic.hotkeyoredictconv.config.GuiFactory")
public class OreDictConv {
    @Mod.Instance("hotkeyoredictconv")
    public static OreDictConv instance;

    public static final String MODID = "hotkeyoredictconv";
    public static final String NAME = "Hotkey Ore Dictionary Converter";
    public static final String VERSION = "@VERSION@";
    public static final String METASEPARATOR = "@";
    
    @SidedProxy(clientSide = "com.mattdahepic.hotkeyoredictconv.client.ClientProxy",serverSide = "com.mattdahepic.hotkeyoredictconv.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit (FMLPreInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(instance);
        Config.load(event);
        FMLCommonHandler.instance().bus().register(new Config());
    }
    @Mod.EventHandler
    public void init (FMLInitializationEvent event) {
        proxy.registerKeys();
        PacketHandler.initPackets();
    }
    @Mod.EventHandler
    public void postInit (FMLPostInitializationEvent event) {
        //Log.info("Ready to convert with " + Config.oreValues.length + " entries in the config!");
    }
    @Mod.EventHandler
    public void serverStarting (FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandConfig());
    }

    @Mod.EventHandler
    public void serverStarted (FMLServerStartedEvent event) {
    	OreDictionary.rebakeMap(); // UGLY FIX for Metallurgy items not returning there OreDictionery. Probably a forge or Metallurgy bug.
    }

}
