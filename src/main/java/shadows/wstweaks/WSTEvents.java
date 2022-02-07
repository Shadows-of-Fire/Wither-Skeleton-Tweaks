package shadows.wstweaks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = WitherSkeletonTweaks.MODID)
public class WSTEvents {

	@SubscribeEvent
	public static void witherTransform(LivingSpawnEvent.SpecialSpawn event) {
		if (event.getEntity() instanceof Skeleton) {
			Skeleton entity = (Skeleton) event.getEntity();
			Level world = entity.level;
			Random rand = world.random;
			if (!event.getEntity().level.isClientSide) {
				double x = entity.getX();
				double y = entity.getY();
				double z = entity.getZ();
				if (world.dimension() == Level.NETHER || WSTConfig.allBiomes && event.getWorld().getRawBrightness(new BlockPos(x, y, z), 0) < 9 && rand.nextFloat() < WSTConfig.allBiomesChance) {
					event.setCanceled(true);
					entity.getPersistentData().putBoolean("wst.removed", true);
					WitherSkeleton k = EntityType.WITHER_SKELETON.create(world);
					k.moveTo(x, y, z, 0, 0);
					world.addFreshEntity(k);
					if (WSTConfig.giveBows) k.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BOW));
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
		if (WSTConfig.delSwords && !event.getEntity().level.isClientSide && event.getEntity() instanceof AbstractSkeleton) {

			List<ItemEntity> toRemove = new ArrayList<>();
			for (ItemEntity entity : event.getDrops()) {
				ItemStack stack = entity.getItem();
				if (stack.getItem() == Items.STONE_SWORD || stack.getItem() == Items.BOW) {
					CompoundTag tag = stack.getTag();
					if (tag != null && (tag.contains("Damage") && tag.getAllKeys().size() > 2 || tag.getAllKeys().size() > 1)) continue;
					toRemove.add(entity);
				}
			}

			for (ItemEntity i : toRemove)
				event.getDrops().remove(i);
		}
	}
}
