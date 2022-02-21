package src.main.java;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

class SpaceInvadersTest {

	static List<SpaceInvaders> invaders;
    static int ALIEN_INIT_X = 150;//Position of first invader
    static int ALIEN_INIT_Y = 5;
    static int score=100;
    static int NumberOfInvaders=24;
    static int InvaderRows=4;
    static int InvaderCols=6;
  //Create bullet
	static Bullet bullet=new Bullet();

   public SpaceInvadersTest() {
    	
    }

    
    public static void main(String[] args){
    	InvadersCreation();
        invaderShotTest();
        updateBoard();
        invaderShootingTest();
        invaderMoveTest();
        isVisibleTest();
    }
    
    public static void InvadersCreation() {
    	invaders=new ArrayList<>();
    	 for (int i = 0; i < InvaderRows; i++) {
             for (int j = 0; j < InvaderCols; j++) {

            	 SpaceInvaders invader=new SpaceInvaders(ALIEN_INIT_X + 18 * j, ALIEN_INIT_Y + 18 * i,(i+1)*10);
                 invaders.add(invader);
                 System.out.println("Invader number "+invader.invaderId+" was created");
             }
         } 
	}
    public static void invaderShotTest(){
    	System.out.println("Score before Shooting: "+score);
//If shot an invader in the first row
    	for(SpaceInvaders invader: invaders) {
    		if(invader.invaderShot(150,5)==true) {
    			score=invader.keepScore(score);
    		}
    	}
    	System.out.println("Update score after shooting an invader in the first row: "+score);
//If shot an invader in the second row
    	for(SpaceInvaders invader: invaders) {
    		if(invader.invaderShot(150,22)==true) {//Doesn't hit exactly the middle
    			score=invader.keepScore(score);
    		}
    	}
    	System.out.println("Update score after shooting an invader in the second row: "+score);
//If shot missed    	
    	for(SpaceInvaders invader: invaders) {
    		if(invader.invaderShot(200,2)==true) {
    			score=invader.keepScore(score);
    		}
    	}
    	System.out.println("Update score after a miss: "+score);
    }
    
    public static void updateBoard(){
    	for(SpaceInvaders invader: invaders) {
    		if(invader.isAlive==true) {
    			System.out.println("Invader postitons "+invader.x+" , "+invader.y);
    		}
    	}
    }
// Space invader shooting
    public static void invaderShootingTest(){
    	//Pick an alien
    	Random generator = new Random();
    	int randomInvader=generator.nextInt(NumberOfInvaders);
    	for (SpaceInvaders invader: invaders) {
    	//If there is no bullet being shot (dropped==false) and alien is alive (isAlive=true)
    		System.out.println("Invader num "+invader.invaderId);
    		if((invader.invaderId==randomInvader) && (bullet.dropped==false)) {
    			bullet.set(invader.x,invader.y);
    			System.out.println("Invader number "+invader.invaderId+" is shooting");
    		}
    			
    	}
    	
    	//create bomb with the alien location and set dropped flag to true
    }
    
 //Visible aliens after invaderShotTest
    public static void isVisibleTest() {
    	for(SpaceInvaders invader: invaders) {
    		if(invader.isVisible() == true) {
    			System.out.println("Invader "+ invader.invaderId+ " is visible");
    		}
    		else
    			System.out.println("Invader "+ invader.invaderId+ " is not visible");
    	}
    }

  //Aliens movement test
    static int ALIEN_HEIGHT = 5;
	static int ALIEN_WIDTH = 5;
    static int BOARD_HEIGHT = 300;//temporary board limits
    static int BOARD_WIDTH = 300;//temporary board limits
    static int BOARD_MARGIN = 5; //So the alien doesn't go beyond the margins
	
	public static void invaderMoveTest(){ //Check if right border is reached 
		int count = 0; //To count the number of times the aliens have reached the right border
		for(SpaceInvaders invader: invaders) {
			invader.invadersMove(invaders); 
			if(invader.getxpos() >= BOARD_WIDTH - BOARD_MARGIN - ALIEN_WIDTH) {
				//System.out.println("Space invaders have reached the right border."); 
				count++; 
			} 
		}
		System.out.println("Number of times the space invaders have reached the right border: " + count); 
	}	
	 
}
