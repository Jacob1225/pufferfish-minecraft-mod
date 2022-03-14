package com.project.pufferfish.screen;

public class Player {
	
	public int x;
	public int y;
	int speed =5;
	boolean isVisible;
	public boolean movesRight; 
	public boolean movesLeft; 
	public boolean movesUp;
	
    
  //Constructor
    public Player(int xpos,int ypos){
        this.x=xpos;
        this.y=ypos;
        isVisible=true;
        this.movesRight = false; 
        this.movesLeft = false; 
        this.movesUp = false; 
    }
    
    public void movePlayer() {
    	
     	//System.out.println("here  "+x);
   	 	if (movesRight==true && x<241)
   			x = x+ speed;
   	 	if (movesLeft==true  && x>4)
   			x = x- speed;
    	movesRight = false; 
        movesLeft = false;
    }
    
    public void moveShot() {
    	y = y - speed-1;	
		
	 }	 
       
    //get position
    public int getxpos() { return x;}
    public int getypos() { return y;}


    //set position
    public void setxpos(int xpos) { this.x = xpos;}
    public void setypos(int ypos) { this.y = ypos;}
      
	public void setisVisible(boolean isVisible) {this.isVisible = isVisible;}

}












