import java.util.ArrayList;


public class Player {

	public int depthLimit;
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
	
	private int currentDepth;
	private int closestDistanceToTarget;
	
	private boolean detourMode;
	private Coordinate detourTarget;
	private int detourCount;
	
	public Player(Coordinate location, char charr, boolean enemy){
		this.halted = false;
		this.location = location;
		this.targetLocation = null;
		this.flagPosession = false;
		this.allMoves = new ArrayList<Coordinate>();
		this.heldFlag = null;
		this.setTargetedFlag(null);
		
		this.currentDepth = 0;
		this.closestDistanceToTarget = width;
		this.detourMode = false;
		this.detourCount = 0;
		this.depthLimit = 0;
		this.enemy = enemy;
	}
	
	//detour counts
	public int getDetourCount(){
		return this.detourCount;
	}
	public void addDetourCount(){
		this.detourCount++;
	}
	public void resetDetourCount(){
		this.detourCount = 0;
	}
	//Detour Mode
	public void setDetourMode(boolean truth){
		this.detourMode = truth;
	}
	public boolean getDetourMode(){
		return this.detourMode;
	}
	//detour target
	public void setDetourTarget(Coordinate target){
		this.detourTarget = target;
		setDepthLimit(this.location.difference(target));
		this.setClosestDistanceToTarget(this.location.difference(target));
	}	
	public Coordinate getDetourTarget(){
		return this.detourTarget;
	}
	//flag possession
	public void setFlagPosession(boolean bool){
		this.flagPosession = bool;
	}
	public boolean getFlagPossession(){
		return this.flagPosession;
	}
	//closest distance ever reached
	public void setClosestDistanceToTarget(int renew){
		this.closestDistanceToTarget = renew;
	}
	public int getClosestDistanceToTarget(){
		return this.closestDistanceToTarget;
	}
	//depth of current try
	public int getCurrentDepth(){
		return this.currentDepth;
	}
	public void setCurrentDepth(int depth){
		this.currentDepth = depth;
	}
	//set and get depth limit
	public void setDepthLimit(int value){
		this.depthLimit = (int)(value*1.8);
	}
	public int getDepthLimit(){
		return this.depthLimit;
	}
	// Goal
	public void setTarget(Coordinate target){
		this.targetLocation = target;
		setDepthLimit(this.location.difference(target));
		this.setClosestDistanceToTarget(this.location.difference(target));
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
	public Coordinate getLocation(){
		return this.location;
	}
	public void setLocation(Coordinate coordinate){
		this.location = coordinate;
	}
	
	public void setNextMove(Coordinate coordinate){
		this.allMoves.add(coordinate);
	}
	
	public ArrayList<Coordinate> getAllMoves(){
		return this.allMoves;
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
}
