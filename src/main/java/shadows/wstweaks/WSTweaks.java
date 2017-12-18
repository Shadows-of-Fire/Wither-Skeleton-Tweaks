package shadows.wstweaks;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import shadows.placebo.registry.RegistryInformation;
import shadows.placebo.util.RecipeHelper;
import shadows.wstweaks.core.ConfigFile;
import shadows.wstweaks.core.ModRegistry;
import shadows.wstweaks.proxy.CommonProxy;
import shadows.wstweaks.util.Events;

@Mod(modid = WSTweaks.MODID, version = WSTweaks.VERSION, name = WSTweaks.MODNAME, acceptedMinecraftVersions = "[1.12, 1.13)", dependencies = "required-after:placebo@[1.1.0,)")

public class WSTweaks {
	public static final String MODID = "witherskelefix";
	public static final String MODNAME = "Wither Skeleton Tweaks";
	public static final String VERSION = "2.3.0";

	@SidedProxy(clientSide = "shadows.wstweaks.proxy.ClientProxy", serverSide = "shadows.wstweaks.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static Configuration config;
	
	public static final RegistryInformation INFO = new RegistryInformation(MODID, null);
	
	public static final RecipeHelper HELPER = new RecipeHelper(MODID, MODNAME, INFO.getRecipeList());

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());
		ConfigFile.syncConfig(config);
		EnumHelper.addToolMaterial("immolation", 9, 4096, 0.6f, 12f, 72);
		MinecraftForge.EVENT_BUS.register(new ModRegistry());
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new Events());
		proxy.init(e);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}

}
