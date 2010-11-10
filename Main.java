
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class Main implements ConnectionListener {

	Packet packet;
	public static void main(String[] arstring) throws Exception {
		new Main();
	}


	public Main() throws Exception
	{
		// Load the protocol.
		Protocol protocol = new Protocol("CTFProtocol.txt");

		// Create the connection and tell it to send incoming packets this way.
		Connection connection = new Connection(protocol, "localhost", 2837);
		connection.AddConnectionListener(this);

		String temp = JOptionPane.showInputDialog("0 if joining a game, 1 if creating a game");
		int joinOrMakeGame = Integer.parseInt(temp);
		// Create a tournament game with the default seed of zero.
		if(joinOrMakeGame == 0){
			// Sign into the server with your team name and lock yourself into team "a".
			connection.SendPacket(new Packet(protocol, "SignIn", new Object[]{"Team bla22"}));
			connection.SendPacket(new Packet(protocol, "SetConstantPlayer", new Object[]{}));

			// Join "Team One"'s existing game.
			connection.SendPacket(new Packet(protocol, "JoinGame", new Object[]{"Team One"}));
		}
		else{
			// Sign into the server with your team name and lock yourself into team "a".
			connection.SendPacket(new Packet(protocol, "SignIn", new Object[]{"Team Ybbb"}));
			Thread.sleep(500);
			connection.SendPacket(new Packet(protocol, "SetConstantPlayer", new Object[]{}));
			Thread.sleep(500);
			// Create a tournament game with the default seed of zero.
			connection.SendPacket(new Packet(protocol, "CreateTournament", new Object[]{"0"}));
			Thread.sleep(500);

		}

		//start the game loop
		gamePlayLoop(protocol, connection);
		//after game ends code terminates
		while(true){

		}
	}

	private void gamePlayLoop(Protocol protocol, Connection connection) throws Exception{
		//convertToMap
		connection.SendPacket(new Packet(protocol, "RequestGameState", new Object[]{}));
		Thread.currentThread().sleep(1000);

		String state = (String) this.packet.Data.get("State");
		Byte b = (Byte) (this.packet.Data.get("Width"));
		int width = Integer.parseInt(b.toString());
		Byte c = (Byte) (this.packet.Data.get("Height"));
		int height = Integer.parseInt(c.toString());

		ConvertToMap map = new ConvertToMap(state, width, height);
		int animationBarCounter = 0;

		//print the map

		//initiate game
		String temp = JOptionPane.showInputDialog("start the game!");

		boolean firstOnly = false;
		while(true){
			if(animationBarCounter == height){
				animationBarCounter = 0;
			}

			//loop to wait until we get confirmation
			boolean loop1 = true;
			while(loop1 && firstOnly){
				Thread.sleep(100);
				if(this.packet.Data.get("Type").equals("Success"))
					loop1 = false;
				else if(this.packet.Data.get("Type").equals("Fail")){
					System.out.println("Our move did not work");
					System.out.println("Reason being:");
					System.out.println(this.packet.Data.get("Reason"));
				}

				if(loop1 == false){
					//reprint our move
					System.out.println("reprint");
				}
			}

			firstOnly = true;

			//loop until we get our turn
			boolean loop=true;
			while(loop)
			{
				Thread.sleep(500);
				if(this.packet.Data.get("Type").equals("YourTurn"))
					loop = false;
				//when game over return and get out
				else if(this.packet.Data.get("Type").equals("GameOver")){
					String winner = (String) this.packet.Data.get("Winner");
					System.out.println("winner is: " + winner);

					//reprint their move
					System.out.println("reprint");
					String confirm = JOptionPane.showInputDialog("Do you agree with the outcome?");
					return;
				}
			}
			//convertToMap
			state = (String) this.packet.Data.get("State");
			b = (Byte) (this.packet.Data.get("Width"));
			width = Integer.parseInt(b.toString());
			c = (Byte) (this.packet.Data.get("Height"));
			height = Integer.parseInt(c.toString());
			map = new ConvertToMap(state, width, height);
			
			//request game score and display later
			connection.SendPacket(new Packet(protocol, "RequestScores", new Object[]{}));
			Thread.sleep(500);
			String score = (String) this.packet.Data.get("ScoreList");
			System.out.println(score);
			
			//create working data
			State stateInfo = new State(map);
			//reprint their move
			System.out.println("reprint");
			
			//give the info to the decisions process
			//State newState = nextMove(stateInfo);
			//send move to server
			//sendMove(connection, protocol, newState);

			animationBarCounter++;
		}
	}

		private void rePrint(int animationBarCounter, ArrayList<ArrayList<Space>> twoDArray){
			//for loop for lines
			//		for(int y = 0; y < height; y++)
			//		{
			//			//for loop for columns of the line
			//			for(int x = 0; x < width; x++)
			//			{
			//				if(Wall){
			//					System.out.println("W");
			//				}
			//				else if(Player)
			//				{
			//					String playerAlphabet = array.get(x,y);
			//					System.out.println(playerAlphabet);
			//				}
			//				else if(emptySpace){
			//					System.out.println("-");
			//				}
			//				else if(flag){
			//					System.out.println("");
			//				}
			//
			//
			//			}
			//			if(line == animationBarCounter){
			//				System.out.println("-");
			//			}
			//			else{
			//				System.out.println();
			//			}
			//		}
		}
		public void PacketReceived(Packet packet) {
			if(packet.Data.get("Type").equals("GameState")){
				this.packet = packet;
			}
			else if(packet.Data.get("Type").equals("Success")){
				this.packet = packet;
			}
			else if(packet.Data.get("Type").equals("Fail")){
				this.packet = packet;
			}
			else if(packet.Data.get("Type").equals("GameOver")){
				this.packet = packet;
			}
			else if(packet.Data.get("Type").equals("ElapsedTime")){
				this.packet = packet;
			}
			else if(packet.Data.get("Type").equals("Scores")){
				this.packet = packet;
			}
			else if(packet.Data.get("Type").equals("YourTurn")){
				this.packet = packet;
			}
			else if(packet.Data.get("Type").equals("Scores")){
				this.packet = packet;
			}
			else if(packet.Data.get("Type").equals("Scores")){
				this.packet = packet;
			}
		}

		public void sendMove(Connection connection, Protocol protocol, State state) throws Exception{
			String string = "";
			for(int z = 0 ; z < state.getPlayers().size(); z++){
				Coordinate temp1 = state.getPlayers().get(z).getLocation();
				Coordinate temp2 = state.getPlayers().get(z).getNextLocation();
				if(!temp1.equals(temp2)){
					string = string + temp1.getX() + "x" + temp1.getY() + "-" + temp2.getX() + "x" + temp2.getY();
					if(z < state.getPlayers().size()-1){
						string = string + ";";
					}
				}
			}
			connection.SendPacket(new Packet(protocol, "MakeMove", new Object[]{string}));
			Thread.currentThread().sleep(1000);
		}

	}

