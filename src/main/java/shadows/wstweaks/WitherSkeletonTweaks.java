package shadows.wstweaks;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ObjectHolder;
import shadows.placebo.recipe.RecipeHelper;
import shadows.placebo.util.RunnableReloader;

@Mod(WitherSkeletonTweaks.MODID)
public class WitherSkeletonTweaks {

	public static final String MODID = "wstweaks";
	public static final RecipeHelper HELPER = new RecipeHelper(MODID);
	public static Tier IMMOLATION;

	@ObjectHolder("wstweaks:fragment")
	public static final Item FRAGMENT = null;

	public WitherSkeletonTweaks() {
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
		WSTConfig.load();
		IMMOLATION = new ForgeTier(9, WSTConfig.swordDurability, WSTConfig.swordAtkSpeed, WSTConfig.swordDamage, 30, null, () -> Ingredient.of(Items.NETHER_STAR));
		MinecraftForge.EVENT_BUS.addListener(this::reload);
	}

	@SubscribeEvent
	public void onItemRegistry(RegistryEvent.Register<Item> e) {
		e.getRegistry().registerAll(new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)).setRegistryName(MODID, "fragment"), new ItemImmolationBlade().setRegistryName(MODID, "lava_blade"), new ItemImmolationBlade().setRegistryName(MODID, "blaze_blade"));
	}

	@SubscribeEvent
	public void setup(FMLCommonSetupEvent e) {
		WitherSkeletonTweaks.HELPER.addShapeless(new ItemStack(Items.WITHER_SKELETON_SKULL), NonNullList.withSize(WSTConfig.shardValue, Ingredient.of(FRAGMENT)).toArray());
	}

	@SubscribeEvent
	public void onGLMRegister(Register<GlobalLootModifierSerializer<?>> e) {
		e.getRegistry().register(new WSTLootModifier.Serializer().setRegistryName("wstmodifier"));
	}

	public void reload(AddReloadListenerEvent e) {
		e.addListener(new RunnableReloader(WSTConfig::load));
	}

}
