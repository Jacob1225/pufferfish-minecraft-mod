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
    public void testArcadeBreaksWithEmptyHand()
    {
        doTest(ModBlocks.ARCADE_MACHINE.get(), Items.AIR, false);
    }
	
	@Test
    public void testArcadeBreaksWithPickAxe()
    {
        doTest(ModBlocks.ARCADE_MACHINE.get(), Items.IRON_PICKAXE, true);
    }
	
	@Test
    public void testArcadeBreaksWithoutPickAxe()
    {
        doTest(ModBlocks.ARCADE_MACHINE.get(), Items.IRON_SWORD, false);
    }

    @Test
    public void testArcadeBreaksWoodenPickAxe()
    {
        doTest(ModBlocks.ARCADE_MACHINE.get(), Items.WOODEN_PICKAXE, false);
    }

    @Test
    public void testArcadeBreaksGoldenPickAxe()
    {
        doTest(ModBlocks.ARCADE_MACHINE.get(), Items.DIAMOND_PICKAXE, true);
    }
    
    @Test 
    public void testTrophyAttackStrengthDelay()
    
    {
       final PlayerEntity player = FakePlayerFactory.getMinecraft(ServerLifecycleHooks.getCurrentServer().overworld());
       final ItemStack stack = new ItemStack(ModItems.SPACE_INVADERS_TROPHY.get());
       player.setItemInHand(Hand.MAIN_HAND, stack);
       assertEquals(5.0, player.getCurrentItemAttackStrengthDelay(), "Expect 5.0 attack strength delay");
    }
    
    @Test 
    public void testTrophyMaxDamage()
    
    {
        final ItemStack stack = new ItemStack(ModItems.SPACE_INVADERS_TROPHY.get());

       assertEquals(25.0, ModItems.SPACE_INVADERS_TROPHY.get().getMaxDamage(stack), "Expect Trophy to be damaged");
    }
    
    @Test 
    public void testTrophyAttack()
    
    {
       final PlayerEntity player = FakePlayerFactory.getMinecraft(ServerLifecycleHooks.getCurrentServer().overworld());
       final PlayerEntity player2 = FakePlayerFactory.getMinecraft(ServerLifecycleHooks.getCurrentServer().overworld());
       final ItemStack stack = new ItemStack(ModItems.SPACE_INVADERS_TROPHY.get());

       player.setItemInHand(Hand.MAIN_HAND, stack);
       player.attack(player2);
       assertEquals(20.0, player2.getHealth(), "Expect Player2 to be 20 health");
    }
    
    @Test
    public void testTrophyAsWeapon()
    {
        doTest(ModBlocks.ARCADE_MACHINE.get(), Items.DIAMOND_PICKAXE, true);
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
