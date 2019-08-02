package shadows.wstweaks;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class WSTConfig {

	public static final ForgeConfigSpec SPEC;
	public static final WSTConfig INSTANCE;
	static {
		Pair<WSTConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(WSTConfig::new);
		SPEC = specPair.getRight();
		INSTANCE = specPair.getLeft();
	}

	public final IntValue shardValue;
	public final IntValue shardDropChance;
	public final BooleanValue allowAllBiomes;
	public final IntValue allBiomesChance;
	public final BooleanValue delSwords;
	public final BooleanValue giveBows;

	public WSTConfig(ForgeConfigSpec.Builder build) {
		build.comment("Server configuration");
		build.push("server");
		shardValue = build.comment("How many fragments are required to make a skull.").defineInRange("fragvalue", 9, 1, 9);
		shardDropChance = build.comment("The 1/n chance for a wither skeleton to drop a fragment.").defineInRange("fragchance", 1, 1, 32767);
		allowAllBiomes = build.comment("If skeletons outside of hell can be transformed into wither skeletons.").define("allbiomes", false);
		allBiomesChance = build.comment("The 1/n chance for wither skeletons outside hell to be transformed.  Requires allbiomes == true.").defineInRange("allbiomechance", 1, 1, 32767);
		delSwords = build.comment("If stone swords that would be dropped are deleted.").define("delswords", true);
		giveBows = build.comment("If transformed skeletons are given bows instead of stone swords.").define("givebows", true);
		build.pop();
	}

}
