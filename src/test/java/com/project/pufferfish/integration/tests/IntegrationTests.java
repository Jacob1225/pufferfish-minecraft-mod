package com.project.pufferfish.integration.tests;

import com.project.pufferfish.block.ModBlocks;
import com.project.pufferfish.item.ModItems;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.alcatrazescapee.mcjunitlib.framework.IntegrationTest;
import com.alcatrazescapee.mcjunitlib.framework.IntegrationTestClass;
import com.alcatrazescapee.mcjunitlib.framework.IntegrationTestHelper;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.block.Block;
import net.minecraft.util.Hand;
import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.util.math.BlockPos;
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


@IntegrationTestClass("arcade_machine_tests")
public class IntegrationTests {
	
	 @IntegrationTest("arcade-machine")
	 public void testArcadeBlockIsPlaced(IntegrationTestHelper helper)
	    {	
		 	//places the arcade machine down and asserts that the block is placed.
	        helper.placeBlock(new BlockPos(0, 1, 0), Direction.UP, ModBlocks.ARCADE_MACHINE.get());
            helper.assertBlockAt(new BlockPos(0, 1, 0), ModBlocks.ARCADE_MACHINE.get(), "Arcade machine should be placed");
	    }
	 
	 @IntegrationTest("arcade-machine")
	 public void testArcadeBlockBreaks(IntegrationTestHelper helper)
	    {	
		 	//places the arcade machine down and asserts that the block is placed.
		 	helper.pushButton(new BlockPos(0, 1, 0));
		 	helper.assertAirAt(new BlockPos(0, 1, 0), "Arcade machine should break on hit");
	    }
	 
	 @IntegrationTest("arcade-machine")
	 public void testPrizeToken(IntegrationTestHelper helper)
	    {	
		 	helper.useItem(new BlockPos(0, 1, 0), Direction.UP, ModItems.GAME_TOKEN.get());
		 	helper.assertAirAt(new BlockPos(0, 1, 0), "Using arcade game token");
	    }
	 
	 
	 @IntegrationTest("arcade-machine")
	 public void testDestroyArcadeMachine(IntegrationTestHelper helper)
	    {	
		 	helper.destroyBlock(new BlockPos(0, 1, 0));
		 	helper.assertAirAt(new BlockPos(0, 1, 0), "Arcade machine should not be there");
		}
	 
	 @IntegrationTest("arcade-machine")
	 public void testGameTrophyDurability(IntegrationTestHelper helper)
	    {	
	       	final PlayerEntity player = FakePlayerFactory.getMinecraft(ServerLifecycleHooks.getCurrentServer().overworld());
	     	
		 	helper.useItem(new BlockPos(0, 1, 0), Direction.UP, ModItems.SPACE_INVADERS_TROPHY.get());
		 	
		 	final ItemStack stack = new ItemStack(ModItems.SPACE_INVADERS_TROPHY.get());
	        player.setItemInHand(Hand.MAIN_HAND, stack);
        
	        stack.getItem().canContinueUsing(stack, new ItemStack(ModItems.SPACE_INVADERS_TROPHY.get()));
	        for (int i=0; i < 30; i++) {
			 	helper.useItem(new BlockPos(0, 1, 0), Direction.UP, ModItems.SPACE_INVADERS_TROPHY.get());

	        }
	        assertEquals(false, stack.isEmpty());

        }

	 
}
