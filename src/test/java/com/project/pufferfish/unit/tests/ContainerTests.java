package com.project.pufferfish.unit.tests;

import com.project.pufferfish.container.ModContainers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContainerTests
{
    @Test
    public void arcadeMachineContainerTest()
    {
        assertNotNull(ModContainers.ARCADE_MACHINE_CONTAINER);
        assertEquals("pufferfish:arcade_machine_container", ModContainers.ARCADE_MACHINE_CONTAINER.getId().toString());
        assertTrue(ModContainers.ARCADE_MACHINE_CONTAINER.isPresent());

    }
    
}