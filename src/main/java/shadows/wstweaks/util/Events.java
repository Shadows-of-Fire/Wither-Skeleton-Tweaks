package shadows.wstweaks.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shadows.wstweaks.core.WSTConfig;
import shadows.wstweaks.core.WSTRegistry;

public class Events {

	private final int tries;
	private final int allBiomesChance;
	
	public Events(int tries, int allBiomesChance) {
		this.tries = tries;
		this.allBiomesChance = allBiomesChance;
	}

	@SubscribeEvent
	public void witherTransform(LivingSpawnEvent.SpecialSpawn event) {
		if (event.getEntity() instanceof EntitySkeleton) {
			Entity entity = event.getEntity();
			World world = entity.world;
			Random rand = world.rand;
			if (!event.getEntity().world.isRemote) {
				double x = entity.posX;
				double y = entity.posY;
				double z = entity.posZ;
				if (world.getBiome(new BlockPos(x, y, z)) == Biomes.HELL || (WSTConfig.allowAllBiomes && !event.getWorld().isDaytime() && rand.nextInt(allBiomesChance) == 0)) {
					event.setCanceled(true);
					entity.setDropItemsWhenDead(false);
					entity.setDead();
					EntityWitherSkeleton k = new EntityWitherSkeleton(world);
					k.setLocationAndAngles(x, y, z, 0, 0);
					world.spawnEntity(k);
					k.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.BOW));
					for (int i = 0; i < tries; i++) {
						EntityWitherSkeleton a = new EntityWitherSkeleton(world);
						a.setLocationAndAngles(x, y, z, 0, 0);
						world.spawnEntity(a);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void skeleFixer(LivingSpawnEvent.CheckSpawn event) {
		if (event.getEntity().getClass() == EntityWitherSkeleton.class) {
			if (!event.getEntity().world.isRemote) {
				Entity entity = event.getEntity();
				World world = entity.world;
				double x = entity.posX;
				double y = entity.posY;
				double z = entity.posZ;
				if (world.getBiome(new BlockPos(x, y, z)) == Biomes.HELL) {
					for (int i = 0; i < tries; i++) {
						EntityWitherSkeleton a = new EntityWitherSkeleton(world);
						a.setLocationAndAngles(x, y, z, 0, 0);
						world.spawnEntity(a);
					}
				}
			}
		} else if (event.getEntity() instanceof EntityBlaze || event.getEntity() instanceof EntityPigZombie) {
			if (WSTConfig.extraSpawns) {
				World world = event.getWorld();
				Entity entity = event.getEntity();
				BlockPos pos = entity.getPosition().down();
				if (world.getBlockState(pos).getBlock() == Blocks.NETHER_BRICK) {
					for (int i = -1; i < tries; i++) {
						EntityWitherSkeleton a = new EntityWitherSkeleton(world);
						a.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, 0, 0);
						world.spawnEntity(a);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void addFrags(LivingDropsEvent event) {
		if (WSTConfig.shardDropChance <= 0) return;
		if (event.getEntity().world.rand.nextInt(WSTConfig.shardDropChance) == 0) {
			if (!event.getEntity().world.isRemote && event.getEntity().getClass() == EntityWitherSkeleton.class && !(event.getSource() == DamageSource.FIREWORKS)) {
				List<EntityItem> drops = event.getDrops();
				ItemStack stack = new ItemStack(Items.SKULL, 1, 1);
				if (!isStackInList(drops, stack)) {
					event.getDrops().add(new EntityItem(event.getEntity().world, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, new ItemStack(WSTRegistry.FRAGMENT)));
				}
			}
		}
	}

	@SubscribeEvent
	public void immolate(LivingDropsEvent event) {
		if (!event.getEntity().world.isRemote && event.getSource() == DamageSource.FIREWORKS) {
			List<EntityItem> drops = event.getDrops();
			ItemStack stack = new ItemStack(Items.SKULL, 1, 1);
			if (event.getEntity().getClass() == EntityWitherSkeleton.class) {
				if (!isStackInList(drops, stack)) {
					event.getDrops().add(new EntityItem(event.getEntity().world, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, stack));
				}
			}
			if (event.getEntity() instanceof EntitySkeleton) {
				event.getDrops().clear();
				event.getDrops().add(new EntityItem(event.getEntity().world, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, stack));
				if (event.getEntity().world.rand.nextBoolean()) {
					event.getDrops().add(new EntityItem(event.getEntity().world, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, new ItemStack(Items.ARROW, 3)));
				}
				if (event.getEntity().world.rand.nextBoolean()) {
					event.getDrops().add(new EntityItem(event.getEntity().world, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, new ItemStack(Items.BONE, 2)));
				}
			}

		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void delSwords(LivingDropsEvent event) {
		if (WSTConfig.delSwords && !event.getEntity().world.isRemote && event.getEntity() instanceof AbstractSkeleton) {
			List<EntityItem> drops = event.getDrops();
			Iterator<EntityItem> iterator = drops.iterator();
			List<EntityItem> newDrops = new ArrayList<EntityItem>();
			while (iterator.hasNext()) {
				EntityItem item = iterator.next();
				if (!(item.getItem().getItem() == Items.STONE_SWORD || item.getItem().getItem() == Items.BOW)) {
					newDrops.add(item);
				}
			}
			event.getDrops().clear();
			for (EntityItem thing : newDrops) {
				event.getDrops().add(thing);
			}
		}
	}

	public static boolean isStackInList(List<EntityItem> list, ItemStack stack) {
		for (EntityItem i : list) {
			ItemStack iStack = i.getItem();
			if (iStack.isItemEqual(stack)) return true;
		}
		return false;
	}

}
