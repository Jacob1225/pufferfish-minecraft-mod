package com.project.pufferfish.unit.tests;

import com.project.pufferfish.item.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTests {

    // unit tests for registration of game token
    @Test
    public void gameTokenItemTest()
    {
        assertNotNull(ModItems.GAME_TOKEN);
        assertEquals("pufferfish:game_token", ModItems.GAME_TOKEN.getId().toString());
        assertTrue(ModItems.GAME_TOKEN.isPresent());
    }

    // unit tests for registration of prize ticket
    @Test
    public void prizeTicketItemTest()
    {
        assertNotNull(ModItems.PRIZE_TICKET);
        assertEquals("pufferfish:prize_ticket", ModItems.PRIZE_TICKET.getId().toString());
        assertTrue(ModItems.PRIZE_TICKET.isPresent());
    }
    
 // unit tests for registration of game trophy ticket
    @Test
    public void gameTrophyItemTest()
    {
        assertNotNull(ModItems.SPACE_INVADERS_TROPHY);
        assertEquals("pufferfish:space_invaders_trophy", ModItems.SPACE_INVADERS_TROPHY.getId().toString());
        assertTrue(ModItems.SPACE_INVADERS_TROPHY.isPresent());
    }
    
    // unit tests for registration of game trophy ticket
    @Test
    public void gameTrophyRarityTest()
    {
    	final ItemStack stack = new ItemStack(ModItems.SPACE_INVADERS_TROPHY.get());
        assertEquals("COMMON", ModItems.SPACE_INVADERS_TROPHY.get().getRarity(stack).toString());
    }

}
