package dev.shadowsoffire.wstweaks;

import dev.shadowsoffire.placebo.util.RunnableReloader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(WitherSkeletonTweaks.MODID)
public class WitherSkeletonTweaks {

    public static final String MODID = "wstweaks";
    public static Tier IMMOLATION;

    public WitherSkeletonTweaks(IEventBus bus) {
        bus.register(this);
        WSTConfig.load();
        IMMOLATION = new SimpleTier(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, WSTConfig.swordDurability, 0, WSTConfig.swordDamage, 30, () -> Ingredient.of(Items.NETHER_STAR));
        NeoForge.EVENT_BUS.addListener(this::reload);
        WSTObjects.bootstrap(bus);
    }

    @SubscribeEvent
    public void setup(FMLCommonSetupEvent e) {
        e.enqueueWork(() -> {
            BuiltInRegistries.ITEM.addAlias(loc("blaze_blade"), WSTObjects.IMMOLATION_BLADE.getKey().location());
            BuiltInRegistries.ITEM.addAlias(loc("lava_blade"), WSTObjects.IMMOLATION_BLADE.getKey().location());
        });
    }

    @SubscribeEvent
    public void tabs(BuildCreativeModeTabContentsEvent e) {
        if (e.getTabKey() == CreativeModeTabs.COMBAT) {
            e.accept(WSTObjects.IMMOLATION_BLADE.value());
        }
        else if (e.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            e.accept(WSTObjects.FRAGMENT.value());
        }
    }

    public void reload(AddReloadListenerEvent e) {
        e.addListener(new RunnableReloader(WSTConfig::load));
    }

    public static ResourceLocation loc(String s) {
        return ResourceLocation.fromNamespaceAndPath(MODID, s);
    }

}
