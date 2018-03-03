package shadows.wstweaks.proxy;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shadows.wstweaks.core.WSTConfig;
import shadows.wstweaks.core.WSTRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onModelRegistry(ModelRegistryEvent e) {
		WSTRegistry.FRAGMENT.initModels(e);
		if (WSTConfig.enableLava) WSTRegistry.LAVA_SWORD.initModels(e);
		if (WSTConfig.enableBlaze) WSTRegistry.BLAZE_SWORD.initModels(e);
	}
}
