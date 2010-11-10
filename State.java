import java.util.ArrayList;


public class State {
	
	private ArrayList<ArrayList<Space>> map;
	private ArrayList<Player> players;
	private ArrayList<Player> enemyPlayers;
	private Goal goal;
	private int height;
	private int width;

	public State(ConvertToMap info){
		this.height = info.getHeight();
		this.width = info.getWidth();
		this.map = info.getMap();
		this.goal = new Goal(info.getFlags(), info.getBase());
		this.players = info.getPlayers();
		this.enemyPlayers = info.getEnemyPlayers();
	}
	private ArrayList<Player> initiatePlayers(ArrayList<Player> temp)
	{
		this.players = new ArrayList<Player>();
		for(int i = 0; i < temp.size() ; i++){
			this.players.add(new Player(temp.get(i).getLocation(), map.size()));
			this.players.get(i).setNextMove(temp.get(i).getLocation());
		}
		return players;
	}
	public int getHeight(){
		return this.height;
	}
	public int getWidth(){
		return this.width;
	}
	public ArrayList<Player> getPlayers(){
		return this.players;
	}
	public Goal getGoal(){
		return goal;
	}
	public ArrayList<ArrayList<Space>> getMap(){
		return this.map;
	}
}



