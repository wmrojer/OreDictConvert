package com.mattdahepic.oredictconv.command;

import com.mattdahepic.oredictconv.log.Log;
import net.minecraftforge.oredict.OreDictionary;

public class DumpOreDict {
    private DumpOreDict () {}
    public static void dump () {
        String[] oreDictNames = OreDictionary.getOreNames();
        Log.playerChat("Dumping all Ore Dictionary entries...");
        for (int i = 0; i < oreDictNames.length; i++) {
            Log.info(oreDictNames[i]);
            Log.playerChat(oreDictNames[i]);
        }
    }
}
