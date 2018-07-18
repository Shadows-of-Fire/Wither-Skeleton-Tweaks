package shadows.wstweaks.core;

import net.minecraftforge.common.config.Configuration;
import shadows.wstweaks.WitherSkeletonTweaks;

public class WSTConfig {

	public static int shardValue = 9;
	public static boolean enableLava = true;
	public static boolean enableBlaze = true;
	public static int shardDropChance = 1;
	public static int extraWitherSkeletons = 0;
	public static boolean extraSpawns = false;
	public static boolean allowAllBiomes = false;
	public static boolean delSwords = true;
	public static int allBiomesChance = 1;
	public static boolean giveBows = true;

	public static void syncConfig() {

		Configuration config = WitherSkeletonTweaks.CONFIG;

		config.load();

		shardValue = config.getInt("Shard Value", Configuration.CATEGORY_GENERAL, 9, 1, 9, "The value of a skull fragment, in terms of 1/n (How many shards per skull) Valid values are 1-9");
		enableLava = config.getBoolean("Enable Lava Immolation Blade", Configuration.CATEGORY_GENERAL, true, "If the Immolation Blade (Lava) is enabled");
		enableBlaze = config.getBoolean("Enable Blaze Immolation Blade", Configuration.CATEGORY_GENERAL, true, "If the Immolation Blade (Blaze) is enabled");
		shardDropChance = config.getInt("Fragment Drop Chance", Configuration.CATEGORY_GENERAL, 1, 0, Integer.MAX_VALUE, "The wither skull fragment drop chance in 1/n.  Higher numbers means less drops. Set to 0 to disable.");
		extraWitherSkeletons = config.getInt("Extra Wither Skeletons", Configuration.CATEGORY_GENERAL, 0, 0, 10, "How many extra wither skeletons are spawned when one would be.");
		extraSpawns = config.getBoolean("Extra Spawns", Configuration.CATEGORY_GENERAL, false, "If, when a blaze or pigman spawns on Nether Brick, a wither skeleton spawns with it.");
		allowAllBiomes = config.getBoolean("All Biomes", Configuration.CATEGORY_GENERAL, false, "If ALL skeletons in any Biome are turned into Wither Skeletons.");
		allBiomesChance = config.getInt("All Biomes Chance", Configuration.CATEGORY_GENERAL, 1, 1, Integer.MAX_VALUE, "The 1/n chance for (if \"All Biomes\" is true) a skeleton in a non-Hell biome to be a Wither Skeleton");
		delSwords = config.getBoolean("Delete Stone Swords", Configuration.CATEGORY_GENERAL, true, "Whether or not to remove stone swords from wither skeleton drops.");
		giveBows = config.getBoolean("Give Bows", Configuration.CATEGORY_GENERAL, true, "If converted wither skeletons are given bows.");

		if (config.hasChanged()) config.save();

	}

}
