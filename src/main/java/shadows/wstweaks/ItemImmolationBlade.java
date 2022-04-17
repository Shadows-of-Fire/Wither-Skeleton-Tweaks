package shadows.wstweaks;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

public class ItemImmolationBlade extends SwordItem {

	public static final DamageSource FIREWORKS = new DamageSource("fireworks").setExplosion();

	public ItemImmolationBlade() {
		super(WitherSkeletonTweaks.IMMOLATION, 0, WSTConfig.swordAtkSpeed, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		target.setSecondsOnFire(150);
		super.hurtEnemy(stack, target, attacker);
		if (target instanceof AbstractSkeleton) {
			target.setHealth(1);
			target.hurt(FIREWORKS, 150);
			double i = target.getCommandSenderWorld().random.nextDouble() * 4.0D;
			double d = target.getCommandSenderWorld().random.nextDouble() * 4.0D;
			double k = target.getCommandSenderWorld().random.nextDouble() * 4.0D;
			target.push(2.0D - i, d, 2.0D - k);
			return true;
		}
		return true;
	}

}
