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
		this.goal = new Goal(info.getTargetFlags(), info.getBase());
		this.players = info.getPlayers();
		this.setEnemyPlayers(info.getEnemyPlayers());
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
	public void setEnemyPlayers(ArrayList<Player> enemyPlayers) {
		this.enemyPlayers = enemyPlayers;
	}

	public ArrayList<Player> getEnemyPlayers() {
		return enemyPlayers;
	}
}



