package shadows.wstweaks;

import java.util.List;

import com.google.gson.JsonObject;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

public class WSTLootModifier extends LootModifier {

	protected WSTLootModifier(ILootCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext ctx) {
		Entity ent = ctx.getParamOrNull(LootParameters.THIS_ENTITY);
		DamageSource src = ctx.getParamOrNull(LootParameters.DAMAGE_SOURCE);
		if (src != null && ent != null) {
			if (src.msgId.equals("fireworks") || hasSword(src)) {
				if (ent.getClass() == WitherSkeletonEntity.class) {
					if (generatedLoot.stream().noneMatch(i -> i.getItem() == Items.WITHER_SKELETON_SKULL)) generatedLoot.add(new ItemStack(Items.WITHER_SKELETON_SKULL));
				} else if (ent instanceof AbstractSkeletonEntity) {
					generatedLoot.add(new ItemStack(Items.SKELETON_SKULL));
				}
			}
		}

		int chance = WSTConfig.INSTANCE.shardDropChance.get();
		if (ent != null && ent.getClass() == WitherSkeletonEntity.class && chance > 0 && ctx.getRandom().nextInt(chance) == 0) {
			if (generatedLoot.stream().noneMatch(i -> i.getItem() == Items.WITHER_SKELETON_SKULL)) {
				generatedLoot.add(new ItemStack(WitherSkeletonTweaks.FRAGMENT));
			}
		}
		return generatedLoot;
	}

	private static boolean hasSword(DamageSource source) {
		Entity s = source.getEntity();
		if (s instanceof LivingEntity) return ((LivingEntity) s).getMainHandItem().getItem() instanceof ItemImmolationBlade;
		else return false;
	}

	public static class Serializer extends GlobalLootModifierSerializer<WSTLootModifier> {

		@Override
		public WSTLootModifier read(ResourceLocation location, JsonObject object, ILootCondition[] conditions) {
			return new WSTLootModifier(conditions);
		}

		@Override
		public JsonObject write(WSTLootModifier instance) {
			return this.makeConditions(instance.conditions);
		}
	}

}
