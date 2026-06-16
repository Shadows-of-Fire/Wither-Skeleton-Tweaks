package dev.shadowsoffire.wstweaks;

import java.util.List;
import java.util.Optional;

import dev.shadowsoffire.placebo.config.Configuration;
import dev.shadowsoffire.placebo.network.PayloadProvider;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class WSTConfig {

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
        shardDropChance = cfg.getFloat("Shard Drop Chance", "general", 1.0F, 0, 1, "The chance for a skull shard to drop. 1 = 100%, 0.5 = 50%, etc\nServer-authoritative.");
        allBiomes = cfg.getBoolean("Convert All Biomes", "general", false, "If skeletons in ALL biomes are converted, instead of just the nether.\nServer-authoritative.");
        allBiomesChance = cfg.getFloat("All Biomes Chance", "general", 0.15F, 0, 1, "The chance for skeletons to be converted in all biomes, when enabled. 1 = 100%, 0.5 = 50%, etc\nServer-authoritative.");
        delSwords = cfg.getBoolean("Delete Swords", "general", true, "If stone swords and other trash are removed from wither skeleton drop tables.\nServer-authoritative.");
        giveBows = cfg.getBoolean("Give Bows", "general", true, "If converted skeletons receive bows (Wither Skeletons always shoot flaming arrows).\nServer-authoritative.");
        swordDurability = cfg.getInt("Durability", "blades", 4096, 1, 65536, "The durability of immolation blades.\nSynced.");
        swordDamage = cfg.getFloat("Attack Damage", "blades", 11, 1, 4096, "The attack damage of immolation blades. This is a modifier, so the real value is always 1 higher.\nSynced.");
        swordAtkSpeed = cfg.getFloat("Attack Speed", "blades", -2F, -4096, 4096, "The attack speed of immolation blades. This is a modifier, so the real value is a bit different.\nSynced.");
        if (cfg.hasChanged()) cfg.save();
    }

    public static record ConfigPayload(float swordDamage, float swordAtkSpeed, int swordDurability) implements CustomPacketPayload {

        public static final Type<ConfigPayload> TYPE = new Type<>(WitherSkeletonTweaks.loc("config"));

        public static final StreamCodec<FriendlyByteBuf, ConfigPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, ConfigPayload::swordDamage,
            ByteBufCodecs.FLOAT, ConfigPayload::swordAtkSpeed,
            ByteBufCodecs.VAR_INT, ConfigPayload::swordDurability,
            ConfigPayload::new);

        public ConfigPayload() {
            this(WSTConfig.swordDamage, WSTConfig.swordAtkSpeed, WSTConfig.swordDurability);
        }

        @Override
        public Type<ConfigPayload> type() {
            return TYPE;
        }

        public static class Provider implements PayloadProvider<ConfigPayload> {

            @Override
            public Type<ConfigPayload> getType() {
                return TYPE;
            }

            @Override
            public StreamCodec<? super RegistryFriendlyByteBuf, ConfigPayload> getCodec() {
                return CODEC;
            }

            @Override
            public void handleClient(ConfigPayload msg, IPayloadContext ctx) {
                WSTConfig.swordDamage = msg.swordDamage;
                WSTConfig.swordAtkSpeed = msg.swordAtkSpeed;
                WSTConfig.swordDurability = msg.swordDurability;
            }

            @Override
            public List<ConnectionProtocol> getSupportedProtocols() {
                return List.of(ConnectionProtocol.PLAY);
            }

            @Override
            public Optional<PacketFlow> getFlow() {
                return Optional.of(PacketFlow.CLIENTBOUND);
            }

            @Override
            public String getVersion() {
                return "1";
            }

        }

    }

}
