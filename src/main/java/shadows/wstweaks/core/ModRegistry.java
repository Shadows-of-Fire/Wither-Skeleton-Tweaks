package shadows.wstweaks.core;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shadows.wstweaks.WSTweaks;
import shadows.wstweaks.common.ItemImmolationBlade;
import shadows.wstweaks.common.ItemWitherFragment;

public class ModRegistry {
	public static final ItemWitherFragment FRAGMENT = new ItemWitherFragment();
	public static final ItemImmolationBlade LAVA_SWORD = new ItemImmolationBlade("blade", ToolMaterial.valueOf("immolation"));
	public static final ItemImmolationBlade BLAZE_SWORD = new ItemImmolationBlade("blade2", ToolMaterial.valueOf("immolation"));

	@SubscribeEvent
	public void onItemRegistry(RegistryEvent.Register<Item> e) {
		e.getRegistry().registerAll(WSTweaks.INFO.getItemList().toArray(new Item[3]));
	}

	@SubscribeEvent
	public void onRecipeRegistry(RegistryEvent.Register<IRecipe> e) {
		RecipeRegistry.init();
		e.getRegistry().registerAll(WSTweaks.INFO.getRecipeList().toArray(new IRecipe[0]));
	}

}
