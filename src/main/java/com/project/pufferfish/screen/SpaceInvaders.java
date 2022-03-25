package com.project.pufferfish.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SpaceInvaders {
	public int x;
	public int y;
	int MX_ALIEN = 1;//Horizontal movement 
	int MY_ALIEN = 10; //Vertical movement
	int points;
	public boolean isAlive;
	boolean isVisible;
	public boolean movesRight; //Space invaders start by moving to the right
	public static AtomicInteger Id=new AtomicInteger(); //giving the invaders a number
	public int invaderId;
	
    
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
	
	
	public void invadersMove() {

		if (movesRight == true) { //Invaders move to the right
			setxpos(getxpos() + MX_ALIEN); 

		}
		else { //Invaders move to the left 
			setxpos(getxpos() - MX_ALIEN); 

		} 
	}
	
	public void invaderShot(){
    	//Assuming invaders are 5x5 sized
    	//if ((xpos+10 >= x) && (x >=xpos-10) && (ypos+10 >= y) && (y >=ypos-10)){
    		//Kill the invader
    		isAlive=false;
    		isVisible(isAlive);//To make the alien disappear
    		//return true;	
    	//}
    	//score
    	//return false;
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
