package shadows.wstweaks.proxy;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shadows.wstweaks.core.ConfigFile;
import shadows.wstweaks.core.ModRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onModelRegistry(ModelRegistryEvent e) {
		ModRegistry.FRAGMENT.initModel();
		if (ConfigFile.enableLava) ModRegistry.LAVA_SWORD.initModel();
		if (ConfigFile.enableBlaze) ModRegistry.BLAZE_SWORD.initModel();
	}
}
