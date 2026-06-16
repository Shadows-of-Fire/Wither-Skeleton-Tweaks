package dev.shadowsoffire.wstweaks;

import dev.shadowsoffire.placebo.registry.DeferredHelper;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class WSTObjects {

    private static final DeferredHelper R = DeferredHelper.create(WitherSkeletonTweaks.MODID);

    public static final Holder<Item> FRAGMENT = R.item("fragment", Item::new);

    // The sword's Tool/Weapon/enchantable/repairable/damageable components are kept, but the baked attribute
    // modifiers are blanked so ImmolationBladeItem#getDefaultAttributeModifiers provides them live from the synced config.
    public static final Holder<Item> IMMOLATION_BLADE = R.item("immolation_blade", ImmolationBladeItem::new, p -> WitherSkeletonTweaks.IMMOLATION.applySwordProperties(p, 0, 0).attributes(ItemAttributeModifiers.EMPTY));

    static {
        R.custom("wstmodifier", NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, WSTLootModifier.CODEC);
    }

    public static void bootstrap(IEventBus bus) {
        bus.register(R);
    }
}
