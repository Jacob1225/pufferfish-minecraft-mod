package src.main.java;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.*;

class SpaceInvadersTest {

	 List<SpaceInvaders> invaders;
     int ALIEN_INIT_X = 150;//Position of first invader
     int ALIEN_INIT_Y = 5;
     int score=100;
     int NumberOfInvaders=24;
     int InvaderRows=4;
     int InvaderCols=6;
	 Bullet bullet=new Bullet();

    
   @BeforeEach
   @DisplayName("Invader Creation")
    public void InvadersCreation() {
    	invaders=new ArrayList<>();
    	 for (int i = 0; i < InvaderRows; i++) {
             for (int j = 0; j < InvaderCols; j++) {

            	 SpaceInvaders invader=new SpaceInvaders(ALIEN_INIT_X + 18 * j, ALIEN_INIT_Y + 18 * i,(i+1)*10);
                 invaders.add(invader);
                 //System.out.println("Invader number "+invader.invaderId+" was created at "+invader.x+invader.y);
             }
         } 
	}
   @Test
   @DisplayName("Invader Shot")
    public void invaderShotTest(){
    	for(SpaceInvaders invader: invaders) {
    		if(invader.invaderShot(150,5)==true) {
    			assertEquals(score+10,invader.keepScore(score),"If shot an invader in the first row");
    		}
    		if(invader.invaderShot(150,21)==true) {//Doesn't hit exactly the middle
    			assertEquals(score+20,invader.keepScore(score),"If shot an invader in the second row");
    		}
    		assertEquals(false,invader.invaderShot(240,55),"If shot missed");
    		
    	}
   }
   
 //Visible aliens after invaderShotTest
   @Test
   @DisplayName("Invader visibility test")
    public void isVisibleTest() {
    	for(SpaceInvaders invader: invaders) {
    		assertEquals(true,invader.isVisible(true),"Invader is visible");
    		assertEquals(false,invader.isVisible(false),"Invader is INvisible");
    	}
    }

 
}
