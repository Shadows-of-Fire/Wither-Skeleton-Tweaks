package shadows.wstweaks;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import shadows.placebo.registry.RegistryInformationV2;
import shadows.placebo.util.RecipeHelper;
import shadows.wstweaks.core.WSTConfig;
import shadows.wstweaks.core.WSTRegistry;
import shadows.wstweaks.proxy.CommonProxy;
import shadows.wstweaks.util.Events;

@Mod(modid = WitherSkeletonTweaks.MODID, version = WitherSkeletonTweaks.VERSION, name = WitherSkeletonTweaks.MODNAME, acceptedMinecraftVersions = "[1.12, 1.13)", dependencies = "required-after:placebo@[1.3.0,)")

public class WitherSkeletonTweaks {

	public static final String MODID = "witherskelefix";
	public static final String MODNAME = "Wither Skeleton Tweaks";
	public static final String VERSION = "2.6.2";

	@SidedProxy(clientSide = "shadows.wstweaks.proxy.ClientProxy", serverSide = "shadows.wstweaks.proxy.CommonProxy")
	public static CommonProxy PROXY;

	public static Configuration CONFIG;
	public static final RegistryInformationV2 INFO = new RegistryInformationV2(MODID, null);
	public static final RecipeHelper HELPER = new RecipeHelper(MODID, MODNAME, INFO.getRecipeList());

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		CONFIG = new Configuration(new File(event.getModConfigurationDirectory(), "wither_skeleton_tweaks.cfg"));
		WSTConfig.syncConfig();
		EnumHelper.addToolMaterial("immolation", 9, 4096, 0.6f, WSTConfig.immolationDmg, 72);
		MinecraftForge.EVENT_BUS.register(new WSTRegistry());
		MinecraftForge.EVENT_BUS.register(new Events());
		PROXY.preInit(event);
	}

}
