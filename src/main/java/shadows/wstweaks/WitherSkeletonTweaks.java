package shadows.wstweaks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegisterEvent;
import shadows.placebo.recipe.RecipeHelper;
import shadows.placebo.util.RunnableReloader;

@Mod(WitherSkeletonTweaks.MODID)
public class WitherSkeletonTweaks {

	public static final String MODID = "wstweaks";
	public static final RecipeHelper HELPER = new RecipeHelper(MODID);
	public static Tier IMMOLATION;

	@ObjectHolder(registryName = "item", value = "wstweaks:fragment")
	public static final Item FRAGMENT = null;

	public WitherSkeletonTweaks() {
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
		WSTConfig.load();
		IMMOLATION = new ForgeTier(9, WSTConfig.swordDurability, WSTConfig.swordAtkSpeed, WSTConfig.swordDamage, 30, null, () -> Ingredient.of(Items.NETHER_STAR));
		MinecraftForge.EVENT_BUS.addListener(this::reload);
	}

	@SubscribeEvent
	public void setup(FMLCommonSetupEvent e) {
		WitherSkeletonTweaks.HELPER.registerProvider(factory -> {
			List<Ingredient> inputs = new ArrayList<>(9);
			for (int i = 0; i < WSTConfig.shardValue; i++) {
				inputs.add(Ingredient.of(FRAGMENT));
			}
			factory.addShapeless(new ItemStack(Items.WITHER_SKELETON_SKULL), inputs.toArray());
		});
	}

	@SubscribeEvent
	public void register(RegisterEvent e) {
		if (e.getForgeRegistry() == (Object) ForgeRegistries.ITEMS) {
			registerItems();
		}
		if (e.getForgeRegistry() == (Object) ForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS.get()) {
			registerGMLSer();
		}
	}

	private void registerItems() {
		ForgeRegistries.ITEMS.register("fragment", new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
		ForgeRegistries.ITEMS.register("lava_blade", new ItemImmolationBlade());
		ForgeRegistries.ITEMS.register("blaze_blade", new ItemImmolationBlade());
	}

	private void registerGMLSer() {
		ForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS.get().register("wstmodifier", WSTLootModifier.CODEC.get());
	}

	public void reload(AddReloadListenerEvent e) {
		e.addListener(new RunnableReloader(WSTConfig::load));
	}

	public static ResourceLocation loc(String s) {
		return new ResourceLocation(MODID, s);
	}

}
