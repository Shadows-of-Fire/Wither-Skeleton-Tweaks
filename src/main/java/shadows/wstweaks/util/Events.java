package shadows.wstweaks.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.player.EntityPlayer;
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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shadows.wstweaks.common.ItemImmolationBlade;
import shadows.wstweaks.core.WSTConfig;
import shadows.wstweaks.core.WSTRegistry;

public class Events {

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
				if (world.getBiome(new BlockPos(x, y, z)) == Biomes.HELL || (WSTConfig.allowAllBiomes && !event.getWorld().isDaytime() && rand.nextInt(WSTConfig.allBiomesChance) == 0)) {
					event.setCanceled(true);
					entity.setDropItemsWhenDead(false);
					entity.setDead();
					EntityWitherSkeleton k = new EntityWitherSkeleton(world);
					k.setLocationAndAngles(x, y, z, 0, 0);
					world.spawnEntity(k);
					if (WSTConfig.giveBows) k.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.BOW));
					for (int i = 0; i < WSTConfig.extraWitherSkeletons; i++) {
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
					for (int i = 0; i < WSTConfig.extraWitherSkeletons; i++) {
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
					for (int i = -1; i < WSTConfig.extraWitherSkeletons; i++) {
						EntityWitherSkeleton a = new EntityWitherSkeleton(world);
						a.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, 0, 0);
						world.spawnEntity(a);
					}
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
				if (!isStackInList(drops, stack)) {
					event.getDrops().add(newEntity(event.getEntity(), stack));
				}
			} else if (event.getEntity() instanceof AbstractSkeleton) {
				ItemStack stack = new ItemStack(Items.SKULL);
				EntityItem toRemove = null;

				for (EntityItem i : drops)
					if (i.getItem().isItemEqual(stack)) toRemove = i;

				if (toRemove != null) drops.remove(toRemove);

				drops.add(newEntity(event.getEntity(), stack));
			}
		}
	}

	private static boolean hasSword(DamageSource source) {
		Entity s = source.getTrueSource();
		if (s instanceof EntityPlayer) return ((EntityPlayer) s).getHeldItemMainhand().getItem() instanceof ItemImmolationBlade;
		else return false;
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

			for (EntityItem i : toRemove)
				event.getDrops().remove(i);
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

}
