package shadows.soul.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.oredict.OreDictionary;
import shadows.soul.util.RecipeHelper;

public class RecipeRegistry {

	private static int r = MathHelper.clamp(ConfigFile.shardValue, 1, 9);
	private static List<ItemStack> list = new ArrayList<ItemStack>();

	public static void init() {
		for (int i = 0; i < r; i++) {
			list.add(new ItemStack(ModRegistry.FRAGMENT));
		}
		OreDictionary.registerOre("stickWood", Items.STICK);
		Item L = Items.LAVA_BUCKET;
		Item S = Items.NETHER_STAR;
		String T = "stickWood";
		if (ConfigFile.enableLava)
			RecipeHelper.addShaped(ModRegistry.LAVA_SWORD, 3, 3, null, L, S, L, S, L, T, L, null);
		if (ConfigFile.enableBlaze) {
			L = Items.BLAZE_ROD;
			RecipeHelper.addShaped(ModRegistry.BLAZE_SWORD, 3, 3, null, L, S, L, S, L, T, L, null);
		}
		RecipeHelper.addShapeless(new ItemStack(Items.SKULL, 1, 1), list);
	}
}