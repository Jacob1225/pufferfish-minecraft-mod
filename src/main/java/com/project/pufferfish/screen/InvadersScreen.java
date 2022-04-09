package com.project.pufferfish.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.project.pufferfish.Invaders;
import com.project.pufferfish.tileentity.ArcadeMachineTile;
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
    private static final ResourceLocation pausebackground = new ResourceLocation(Invaders.MOD_ID, "textures/gui/invaders_gui.png");
    private static final ResourceLocation playerImage = new ResourceLocation(Invaders.MOD_ID, "textures/gui/player.png");
    private static final ResourceLocation shotImage = new ResourceLocation(Invaders.MOD_ID, "textures/gui/shot.png");
    private static final ResourceLocation invaderImage = new ResourceLocation(Invaders.MOD_ID, "textures/gui/invader1.png");
    private static final ResourceLocation invader2Image = new ResourceLocation(Invaders.MOD_ID, "textures/gui/invader2.png");
    private static final ResourceLocation invader3Image = new ResourceLocation(Invaders.MOD_ID, "textures/gui/invader3.png");
    private static final ResourceLocation invader4Image = new ResourceLocation(Invaders.MOD_ID, "textures/gui/invader4.png");
    private static final ResourceLocation fireImage1 = new ResourceLocation(Invaders.MOD_ID, "textures/gui/tankfire1.png");
    private static final ResourceLocation fireImage2 = new ResourceLocation(Invaders.MOD_ID, "textures/gui/tankfire2.png"); 
    
    //Gui variables
    public int textureWidth = 256, textureHeight = 266;
    private int xSize = 0, ySize = 0;
    private float scale = 1;
    int relX = 0;
    int relY = 0;
    boolean isPaused;


    //Player variables
    public int playerWidth=15, playerHeight=15;
    Player tank = new  Player (textureWidth/2-playerWidth/2,textureHeight-(2*playerHeight));
    Player shot = new  Player ();
    Player invaderShot = new  Player ();
    int tankOnFire=50; //animation variable

    
    //Display score variables
    MatrixStack matrixStack;
    private int score = 0;

    //Invader variables
    public int invaderWidth=textureWidth/15, invaderHeight=textureHeight/15;
    static int InvaderRows=4;
    static int InvaderCols=6;
    ArrayList<SpaceInvaders> invaders;
    SpaceInvaders invader;
    static int NumberOfInvaders=24;
    Random rand = new Random();//So Random space invaders can shoot
   
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
    	int invaderId=0;
    	 for (int i = 0; i < InvaderRows; i++) {//i=0 is the last row
             for (int j = 0; j < InvaderCols; j++) {
            	 SpaceInvaders invader=new SpaceInvaders(20 * j, 70+20 * i,(j+1)*10,invaderId);
                 invaders.add(invader);
                 invaderId++;
             }
         }
	}

    /**
     * initilizing screen
     */
    protected void init() {

        this.delayTicker = 0;
        this.gamePlay = 0;
        this.isPaused = false;
    }

    /**
     * Closes gui screen when esc key is pressed
     *
     * @return
     */
    public boolean shouldCloseOnEsc() {
        // tells the ArcadeMachineTile that the player has gotten a high score for a prize
        if(this.score >= 400){
            ArcadeMachineTile.hasHighScoreForPrize = true;
        }
        else{
            ArcadeMachineTile.hasHighScoreForPrize = false;
        }

        this.scoreReset();
        this.gamePlay = -1;
        return true;
    }

    /**
     * Draw the gui screen and display score
     *
     */
    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.matrixStack = p_230430_1_;
        relX = (this.width - textureWidth) / 2;
        relY = (this.height - textureHeight) / 2;
        assert this.minecraft != null;

        //title screen
        if(gamePlay == 0){
            this.addButton(new Button(this.width / 2 - 90, this.height / 4 + 120, 180, 20, new TranslationTextComponent("Start Game"), (p_213021_1_) -> {
               this.gamePlay = 1;
            }));
            this.minecraft.getTextureManager().bind(background);
            this.blit(p_230430_1_, relX, relY, 0, 0, textureWidth, textureHeight);
            super.render(this.matrixStack, p_230430_2_, p_230430_3_, p_230430_4_);
        }
        //game play
        else if(gamePlay == 1){
            if(!this.isPaused) {
                this.minecraft.getTextureManager().bind(background);
                this.blit(p_230430_1_, relX, relY, 0, 0, textureWidth, textureHeight);

                               
              //tank visible only when it is not hit
                detectTankHit();
                if (tank.isVisible) {
        	        this.minecraft.getTextureManager().bind(playerImage);
        	        this.blit(p_230430_1_, relX+tank.getxpos(), relY+tank.getypos(),0,0,playerWidth,playerHeight,playerWidth,playerHeight);
        	        }
                
                else if(tankOnFire>-1) {
                	if(tankOnFire%20<10) {
                		this.minecraft.getTextureManager().bind(fireImage1);
                	}
                	else {
                		this.minecraft.getTextureManager().bind(fireImage2);
                	}
        	        this.blit(p_230430_1_, relX+tank.getxpos(), relY+tank.getypos(),0,0,playerWidth,playerHeight,playerWidth,playerHeight);
        	        tankOnFire--;
        	        if (tankOnFire<0) {
        	        	gamePlay = 2;    
        	        }
                }
                
              //tank shot only when space bar is pressed
                if (shot.movesUp) {  
                	shot.moveShotUp();
                	this.minecraft.getTextureManager().bind(shotImage);
                	this.blit(p_230430_1_, relX+shot.getxpos(), relY+shot.getypos(),0,0,playerWidth,playerHeight,playerWidth,playerHeight);
                	if (shot.getypos()<10) {
            			shot.movesUp=false;
            		 }
                }
                
                
                int randomInvader=rand.nextInt(50*NumberOfInvaders-1);
        		for (int i = 0; i < NumberOfInvaders; i++) {
        			if (invaders.get(i).isVisible == true) {
        				if (invaders.get(i).invaderId>=0 && invaders.get(i).invaderId<7)
        				{this.minecraft.getTextureManager().bind(invaderImage);}
        				else if (invaders.get(i).invaderId>6 && invaders.get(i).invaderId<13)
        				{this.minecraft.getTextureManager().bind(invader2Image);}
        				else if (invaders.get(i).invaderId>12 && invaders.get(i).invaderId<19)
        				{this.minecraft.getTextureManager().bind(invader3Image);}
        				else if (invaders.get(i).invaderId>18 && invaders.get(i).invaderId<25)
        				{this.minecraft.getTextureManager().bind(invader4Image);}
        				else {drawString(p_230430_1_, this.font, new TranslationTextComponent(" ").append((new StringTextComponent(Integer.toString(invaders.get(i).invaderId)).withStyle(TextFormatting.WHITE))), relX, 10, 16777215);}
        				
        				this.blit(p_230430_1_, (this.width - textureWidth) / 2 + invaders.get(i).getxpos() + invaderWidth,
        						(this.height - textureHeight) / 2 + invaders.get(i).getypos() + invaderHeight, 0, 0,
        						invaderWidth, invaderHeight, invaderWidth, invaderHeight);
        				// blit(x, y, this.blitOffset, (float) u, (float) v, width of image shown,
        				// height of image shown, x of imported image, y of imported image);
                        if (shot.movesUp==true && (invaders.get(i).getxpos()+2*invaderWidth>shot.getxpos()) && 
                    			(invaders.get(i).getxpos()+invaderWidth < shot.getxpos()+2) && 
                    			(invaders.get(i).getypos()+invaderHeight > shot.getypos()) && 
                    			(invaders.get(i).getypos() < shot.getypos())) {
                    				invaders.get(i).invaderShot();
                    				shot.movesUp = false;
                    				scoreUp(invaders.get(i).points);
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
        			//this.minecraft.getTextureManager().bind(shotImage);
                	//this.blit(p_230430_1_, relX+invaderShot.getxpos(), relY+invaderShot.getypos(),0,0,invaderWidth,invaderHeight,invaderWidth,invaderHeight);

        			if (invaderShot.movesDown && i%12==0) {  //Move down only once per iteration
        				invaderShot.moveShotDown();
        	        	this.minecraft.getTextureManager().bind(shotImage);
        	        	this.blit(p_230430_1_, relX+invaderShot.getxpos(), relY+invaderShot.getypos(),0,0,invaderWidth,invaderHeight,invaderWidth,invaderHeight);
        	        	if (invaderShot.getypos()>textureHeight) {
        	        		invaderShot.movesDown=false;
        	    		 }
        	        }

        		}
                displayScore(this.matrixStack);
                invaderMove();
                invaderAlive();
                if(!invaderAlive()){
                	gamePlay = 3;
                }
            }

            //Pause screen
            else {
                this.minecraft.getTextureManager().bind(pausebackground);
                this.blit(p_230430_1_, relX, relY, 0, 0, textureWidth, textureHeight);
                drawString(p_230430_1_, this.font, new TranslationTextComponent("Game is paused").withStyle(TextFormatting.WHITE), this.width / 2 - 90, this.height / 4 + 40, 16777215);
                drawString(p_230430_1_, this.font, new TranslationTextComponent("Press 'p' again to unpause game").withStyle(TextFormatting.WHITE), this.width / 2 - 90, this.height / 4 + 60, 16777215);
                drawString(p_230430_1_, this.font, new TranslationTextComponent("Press 'esc' to quit game").withStyle(TextFormatting.WHITE), this.width / 2 - 90, this.height / 4 + 80, 16777215);

            }
        }
        //gameover loosing
        else if(gamePlay == 2){
            this.minecraft.getTextureManager().bind(gameover);
            this.blit(p_230430_1_, relX, relY, 0, 0, textureWidth, textureHeight);
            drawString(p_230430_1_, this.font, new TranslationTextComponent("Game over!").withStyle(TextFormatting.WHITE), 150, 110, 16777215);
            drawString(p_230430_1_, this.font, new TranslationTextComponent("Score: ").append((new StringTextComponent(Integer.toString(score)).withStyle(TextFormatting.WHITE))), 150, 130, 16777215);
            drawString(p_230430_1_, this.font, new TranslationTextComponent("Press 'esc' to quit").withStyle(TextFormatting.WHITE), 150, 138, 16777215);
        }
      //gameover winning 
        else if(gamePlay == 3){
            this.minecraft.getTextureManager().bind(gameover);
            this.blit(p_230430_1_, relX, relY, 0, 0, textureWidth, textureHeight);
            drawString(p_230430_1_, this.font, new TranslationTextComponent("You win!").withStyle(TextFormatting.WHITE), 150, 110, 16777215);
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
    		if (!this.isPaused)
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
    		if(invaders.get(i).isVisible == true && invaders.get(i).getypos() >= 207) { 
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
        	 tank.isVisible=false;  //to end the game
    	}
    }

    /**
    * check if all alien are alive
    */
    public boolean invaderAlive() {
    	for (int j = 0; j < NumberOfInvaders; j++) {
    		if (invaders.get(j).isVisible==true) {
    			return true;
    		};
    	}
       	return false;
    }
	
    
    /**
     * Display the score on gui screen
     *
     * @param p_230430_1_
     */
    public void displayScore(MatrixStack p_230430_1_){
        int relX = (this.width - textureWidth) / 2;
        drawString(p_230430_1_, this.font, new TranslationTextComponent("Score: ").append((new StringTextComponent(Integer.toString(score)).withStyle(TextFormatting.WHITE))), relX, 10, 16777215);
        drawString(p_230430_1_, this.font, new TranslationTextComponent("Press q to gameover").withStyle(TextFormatting.WHITE), relX, 25, 16777215);
        drawString(p_230430_1_, this.font, new TranslationTextComponent("Press p to pause").withStyle(TextFormatting.WHITE), relX, 40, 16777215);
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
        //Pausing the game
        if (typedChar == 'p'){
            this.isPaused = !this.isPaused;
        }
        if(gamePlay == 1 && typedChar == 'q'){
            this.gamePlay = 2;
        }
        if(!this.isPaused) {
            // move player to left
            if ((typedChar == 'a' || typedChar == 'A') && tank.isVisible) {
                tank.movesLeft = true;
                tank.movePlayer();
            }
            // move player to right
            if ((typedChar == 'd' || typedChar == 'D') && tank.isVisible) {
                tank.movesRight = true;
                tank.movePlayer();
            }
            //space bar for firing a shot
            if (typedChar == ' ' && !shot.movesUp && tank.isVisible) {
                shot.setxpos(tank.getxpos());
                shot.setypos(tank.getypos());
                shot.movesUp = true;
            }
 
        }

        super.charTyped(typedChar, keyCode);
        return true;
    }
    /**
     * Tank detect a hit from alien
     */
    public void detectTankHit() {
     	if ((Math.abs(invaderShot.getxpos()-tank.getxpos())<1 && invaderShot.getypos()>236 &&  invaderShot.getypos()<252) || (Math.abs(invaderShot.getxpos()-tank.getxpos())<6 && invaderShot.getypos()>240 &&  invaderShot.getypos()<252)) {
     	 tank.isVisible=false;
     	}
     	
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
        return this.isPaused;
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
