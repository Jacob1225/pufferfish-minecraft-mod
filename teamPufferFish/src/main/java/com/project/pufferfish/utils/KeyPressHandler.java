package com.project.pufferfish.utils;

import com.project.pufferfish.gui.GameBoard;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyPressHandler {

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        World world = player.world;
        TileEntityArcade tileEntity = new TileEntityArcade();

        if (Minecraft.getMinecraft().gameSettings.keyBindSneak.isPressed()){
            System.out.println("FUCKFUCKFUCK");
            Minecraft.getMinecraft().displayGuiScreen(new GameBoard(world, tileEntity, null, player));
            return;
        }
    }

 
}
