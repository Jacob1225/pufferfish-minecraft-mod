package com.project.pufferfish.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.project.pufferfish.Invaders;
import com.project.pufferfish.screen.SpaceInvaders;
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
import java.util.ArrayList;
import java.util.List;

public class InvadersScreen extends Screen {
    private int delayTicker;

    // Gui background (black image)
    private static final ResourceLocation background = new ResourceLocation(Invaders.MOD_ID, "textures/gui/invaders_gui.png");
    
    private static final ResourceLocation invaderImage = new ResourceLocation(Invaders.MOD_ID, "textures/gui/invader2.png");

    //Gui variables
    public int textureWidth = 256, textureHeight = 266;
    private int xSize = 0, ySize = 0;
    private float scale = 1;

    //Invader variables
    public int invaderWidth=textureWidth/15, invaderHeight=textureHeight/15;
    static int InvaderRows=4;
    static int InvaderCols=6;
    ArrayList<SpaceInvaders> invaders;
    SpaceInvaders invader;
    static int NumberOfInvaders=24;
    
    //Constructor variables
    private PlayerEntity player;
    private World world;
    private BlockPos pos;

    public InvadersScreen(boolean p_i51118_2_, World world, TileEntity tileEntity, @Nullable BlockPos pos, PlayerEntity player) {
        super(new TranslationTextComponent(p_i51118_2_ ? "deathScreen.title.hardcore" : "deathScreen.title"));
        this.pos = pos;
        this.world = world;
        this.player = player;
        InvadersCreation();
        
    }
    public void InvadersCreation() {
    	invaders=new ArrayList<>();
    	
    	 for (int i = 0; i < InvaderRows; i++) {
             for (int j = 0; j < InvaderCols; j++) {

            	 SpaceInvaders invader=new SpaceInvaders(18 * j, 18 * i,(i+1)*10);
                 invaders.add(invader);
                 //this.drawModalRectWithCustomSizedTexture(GUI_X+18 * j , 18 * i , GUI_X+10, MAZE_Y, 15, 15, 512, 512);//position in the game, position in the image, width/height of the portion wanted, width/height of image 
                 //System.out.println("Invader number "+invader.invaderId+" was created");
//                 invader.invadersMove(invaders);
//                 drawModalRectWithCustomSizedTexture(invader.getxpos() , invader.getypos() , GUI_X+10, MAZE_Y, 15, 15, 512, 512);
             }
         }
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
        for (int i = 0; i < NumberOfInvaders; i++) {
        	this.minecraft.getTextureManager().bind(invaderImage);
        	this.blit(p_230430_1_, (this.width - textureWidth) /2 +invaders.get(i).getxpos()+invaderWidth, (this.height - textureHeight) / 2+invaders.get(i).getypos() +invaderHeight, 0,0, invaderWidth, invaderHeight, invaderWidth,invaderHeight);
        	//blit(x, y, this.blitOffset, (float) u, (float) v, width of image shown, height of image shown, x of imported image, y of imported image);
        	
        }
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        invaderMove();
    }

    public void invaderMove() {
    	boolean leftReached = false;
    	boolean rightReached = false; 

    	for (int i = 0; i < NumberOfInvaders; i++) {		 
    		invaders.get(i).invadersMove();

    		//If right border reached 
    		if( i == 23 && invaders.get(23).getxpos() >= (207)) { 
    			rightReached = true; 
    		}

    		//If left border reached 
    		if(i == 0 && invaders.get(0).getxpos() <= 0) { 
    			leftReached = true; 
    		}

    	}
    	if (rightReached == true) {
    		for (int j = 0; j < NumberOfInvaders; j++) {
    			invaders.get(j).setypos(invaders.get(j).getypos() + 10); 
    			invaders.get(j).movesRight = false; 
    		}
    	}

    	if (leftReached == true) {
    		for (int i = 0; i < NumberOfInvaders; i++) {
    			invaders.get(i).setypos(invaders.get(i).getypos() + 10);
    			invaders.get(i).movesRight = true;
    		}
    	}
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


