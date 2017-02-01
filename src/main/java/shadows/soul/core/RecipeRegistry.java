package shadows.soul.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;



public class RecipeRegistry {

	private static int r = Math.max(1, Math.min(ConfigFile.shardValue, 9));
	private static List<ItemStack> list = new ArrayList<ItemStack>();
	
	
	public static void init(){
		for(int i = 0; i < r; i++){
			list.add(new ItemStack(ModRegistry.fragment));
		}
		if(ConfigFile.enableLava) GameRegistry.addRecipe(new ItemStack(ModRegistry.blade), " LS", "LSL", "TL ", 'L', Items.LAVA_BUCKET, 'S', Items.NETHER_STAR, 'T', Items.STICK);
		if(ConfigFile.enableBlaze) GameRegistry.addRecipe(new ItemStack(ModRegistry.blade2), " LS", "LSL", "TL ", 'L', Items.BLAZE_ROD, 'S', Items.NETHER_STAR, 'T', Items.STICK);
		CraftingManager.getInstance().addRecipe(new ShapelessRecipes(new ItemStack(Items.SKULL, 1, 1), list));
	}
}


/*
		GameRegistry.addShapelessRecipe(new ItemStack(ModRegistry.itemseed, 2), ModRegistry.itemessence, Items.APPLE, Items.WHEAT_SEEDS);
		GameRegistry.addShapedRecipe(new ItemStack(ModRegistry.soilcreator), " L ", "FSF", " L ", 'F', ModRegistry.itemseed, 'L', Items.EGG, 'S', Blocks.DIRT);

*/