package shadows.wstweaks;

import net.minecraftforge.common.config.Configuration;

public class WSTConfig {

	public static int shardValue = 9;
	public static int shardDropChance = 1;
	public static boolean allowAllBiomes = false;
	public static boolean delSwords = true;
	public static int allBiomesChance = 1;
	public static boolean giveBows = true;
	public static float immolationDmg = 12F;

	public static void syncConfig(Configuration config) {
		shardValue = config.getInt("Shard Value", Configuration.CATEGORY_GENERAL, 9, 1, 9, "The value of a skull fragment, in terms of 1/n (How many shards per skull) Valid values are 1-9");
		shardDropChance = config.getInt("Fragment Drop Chance", Configuration.CATEGORY_GENERAL, 1, 0, Integer.MAX_VALUE, "The wither skull fragment drop chance in 1/n.  Higher numbers means less drops. Set to 0 to disable.");
		allowAllBiomes = config.getBoolean("All Biomes", Configuration.CATEGORY_GENERAL, false, "If ALL skeletons in any Biome are turned into Wither Skeletons.");
		allBiomesChance = config.getInt("All Biomes Chance", Configuration.CATEGORY_GENERAL, 1, 1, Integer.MAX_VALUE, "The 1/n chance for (if \"All Biomes\" is true) a skeleton in a non-Hell biome to be a Wither Skeleton");
		delSwords = config.getBoolean("Delete Stone Swords", Configuration.CATEGORY_GENERAL, true, "Whether or not to remove stone swords from wither skeleton drops.");
		giveBows = config.getBoolean("Give Bows", Configuration.CATEGORY_GENERAL, true, "If converted wither skeletons are given bows.");
		immolationDmg = config.getFloat("Immolation Damage", Configuration.CATEGORY_GENERAL, immolationDmg, 1f, 1500f, "The damage of the immolation blade.  The actual value will be higher than this.");
		if (config.hasChanged()) config.save();
	}

}
