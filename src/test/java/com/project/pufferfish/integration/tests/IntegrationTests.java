package com.project.pufferfish.integration.tests;

import com.project.pufferfish.block.ModBlocks;
import com.project.pufferfish.item.ModItems;

import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import com.alcatrazescapee.mcjunitlib.framework.IntegrationTest;
import com.alcatrazescapee.mcjunitlib.framework.IntegrationTestClass;
import com.alcatrazescapee.mcjunitlib.framework.IntegrationTestHelper;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Hand;


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
	 
}
