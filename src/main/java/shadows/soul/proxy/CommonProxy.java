package shadows.soul.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import shadows.soul.core.ConfigFile;
import shadows.soul.core.ModRegistry;
import shadows.soul.util.SoulEvents;

public class CommonProxy {

	public static Configuration config;

	public void preInit(FMLPreInitializationEvent e) {
		config = new Configuration(e.getSuggestedConfigurationFile());
		ConfigFile.syncConfig();
		EnumHelper.addToolMaterial("immolation", 9, 4096, 0.6f, 12f, 72);
		MinecraftForge.EVENT_BUS.register(new ModRegistry());
	}

	public void init(FMLInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new SoulEvents());

	}

	public void postInit(FMLPostInitializationEvent e) {

	}

}
