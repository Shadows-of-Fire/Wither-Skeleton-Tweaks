package shadows.wstweaks;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import shadows.placebo.util.PlaceboUtil;
import shadows.placebo.util.RecipeHelper;
import shadows.wstweaks.common.ItemImmolationBlade;
import shadows.wstweaks.core.WSTConfig;

@ObjectHolder(WitherSkeletonTweaks.MODID)
public class WSTRegistry extends RecipeHelper {

	public WSTRegistry() {
		super(WitherSkeletonTweaks.MODID, WitherSkeletonTweaks.MODNAME);
	}

	public static final Item FRAGMENT = null;
	public static final ItemImmolationBlade LAVA_SWORD = null;
	public static final ItemImmolationBlade BLAZE_SWORD = null;

	@SubscribeEvent
	public void onItemRegistry(RegistryEvent.Register<Item> e) {
		//Formatter::off
		e.getRegistry().registerAll(
			PlaceboUtil.initItem(new Item(), modid, "fragment"),
			PlaceboUtil.initItem(new ItemImmolationBlade(), modid, "lava_sword"),
			PlaceboUtil.initItem(new ItemImmolationBlade(), modid, "blaze_sword")
		);
		//Formatter::on
	}

	@Override
	public void addRecipes() {
		Item L = Items.LAVA_BUCKET;
		Item S = Items.NETHER_STAR;
		String T = "stickWood";
		addShaped(WSTRegistry.LAVA_SWORD, 3, 3, null, L, S, L, S, L, T, L, null);
		L = Items.BLAZE_ROD;
		addShaped(WSTRegistry.BLAZE_SWORD, 3, 3, null, L, S, L, S, L, T, L, null);
		addSimpleShapeless(new ItemStack(Items.SKULL, 1, 1), FRAGMENT, WSTConfig.shardValue);
	}

}
