package shadows.soul.util;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class SoulMethods {

	// Spawns an EntityLiving in world at position.
	public static Entity spawnCreature(World worldIn, Entity entityIn, double x, double y, double z) {
		Entity entity = entityIn;

		if (entity instanceof EntityLivingBase) {
			EntityLiving entityliving = (EntityLiving) entity;
			entity.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
			entityliving.rotationYawHead = entityliving.rotationYaw;
			entityliving.renderYawOffset = entityliving.rotationYaw;
			entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityliving)),
					(IEntityLivingData) null);
			worldIn.spawnEntity(entity);
			entityliving.playLivingSound();

			return entity;
		} else {
			return null;
		}
	}

	// Returns true if an item is found in a mob drop list.
	public static boolean dropSearchFinder(List<EntityItem> list, ItemStack stack) {
		Iterator<EntityItem> iterator = list.iterator();
		while (iterator.hasNext()) {
			EntityItem item = iterator.next();
			if (item.getEntityItem().getItem() == stack.getItem()) {
				if (item.getEntityItem().getMetadata() == stack.getMetadata()) {
					// System.out.println("item is " +
					// item.getEntityItem().toString() + " while target = " +
					// stack.toString() + " result: true");
					return true;
				}
			}
			// System.out.println("item is " + item.getEntityItem().toString() +
			// " while target = " + stack.toString() + " result: false");
		}

		return false;

	}

}
