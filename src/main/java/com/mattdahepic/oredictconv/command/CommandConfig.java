package com.mattdahepic.oredictconv.command;

import com.mattdahepic.oredictconv.log.Log;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;

public class CommandConfig implements ICommand {
    private List aliases, tabCompletionOptions;
    public CommandConfig () {
        this.aliases = new ArrayList();
        this.aliases.add("odc");
        this.tabCompletionOptions = new ArrayList();
        this.tabCompletionOptions.add("detect");
        this.tabCompletionOptions.add("dump");
        this.tabCompletionOptions.add("find");
        this.tabCompletionOptions.add("help");
    }
    @Override
    public int compareTo (Object arg0) {
        return 0;
    }
    @Override
    public String getCommandName () {
        return "odc";
    }
    @Override
    public String getCommandUsage (ICommandSender iCommandSender) {
        return EnumChatFormatting.RED + "/odc <command>";
    }
    @Override
    public List getCommandAliases () {
        return this.aliases;
    }
    @Override
    public void processCommand (ICommandSender iCommandSender, String[] inputString) {
        ChatComponentText returnText = new ChatComponentText("");
        if (inputString.length == 0) { // no input command
            Log.playerChat("Use \"/odc help\" for useage help.");
            return;
        } else { //message contains data
            ItemStack item = null;
            if (iCommandSender instanceof EntityPlayerMP) { //if the command runner is a player, get the item they are holding
                item = ((EntityPlayerMP) iCommandSender).inventory.getCurrentItem();
            }
            if (inputString[0].equalsIgnoreCase("detect")) {
                if (item != null) {
                    Detect.detect(item);
                    return;
                } else {
                    Log.playerChat("You\'re not holding an item!");
                    return;
                }
            } else if (inputString[0].equalsIgnoreCase("dump")) {
                DumpOreDict.dump();
                return;
            } else if (inputString[0].equalsIgnoreCase("find")) {
                if (inputString.length < 2) {
                    Log.playerChat("You didn\'t specify a Ore Dictionary name! Use \"/odc help\" for help.");
                    return;
                }
                Find.find(inputString[1]);
                return;
            } else if (inputString[0].equalsIgnoreCase("help")) {
                Log.playerChat("To get the ore dictionary entries of the item currently held, use \"/odc detect\".");
                Log.playerChat("To dump all ore dictionary entries to the chat and log, use \"/odc dump\".");
                Log.playerChat("To find all items listed as the specified Ore Dictionary name, use \"/odc find <oreDictName>\".");
                return;
            }
        }
    }
    @Override
    public boolean canCommandSenderUseCommand (ICommandSender iCommandSender) {
        if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
            if (iCommandSender instanceof EntityPlayerMP) {
                return iCommandSender.canCommandSenderUseCommand(2,this.getCommandName());
            } else {
                return true;
            }
        }
        return false;
    }
    @Override
    public List addTabCompletionOptions(ICommandSender iCommandSender, String[] inputString) {
        return this.tabCompletionOptions;
    }
    @Override
    public boolean isUsernameIndex(String[] inputString, int i) {
        return false;
    }
}
