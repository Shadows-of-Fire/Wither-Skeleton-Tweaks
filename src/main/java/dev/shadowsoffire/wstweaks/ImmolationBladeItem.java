package dev.shadowsoffire.wstweaks;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ImmolationBladeItem extends Item {

    public ImmolationBladeItem(Item.Properties properties) {
        super(properties);
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
