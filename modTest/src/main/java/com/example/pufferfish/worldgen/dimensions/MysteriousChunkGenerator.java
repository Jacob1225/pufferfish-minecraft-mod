package com.example.pufferfish.worldgen.dimensions;

import com.example.pufferfish.PufferFish;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.server.dedicated.Settings;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class MysteriousChunkGenerator extends ChunkGenerator {

    private static final Codec<Settings> SETTINGS_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("base").forGetter(Settings::baseHeight),
                    Codec.FLOAT.fieldOf("verticalvariance").forGetter(Settings::verticalVariance),
                    Codec.FLOAT.fieldOf("horizontalvariance").forGetter(Settings::horizontalVariance)
            ).apply(instance, Settings::new));

    public static final Codec<MysteriousChunkGenerator> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter(MysteriousChunkGenerator::getBiomeRegistry),
                    SETTINGS_CODEC.fieldOf("settings").forGetter(MysteriousChunkGenerator::getTutorialSettings)
            ).apply(instance, MysteriousChunkGenerator::new));

    private final Settings settings;

    public MysteriousChunkGenerator(Registry<Biome> registry, Settings settings) {
        super(new MysteriousBiomeProvider(registry), new StructureSettings(false));
        this.settings = settings;
        PufferFish.LOGGER.info("Chunk generator settings: " + settings.baseHeight() + ", " + settings.horizontalVariance() + ", " + settings.verticalVariance());
    }

    public Settings getTutorialSettings() {
        return settings;
    }

    public Registry<Biome> getBiomeRegistry() {
        return ((MysteriousBiomeProvider)biomeSource).getBiomeRegistry();
    }

    @Override
    public void buildSurface(WorldGenRegion region, StructureFeatureManager featureManager, ChunkAccess chunk) {
        BlockState bedrock = Blocks.BEDROCK.defaultBlockState();
        BlockState stone = Blocks.STONE.defaultBlockState();
        ChunkPos chunkpos = chunk.getPos();

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        int x;
        int z;

        for (x = 0; x < 16; x++) {
            for (z = 0; z < 16; z++) {
                chunk.setBlockState(pos.set(x, 0, z), bedrock, false);
            }
        }

        int baseHeight = settings.baseHeight();
        float verticalVariance = settings.verticalVariance();
        float horizontalVariance = settings.horizontalVariance();
        for (x = 0; x < 16; x++) {
            for (z = 0; z < 16; z++) {
                int realx = chunkpos.x * 16 + x;
                int realz = chunkpos.z * 16 + z;
                int height = getHeightAt(baseHeight, verticalVariance, horizontalVariance, realx, realz);
                for (int y = 1 ; y < height ; y++) {
                    chunk.setBlockState(pos.set(x, y, z), stone, false);
                }
            }
        }
    }

    private int getHeightAt(int baseHeight, float verticalVariance, float horizontalVariance, int x, int z) {
        return (int) (baseHeight + Math.sin(x / horizontalVariance) * verticalVariance + Math.cos(z / horizontalVariance) * verticalVariance);
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long seed) {
        return new MysteriousChunkGenerator(getBiomeRegistry(), settings);
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender blender, StructureFeatureManager featureManager, ChunkAccess chunkAccess) {
        return CompletableFuture.completedFuture(chunkAccess);
    }

    // Make sure this is correctly implemented so that structures and features can use this
    @Override
    public int getBaseHeight(int x, int z, Heightmap.Types types, LevelHeightAccessor levelHeightAccessor) {
        int baseHeight = settings.baseHeight();
        float verticalVariance = settings.verticalVariance();
        float horizontalVariance = settings.horizontalVariance();
        return getHeightAt(baseHeight, verticalVariance, horizontalVariance, x, z);
    }

    // Make sure this is correctly implemented so that structures and features can use this
    @Override
    public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor levelHeightAccessor) {
        int y = getBaseHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, levelHeightAccessor);
        BlockState stone = Blocks.STONE.defaultBlockState();
        BlockState[] states = new BlockState[y];
        states[0] = Blocks.BEDROCK.defaultBlockState();
        for (int i = 1 ; i < y ; i++) {
            states[i] = stone;
        }
        return new NoiseColumn(levelHeightAccessor.getMinBuildHeight(), states);
    }

    // Carvers only work correctly in combination with NoiseBasedChunkGenerator so we keep this empty here
    @Override
    public void applyCarvers(WorldGenRegion level, long seed, BiomeManager biomeManager,
                             StructureFeatureManager featureManager, ChunkAccess chunkAccess, GenerationStep.Carving carving) {
    }

    @Override
    public Climate.Sampler climateSampler() {
        return (x, y, z) -> Climate.target(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    }

    // This makes sure passive mob spawning works for generated chunks. i.e. mobs that spawn during the creation of chunks themselves
    @Override
    public void spawnOriginalMobs(WorldGenRegion level) {
        ChunkPos chunkpos = level.getCenter();
        Biome biome = level.getBiome(chunkpos.getWorldPosition().atY(level.getMaxBuildHeight() - 1));
        WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(RandomSupport.seedUniquifier()));
        worldgenrandom.setDecorationSeed(level.getSeed(), chunkpos.getMinBlockX(), chunkpos.getMinBlockZ());
        NaturalSpawner.spawnMobsForChunkGeneration(level, biome, chunkpos, worldgenrandom);
    }

    @Override
    public int getMinY() {
        return 0;
    }

    @Override
    public int getGenDepth() {
        return 256;
    }

    @Override
    public int getSeaLevel() {
        return 63;
    }

    private record Settings(int baseHeight, float verticalVariance, float horizontalVariance) { }
}
