package shadows.wstweaks.core;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shadows.wstweaks.WitherSkeletonTweaks;
import shadows.wstweaks.common.ItemImmolationBlade;
import shadows.wstweaks.common.ItemWitherFragment;

public class WSTRegistry {
	public static final ItemWitherFragment FRAGMENT = new ItemWitherFragment();
	public static final ItemImmolationBlade LAVA_SWORD = new ItemImmolationBlade("blade", WitherSkeletonTweaks.IMMOLATION);
	public static final ItemImmolationBlade BLAZE_SWORD = new ItemImmolationBlade("blade2", WitherSkeletonTweaks.IMMOLATION);

	@SubscribeEvent
	public void onItemRegistry(RegistryEvent.Register<Item> e) {
		WitherSkeletonTweaks.INFO.getItemList().register(e.getRegistry());
	}

	@SubscribeEvent
	public void onRecipeRegistry(RegistryEvent.Register<IRecipe> e) {
		Item L = Items.LAVA_BUCKET;
		Item S = Items.NETHER_STAR;
		String T = "stickWood";
		if (WSTConfig.enableLava) WitherSkeletonTweaks.HELPER.addShaped(WSTRegistry.LAVA_SWORD, 3, 3, null, L, S, L, S, L, T, L, null);
		if (WSTConfig.enableBlaze) {
			L = Items.BLAZE_ROD;
			WitherSkeletonTweaks.HELPER.addShaped(WSTRegistry.BLAZE_SWORD, 3, 3, null, L, S, L, S, L, T, L, null);
		}
		WitherSkeletonTweaks.HELPER.addShapeless(new ItemStack(Items.SKULL, 1, 1), NonNullList.withSize(WSTConfig.shardValue, Ingredient.fromItem(FRAGMENT)));
		WitherSkeletonTweaks.INFO.getRecipeList().register(e.getRegistry());
	}

}
