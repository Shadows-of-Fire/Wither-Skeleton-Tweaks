package shadows.wstweaks.core;

import net.minecraftforge.common.config.Configuration;

public class ConfigFile {

	public static int shardValue = 9;
	public static boolean enableLava = true;
	public static boolean enableBlaze = true;
	public static int shardDropChance = 1;
	public static int extraWitherSkeletons = 0;
	public static boolean extraSpawns = false;
	public static boolean allowAllBiomes = false;
	public static boolean delSwords = true;
	public static int allBiomesChance = 1;

	public static void syncConfig(Configuration config) {

		config.load();

		shardValue = config.getInt(Configuration.CATEGORY_GENERAL, "Shard Value", 9, 1, 9, "The value of a skull fragment, in terms of 1/n (How many shards per skull) Valid values are 1-9");
		enableLava = config.getBoolean(Configuration.CATEGORY_GENERAL, "Enable Lava Immolation Blade", true, "If the Immolation Blade (Lava) is enabled");
		enableBlaze = config.getBoolean(Configuration.CATEGORY_GENERAL, "Enable Blaze Immolation Blade", true, "If the Immolation Blade (Blaze) is enabled");
		shardDropChance = config.getInt(Configuration.CATEGORY_GENERAL, "Fragment Drop Chance", 1, 0, Integer.MAX_VALUE, "The wither skull fragment drop chance in 1/n.  Higher numbers means less drops. Set to 0 to disable.");
		extraWitherSkeletons = config.getInt(Configuration.CATEGORY_GENERAL, "Extra Wither Skeletons", 0, 0, 10, "How many extra wither skeletons are spawned when one would be.");
		extraSpawns = config.getBoolean(Configuration.CATEGORY_GENERAL, "Extra Spawns", false, "If, when a blaze or pigman spawns on Nether Brick, a wither skeleton spawns with it.");
		allowAllBiomes = config.getBoolean(Configuration.CATEGORY_GENERAL, "All Biomes", false, "If ALL skeletons in any Biome are turned into Wither Skeletons.");
		allBiomesChance = config.getInt(Configuration.CATEGORY_GENERAL, "All Biomes Chance", 1, 1, Integer.MAX_VALUE, "The 1/n chance for (if \"All Biomes\" is true) a skeleton in a non-Hell biome to be a Wither Skeleton");
		delSwords = config.getBoolean(Configuration.CATEGORY_GENERAL, "Delete Stone Swords", true, "Whether or not to remove stone swords from wither skeleton drops.");

		if (config.hasChanged()) config.save();

	}

}
