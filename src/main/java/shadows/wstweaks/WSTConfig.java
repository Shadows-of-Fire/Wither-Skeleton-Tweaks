package shadows.wstweaks;

import dev.shadowsoffire.placebo.config.Configuration;

public class WSTConfig {

    public static int shardValue;
    public static float shardDropChance;
    public static boolean allBiomes;
    public static float allBiomesChance;
    public static boolean delSwords;
    public static boolean giveBows;
    public static int swordDurability;
    public static float swordDamage;
    public static float swordAtkSpeed;

    public static void load() {
        Configuration cfg = new Configuration(WitherSkeletonTweaks.MODID);
        shardValue = cfg.getInt("Shard Value", "general", 9, 1, 9, "How many shards it takes to craft a Wither Skeleton Skull.");
        shardDropChance = cfg.getFloat("Shard Drop Chance", "general", 1.0F, 0, 1, "The chance for a skull shard to drop. 1 = 100%, 0.5 = 50%, etc");
        allBiomes = cfg.getBoolean("Convert All Biomes", "general", false, "If skeletons in ALL biomes are converted, instead of just the nether.");
        allBiomesChance = cfg.getFloat("All Biomes Chance", "general", 0.15F, 0, 1, "The chance for skeletons to be converted in all biomes, when enabled. 1 = 100%, 0.5 = 50%, etc");
        delSwords = cfg.getBoolean("Delete Swords", "general", true, "If stone swords and other trash are removed from wither skeleton drop tables.");
        giveBows = cfg.getBoolean("Give Bows", "general", true, "If converted skeletons receive bows (Wither Skeletons always shoot flaming arrows).");
        swordDurability = cfg.getInt("Durability", "blades", 4096, 1, 65536, "[Unsynced]\n[Requires Restart]\nThe durability of immolation blades.");
        swordDamage = cfg.getFloat("Attack Damage", "blades", 11, 1, 4096, "[Unsynced]\n[Requires Restart]\nThe attack damage of immolation blades. This is a modifier, so the real value is always 1 higher.");
        swordAtkSpeed = cfg.getFloat("Attack Speed", "blades", -2F, -4096, 4096, "[Unsynced]\n[Requires Restart]\nThe attack speed of immolation blades. This is a modifier, so the real value is a bit different.");
        if (cfg.hasChanged()) cfg.save();
    }

}
