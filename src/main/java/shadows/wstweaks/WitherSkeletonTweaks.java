package shadows.wstweaks;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ObjectHolder;
import shadows.placebo.recipe.RecipeHelper;

@Mod(WitherSkeletonTweaks.MODID)
public class WitherSkeletonTweaks {

	public static final String MODID = "wstweaks";
	public static final RecipeHelper HELPER = new RecipeHelper(MODID);
	public static Tier IMMOLATION = new ForgeTier(9, 4096, 0.6F, 12, 30, null, () -> Ingredient.of(Items.NETHER_STAR));

	@ObjectHolder("wstweaks:fragment")
	public static final Item FRAGMENT = null;

	@ObjectHolder("wstweaks:lava_blade")
	public static final ItemImmolationBlade LAVA_SWORD = null;

	@ObjectHolder("wstweaks:blaze_blade")
	public static final ItemImmolationBlade BLAZE_SWORD = null;

	public WitherSkeletonTweaks() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WSTConfig.SPEC);
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.register(this);
	}

	@SubscribeEvent
	public void onItemRegistry(RegistryEvent.Register<Item> e) {
		e.getRegistry().registerAll(new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)).setRegistryName(MODID, "fragment"), new ItemImmolationBlade().setRegistryName(MODID, "lava_blade"), new ItemImmolationBlade().setRegistryName(MODID, "blaze_blade"));
	}

	@SubscribeEvent
	public void setup(FMLCommonSetupEvent e) {
		Item L = Items.LAVA_BUCKET;
		Item S = Items.NETHER_STAR;
		Item T = Items.STICK;
		HELPER.addShaped(LAVA_SWORD, 3, 3, null, L, S, L, S, L, T, L, null);
		L = Items.BLAZE_ROD;
		HELPER.addShaped(BLAZE_SWORD, 3, 3, null, L, S, L, S, L, T, L, null);
		WitherSkeletonTweaks.HELPER.addShapeless(new ItemStack(Items.WITHER_SKELETON_SKULL), NonNullList.withSize(WSTConfig.INSTANCE.shardValue.get(), Ingredient.of(FRAGMENT)).toArray());
	}

	@SubscribeEvent
	public void onGLMRegister(Register<GlobalLootModifierSerializer<?>> e) {
		e.getRegistry().register(new WSTLootModifier.Serializer().setRegistryName("wstmodifier"));
	}

}
