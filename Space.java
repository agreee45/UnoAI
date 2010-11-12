
public class Space {
	private int type;
	private Coordinate location;
	private String str;
	
	public Space(Coordinate location, int type, char charr){
		this.type = type;
		this.location = location;
		if(type == 4){
			//player
			this.str = " " + charr + " ";
		}
		else if(type == 1){
			//wall
			this.str = "" + charr;
		}
		else if(type == 2){
			//base
			this.str = " " + charr  + " ";
		}
		else if(type == 3 || type == 5){
			//flag
			this.str = " " + charr  + " ";
		}
		else{
			//space
			this.str = " " + charr + " ";
		}
	}
	
	public int getType(){
		return this.type;
	}
	
	public Coordinate getLocation(){
		return this.location;
	}
	public String getString(){
		return this.str;
	}
	
}
