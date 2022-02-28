package src.main.java;
public class Bullet{
	boolean dropped;
	int x;
	int y;
	
	public Bullet(){
        dropped=false;		
	}
	
	public void set(int x, int y) {
		this.x=x;
        this.y=y;
	}
	

}
