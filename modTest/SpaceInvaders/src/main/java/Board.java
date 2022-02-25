package src.main.java;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.Random;

	public class Board {

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

	   public Board() {
		   InvadersCreation();
	    }

	    
	    public static void main(String[] args){
	    	Board board=new Board();
	    	board.updateBoard();
	    }

	    public static void InvadersCreation() {
	    	invaders=new ArrayList<>();
	    	 for (int i = 0; i < InvaderRows; i++) {
	             for (int j = 0; j < InvaderCols; j++) {

	            	 SpaceInvaders invader=new SpaceInvaders(ALIEN_INIT_X + 18 * j, ALIEN_INIT_Y + 18 * i,(i+1)*10);
	                 invaders.add(invader);
	                 //System.out.println("Invader number "+invader.invaderId+" was created");
	             }
	         } 
		}
	    
	    public static void updateBoard(){
	    	for(SpaceInvaders invader: invaders) {
	    		if(invader.isAlive==true) {
	    			//System.out.println("Invader postitons "+invader.x+" , "+invader.y);
	    		}
	    		
	    		int count=0;
	    		invaderMove(invader,count);
	    		
	    		//Pick a random invader
	    		Random generator = new Random();
		    	int randomInvader=generator.nextInt(NumberOfInvaders);
		    	invaderShooting(invader,randomInvader);
	    	}
	    }
	// Space invader shooting
	    public static void invaderShooting(SpaceInvaders invader,int randomInvader){
	    	//If there is no bullet being shot (dropped==false) and alien is alive (isAlive=true)
	    		System.out.println("Invader num "+invader.invaderId);
	    		if((invader.invaderId==randomInvader)&&(invader.isAlive==true) && (bullet.dropped==false)) {
	    			bullet.set(invader.x,invader.y);
	    			bullet.dropped=true;// set back to false once bullet has reached target or left the board
	    			//System.out.println("Invader number "+invader.invaderId+" is shooting");
	    		}		
	    	
	    }
	    

	  //Aliens movement		
		public static void invaderMove(SpaceInvaders invader,int count){ //Check if right border is reached 
				invader.invadersMove(invaders); 
				if(invader.getxpos() >= BOARD_WIDTH - BOARD_MARGIN - ALIEN_WIDTH) {
					//System.out.println("Space invaders have reached the right border."); 
					count++; 
				} 
			//System.out.println("Number of times the space invaders have reached the right border: " + count); 
		}	
		 
	}


