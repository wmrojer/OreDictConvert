package com.mattdahepic.oredictconv;

import com.mattdahepic.oredictconv.input.KeyHandler;
import com.mattdahepic.oredictconv.log.Log;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = OreDictConv.MODID,name = OreDictConv.NAME,version = OreDictConv.VERSION)
public class OreDictConv {
    @Mod.Instance("oredictconv")
    public static OreDictConv instance;

    public static final String MODID = "oredictconv";
    public static final String NAME = "Ore Dictionary Converter";
    public static final String VERSION = "v1.0";

    @SidedProxy(clientSide = "com.mattdahepic.oredictconv.client.ClientProxy",serverSide = "com.mattdahepic.oredictconv.CommonProxy")
    public static CommonProxy proxy;

    public static Configuration configFile;

    @Mod.EventHandler
    public void preInit (FMLPreInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(instance);
        //TODO: Config.load();
    }
    @Mod.EventHandler
    public void init (FMLInitializationEvent event) {
        proxy.registerKeys();
    }
    @Mod.EventHandler
    public void postInit (FMLPostInitializationEvent event) {
        Log.info("Ready to convert!");
    }
}
