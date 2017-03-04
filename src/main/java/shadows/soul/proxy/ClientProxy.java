package shadows.soul.proxy;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import shadows.soul.core.ModRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		ModRegistry.initModels();

	}
}
