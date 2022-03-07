package com.project.pufferfish.unit.tests;

import com.project.pufferfish.tileentity.ModTileEntities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TileEntityTests {

    // unit tests for registration of arcade machine tile entity
    @Test
    public void arcadeMachineTileTest()
    {
        assertNotNull(ModTileEntities.ARCADE_MACHINE_TILE);
        assertEquals("pufferfish:arcade_machine_tile", ModTileEntities.ARCADE_MACHINE_TILE.getId().toString());
        assertTrue(ModTileEntities.ARCADE_MACHINE_TILE.isPresent());
    }
}
