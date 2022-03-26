package com.project.pufferfish.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.project.pufferfish.Invaders;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;



/**
 * This is the main class for the GUI screen to play the game. It also contains the "score" for the game
 */
public class InvadersScreen extends Screen {
    private int delayTicker;

    // Gui background (black image)
    private static final ResourceLocation gametitle = new ResourceLocation(Invaders.MOD_ID, "textures/gui/game-start2.jpeg");
    private static final ResourceLocation gameover = new ResourceLocation(Invaders.MOD_ID, "textures/gui/game-over.jpeg");
    private static final ResourceLocation background = new ResourceLocation(Invaders.MOD_ID, "textures/gui/invaders_gui.png");
    private static final ResourceLocation playerImage = new ResourceLocation(Invaders.MOD_ID, "textures/gui/player.png");
    private static final ResourceLocation shotImage = new ResourceLocation(Invaders.MOD_ID, "textures/gui/shot.png");
    private static final ResourceLocation invaderImage = new ResourceLocation(Invaders.MOD_ID, "textures/gui/invader2.png");

    //Gui variables
    public int textureWidth = 256, textureHeight = 266;
    private int xSize = 0, ySize = 0;
    private float scale = 1;
    int relX = 0;
    int relY = 0;

    
    //Player variables
    public int playerWidth=15, playerHeight=15;
    Player tank = new  Player (textureWidth/2-playerWidth/2,textureHeight-(2*playerHeight));
    
   
    Player shot = new  Player (textureWidth/2-playerWidth/2,textureHeight-(2*playerHeight));
    
    //Display score variables
    MatrixStack matrixStack;
    private int score;

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
    private int gamePlay;

    /**
     * Constructor class for Gui screen
     *
     * @param p_i51118_2_
     * @param world
     * @param tileEntity
     * @param pos
     * @param player
     */
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

            	 SpaceInvaders invader=new SpaceInvaders(18 * j, 70+18 * i,(i+1)*10);
                 invaders.add(invader);
             }
         }
	}

    /**
     * initilizing screen
     */
    protected void init() {

        this.delayTicker = 0;
        this.gamePlay = 0;
    }

    /**
     * Closes gui screen when esc key is pressed
     *
     * @return
     */
    public boolean shouldCloseOnEsc() {
        return true;
    }

    /**
     * Draw the gui screen and display score
     *
     * @param p_230430_1_
     * @param p_230430_2_
     * @param p_230430_3_
     * @param p_230430_4_
     */
    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.matrixStack = p_230430_1_;
        relX = (this.width - textureWidth) / 2;
        relY = (this.height - textureHeight) / 2;
        assert this.minecraft != null;

        //title screen
        if(gamePlay == 0){
            this.minecraft.getTextureManager().bind(gametitle);
            this.blit(p_230430_1_, relX, relY, 0, 0, textureWidth, textureHeight);
            drawString(p_230430_1_, this.font, new TranslationTextComponent("Press 's' to start").withStyle(TextFormatting.WHITE), 170, 170, 16777215);
        }
        //game play
        if(gamePlay == 1){
            this.minecraft.getTextureManager().bind(background);
            this.blit(p_230430_1_, relX, relY, 0, 0, textureWidth, textureHeight);

            this.minecraft.getTextureManager().bind(playerImage);
            this.blit(p_230430_1_, relX+tank.getxpos(), relY+tank.getypos(),0,0,playerWidth,playerHeight,playerWidth,playerHeight);

            if (shot.movesUp) {  //display player shot only when space bar is pressed
                shot.moveShot();
                this.minecraft.getTextureManager().bind(shotImage);
                this.blit(p_230430_1_, relX+shot.getxpos(), relY+shot.getypos(),0,0,playerWidth,playerHeight,playerWidth,playerHeight);
                if (shot.getypos()<10) {
                    shot.movesUp=false;
                }
            }

            for (int i = 0; i < NumberOfInvaders; i++) {
                if(invaders.get(i).isVisible==true) {
                    this.minecraft.getTextureManager().bind(invaderImage);
                    this.blit(p_230430_1_, (this.width - textureWidth) /2 +invaders.get(i).getxpos()+invaderWidth, (this.height - textureHeight) / 2+invaders.get(i).getypos() +invaderHeight, 0,0, invaderWidth, invaderHeight, invaderWidth,invaderHeight);
                    //blit(x, y, this.blitOffset, (float) u, (float) v, width of image shown, height of image shown, x of imported image, y of imported image);
                }
            }

            displayScore(this.matrixStack);
            invaderMove();
            super.render(this.matrixStack, p_230430_2_, p_230430_3_, p_230430_4_);
        }
        if(gamePlay == 2){
            //gameover
            this.minecraft.getTextureManager().bind(gameover);
            this.blit(p_230430_1_, relX, relY, 0, 0, textureWidth, textureHeight);
            drawString(p_230430_1_, this.font, new TranslationTextComponent("Game over!").withStyle(TextFormatting.WHITE), 150, 110, 16777215);
            drawString(p_230430_1_, this.font, new TranslationTextComponent("Score: ").append((new StringTextComponent(Integer.toString(score)).withStyle(TextFormatting.WHITE))), 150, 130, 16777215);
            drawString(p_230430_1_, this.font, new TranslationTextComponent("Press 'esc' to quit").withStyle(TextFormatting.WHITE), 150, 138, 16777215);
        }


    }

    public void invaderMove() {
    	boolean leftReached = false;
    	boolean rightReached = false; 
    	boolean bottomReached = false; 

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
    		
    		//If left border reached 
    		if(i == 23 && invaders.get(23).getypos() >= 207) { 
    			bottomReached = true; 
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
    	if (bottomReached == true) {
    		for (int j = 0; j < NumberOfInvaders; j++) {
    			invaders.get(j).isVisible=false;
    		}
    	}
    }

    /**
     * Display the score on gui screen
     *
     * @param p_230430_1_
     */
    public void displayScore(MatrixStack p_230430_1_){
        int relX = (this.width - textureWidth) / 2;
        drawString(p_230430_1_, this.font, new TranslationTextComponent("Score: ").append((new StringTextComponent(Integer.toString(score)).withStyle(TextFormatting.WHITE))), relX, 10, 16777215);
        drawString(p_230430_1_, this.font, new TranslationTextComponent("Press t to score up").withStyle(TextFormatting.WHITE), relX, 25, 16777215);
        drawString(p_230430_1_, this.font, new TranslationTextComponent("Press r to reset").withStyle(TextFormatting.WHITE), relX, 40, 16777215);
        drawString(p_230430_1_, this.font, new TranslationTextComponent("Press q to gameover").withStyle(TextFormatting.WHITE), relX, 55, 16777215);
    }

    /**
     * Register key inputs and adjust score as needed
     *
     * @param typedChar
     * @param keyCode
     * @return
     */
    @Override
    public boolean charTyped(char typedChar, int keyCode){
        if(gamePlay == 0 && typedChar == 's'){
            this.gamePlay = 1;
        }
        if(gamePlay == 1 && typedChar == 'q'){
            this.gamePlay = 2;
        }
    	// move player to left        
    	if (typedChar == 'a') {
    		tank.movesLeft= true;
    		tank.movePlayer();
    	}
    	// move player to right
    	if (typedChar == 'd') {
    		tank.movesRight= true;
    		tank.movePlayer();
    	}
    	//space bar for firing a shot
    	if (typedChar == ' ' && !shot.movesUp) {
    		shot.setxpos(tank.getxpos());
    		shot.setypos(tank.getypos());
    		shot.movesUp= true;
    	}
        if (typedChar == 'r') {
            scoreReset();
            drawCenteredString(this.matrixStack, this.font, new TranslationTextComponent("Score: ").append((new StringTextComponent(Integer.toString(score)).withStyle(TextFormatting.WHITE))), this.width / 2, 30, 16777215);
        }
        if (typedChar == 't') {
            scoreUp(10);
            drawCenteredString(this.matrixStack, this.font, new TranslationTextComponent("Score: ").append((new StringTextComponent(Integer.toString(score)).withStyle(TextFormatting.WHITE))), this.width / 2, 30, 16777215);
        }
        super.charTyped(typedChar, keyCode);
        return true;
    }
    
 
   
    
   

    /**
     * Resets the score
     */
    public void scoreReset() {
        this.score = 0;
    }

    /**
     * Increment the score
     *
     * @param upBy
     */
    public void scoreUp(int upBy) {
        this.score += upBy;
    }

    /**
     * pause screen
     *
     * @return
     */
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


