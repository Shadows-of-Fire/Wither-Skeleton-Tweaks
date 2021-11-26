package shadows.wstweaks;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.DamageSource;

public class ItemImmolationBlade extends SwordItem {

	public static final DamageSource FIREWORKS = new DamageSource("fireworks").setExplosion();

	public ItemImmolationBlade() {
		super(WitherSkeletonTweaks.IMMOLATION, 0, -1, new Item.Properties().tab(ItemGroup.TAB_COMBAT));
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		target.setSecondsOnFire(150);
		super.hurtEnemy(stack, target, attacker);
		if (target instanceof AbstractSkeletonEntity) {
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
