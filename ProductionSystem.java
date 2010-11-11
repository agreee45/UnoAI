import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;


public class ProductionSystem {

	private State state;

	public ProductionSystem(State state)
	{
		this.state = state;
	}

	public boolean initiateMove(){
		while(true){
			ArrayList<Coordinate> recentPlayerMoves = new ArrayList<Coordinate>();
			for(int v = 0; v < this.state.getPlayers().size(); v++)
			{
				Player tempPlayer = this.state.getPlayers().get(v);
				//if they don't have flag
				if(!tempPlayer.getFlagPossession() && !tempPlayer.getHalted())
				{
					boolean	flagCheck = setClosestFlag(tempPlayer);
					if(flagCheck == true){
						production(tempPlayer);
					}
				}
				//if they have flag in possession
				else if(tempPlayer.getFlagPossession() && !tempPlayer.getHalted())
				{
					//go towards base
					production(tempPlayer);
				}
				//if the player is halted
				else
				{
					tempPlayer.setNextMove(tempPlayer.getLocation());
				}
				recentPlayerMoves.add(tempPlayer.getAllMoves().get(tempPlayer.getAllMoves().size()-1));
			}

			//visualize their moves
			
			ArrayList<ArrayList<Space>> map = this.state.getMap();
			for(int i = 0; i < map.size(); i++){
				for(int z = 0; z < map.get(i).size(); z++){
					Coordinate compare = new Coordinate(i,z);
					boolean bool = false;
					for(int x = 0; x < recentPlayerMoves.size(); x++){
						if(compare.equals(recentPlayerMoves.get(x))){
							System.out.print("0");
							bool = true;
						}
					}
					if(bool){

					}
					else{
						if(map.get(i).get(z).getType() == 3){
							System.out.print("F");
						}
						else if(map.get(i).get(z).getType() == 1){
							System.out.print("W");
						}
						else if(map.get(i).get(z).getType() == 0){
							System.out.print("-");
						}
						else if(map.get(i).get(z).getType() == 4){
							System.out.print("-");
						}
						else if(map.get(i).get(z).getType() == 2){
							System.out.print("a");
						}
					}
				}
				System.out.println();
			}
			String someString = JOptionPane.showInputDialog("Enter someString");


			//if all players are halted and the not all flags are captured, there is no solution
			int check = 0;
			for(int v = 0; v < this.state.getPlayers().size(); v++)
			{
				if(this.state.getPlayers().get(v).getHalted() == true)
				{
					check++;
				}
			}
			if(check == this.state.getPlayers().size()){
				if(this.state.getGoal().getFlagsCaptured() == this.state.getGoal().getTotalFlags())
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
	}

	private void production(Player player)
	{
		//create possible moves
		ArrayList<Coordinate> possibleMove = new ArrayList<Coordinate>();
		Coordinate temp = new Coordinate(player.getLocation().getX()+1, player.getLocation().getY());
		if(!(getSpaceAtCoordinate(temp).getType() == 1))
			possibleMove.add(temp);
		temp = new Coordinate(player.getLocation().getX()-1, player.getLocation().getY());
		if(!(getSpaceAtCoordinate(temp).getType() == 1))
			possibleMove.add(temp);
		temp = new Coordinate(player.getLocation().getX(), player.getLocation().getY()+1);
		if(!(getSpaceAtCoordinate(temp).getType() == 1))
			possibleMove.add(temp);
		temp = new Coordinate(player.getLocation().getX(), player.getLocation().getY()-1);
		if(!(getSpaceAtCoordinate(temp).getType() == 1))
			possibleMove.add(temp);

		//find the step
		Coordinate coordinate = findNextStep(possibleMove, player);

		
		//deal with the found step
		if(coordinate == null)
		{
			player.setHalted(true);
			player.setNextMove(player.getLocation());
			return;
		}

		//when the player is moving onto the flag
		for(int z = 0; z < state.getGoal().getFlags().size(); z++){
			if(coordinate.difference(state.getGoal().getFlags().get(z).getLocation()) == 0){
				if(player.getFlagPossession() == false){
					//player has got to the flag and picked it up
					player.setNextMove(coordinate);
					player.setTarget(this.state.getGoal().getBase().getLocation());
					player.setHeldFlag(player.getTargetedFlag());
					player.setFlagPosession(true);
					return;
				}
			}
		}
		//when the player is moving right beside a base
		if(coordinate.difference(state.getGoal().getBase().getLocation()) == 1){
			if(player.getFlagPossession() == true){
				//player has got to base and thrown the flag to the base
				player.setNextMove(coordinate);
				player.setFlagPosession(false);
				this.state.getGoal().addFlagsCaptured();
				player.resetDetourCount();
			}
		}
		else if(player.getDetourMode()){
			if(player.getDetourTarget().difference(coordinate)==0){
				player.setDetourMode(false);
				player.setDepthLimit(player.getLocation().difference(player.getTarget()));
				player.setNextMove(coordinate);
			}
		}
		else
		{
			player.setNextMove(coordinate);
		}
	}


	//returns null when there is no next move.
	private Coordinate findNextStep(ArrayList<Coordinate> possibleMove, Player player){
		//try different things
		Coordinate coordinate;
		if(!player.getDetourMode())
		{
			coordinate = minimumDifference(possibleMove, player, player.getTarget());
		}
		else
		{
			coordinate = minimumDifference(possibleMove, player, player.getDetourTarget());
		}
		//algorithm when they are stuck
		if(coordinate == null){
			if(player.getDetourCount() < 3)
			{
				player.setDetourMode(true);
				//calculate new target
				Coordinate detourTarget;
				if(player.getDetourCount() == 0)
				{
					detourTarget = setNextDetourTarget(player, 0);
					player.addDetourCount();
				}
				else if(player.getDetourCount() == 1)
				{
					detourTarget = setNextDetourTarget(player, 1);
					player.addDetourCount();
				}
				else
				{
					detourTarget = setNextDetourTarget(player,2);
					player.addDetourCount();
				}
				player.setDetourTarget(detourTarget);
				//get the move when the new target is set.
			}
			else
			{
				//will make it return no solution
				return null;
			}
		}
		return coordinate;
	}

	//randomly choose between same heuristics
	private Coordinate minimumDifference(ArrayList<Coordinate> possibleMove, Player player, Coordinate target){
		int min = possibleMove.get(0).difference(target);
		//find the minimum value
		for(int i = 0 ; i < possibleMove.size(); i++){
			int tempNum = possibleMove.get(i).difference(target);
			if(min > tempNum){
				min = tempNum;
			}
		}
		//check if the player is stuck and didn't improve in distance for 20 moves
		if(min >= player.getClosestDistanceToTarget()){
			if(player.getCurrentDepth() == player.getDepthLimit()){
				//do something else
				player.setCurrentDepth(0);
				return  null;
			}
			else{
				player.setCurrentDepth(player.getCurrentDepth()+1);
			}
		}
		else{
			//if the player made an improvement in distance, renew the current closest distance
			player.setClosestDistanceToTarget(min);
			player.setCurrentDepth(0);
		}
		//add in all the minimum nodes
		ArrayList<Coordinate> minimums = new ArrayList<Coordinate>();
		for(int i = 0; i < possibleMove.size(); i++){
			if(possibleMove.get(i).difference(target) == min){
				minimums.add(possibleMove.get(i));
			}
		}
		Random generator = new Random();
		int chosen = (int) Math.round(generator.nextDouble()*(minimums.size()-1));
		return minimums.get(chosen);
	}
	private boolean setClosestFlag(Player player){
		Space flagTargeted;
		ArrayList<Space> flags = this.state.getGoal().getFlags();
		if(flags.isEmpty()){
			player.setHalted(true);
			return false;
		}
		int min = flags.get(0).getLocation().difference(player.getLocation());
		flagTargeted = flags.get(0);
		int index = 0;
		for(int i = 1; i < flags.size(); i++){
			int temp = flags.get(i).getLocation().difference(player.getLocation());
			if(temp < min)
			{
				min = temp;
				flagTargeted = flags.get(i);
				index = i;
			}
		}
		player.setTargetedFlag(flagTargeted);
		player.setTarget(flagTargeted.getLocation());
		return true;
	}

	private Coordinate setNextDetourTarget(Player player, int orientation){
		int x, y;
		int dy = player.getTarget().getY() - player.getLocation().getY();
		int dx = player.getTarget().getX() - player.getLocation().getX();
		// angle to where the detour will be target to
		double angle;

		if(orientation == 0){
			if(dx == 0){
				if(dy > 0)
					angle = 0;
				else
					angle = Math.PI;
			}
			else{
				if(dx > 0){
					if(dy > 0)
						angle = Math.atan(dy/dx) - Math.PI/2;
					else{
						angle = (3*Math.PI/2 - Math.atan(dy/dx)) - Math.PI/2;
					}
				}
				else{
					if(dy < 0)
						angle = (Math.PI + Math.atan(dy/dx)) - Math.PI/2;
					else
						angle = (Math.PI/2 - Math.atan(dy/dx)) - Math.PI/2;
				}
			}
		}
		else if(orientation == 1){
			if(dx == 0){
				if(dy > 0)
					angle = Math.PI;
				else
					angle = 0;
			}
			else{
				if(dx > 0){
					if(dy > 0)
						angle = Math.atan(dy/dx) + Math.PI/2;
					else{
						angle = (3*Math.PI/2 - Math.atan(dy/dx)) + Math.PI/2;
					}
				}
				else{
					if(dy < 0)
						angle = (Math.PI + Math.atan(dy/dx)) + Math.PI/2;
					else
						angle = (Math.PI/2 - Math.atan(dy/dx)) + Math.PI/2;
				}
			}
		}
		else{
			Random generator = new Random();
			double xRandom = generator.nextDouble();
			double yRandom = generator.nextDouble();

			x = (int) (xRandom * (state.getWidth()));
			y = (int) (yRandom * (state.getHeight()));

			return new Coordinate(x,y);
		}


		//find what distance x has to go
		int appliedDx = (int) (((state.getWidth())/3) * Math.cos(angle));
		//find what distance y has to go
		int appliedDy =  (int) (((state.getHeight())/3) * Math.sin(angle));


		x = player.getLocation().getX();
		y = player.getLocation().getY();

		if((x + appliedDx) <0){
			x = 0;
			if((x + appliedDx) >= state.getWidth()){
				x = state.getWidth() - 1;
			}
		}
		if((y + appliedDy) <0 ){
			y = 0;
			if((y + appliedDy) >= state.getHeight()){
				y = state.getHeight() - 1;
			}
		}
		return new Coordinate(x,y);
	}

	private Space getSpaceAtCoordinate(Coordinate coordinate){
		return this.state.getMap().get(coordinate.getX()).get(coordinate.getY());
	}

	private void setNewSpace(Space space){
		this.state.getMap().get(space.getLocation().getX()).remove(space.getLocation().getY());	
		this.state.getMap().get(space.getLocation().getX()).add(space.getLocation().getY(), space);
	}
}