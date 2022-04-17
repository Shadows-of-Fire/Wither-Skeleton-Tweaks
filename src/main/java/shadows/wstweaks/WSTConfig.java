package shadows.wstweaks;

import shadows.placebo.config.Configuration;

public class WSTConfig {

	public static int shardValue = 9;
	public static float shardDropChance = 1.0F;
	public static boolean allBiomes = false;
	public static float allBiomesChance = 1.0F;
	public static boolean delSwords = true;
	public static boolean giveBows = true;
	public static int swordDurability = 4096;
	public static float swordDamage = 12;
	public static float swordAtkSpeed = -2F;

	public static void load() {
		Configuration cfg = new Configuration(WitherSkeletonTweaks.MODID);
		shardValue = cfg.getInt("Shard Value", "general", shardValue, 1, 9, "How many shards it takes to craft a Wither Skeleton Skull.");
		shardDropChance = cfg.getFloat("Shard Drop Chance", "general", shardDropChance, 0, 1, "The chance for a skull shard to drop. 1 = 100%, 0.5 = 50%, etc");
		allBiomes = cfg.getBoolean("Convert All Biomes", "general", allBiomes, "If skeletons in ALL biomes are converted, instead of just the nether.");
		allBiomesChance = cfg.getFloat("All Biomes Chance", "general", allBiomesChance, 0, 1, "The chance for skeletons to be converted in all biomes, when enabled. 1 = 100%, 0.5 = 50%, etc");
		delSwords = cfg.getBoolean("Delete Swords", "general", delSwords, "If stone swords and other trash are removed from wither skeleton drop tables.");
		giveBows = cfg.getBoolean("Give Bows", "general", giveBows, "If converted skeletons receive bows (Wither Skeletons always shoot flaming arrows).");
		swordDurability = cfg.getInt("Durability", "blades", swordDurability, 1, 65536, "The durability of immolation blades.");
		swordDamage = cfg.getFloat("Attack Damage", "blades", swordDamage, 1, 4096, "The attack damage of immolation blades. This is a modifier, so the real value is always 1 higher.");
		swordAtkSpeed = cfg.getFloat("Attack Speed", "blades", swordAtkSpeed, -4096, 4096, "The attack speed of immolation blades. This is a modifier, so the real value is a bit different.");
		if (cfg.hasChanged()) cfg.save();
	}

}
