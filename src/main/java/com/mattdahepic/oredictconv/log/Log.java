package com.mattdahepic.oredictconv.log;

import com.mattdahepic.oredictconv.OreDictConv;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.Level;

public class Log {
    public static final String MODID = OreDictConv.MODID;
    public Log () {}
    public static void info (String message) {
        FMLLog.log(MODID,Level.INFO,message);
    }
    public static void debug (String message) {
        FMLLog.log(MODID,Level.DEBUG,message);
    }
    public static void error (String message) {
        FMLLog.log(MODID,Level.ERROR,message);
    }
    public static void warn (String message) {
        FMLLog.log(MODID,Level.WARN,message);
    }
    public static void trace (String message, Throwable ex) {
        FMLLog.log(MODID,Level.TRACE,ex, message);
    }
    public static void playerChat (String message) {
        EntityPlayer player =  Minecraft.getMinecraft().thePlayer;
        player.addChatMessage(new ChatComponentText(message));
    }
}
