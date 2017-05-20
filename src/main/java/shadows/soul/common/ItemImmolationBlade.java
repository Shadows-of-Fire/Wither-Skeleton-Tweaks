package shadows.soul.common;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import shadows.soul.core.WitherFix;

public class ItemImmolationBlade extends ItemSword {

	private ToolMaterial material;

	public ItemImmolationBlade(String name, ToolMaterial material_) {
		super(material_);
		material = material_;
		setRegistryName(name);
		setUnlocalizedName(WitherFix.MODID + "." + name);
		setCreativeTab(CreativeTabs.COMBAT);
		GameRegistry.register(this);
	}

	@Override
	public float getDamageVsEntity() {
		return this.material.getDamageVsEntity();
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		stack.damageItem(1, attacker);

		if (target instanceof AbstractSkeleton) {
			target.setHealth(1);
			target.attackEntityFrom(DamageSource.field_191552_t, 150);
			double i = target.getEntityWorld().rand.nextDouble() * 4.0D;
			double d = target.getEntityWorld().rand.nextDouble() * 4.0D;
			double k = target.getEntityWorld().rand.nextDouble() * 4.0D;
			target.addVelocity((2.0D - i), d, (2.0D - k));
			return true;
		}
		target.setFire(150);
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}

}
