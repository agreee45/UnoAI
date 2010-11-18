import java.util.ArrayList;

/**
 * 
 */

/**
 * @author baekj
 *
 */
public class ConvertToMap {

	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Player> enemyPlayers = new ArrayList<Player>();
	private Space base;
	private ArrayList<Space> targetFlags = new ArrayList<Space>();
	private ArrayList<Space> defendFlags = new ArrayList<Space>();
	private ArrayList<ArrayList<Space>> map = new ArrayList<ArrayList<Space>>();
	private int width;
	private int height;

	public ConvertToMap(String map, int width, int height){
		this.width = width;
		this.height = height;
		for(int i = 0; i < width; i++){
			this.map.add(new ArrayList<Space>());
		}
		findPOI(map);
	}
	public void findPOI(String map){
		int x = 0;
		int y = 0;
		String tempString = "";

		for(int i = 0; i < map.length(); i++){
			if(x == width)
			{
				x=0;
				y++;
			}
			if(map.charAt(i) == 'X'){
				this.targetFlags.add(new Space(new Coordinate(x, y),3, map.charAt(i)));
				this.map.get(y).add(this.targetFlags.get(this.targetFlags.size()-1));
			}
			else if(map.charAt(i) == 'F'){
				this.defendFlags.add(new Space(new Coordinate(x, y),5, map.charAt(i)));
				this.map.get(y).add(this.defendFlags.get(this.defendFlags.size()-1));
			}
			else if(map.charAt(i) == 'W'){
				this.map.get(y).add(new Space(new Coordinate(x, y),1, map.charAt(i)));
			}

			//players
			//with flags
			else if(map.charAt(i) >64 && map.charAt(i) < 91){
				//us
				if('0' == map.charAt(i)-65){
					this.players.add(new Player(new Coordinate(x, y),map.charAt(i),false));
					this.map.get(y).add(new Space(new Coordinate(x, y),4, map.charAt(i)));
				}
				//enemy
				else{
					this.enemyPlayers.add(new Player(new Coordinate(x, y),map.charAt(i),true));
					this.map.get(y).add(new Space(new Coordinate(x, y),4, map.charAt(i)));
				}
			}
			//without flags
			else if(map.charAt(i) > 96 && map.charAt(i) < 123){
				//us
				if('0' == map.charAt(i)-97){
					this.players.add(new Player(new Coordinate(x, y),map.charAt(i),false));
					this.map.get(y).add(new Space(new Coordinate(x, y),4, map.charAt(i)));
				}
				//enemy
				else{
					this.enemyPlayers.add(new Player(new Coordinate(x, y),map.charAt(i),true));
					this.map.get(y).add(new Space(new Coordinate(x, y),4, map.charAt(i)));
				}
			}
			//base
			else if(map.charAt(i) > 47 && map.charAt(i) < 58){
				this.base = new Space(new Coordinate(x, y),2,map.charAt(i));
				this.map.get(y).add(this.base);
			}
			else{
				this.map.get(y).add(new Space(new Coordinate(x, y),0, map.charAt(i)));
			}
			x++;
		}
		for(int i = 0 ; i < 4 ; i++){
			System.out.println("done");
		}
	}
	public int getWidth(){
		return this.width;
	}
	public int getHeight(){
		return this.height;
	}
	public ArrayList<Player> getPlayers(){
		return this.players;
	}
	public ArrayList<Player> getEnemyPlayers(){
		return this.enemyPlayers;
	}

	public ArrayList<Space> getTargetFlags(){
		return this.targetFlags;
	}
	public ArrayList<Space> getDefendFlags(){
		return this.defendFlags;
	}
	public Space getBase(){
		return this.base;
	}
	public ArrayList<ArrayList<Space>> getMap(){
		return this.map;
	}
	public void printOutMap(ArrayList<ArrayList<Space>> map, int animationBar){
		for(int i = 0; i < map.size(); i++){
			for(int z = 0; z < map.get(i).size(); z++){
				System.out.print(this.map.get(i).get(z).getString());
				if(z == map.size() -1 && i == animationBar){
					System.out.print("+++");
				}
			}
			System.out.println();
		}
	}
}
