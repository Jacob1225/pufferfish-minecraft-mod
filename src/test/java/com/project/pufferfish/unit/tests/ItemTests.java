package com.project.pufferfish.unit.tests;

import com.project.pufferfish.item.ModItems;
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

}
