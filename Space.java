
public class Space {
	private int type;
	private Coordinate location;
	private String str;
	
	public Space(Coordinate location, int type, char charr){
		this.type = type;
		this.location = location;
		this.str = ""+charr;
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
