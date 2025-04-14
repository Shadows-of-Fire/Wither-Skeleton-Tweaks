package dev.shadowsoffire.wstweaks;

import dev.shadowsoffire.placebo.registry.DeferredHelper;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class WSTObjects {

    private static final DeferredHelper R = DeferredHelper.create(WitherSkeletonTweaks.MODID);

    public static final Holder<Item> FRAGMENT = R.item("fragment", Item::new);

    public static final Holder<Item> LAVA_BLADE = R.item("lava_blade", ItemImmolationBlade::new, p -> p.attributes(SwordItem.createAttributes(WitherSkeletonTweaks.IMMOLATION, 0, WSTConfig.swordAtkSpeed)));

    public static final Holder<Item> BLAZE_BLADE = R.item("blaze_blade", ItemImmolationBlade::new, p -> p.attributes(SwordItem.createAttributes(WitherSkeletonTweaks.IMMOLATION, 0, WSTConfig.swordAtkSpeed)));

    static {
        R.custom("wstmodifier", NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, () -> WSTLootModifier.CODEC);
    }

    public static void bootstrap(IEventBus bus) {
        bus.register(R);
    }
}
