package com.project.pufferfish.utils;

import com.project.pufferfish.gui.GameBoard;
import com.project.pufferfish.gui.testGuiMainMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import javax.annotation.Nullable;

public class KeyPressHandler {

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        World world = player.world;
        TileEntityArcade tileEntity = new TileEntityArcade();

        if (Minecraft.getMinecraft().gameSettings.keyBindSneak.isPressed()){ //when the shift key is pressed, it will open the GUI
            Minecraft.getMinecraft().displayGuiScreen(new GameBoard(world, tileEntity, null, player)); //Opening the GUI
            return;
        }
    }

}
