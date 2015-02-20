package com.mattdahepic.hotkeyoredictconv.network;

//props to CatDany: https://gist.github.com/CatDany/4a3df7fcb3c8270cf70b

import com.mattdahepic.hotkeyoredictconv.OreDictConv;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {
    public static SimpleNetworkWrapper net;
    public static void initPackets () {
        net = NetworkRegistry.INSTANCE.newSimpleChannel(OreDictConv.MODID.toUpperCase());
        registerMessage(ODCPacket.class,ODCPacket.ODCMessage.class);
    }
    private static int nextPacketId = 0;
    private static void registerMessage(Class packet, Class message) {
        net.registerMessage(packet,message,nextPacketId, Side.CLIENT);
        net.registerMessage(packet,message,nextPacketId,Side.SERVER);
        nextPacketId++;
    }
}
