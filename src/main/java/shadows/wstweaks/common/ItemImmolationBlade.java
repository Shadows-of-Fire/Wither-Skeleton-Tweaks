package shadows.wstweaks.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import shadows.placebo.client.IHasModel;
import shadows.wstweaks.WitherSkeletonTweaks;

public class ItemImmolationBlade extends ItemSword implements IHasModel {

	public ItemImmolationBlade(String name, ToolMaterial material) {
		super(material);
		setRegistryName(name);
		setUnlocalizedName(WitherSkeletonTweaks.MODID + "." + name);
		setCreativeTab(CreativeTabs.COMBAT);
		WitherSkeletonTweaks.INFO.getItemList().add(this);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		stack.damageItem(1, attacker);
		if (target instanceof AbstractSkeleton) {
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
