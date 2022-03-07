package com.project.pufferfish.unit.tests;

import com.project.pufferfish.block.ModBlocks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BlockTests
{
    @Test
    public void arcadeMachineBlockTest()
    {
        assertNotNull(ModBlocks.ARCADE_MACHINE);
        assertEquals("Block{pufferfish:arcade_machine}", ModBlocks.ARCADE_MACHINE.get().toString());

    }
    
}