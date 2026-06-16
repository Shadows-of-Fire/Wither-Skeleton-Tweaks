package dev.shadowsoffire.wstweaks;

import dev.shadowsoffire.placebo.network.PayloadHelper;
import dev.shadowsoffire.placebo.util.RunnableReloader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(WitherSkeletonTweaks.MODID)
public class WitherSkeletonTweaks {

    public static final String MODID = "wstweaks";
    public static final TagKey<Item> IMMOLATION_REPAIR = TagKey.create(Registries.ITEM, loc("immolation_repair"));

    // The durability and attack-damage params are placeholders: both are supplied live (from the synced config) by
    // ImmolationBladeItem#getMaxDamage / #getDefaultAttributeModifiers, so nothing latches at registration.
    public static final ToolMaterial IMMOLATION = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 1, 0, 0, 30, IMMOLATION_REPAIR);

    public WitherSkeletonTweaks(IEventBus bus) {
        bus.register(this);
        WSTConfig.load();
        NeoForge.EVENT_BUS.addListener(this::reload);
        WSTObjects.bootstrap(bus);
    }

    @SubscribeEvent
    public void setup(FMLCommonSetupEvent e) {
        e.enqueueWork(() -> {
            BuiltInRegistries.ITEM.addAlias(loc("blaze_blade"), WSTObjects.IMMOLATION_BLADE.getKey().identifier());
            BuiltInRegistries.ITEM.addAlias(loc("lava_blade"), WSTObjects.IMMOLATION_BLADE.getKey().identifier());
            PayloadHelper.registerPayload(new WSTConfig.ConfigPayload.Provider());
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

    public void reload(AddServerReloadListenersEvent e) {
        e.addListener(loc("reload_config"), new RunnableReloader(WSTConfig::load));
    }

    public static Identifier loc(String s) {
        return Identifier.fromNamespaceAndPath(MODID, s);
    }

}
