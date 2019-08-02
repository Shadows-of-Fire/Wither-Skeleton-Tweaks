package shadows.wstweaks;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ObjectHolder;
import shadows.placebo.recipe.RecipeHelper;
import shadows.placebo.util.ItemTier;

@Mod(WitherSkeletonTweaks.MODID)
public class WitherSkeletonTweaks {

	public static final String MODID = "wstweaks";
	public static final RecipeHelper HELPER = new RecipeHelper(MODID);
	public static IItemTier IMMOLATION = new ItemTier(9, 4096, 0.6F, 12, 30, Ingredient.fromItems(Items.NETHER_STAR));

	@ObjectHolder("wstweaks:fragment")
	public static final Item FRAGMENT = null;

	@ObjectHolder("wstweaks:lava_blade")
	public static final ItemImmolationBlade LAVA_SWORD = null;

	@ObjectHolder("wstweaks:blaze_blade")
	public static final ItemImmolationBlade BLAZE_SWORD = null;

	public WitherSkeletonTweaks() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WSTConfig.SPEC);
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addGenericListener(Item.class, this::onItemRegistry);
		bus.addListener(this::setup);
	}

	@SubscribeEvent
	public void onItemRegistry(RegistryEvent.Register<Item> e) {
		e.getRegistry().registerAll(new Item(new Item.Properties().group(ItemGroup.MATERIALS)).setRegistryName(MODID, "fragment"), new ItemImmolationBlade().setRegistryName(MODID, "lava_blade"), new ItemImmolationBlade().setRegistryName(MODID, "blaze_blade"));
	}

	@SubscribeEvent
	public void setup(FMLCommonSetupEvent e) {
		Item L = Items.LAVA_BUCKET;
		Item S = Items.NETHER_STAR;
		Item T = Items.STICK;
		HELPER.addShaped(LAVA_SWORD, 3, 3, null, L, S, L, S, L, T, L, null);
		L = Items.BLAZE_ROD;
		HELPER.addShaped(BLAZE_SWORD, 3, 3, null, L, S, L, S, L, T, L, null);
		WitherSkeletonTweaks.HELPER.addShapeless(new ItemStack(Items.WITHER_SKELETON_SKULL), NonNullList.withSize(WSTConfig.INSTANCE.shardValue.get(), Ingredient.fromItems(FRAGMENT)).toArray());
	}

}
