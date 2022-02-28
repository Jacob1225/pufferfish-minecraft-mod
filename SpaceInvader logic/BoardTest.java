package src.main.java;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {
	static List<SpaceInvaders> invaders;
    static int ALIEN_INIT_X = 150;//Position of first invader
    static int ALIEN_INIT_Y = 5;
    static int score=100;
    static int NumberOfInvaders=24;
    static int InvaderRows=4;
    static int InvaderCols=6;
    static int ALIEN_HEIGHT = 5;
	static int ALIEN_WIDTH = 5;
    static int BOARD_HEIGHT = 300;//temporary board limits
    static int BOARD_WIDTH = 300;//temporary board limits
    static int BOARD_MARGIN = 5; //So the alien doesn't go beyond the margins

  //Create bullet
	static Bullet bullet=new Bullet();

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

}
