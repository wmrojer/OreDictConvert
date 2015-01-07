package com.mattdahepic.oredictconv;

import com.mattdahepic.oredictconv.input.KeyHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class CommonProxy {
    public void registerRenderers() {}
    public void registerKeys() {
        //register key handler
        KeyHandler.instance.dummyKeyHandlerLoad();
    }
}
