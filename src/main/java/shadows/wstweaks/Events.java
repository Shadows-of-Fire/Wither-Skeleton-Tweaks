package shadows.wstweaks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shadows.wstweaks.item.ItemImmolationBlade;

public class Events {

	@SubscribeEvent
	public void witherTransform(LivingSpawnEvent.SpecialSpawn event) {
		if (event.getEntity() instanceof EntitySkeleton) {
			Entity entity = event.getEntity();
			World world = entity.world;
			Random rand = world.rand;
			if (!world.isRemote) {
				double x = entity.posX;
				double y = entity.posY;
				double z = entity.posZ;
				if (BiomeDictionary.hasType(world.getBiome(new BlockPos(x, y, z)), Type.NETHER) || (WSTConfig.allowAllBiomes && !world.isDaytime() && rand.nextInt(WSTConfig.allBiomesChance) == 0)) {
					event.setCanceled(true);
					entity.setDropItemsWhenDead(false);
					entity.setDead();
					EntityWitherSkeleton wSkele = new EntityWitherSkeleton(world);
					wSkele.setLocationAndAngles(x, y, z, 0, 0);
					world.spawnEntity(wSkele);
					if (WSTConfig.giveBows) wSkele.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.BOW));
				}
			}
		}
	}

	@SubscribeEvent
	public void handleDropsEvent(LivingDropsEvent event) {
		immolate(event);
		addFrags(event);
		delSwords(event);
	}

	public static void immolate(LivingDropsEvent event) {
		if (!event.getEntity().world.isRemote && (event.getSource() == DamageSource.FIREWORKS || hasSword(event.getSource()))) {
			List<EntityItem> drops = event.getDrops();
			if (event.getEntity().getClass() == EntityWitherSkeleton.class) {
				ItemStack stack = new ItemStack(Items.SKULL, 1, 1);
				if (!isStackInList(drops, stack)) event.getDrops().add(newEntity(event.getEntity(), stack));
			} else if (event.getEntity() instanceof AbstractSkeleton) {
				ItemStack stack = new ItemStack(Items.SKULL, 1, 1);
				for (int i = 0; i < drops.size(); i++) {
					if (drops.get(i).getItem().getItem() == Items.SKULL) drops.remove(i--);
				}
				drops.add(newEntity(event.getEntity(), stack));
			}
		}
	}

	public static void addFrags(LivingDropsEvent event) {
		if (WSTConfig.shardDropChance <= 0) return;
		if (event.getEntity().world.rand.nextInt(WSTConfig.shardDropChance) == 0) {
			if (!event.getEntity().world.isRemote && event.getEntity().getClass() == EntityWitherSkeleton.class && !(event.getSource() == DamageSource.FIREWORKS)) {
				List<EntityItem> drops = event.getDrops();
				ItemStack stack = new ItemStack(Items.SKULL, 1, 1);
				if (!isStackInList(drops, stack)) {
					drops.add(newEntity(event.getEntity(), new ItemStack(WSTRegistry.FRAGMENT)));
				}
			}
		}
	}

	public static void delSwords(LivingDropsEvent event) {
		if (WSTConfig.delSwords && !event.getEntity().world.isRemote && event.getEntity() instanceof AbstractSkeleton) {
			List<EntityItem> toRemove = new ArrayList<>();
			for (EntityItem entity : event.getDrops()) {
				ItemStack stack = entity.getItem();
				if (stack.getItem() == Items.STONE_SWORD || stack.getItem() == Items.BOW) toRemove.add(entity);
			}
			event.getDrops().removeAll(toRemove);
		}
	}

	public static EntityItem newEntity(Entity e, ItemStack stack) {
		return new EntityItem(e.world, e.posX, e.posY, e.posZ, stack);
	}

	public static boolean isStackInList(List<EntityItem> list, ItemStack stack) {
		for (EntityItem i : list) {
			ItemStack iStack = i.getItem();
			if (iStack.isItemEqual(stack)) return true;
		}
		return false;
	}

	private static boolean hasSword(DamageSource source) {
		Entity s = source.getTrueSource();
		if (s instanceof EntityLivingBase) return ((EntityLivingBase) s).getHeldItemMainhand().getItem() instanceof ItemImmolationBlade;
		else return false;
	}

}
