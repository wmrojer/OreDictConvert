package com.mattdahepic.hotkeyoredictconv.input;

import com.mattdahepic.hotkeyoredictconv.config.Config;
import com.mattdahepic.hotkeyoredictconv.log.Log;
import com.mattdahepic.hotkeyoredictconv.network.ODCPacket;
import com.mattdahepic.hotkeyoredictconv.network.PacketHandler;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
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
        if (convertKey.getIsKeyPressed()) {
        	if (Config.debug)
        		Log.playerChat("Beginning conversion!");
            IMessage msg = new ODCPacket.ODCMessage();
            PacketHandler.net.sendToServer(msg);
        }
    }
    public boolean dummyKeyHandlerLoad () {
        return true;
    }
}
