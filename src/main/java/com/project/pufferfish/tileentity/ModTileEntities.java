package com.project.pufferfish.tileentity;

import com.project.pufferfish.Invaders;
import com.project.pufferfish.block.ModBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {

    public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Invaders.MOD_ID);

    public static RegistryObject<TileEntityType<ArcadeMachineTile>> ARCADE_MACHINE_TILE =
            TILE_ENTITIES.register("arcade_machine_tile", () -> TileEntityType.Builder.of(
                    ArcadeMachineTile::new, ModBlocks.ARCADE_MACHINE.get()).build(null));

    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }
}
