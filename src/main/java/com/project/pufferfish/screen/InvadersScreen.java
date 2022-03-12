package com.project.pufferfish.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.project.pufferfish.Invaders;
import com.project.pufferfish.tileentity.InvadersTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.IOException;

public class InvadersScreen extends Screen {
    private int delayTicker;

    // Gui background (black image)
    private static final ResourceLocation background = new ResourceLocation(Invaders.MOD_ID, "textures/gui/invaders_gui.png");

    //Gui variables
    public int textureWidth = 256, textureHeight = 266;
    private int xSize = 0, ySize = 0;
    private float scale = 1;

    //Constructor variables
    private PlayerEntity player;
    private World world;
    private BlockPos pos;

    public InvadersScreen(boolean p_i51118_2_, World world, TileEntity tileEntity, @Nullable BlockPos pos, PlayerEntity player) {
        super(new TranslationTextComponent(p_i51118_2_ ? "deathScreen.title.hardcore" : "deathScreen.title"));
        this.pos = pos;
        this.world = world;
        this.player = player;
    }

    protected void init() {
        this.delayTicker = 0;
    }

    public boolean shouldCloseOnEsc() {
        return true;
    }


    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        int relX = (this.width - textureWidth) / 2;
        int relY = (this.height - textureHeight) / 2;
        assert this.minecraft != null;
        this.minecraft.getTextureManager().bind(background);
        this.blit(p_230430_1_, relX, relY, 0, 0, textureWidth, textureHeight);

        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
    }


    public boolean isPauseScreen() {
        return false;
    }

    public void tick() {
        super.tick();
        ++this.delayTicker;
        if (this.delayTicker == 20) {
            for (Widget widget : this.buttons) {
                widget.active = true;
            }
        }
    }
}


