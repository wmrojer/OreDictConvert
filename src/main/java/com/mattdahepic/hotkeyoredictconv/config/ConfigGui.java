package com.mattdahepic.hotkeyoredictconv.config;


import com.mattdahepic.hotkeyoredictconv.OreDictConv;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.config.GuiConfig;

public class ConfigGui extends GuiConfig {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ConfigGui(GuiScreen gui)
	{
		super(gui,
				new ConfigElement(Config.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
				OreDictConv.MODID,
				false,
				true,
				GuiConfig.getAbridgedConfigPath(Config.config.toString()));
		
	}
	
}
