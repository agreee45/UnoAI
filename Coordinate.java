/**
 * 
 */

/**
 * @author baekj
 *
 */
public class Coordinate {
	
	
	private int x;
	private int y;
	
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public boolean equals(Coordinate second){
		if(x == second.getX() && this.y == second.getY()){
			return true;
		}
		else
			return false;
	}
	public int difference(Coordinate temp){
		int diffX = Math.abs(this.x - temp.getX());
		int diffY = Math.abs(this.y - temp.getY());
		return diffX + diffY;
	}
	
}
