package shadows.soul.proxy;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import shadows.soul.core.ModRegistry;



public class ClientProxy extends CommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		ModRegistry.initModels();

	}
}

