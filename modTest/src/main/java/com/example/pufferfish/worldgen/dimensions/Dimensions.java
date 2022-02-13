package com.example.pufferfish.worldgen.dimensions;

import com.example.pufferfish.PufferFish;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class Dimensions {

    public static final ResourceKey<Level> MYSTERIOUS = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(PufferFish.MODID, "mysterious"));

    public static void register() {
        Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(PufferFish.MODID, "mysterious_chunkgen"),
                MysteriousChunkGenerator.CODEC);
        Registry.register(Registry.BIOME_SOURCE, new ResourceLocation(PufferFish.MODID, "biomes"),
                MysteriousBiomeProvider.CODEC);
    }
}
