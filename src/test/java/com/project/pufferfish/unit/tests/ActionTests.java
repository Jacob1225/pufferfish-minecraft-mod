package com.project.pufferfish.unit.tests;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import com.project.pufferfish.block.ModBlocks;
import com.project.pufferfish.item.ModItems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionTests {
	
	@Test
    public void testUsingGameToken()
    {
        doTest(ModBlocks.ARCADE_MACHINE.get(), ModItems.GAME_TOKEN.get(), false);
    }
	
	@Test
    public void testArcadeBreaksWithEmptyHand()
    {
        doTest(ModBlocks.ARCADE_MACHINE.get(), Items.AIR, true);
    }
	
	private void doTest(Block block, Item item, boolean shouldHarvest)
    {
        doTest(block, item, shouldHarvest, "Block " + block.getRegistryName() + " with item " + item.getRegistryName() + " should harvest = " + shouldHarvest);
    }

    private void doTest(Block block, Item item, boolean shouldHarvest, String message)
    
    {
        final PlayerEntity player = FakePlayerFactory.getMinecraft(ServerLifecycleHooks.getCurrentServer().overworld());
        final ItemStack stack = new ItemStack(item);
        final BlockState state = block.defaultBlockState();

        player.setItemInHand(Hand.MAIN_HAND, stack);
        assertEquals(shouldHarvest, player.hasCorrectToolForDrops(state), message);
    }

}
