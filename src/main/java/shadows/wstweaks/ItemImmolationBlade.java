package shadows.wstweaks;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.DamageSource;

public class ItemImmolationBlade extends SwordItem {

	public ItemImmolationBlade() {
		super(WitherSkeletonTweaks.IMMOLATION, 0, -1, new Item.Properties().group(ItemGroup.COMBAT));
	}

	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		super.hitEntity(stack, target, attacker);
		if (target instanceof AbstractSkeletonEntity) {
			target.setHealth(1);
			target.attackEntityFrom(DamageSource.FIREWORKS, 150);
			double i = target.getEntityWorld().rand.nextDouble() * 4.0D;
			double d = target.getEntityWorld().rand.nextDouble() * 4.0D;
			double k = target.getEntityWorld().rand.nextDouble() * 4.0D;
			target.addVelocity((2.0D - i), d, (2.0D - k));
			return true;
		}
		target.setFire(150);
		return true;
	}

}
