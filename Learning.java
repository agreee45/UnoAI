import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;


public class Learning {

	private ArrayList<ArrayList<Double>> percentage;
	private State state;

	public Learning(State state){
		this.percentage = readFile();
		this.state = state;
	}
	private ArrayList<ArrayList<Double>> readFile() {
		BufferedReader input;
		try {
			input = new BufferedReader(new FileReader("PercentageMatrix.txt"));

			String line;
			while ((line = input.readLine()) != null)
			{
				// Remove comments.
				line = line.replaceAll("#[^\n]*$", "");

				// Ignore empty lines.
				if (line.length() == 0 || line.startsWith("#"))
				{
					continue;
				}
			}
		}
		catch (IOException e) {
			System.out.println("There was a problem reading the text:" + e);
		}
	}

	private void writeFile(String text) throws IOException{

		for(int line = 0; line < this.percentage.size(); line++){
			String stringLine;
			for(int digits = 0; digits < this.percentage.get(line).size(); digits++){


			}

			Writer output = null;
			File file = new File("write.txt");
			output = new BufferedWriter(new FileWriter(file));
			output.write(text);
			output.close();
			System.out.println("Your file has been written"); 
		}


	}

	public double accessRandom(int node){

	}

	public void adjustByExpert(){
		//has flag
		if (hasflag)
		{
			//on our side
			if()
			{
				//enemy is right beside us
				if()
				{
					//penalize if they didn't capture the enemy
				}
				//enemy is not beside us
				else
				{
					//penalize if they didn't move towards base
				}
			}
			//on opponent's side
			else
			{
				//enemy is right beside us
				if()
				{
					//penalize if they stayed
				}
				//enemy is not beside us
				else
				{
					//penalize if they didn't move towards base
				}
			}
		}
		//no flag
		else
		{
			//on our side
			if()
			{
				//enemy is right beside us
				if()
				{
					//penalize if the player does not capture enemy
				}
				//enemy is not beside us
				else
				{
					
				}
			}
			//on opponent's side
			else
			{
				//enemy is right beside us
				if()
				{
					//penalize if they stay put
				}
				//enemy is not beside us
				else
				{
					
				}
			}
		}
	}
}
