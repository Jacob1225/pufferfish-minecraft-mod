package com.project.pufferfish.gui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class testGuiMainMenu extends GuiScreen{

    private final ResourceLocation background = new ResourceLocation("textures/gui/generic_gui.png");

    private final int xsize = 248;
    private final int ysize = 164;
    private final BlockPos pos;
    private final World world;
    private final EntityPlayer player;

    private int guiLeft;
    private int guiTop;

    private Minecraft minecraft;

    public testGuiMainMenu(World world, TileEntity tileEntity, @Nullable BlockPos pos, EntityPlayer player) {
        this.pos = pos;
        this.world = world;
        this.player = player;
        this.fontRenderer = mc.getMinecraft().fontRenderer;
    }

    public void initGui(){
        guiLeft = (this.width - this.xsize) / 2;
        guiTop = (this.height - this.ysize) / 2;
    }

    public void actionPerformed(GuiButton button){

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        drawBackgroundImage();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }


    public void drawBackgroundImage(){
        GlStateManager.pushMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        minecraft.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xsize, ysize);

        GlStateManager.popMatrix();
    }
}
