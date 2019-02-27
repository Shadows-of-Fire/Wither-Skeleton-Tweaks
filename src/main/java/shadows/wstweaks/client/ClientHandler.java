package shadows.wstweaks.client;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import shadows.placebo.util.PlaceboUtil;
import shadows.wstweaks.WSTRegistry;
import shadows.wstweaks.WitherSkeletonTweaks;

@EventBusSubscriber(modid = WitherSkeletonTweaks.MODID, value = Side.CLIENT)
public class ClientHandler {

	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent e) {
		PlaceboUtil.sMRL(WSTRegistry.FRAGMENT, 0, "inventory");
		PlaceboUtil.sMRL(WSTRegistry.BLAZE_SWORD, 0, "inventory");
		PlaceboUtil.sMRL(WSTRegistry.LAVA_SWORD, 0, "inventory");
	}

}
