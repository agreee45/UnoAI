
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
			connection.SendPacket(new Packet(protocol, "SignIn", new Object[]{"Guest11"}));
			connection.SendPacket(new Packet(protocol, "SetConstantPlayer", new Object[]{}));

			// Join "Team One"'s existing game.
			connection.SendPacket(new Packet(protocol, "JoinGame", new Object[]{"Host11"}));
			Thread.sleep(500);
			gamePlayLoop(protocol, connection, true);
		}
		else{
			// Sign into the server with your team name and lock yourself into team "a".
			connection.SendPacket(new Packet(protocol, "SignIn", new Object[]{"Host11"}));
			Thread.sleep(500);
			connection.SendPacket(new Packet(protocol, "SetConstantPlayer", new Object[]{}));
			Thread.sleep(500);
			// Create a tournament game with the default seed of zero.
			connection.SendPacket(new Packet(protocol, "CreateTournament", new Object[]{"0"}));
			Thread.sleep(500);

			//loop until the other joins
			boolean loop = true;
			while(loop){
				connection.SendPacket(new Packet(protocol, "ListPlayers", new Object[]{}));
				Thread.sleep(2000);

				if(this.packet.Data.get("Type").equals("PlayerList")){
					String list = (String) this.packet.Data.get("List");
					if(list.contains("Host11;"))
						loop = false;
				}
			}
			gamePlayLoop(protocol, connection, false);
		}
		//after game ends code terminates
		while(true){

		}
	}

	private void gamePlayLoop(Protocol protocol, Connection connection, boolean joinedOrNot) throws Exception{
		//convertToMap

		System.out.println("got into game loop");

		int animationBarCounter = 0;

		//convertToMap
		connection.SendPacket(new Packet(protocol, "RequestGameState", new Object[]{}));
		Thread.sleep(1000);	
		String state = (String) this.packet.Data.get("State");
		Byte b = (Byte) (this.packet.Data.get("Width"));
		int width = Integer.parseInt(b.toString());
		Byte c = (Byte) (this.packet.Data.get("Height"));
		int height = Integer.parseInt(c.toString());
		System.out.println("initiating convertomap");
		ConvertToMap map = new ConvertToMap(state, width, height);
		System.out.println("initiating state");
		State stateInfo = new State(map);

		System.out.println("about to print the map");
		map.printOutMap(map.getMap(), animationBarCounter);

		System.out.println("about to enter Mockmover");
		MockMover mover = new MockMover(stateInfo);
		System.out.println("got out of mockmover");
		if(!joinedOrNot){
			mover.makeMove();
			sendMove(connection, protocol, stateInfo);
			System.out.println("First move sent!");
		}
		else{
			Thread.sleep(300);
		}

		//initiate game
		String temp = JOptionPane.showInputDialog("start the game!");
		boolean firstOnly;
		if(joinedOrNot){
			firstOnly = false;
		}
		else{
			firstOnly = true;
		}
		while(true){
			if(animationBarCounter == height){
				animationBarCounter = 0;
			}
			//loop to wait until we get confirmation
			boolean loop1 = true;
			while(loop1 && firstOnly){
				Thread.sleep(100);
				if(this.packet.Data.get("Type").equals("Success")){
					loop1 = false;
					String temp111 = JOptionPane.showInputDialog("got Success back for my move");
				}
				else if(this.packet.Data.get("Type").equals("Fail")){
					System.out.println("Our move did not work");
					System.out.println("Reason being:");
					System.out.println(this.packet.Data.get("Reason"));
				}

				if(loop1 == false){
					//reprint our move
					map.printOutMap(map.getMap(), animationBarCounter);
				}
			}
			firstOnly = true;

			//loop until we get our turn
			boolean loop=true;
			while(loop)
			{
				Thread.sleep(500);
				if(this.packet.Data.get("Type").equals("YourTurn")){
					loop = false;
					String temp111 = JOptionPane.showInputDialog("got Yourturn back");
				}
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

			//get information again
			state = (String) this.packet.Data.get("State");
			b = (Byte) (this.packet.Data.get("Width"));
			width = Integer.parseInt(b.toString());
			c = (Byte) (this.packet.Data.get("Height"));
			height = Integer.parseInt(c.toString());
			map = new ConvertToMap(state, width, height);
			stateInfo = new State(map);
		
			mover.reset(stateInfo);
			mover.makeMove();
			sendMove(connection, protocol, stateInfo);

			//request game score and display later
//			connection.SendPacket(new Packet(protocol, "RequestScores", new Object[]{}));
//			Thread.sleep(500);
//			String score = (String) this.packet.Data.get("ScoreList");
//			System.out.println(score);

			animationBarCounter++;

		}
		
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
		else if(packet.Data.get("Type").equals("PlayerList")){
			this.packet = packet;
		}
	}

	public void sendMove(Connection connection, Protocol protocol, State state) throws Exception{
		String string = "";
		for(int z = 0 ; z < state.getPlayers().size(); z++){
			Coordinate temp1 = state.getPlayers().get(z).getCurrentLocation();
			Coordinate temp2 = state.getPlayers().get(z).getNextLocation();
			if(!temp1.equals(temp2)){
				string = string + temp1.getX() + "x" + temp1.getY() + "-" + temp2.getX() + "x" + temp2.getY();
				if(z < state.getPlayers().size()-1){
					string = string + ";";
				}
			}
		}
		String temp = JOptionPane.showInputDialog("sendingmove");
		connection.SendPacket(new Packet(protocol, "MakeMove", new Object[]{string}));
		Thread.currentThread().sleep(1000);
	}

}

