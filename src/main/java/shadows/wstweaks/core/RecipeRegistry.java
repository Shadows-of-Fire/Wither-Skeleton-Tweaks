package shadows.wstweaks.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import shadows.wstweaks.WSTweaks;

public class RecipeRegistry {

	public static void init() {

		int r = MathHelper.clamp(ConfigFile.shardValue, 1, 9);
		List<ItemStack> list = new ArrayList<ItemStack>();

		for (int i = 0; i < r; i++) {
			list.add(new ItemStack(ModRegistry.FRAGMENT));
		}
		Item L = Items.LAVA_BUCKET;
		Item S = Items.NETHER_STAR;
		String T = "stickWood";
		if (ConfigFile.enableLava) WSTweaks.HELPER.addShaped(ModRegistry.LAVA_SWORD, 3, 3, null, L, S, L, S, L, T, L, null);
		if (ConfigFile.enableBlaze) {
			L = Items.BLAZE_ROD;
			WSTweaks.HELPER.addShaped(ModRegistry.BLAZE_SWORD, 3, 3, null, L, S, L, S, L, T, L, null);
		}
		WSTweaks.HELPER.addShapeless(new ItemStack(Items.SKULL, 1, 1), list);
	}
}