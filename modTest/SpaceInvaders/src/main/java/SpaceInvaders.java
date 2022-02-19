package src.main.java;
import java.util.concurrent.atomic.AtomicInteger;
public class SpaceInvaders {
public int x;
public int y;
int points;
public boolean isAlive;
boolean movesRight; //Space invaders start by moving to the right
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
        this.movesRight = true; //Space invaders start by moving to the right
        this.invaderId=Id.incrementAndGet();
    }
    
  //get position
	/*
	 * public int getxpos() { //x position of 1 alien return x; }
	 * 
	 * public int getypos() {//y position of 1 alien return y; }
	 * 
	 * //set position
	 * 
	 * public void setxpos(int xpos) { this.x = xpos; }
	 * 
	 * public void setypos(int ypos) { this.y = ypos; }
	 * 
	 * public static void invadersMove() { //To make the invaders move from left to
	 * right
	 * 
	 * //constants may go somewhere else static int MX_ALIEN = 10;//Horizontal
	 * movement static int MY_ALIEN = 20;//Vertical movement
	 * 
	 * if (this.goesRight == true) { //Invaders move to the right for(SpaceInvaders
	 * invader: invaders) { invader.setxpos(invader.getxpos() + MX_ALIEN); } } else
	 * { //Invaders move to the left for(SpaceInvaders invader: invaders) {
	 * invader.setxpos(invader.getxpos() - MY_ALIEN); } } }
	 * 
	 * public static void detectBordersGoDown(){ //To detect borders, go down and
	 * then change direction
	 * 
	 * //constants may go somewhere else static int ALIEN_HEIGHT = 5; static int
	 * ALIEN_WIDTH = 5; static int MX_ALIEN = 10;//Horizontal movement static int
	 * MY_ALIEN = 20;//Vertical movement static int BOARD_HEIGHT = 300;//temporary
	 * board limits static int BOARD_WIDTH = 300;//temporary board limits static int
	 * BOARD_MARGIN = 10; //So the alien doesn't go beyond the margins
	 * 
	 * //If left border reached boolean leftReached = false; for(SpaceInvaders
	 * invader: invaders) { if(invader.getxpos() < BOARD_MARGIN) { leftReached =
	 * true; break; } } return leftReached;
	 * 
	 * //If right border reached boolean rightReached = false; for(SpaceInvaders
	 * invader: invaders) { if(invader.getxpos() > (BOARD_WIDTH -BOARD_MARGIN -
	 * ALIEN_WIDTH)) { RightReached = true; break; } } return leftReached;
	 * 
	 * //Invaders detect borders and go down if (rightReached == true) {
	 * for(SpaceInvaders invader: invaders) { invader.setypos(invader.getypos() +
	 * MY_ALIEN); } this.movesRight = false; } else { if (leftReached == true) {
	 * for(SpaceInvaders invader: invaders) { invader.setypos(invader.getypos() +
	 * MY_ALIEN); } this.movesRight = false; } } }
	 */	 
    public boolean invaderShot(int xpos,int ypos)
    {
    	//Assuming invaders are 5x5 sized
    	if ((xpos+2 >= x) && (x >=xpos-2) && (ypos+2 >= y) && (y >=ypos-2)){
    		//Kill the invader
    		isAlive=false;
    		return true;
    	}
    	//score
    	return false;
    }

    public int keepScore(int TotalScore){
        //if shot, update score
    	TotalScore=TotalScore+points;
        return TotalScore;
    }

}

