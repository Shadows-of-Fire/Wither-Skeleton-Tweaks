package shadows.wstweaks;

import java.io.File;

import net.minecraft.init.Items;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import shadows.wstweaks.core.WSTConfig;
import shadows.wstweaks.util.Events;

@Mod(modid = WitherSkeletonTweaks.MODID, version = WitherSkeletonTweaks.VERSION, name = WitherSkeletonTweaks.MODNAME, dependencies = "required-after:placebo@[2.0.0,)")

public class WitherSkeletonTweaks {

	public static final String MODID = "witherskelefix";
	public static final String MODNAME = "Wither Skeleton Tweaks";
	public static final String VERSION = "3.0.0";

	public static ToolMaterial immolation;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		WSTConfig.syncConfig(new Configuration(new File(event.getModConfigurationDirectory(), "wither_skeleton_tweaks.cfg")));
		immolation = EnumHelper.addToolMaterial("immolation", 9, 4096, 0.6f, WSTConfig.immolationDmg, 72).setRepairItem(new ItemStack(Items.NETHER_STAR));
		MinecraftForge.EVENT_BUS.register(new WSTRegistry());
		MinecraftForge.EVENT_BUS.register(new Events());
	}

}
