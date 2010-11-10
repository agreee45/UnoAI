import java.util.ArrayList;


public class Goal {

	private ArrayList<Space> flags;
	private ArrayList<Space> flagsHeld;
	private int totalFlags;
	private int flagsReturned;
	private Space base;
	
	public Goal(ArrayList<Space> coordinates, Space base){
		this.flags = coordinates;
		this.flagsReturned = 0;
		this.totalFlags = coordinates.size();
		this.base = base;
	}
	
	public ArrayList<Space> getFlags(){
		return this.flags;
	}
	
	public Space getBase(){
		return this.base;
	}
	//flags count
	public void addFlagsCaptured(){
		this.flagsReturned++;
	}
	public int getFlagsCaptured(){
		return this.flagsReturned;
	}
	public ArrayList<Space> getFlagsHeld(){
		return this.flagsHeld;
	}
	public int getTotalFlags(){
		return this.totalFlags;
	}
}
