package com.project.pufferfish.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.project.pufferfish.Invaders;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
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
import java.util.Random;


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
    private static final ResourceLocation fireImage1 = new ResourceLocation(Invaders.MOD_ID, "textures/gui/tankfire1.png");
    private static final ResourceLocation fireImage2 = new ResourceLocation(Invaders.MOD_ID, "textures/gui/tankfire2.png");

    

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
    Player invaderShot = new  Player ();

    int tankOnFire=50;

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
    Random rand = new Random();//So Random spcae invaders can shoot
 
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
        this.gamePlay = -1;
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

        this.minecraft.getTextureManager().bind(background);
        this.blit(p_230430_1_, relX, relY, 0, 0, textureWidth, textureHeight);

        //tank visible only when it is not hit
        detectTankHit();
        if (tank.isVisible) {
	        this.minecraft.getTextureManager().bind(playerImage);
	        this.blit(p_230430_1_, relX+tank.getxpos(), relY+tank.getypos(),0,0,playerWidth,playerHeight,playerWidth,playerHeight);
	        }
        
        else if(tankOnFire>0) {
        	if(tankOnFire%20<10) {
        		this.minecraft.getTextureManager().bind(fireImage1);
        	}
        	else {
        		this.minecraft.getTextureManager().bind(fireImage2);
        	}
	        this.blit(p_230430_1_, relX+tank.getxpos(), relY+tank.getypos(),0,0,playerWidth,playerHeight,playerWidth,playerHeight);
	        tankOnFire--;
        }
        
      
        if (shot.movesUp) {  //display player shot only when space bar is pressed
        	shot.moveShotUp();
        	this.minecraft.getTextureManager().bind(shotImage);
        	this.blit(p_230430_1_, relX+shot.getxpos(), relY+shot.getypos(),0,0,playerWidth,playerHeight,playerWidth,playerHeight);
        	if (shot.getypos()<10) {
    			shot.movesUp=false;
    		 }
        }
//        if  (invaderShot.movesDown == false) {
//			invaderShot.setxpos(invaders.get(1).getxpos());
//			invaderShot.setypos(invaders.get(1).getypos());
//			invaderShot.movesDown = true;// set back to false once bullet has reached target or left the board
//			// System.out.println("Invader number "+invader.invaderId+" is shooting");
//
//		}
//		if (invaderShot.movesDown) {  //display player shot only when space bar is pressed
//			invaderShot.moveShotDown();
//        	this.minecraft.getTextureManager().bind(shotImage);
//        	this.blit(p_230430_1_, relX+invaderShot.getxpos(), relY+invaderShot.getypos(),0,0,playerWidth,playerHeight,playerWidth,playerHeight);
//        	if (invaderShot.getypos()>textureHeight) {
//        		invaderShot.movesDown=false;
//                //randomInvader=rand.nextInt(NumberOfInvaders-1);
//    		 }
//        }
        int randomInvader=rand.nextInt(50*NumberOfInvaders-1);
		for (int i = 0; i < NumberOfInvaders; i++) {
			if (invaders.get(i).isVisible == true) {
				this.minecraft.getTextureManager().bind(invaderImage);
				this.blit(p_230430_1_, (this.width - textureWidth) / 2 + invaders.get(i).getxpos() + invaderWidth,
						(this.height - textureHeight) / 2 + invaders.get(i).getypos() + invaderHeight, 0, 0,
						invaderWidth, invaderHeight, invaderWidth, invaderHeight);
				// blit(x, y, this.blitOffset, (float) u, (float) v, width of image shown,
				// height of image shown, x of imported image, y of imported image);
                if ((invaders.get(i).getxpos()+invaderWidth/2 > shot.getxpos()) && 
            			(invaders.get(i).getxpos()-invaderWidth/2 < shot.getxpos()) && 
            			(invaders.get(i).getypos()+invaderHeight/2 > shot.getypos()) && 
            			(invaders.get(i).getypos()-invaderHeight/2 < shot.getypos())) {
            				invaders.get(i).invaderShot();
            				shot.movesUp = false;
            	}
			}
			// Space invader shooting
			// If there is no bullet being shot (dropped==false) and alien is alive
			// (isAlive=true)
			if ((i == randomInvader) && (invaders.get(i).isAlive == true) && (invaders.get(i).isVisible == true) && (invaderShot.movesDown == false)) {
				invaderShot.setxpos(invaders.get(i).getxpos()+invaderWidth/2);
				invaderShot.setypos(invaders.get(i).getypos()+invaderHeight);
				invaderShot.movesDown = true;// set back to false once bullet has reached target or left the board
			}
			this.minecraft.getTextureManager().bind(shotImage);
        	this.blit(p_230430_1_, relX+invaderShot.getxpos(), relY+invaderShot.getypos(),0,0,invaderWidth,invaderHeight,invaderWidth,invaderHeight);

			if (invaderShot.movesDown && i%12==0) {  //Move down only once per iteration
				invaderShot.moveShotDown();
//	        	this.minecraft.getTextureManager().bind(shotImage);
//	        	this.blit(p_230430_1_, relX+invaderShot.getxpos(), relY+invaderShot.getypos(),0,0,invaderWidth,invaderHeight,invaderWidth,invaderHeight);
	        	if (invaderShot.getypos()>textureHeight) {
	        		invaderShot.movesDown=false;
	    		 }
	        }

		}
      
        displayScore(this.matrixStack);
        invaderMove();

        super.render(this.matrixStack, p_230430_2_, p_230430_3_, p_230430_4_);


        //title screen
        if(gamePlay == 0){
            this.addButton(new Button(this.width / 2 - 90, this.height / 4 + 120, 180, 20, new TranslationTextComponent("Start Game"), (p_213021_1_) -> {
               this.gamePlay = 1;
            }));
            this.minecraft.getTextureManager().bind(gametitle);
            this.blit(p_230430_1_, relX, relY, 0, 0, textureWidth, textureHeight);
            super.render(this.matrixStack, p_230430_2_, p_230430_3_, p_230430_4_);
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
    
    
    /**
     * Make the aliens move from left to right
     */
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
    		
    		//If bottom border reached 
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

  
   
    public void detectTankHit() {
     	if ((Math.abs(invaderShot.getxpos()-tank.getxpos())<1 && invaderShot.getypos()>236 &&  invaderShot.getypos()<252) || (Math.abs(invaderShot.getxpos()-tank.getxpos())<6 && invaderShot.getypos()>240 &&  invaderShot.getypos()<252)) {
     	 tank.isVisible=false;
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

        if(gamePlay == 1 && typedChar == 'q'){
            this.gamePlay = 2;
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

    
    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        // To prevent recursion when focused is itself and a key is released
        // To prevent recursion when focused is itself and a key is pressed
    	if (keyCode == 263 && (keyCode == 32 && !shot.movesUp && tank.isVisible)) {
    		tank.movesLeft= false;
    		shot.movesUp= false;
    	}
    	// move player to right
    	if (keyCode == 262 && (keyCode == 32 && !shot.movesUp && tank.isVisible)) {
    		tank.movesRight= false;
    		shot.movesUp= false;
    	}
    	if (keyCode == 263 ) {
    		tank.movesLeft= false;
    	}
    	// move player to right
    	if (keyCode == 262) {
    		tank.movesRight= false;
    	}

    	//space bar for firing a shot only one shot at a time when tank is visible
    	if (keyCode == 32 && !shot.movesUp && tank.isVisible) {
    		shot.movesUp= false;
    	}
        return this.getFocused() != this && super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // To prevent recursion when focused is itself and a key is pressed
    	if (keyCode == 263 && (keyCode == 32 && !shot.movesUp && tank.isVisible)) {
    		tank.movesLeft= true;
    		tank.movePlayer();
    		shot.setxpos(tank.getxpos());
    		shot.setypos(tank.getypos());
    		shot.movesUp= true;
    	}
    	// move player to right
    	if (keyCode == 262 && (keyCode == 32 && !shot.movesUp && tank.isVisible)) {
    		tank.movesRight= true;
    		tank.movePlayer();
    		shot.setxpos(tank.getxpos());
    		shot.setypos(tank.getypos());
    		shot.movesUp= true;
    	}
    	
    	if (keyCode == 263 ) {
    		tank.movesLeft= true;
    		tank.movePlayer();
    	}
    	// move player to right
    	if (keyCode == 262) {
    		tank.movesRight= true;
    		tank.movePlayer();
    	}
    	
    	//space bar for firing a shot only one shot at a time when tank is visible
    	if (keyCode == 32 && !shot.movesUp && tank.isVisible) {
    		shot.setxpos(tank.getxpos());
    		shot.setypos(tank.getypos());
    		shot.movesUp= true;
    	}
        return this.getFocused() != this && super.keyPressed(keyCode, scanCode, modifiers);
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