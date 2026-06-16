package dev.shadowsoffire.wstweaks;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class ImmolationBladeItem extends Item {

    public ImmolationBladeItem(Item.Properties properties) {
        super(properties);
    }

    /**
     * Provided live (instead of baked at registration) so the synced config values take effect without a restart and a
     * connected client always matches the server. Consulted because the registered stack has an empty
     * {@code ATTRIBUTE_MODIFIERS} component (see {@link WSTObjects}).
     */
    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        return ItemAttributeModifiers.builder()
            .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, WSTConfig.swordDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, WSTConfig.swordAtkSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .build();
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return WSTConfig.swordDurability;
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.igniteForSeconds(150);
        if (target instanceof AbstractSkeleton && target.level() instanceof ServerLevel level) {
            target.setHealth(1);
            target.hurtServer(level, level.damageSources().source(DamageTypes.FIREWORKS), 150);
            double i = target.getRandom().nextDouble() * 4.0D;
            double d = target.getRandom().nextDouble() * 4.0D;
            double k = target.getRandom().nextDouble() * 4.0D;
            target.push(2.0D - i, d, 2.0D - k);
        }
    }

}
