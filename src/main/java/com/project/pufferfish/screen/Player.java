package com.project.pufferfish.screen;




public class Player {
	
	public int x;
	public int y;
	int speed =5;
	boolean isVisible;
	public boolean movesRight; 
	public boolean movesLeft; 
	public boolean moveUp;
	
	
    public static void main(String[] args)
    {
       //	System.out.print("here");
    }
    
  //Constructor
    public Player(int xpos,int ypos){
        this.x=xpos;
        this.y=ypos;
        isVisible=true;
        this.movesRight = false; 
        this.movesLeft = false; 
        this.moveUp = false; 
    }
    
    public void movePlayer() {
    	
   	 if (movesRight==true )
   			x = x+ speed;
   	 if (movesLeft==true)
   			x = x- speed;
    }
    
    
   
	
    
    //get position
    public int getxpos() { return x;}
    public int getypos() { return y;}


    //set position
    public void setxpos(int xpos) { this.x = xpos;}
    public void setypos(int ypos) { this.y = ypos;}
    
    
    

}












