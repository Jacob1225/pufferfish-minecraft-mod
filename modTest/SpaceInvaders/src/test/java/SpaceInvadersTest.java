package test.java;
import java.util.ArrayList;
import java.util.List;
import main.java.SpaceInvaders;
//import static org.junit.jupiter.api.Assertions.*;


class SpaceInvadersTest {
	static List<SpaceInvaders> invaders;
    static int ALIEN_INIT_X = 150;//Position of first invader
    static int ALIEN_INIT_Y = 5;
    static int score=100;
    int NumberOfInvaders=24;
    static int InvaderRows=4;
    static int InvaderCols=6;

   public SpaceInvadersTest() {
    	
    }

    
    public static void main(String[] args){
//NOT ORIGINAL CODE -> creating individual invaders
    	//System.out.print("hello");

    	InvadersCreation();
        invaderShotTest();
    }
    
    public static void InvadersCreation() {
    	invaders=new ArrayList<>();
    	 for (int i = 0; i < InvaderRows; i++) {
             for (int j = 0; j < InvaderCols; j++) {

            	 SpaceInvaders invader=new SpaceInvaders(ALIEN_INIT_X + 18 * j, ALIEN_INIT_Y + 18 * i,(i+1)*10);
                 invaders.add(invader);
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

    public static void InvaderMoveTest(){
        //send board limits
    }
}