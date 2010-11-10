

public class Player {

	private boolean halted;
	//change the name of location to current location
	private Coordinate currentLocation;
	//added also, erased the arraylist of allmoves as our algorithm will just find the next move and terminate.
	private Coordinate nextLocation;
	private boolean flagPosession;
	private Space heldFlag;
	private Space targetedFlag;
	private Coordinate targetLocation;
	private boolean enemy;
	
	public Player(Coordinate location, char charr, boolean enemy){
		this.halted = false;
		this.currentLocation = location;
		this.targetLocation = null;
		this.flagPosession = false;
		this.heldFlag = null;
		this.setTargetedFlag(null);
		this.nextLocation= null;
		//whether its our player or not: true if enemy
		this.setEnemy(enemy);
	}
	
	//flag possession
	public void setFlagPosession(boolean bool){
		this.flagPosession = bool;
	}
	public boolean getFlagPossession(){
		return this.flagPosession;
	}
	
	// Goal
	public void setTarget(Coordinate target){
		this.targetLocation = target;
	}
	public Coordinate getTarget(){
		return this.targetLocation;
	}
	//Halted
	public boolean getHalted(){
		return halted;
	}
	public void setHalted(boolean temp){
		this.halted = temp;
	}
	//Coordinate
	public Coordinate getCurrentLocation(){
		return this.currentLocation;
	}
	public void setLocation(Coordinate coordinate){
		this.currentLocation = coordinate;
	}
	
	//changes
	//from here 
	public Coordinate getNextLocation(){
		return this.nextLocation;
	}
	public void setNextLocation(Coordinate nextMove){
		this.nextLocation = nextMove;
	}
	//to here
	
	public void setHeldFlag(Space flagPosessed) {
		this.heldFlag = flagPosessed;
		if(this.targetedFlag !=null){
			this.targetedFlag = null;
		}
	}

	public Space getHeldFlag() {
		return this.heldFlag;
	}

	public void setTargetedFlag(Space targetedFlag) {
		this.targetedFlag = targetedFlag;
	}

	public Space getTargetedFlag() {
		return targetedFlag;
	}

	public void setEnemy(boolean enemy) {
		this.enemy = enemy;
	}

	public boolean getEnemy() {
		return enemy;
	}
}
