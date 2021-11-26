package shadows.wstweaks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = WitherSkeletonTweaks.MODID)
public class WSTEvents {

	@SubscribeEvent
	public static void witherTransform(LivingSpawnEvent.SpecialSpawn event) {
		if (event.getEntity() instanceof SkeletonEntity) {
			SkeletonEntity entity = (SkeletonEntity) event.getEntity();
			World world = entity.level;
			Random rand = world.random;
			if (!event.getEntity().level.isClientSide) {
				double x = entity.getX();
				double y = entity.getY();
				double z = entity.getZ();
				if (world.dimension() == World.NETHER || WSTConfig.INSTANCE.allowAllBiomes.get() && event.getWorld().getRawBrightness(new BlockPos(x, y, z), 0) < 9 && rand.nextInt(WSTConfig.INSTANCE.allBiomesChance.get()) == 0) {
					event.setCanceled(true);
					entity.getPersistentData().putBoolean("wst.removed", true);
					WitherSkeletonEntity k = EntityType.WITHER_SKELETON.create(world);
					k.moveTo(x, y, z, 0, 0);
					world.addFreshEntity(k);
					if (WSTConfig.INSTANCE.giveBows.get()) k.setItemInHand(Hand.MAIN_HAND, new ItemStack(Items.BOW));
				}
			}
		}
	}

	@SubscribeEvent
	public static void join(EntityJoinWorldEvent e) {
		if (e.getEntity().getPersistentData().getBoolean("wst.removed")) e.setCanceled(true);
	}

	@SubscribeEvent
	public static void handleDropsEvent(LivingDropsEvent event) {
		delSwords(event);
	}

	public static void delSwords(LivingDropsEvent event) {
		if (WSTConfig.INSTANCE.delSwords.get() && !event.getEntity().level.isClientSide && event.getEntity() instanceof AbstractSkeletonEntity) {

			List<ItemEntity> toRemove = new ArrayList<>();
			for (ItemEntity entity : event.getDrops()) {
				ItemStack stack = entity.getItem();
				if (stack.getItem() == Items.STONE_SWORD || stack.getItem() == Items.BOW) {
					CompoundNBT tag = stack.getTag();
					if (tag != null && (tag.contains("Damage") && tag.getAllKeys().size() > 2 || tag.getAllKeys().size() > 1)) continue;
					toRemove.add(entity);
				}
			}

			for (ItemEntity i : toRemove)
				event.getDrops().remove(i);
		}
	}
}
