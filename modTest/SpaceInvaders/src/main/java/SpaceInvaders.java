package main.java;



public class SpaceInvaders {
int x;
int y;
int points;
    public static void main(String[] args)
    {
        //System.out.print("here");
    }

    //public static void updateScreen(array with alien positions)
    //score
    //get position
    //bomb
    //Constructor
    public SpaceInvaders(int xpos,int ypos,int points){//put default value
        this.x=xpos;
        this.y=ypos;
        this.points=points;

    }
    public static void invaderMove(){
        //go to right until wall, go down, go to left until wall, go down... until bottom
    }
    public boolean invaderShot(int xpos,int ypos)
        {
    	//Assuming invaders are 5x5 sized
    		if ((xpos+2 >= x) && (x >=xpos-2) && (ypos+2 >= y) && (y >=ypos-2)){
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

