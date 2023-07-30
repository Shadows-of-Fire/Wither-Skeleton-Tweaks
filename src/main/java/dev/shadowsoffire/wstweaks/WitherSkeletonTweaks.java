package dev.shadowsoffire.wstweaks;

import java.util.ArrayList;
import java.util.List;

import dev.shadowsoffire.placebo.recipe.RecipeHelper;
import dev.shadowsoffire.placebo.util.RunnableReloader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod(WitherSkeletonTweaks.MODID)
public class WitherSkeletonTweaks {

    public static final String MODID = "wstweaks";
    public static final RecipeHelper HELPER = new RecipeHelper(MODID);
    public static Tier IMMOLATION;

    static Item fragment, lavaBlade, blazeBlade;

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
                inputs.add(Ingredient.of(fragment));
            }
            factory.addShapeless(new ItemStack(Items.WITHER_SKELETON_SKULL), inputs.toArray());
        });
    }

    @SubscribeEvent
    public void register(RegisterEvent e) {
        if (e.getForgeRegistry() == (Object) ForgeRegistries.ITEMS) {
            this.registerItems();
        }
        if (e.getForgeRegistry() == (Object) ForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS.get()) {
            this.registerGMLSer();
        }
    }

    @SubscribeEvent
    public void tabs(BuildCreativeModeTabContentsEvent e) {
        if (e.getTabKey() == CreativeModeTabs.COMBAT) {
            e.accept(lavaBlade);
            e.accept(blazeBlade);
        }
        else if (e.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            e.accept(fragment);
        }
    }

    private void registerItems() {
        ForgeRegistries.ITEMS.register("fragment", fragment = new Item(new Item.Properties()));
        ForgeRegistries.ITEMS.register("lava_blade", lavaBlade = new ItemImmolationBlade());
        ForgeRegistries.ITEMS.register("blaze_blade", blazeBlade = new ItemImmolationBlade());
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
