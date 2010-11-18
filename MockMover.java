import java.util.ArrayList;


public class MockMover {
	private State state;
	private ArrayList<Coordinate> move;

	public MockMover(State state){
		this.state = state;
		this.move = new ArrayList<Coordinate>();
		this.setup();
	}
	public void reset(State state){
		this.state = state;
		this.setup();
	}

	private void setup(){
		ArrayList<Player> players = this.state.getPlayers();
		for(int i =0; i < players.size(); i++){

			ArrayList<Coordinate> possibleMove = new ArrayList<Coordinate>();
			Player player = players.get(i);
			possibleMove.add(new Coordinate(player.getCurrentLocation().getX()+1, player.getCurrentLocation().getY()));
			possibleMove.add(new Coordinate(player.getCurrentLocation().getX()-1, player.getCurrentLocation().getY()));
			possibleMove.add(new Coordinate(player.getCurrentLocation().getX(), player.getCurrentLocation().getY()+1));
			possibleMove.add(new Coordinate(player.getCurrentLocation().getX(), player.getCurrentLocation().getY()-1));

			for(int z = possibleMove.size()-1; z >= 0; z--){
				if(getSpaceAtCoordinate(possibleMove.get(z)).getType() == 1){
					possibleMove.remove(z);
				}
			}
			if(possibleMove.size() != 0)
				this.move.add(possibleMove.get(0));
			else
				System.out.println("server has error");
		}
	}
	public void makeMove(){
		ArrayList<Player> players = this.state.getPlayers();

		for(int i =0; i < players.size(); i++){
			//make move to where it is permitted
			players.get(i).setNextLocation(this.move.get(i));
		}
	}
	private Space getSpaceAtCoordinate(Coordinate coordinate){
		return this.state.getMap().get(coordinate.getY()).get(coordinate.getX());
	}
}
