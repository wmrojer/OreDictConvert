package com.mattdahepic.oredictconv.input;

import com.mattdahepic.oredictconv.convert.Convert;
import com.mattdahepic.oredictconv.log.Log;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

public class KeyHandler {
    public static KeyHandler instance = new KeyHandler();

    static {
        MinecraftForge.EVENT_BUS.register(instance);
        FMLCommonHandler.instance().bus().register(instance);
    }

    public static KeyBinding convertKey;

    public KeyHandler() {
        KeyHandler.convertKey = new KeyBinding("Convert OreDict Items", Keyboard.KEY_C,"Ore Dictionary Converter");
        ClientRegistry.registerKeyBinding(KeyHandler.convertKey);
    }
    @SubscribeEvent
    public void onKeyInput (InputEvent.KeyInputEvent event) {
        handleConvert();
    }
    private void handleConvert () {
        //Log.debug("Recieved KeyInputEvent!");
        if (convertKey.getIsKeyPressed()) {
            Log.playerChat("Beginning conversion!");
            Convert.convert();
        }
    }
    public boolean dummyKeyHandlerLoad () {
        return true;
    }
}
