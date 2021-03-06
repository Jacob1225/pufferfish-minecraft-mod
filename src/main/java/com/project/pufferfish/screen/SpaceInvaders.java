package com.project.pufferfish.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SpaceInvaders {
	public int x;
	public int y;
	public int invaderId;
	int MX_ALIEN = 1;//Horizontal movement 
	int MY_ALIEN = 10; //Vertical movement
	int points;
	public boolean isAlive;
	boolean isVisible;
	public boolean movesRight; //Space invaders start by moving to the right

	
    
    //Constructor
    public SpaceInvaders(int xpos,int ypos,int points, int invaderId){
        this.x=xpos;
        this.y=ypos;
        this.points=points;
        isAlive=true;
        isVisible=true;
        this.movesRight = true; //Space invaders start by moving to the right
        this.invaderId=invaderId;
    }
    
    //get position
    public int getxpos() { return x;}
    public int getypos() { return y;}


    //set position
    public void setxpos(int xpos) { this.x = xpos;}
    public void setypos(int ypos) { this.y = ypos;}
	
	
	public void invadersMove() {

		if (movesRight == true) { //Invaders move to the right
			setxpos(getxpos() + MX_ALIEN); 

		}
		else { //Invaders move to the left 
			setxpos(getxpos() - MX_ALIEN); 

		} 
	}
	
	public void invaderShot(){
    	isAlive=false;
    	isVisible(isAlive);//To make the alien disappear
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
