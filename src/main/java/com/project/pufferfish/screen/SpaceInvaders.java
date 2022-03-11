package com.project.pufferfish.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SpaceInvaders {
	public int x;
	public int y;
	int points;
	public boolean isAlive;
	boolean isVisible;
	public boolean movesRight; //Space invaders start by moving to the right
	public static AtomicInteger Id=new AtomicInteger(); //giving the invaders a number
	public int invaderId;
	
    public static void main(String[] args)
    {
        //System.out.print("here");
    }

    //public static void updateScreen(array with alien positions) 
    //score
    //bomb
    
    //Constructor
    public SpaceInvaders(int xpos,int ypos,int points){
        this.x=xpos;
        this.y=ypos;
        this.points=points;
        isAlive=true;
        isVisible=true;
        this.movesRight = true; //Space invaders start by moving to the right
        this.invaderId=Id.incrementAndGet();
    }
    
    //get position
    public int getxpos() { return x;}
    public int getypos() { return y;}


    //set position
    public void setxpos(int xpos) { this.x = xpos;}
    public void setypos(int ypos) { this.y = ypos;}
    
    boolean leftReached = false;
	boolean rightReached = false; 
	
	//constants may go somewhere else
	int MX_ALIEN = 10;//Horizontal movement 
	int MY_ALIEN = 20; //Vertical movement
	int ALIEN_HEIGHT = 15; 
	int ALIEN_WIDTH = 15; 
	int BOARD_HEIGHT = 400;//temporary board limits 
	int BOARD_WIDTH = 400;//temporary board limits 
	int BOARD_MARGIN = 10; //So the alien doesn't go beyond the margins

    public void invadersMove() {

    	if (this.movesRight == true) { //Invaders move to the right
    			setxpos(getxpos() + MX_ALIEN); 
    		
    	}
    	else { //Invaders move to the left 
    			setxpos(getxpos() - MX_ALIEN); 
    	
    	} 
//    public void invadersMove(ArrayList<SpaceInvaders> invaders) {
//
//    	if (this.movesRight == true) { //Invaders move to the right
//    		for(SpaceInvaders invader: invaders) { 
//    			invader.setxpos(invader.getxpos() + MX_ALIEN); 
//    		}
//    	}
//    	else { //Invaders move to the left 
//    		for(SpaceInvaders invader: invaders) {
//    			invader.setxpos(invader.getxpos() - MX_ALIEN); 
//    		}
//    	}  	

    	//To detect borders, go down and then change direction
//    	boolean leftReached = false;
//    	boolean rightReached = false; 

//    	//If left border reached 
//    	for(SpaceInvaders invader: invaders) { 
//    		if(isAlive==true && invader.getxpos() < BOARD_MARGIN) { 
//    			leftReached = true; 
//    			break; 
//    		}
//    	}
//
//    	//If right border reached 
//    	for(SpaceInvaders invader: invaders) { 
//    		if(isAlive==true && invader.getxpos() > (BOARD_WIDTH -BOARD_MARGIN -ALIEN_WIDTH)) { 
//    			rightReached = true; 
//    			break; 
//    		}
//    	}
//
//    	//Invaders detect borders and go down 
//    	if (rightReached == true) {
//    		for(SpaceInvaders invader: invaders) {
//    			invader.setypos(invader.getypos() + MY_ALIEN); 
//    		}
//    		this.movesRight = false; 
//    	}
//    	if (leftReached == true) {
//    		for(SpaceInvaders invader: invaders) { 
//    			invader.setypos(invader.getypos() + MY_ALIEN);
//    		}
//    		this.movesRight = false; 
//    	}
    	//this.detectBordersGoDown(invaders);
    }
    
//    int moveX, moveY; //for each invader x and y movement
//    
//    public void updatePosition() {
////    	int newPosX = x + moveX;
////    	int newPosY = y + moveY;
////    	
//    	x = getxpos();
//    	y = getypos();
//    }
    
    private boolean detectLeft(List<SpaceInvaders> invaders) {
    	boolean detLeft = false;
    	for(SpaceInvaders invader: invaders) { 
    		if(isAlive==true && invader.getxpos() < BOARD_MARGIN) { 
    			leftReached = true; 
    			break; 
    		}
    	}
		return detLeft;
    }
    
    private boolean detectRight(List<SpaceInvaders> invaders) {
    	boolean detRight = false;
    	for(SpaceInvaders invader: invaders) { 
    		if(isAlive==true && invader.getxpos() > (BOARD_WIDTH -BOARD_MARGIN -ALIEN_WIDTH)) { 
    			rightReached = true; 
    			break; 
    		}
    	}
		return detRight;
    }
    
    public void detectBordersGoDown(List<SpaceInvaders> invaders) {

    	//Invaders detect borders and go down 
    	if (detectRight(invaders) == true) {
    		for(SpaceInvaders invader: invaders) {
    			invader.setypos(invader.getypos() + MY_ALIEN); 
    		}
    		this.movesRight = false; 
    	}
    	if (detectLeft(invaders) == true) {
    		for(SpaceInvaders invader: invaders) { 
    			invader.setypos(invader.getypos() + MY_ALIEN);
    		}
    		this.movesRight = false; 
    	}
    }
	 	 
    public boolean invaderShot(int xpos,int ypos)
    {
    	//Assuming invaders are 5x5 sized
    	if ((xpos+2 >= x) && (x >=xpos-2) && (ypos+2 >= y) && (y >=ypos-2)){
    		//Kill the invader
    		isAlive=false;
    		isVisible(isAlive);//To make the alien disappear
    		return true;	
    	}
    	//score
    	return false;
    }
    
    public boolean isVisible(boolean isAlive) {
    	if (isAlive == false) {
    		isVisible = false;
    	}
    	return isVisible;
    }

    public int keepScore(int TotalScore){
        //if shot, update score
    	TotalScore=TotalScore+points;
        return TotalScore;
    }

}
