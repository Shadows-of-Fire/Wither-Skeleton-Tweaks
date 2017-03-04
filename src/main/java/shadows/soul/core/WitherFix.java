package shadows.soul.core;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import shadows.soul.proxy.CommonProxy;

@Mod(modid = WitherFix.MODID, version = WitherFix.VERSION, name = WitherFix.MODNAME)

public class WitherFix {
	public static final String MODID = "witherskelefix";
	public static final String MODNAME = "Wither Skeleton Tweaks";
	public static final String VERSION = "1.6.0";

	@SidedProxy(clientSide = "shadows.soul.proxy.ClientProxy", serverSide = "shadows.soul.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Mod.Instance
	public static WitherFix instance;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}

}
