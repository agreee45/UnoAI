import java.util.ArrayList;


public class MobileState {

	private Coordinate guarder;
	private ArrayList<PreviousTarget> array;

	public MobileState(Coordinate guarder, ArrayList<Player> players){

		this.guarder = guarder;
		for(int i =0 ; i < players.size(); i++){
			PreviousTarget temp = new PreviousTarget(players.get(i).getNextLocation(), players.get(i).getTarget());
			this.array.add(temp);
		}
	}

	public ArrayList<PreviousTarget> getArray(){
		return this.array;
	}	
}
class PreviousTarget{
	private Coordinate player;
	private Coordinate target;

	public PreviousTarget(Coordinate player, Coordinate target){
		this.player = player;
		this.target = target;
	}
	public Coordinate getPlayer(){
		return this.player;
	}
	public Coordinate getTarget(){
		return this.target;
	}
}