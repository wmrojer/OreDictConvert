package com.mattdahepic.hotkeyoredictconv.client;

import com.mattdahepic.hotkeyoredictconv.CommonProxy;
import com.mattdahepic.hotkeyoredictconv.input.KeyHandler;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenderers() {
        // This is for rendering entities and so forth later on
    }
    @Override
    public void registerKeys() {
        //register key handler
        KeyHandler.instance.dummyKeyHandlerLoad();
    }
}
