package com.mattdahepic.oredictconv.client;

import com.mattdahepic.oredictconv.CommonProxy;
import com.mattdahepic.oredictconv.input.KeyHandler;

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
