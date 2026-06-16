package dev.shadowsoffire.wstweaks;

import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ConversionParams;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.monster.skeleton.WitherSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = WitherSkeletonTweaks.MODID)
public class WSTEvents {

    @SubscribeEvent
    public static void witherTransform(FinalizeSpawnEvent event) {
        if (event.getEntity() instanceof Skeleton skeleton && !skeleton.isRemoved()) {
            RandomSource rand = event.getLevel().getRandom();
            if (!event.getLevel().isClientSide()) {
                if (skeleton.level().dimension() == Level.NETHER || WSTConfig.allBiomes && event.getLevel().getRawBrightness(skeleton.blockPosition(), 0) < 9 && rand.nextFloat() < WSTConfig.allBiomesChance) {
                    event.setCanceled(true);
                    skeleton.getPersistentData().putBoolean("wst.removed", true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void join(EntityJoinLevelEvent e) {
        if (e.getEntity() instanceof Skeleton skeleton && e.getEntity().getPersistentData().getBooleanOr("wst.removed", false)) {
            e.setCanceled(true);
            WitherSkeleton witherSkel = skeleton.convertTo(EntityType.WITHER_SKELETON, ConversionParams.single(skeleton, true, true), wskel -> {
                if (WSTConfig.giveBows) wskel.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BOW));
            });
            if (witherSkel == null) return;
            EventHooks.onLivingConvert(skeleton, witherSkel);
        }
    }

    @SubscribeEvent
    public static void handleDropsEvent(LivingDropsEvent event) {
        delSwords(event);
    }

    @SubscribeEvent
    public static void sync(OnDatapackSyncEvent e) {
        e.getRelevantPlayers().forEach(p -> PacketDistributor.sendToPlayer(p, new WSTConfig.ConfigPayload()));
    }

    public static void delSwords(LivingDropsEvent event) {
        if (WSTConfig.delSwords && !event.getEntity().level().isClientSide() && event.getEntity() instanceof AbstractSkeleton) {
            event.getDrops().removeIf(ie -> {
                ItemStack stack = ie.getItem();
                return stack.is(Items.STONE_SWORD) || stack.is(Items.BOW);
            });
        }
    }
}
