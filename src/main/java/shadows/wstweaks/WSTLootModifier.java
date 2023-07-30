package shadows.wstweaks;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

public class WSTLootModifier extends LootModifier {

    public static final Supplier<Codec<WSTLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, WSTLootModifier::new)));

    protected WSTLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext ctx) {
        Entity ent = ctx.getParamOrNull(LootContextParams.THIS_ENTITY);
        DamageSource src = ctx.getParamOrNull(LootContextParams.DAMAGE_SOURCE);
        if (src != null && ent != null) {
            if (src.typeHolder().is(DamageTypes.FIREWORKS) || hasSword(src)) {
                if (ent.getClass() == WitherSkeleton.class) {
                    if (generatedLoot.stream().noneMatch(i -> i.getItem() == Items.WITHER_SKELETON_SKULL)) generatedLoot.add(new ItemStack(Items.WITHER_SKELETON_SKULL));
                }
                else if (ent instanceof AbstractSkeleton) {
                    generatedLoot.add(new ItemStack(Items.SKELETON_SKULL));
                }
            }
        }

        if (ent != null && ent.getClass() == WitherSkeleton.class && ctx.getRandom().nextFloat() <= WSTConfig.shardDropChance) {
            if (generatedLoot.stream().noneMatch(i -> i.getItem() == Items.WITHER_SKELETON_SKULL)) {
                generatedLoot.add(new ItemStack(WitherSkeletonTweaks.fragment));
            }
        }
        return generatedLoot;
    }

    private static boolean hasSword(DamageSource source) {
        Entity s = source.getEntity();
        if (s instanceof LivingEntity) return ((LivingEntity) s).getMainHandItem().getItem() instanceof ItemImmolationBlade;
        else return false;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }

}
