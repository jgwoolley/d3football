import java.awt.Color;
import java.util.Random;


public class CalculatorRunner {
	public static final int yardlineSize = 99;
	public static final int distanceSize = 11;
	public static final int playSize = 3;
	
	public static Color[] colors = new Color[CalculatorArray.playSize];
	public static String[] plays = new String[CalculatorArray.playSize];

	
	public static void main(String[] args) {
		Random rand = new Random();
		for(int x = 0; x < colors.length; x++){
			colors[x] = new Color(rand.nextInt(0xFFFFFF));
			plays[x] = "play " + (x+1);
		}						
		
		CalculatorArray array = new CalculatorArray();
		array.randomizeArray();
		
		CalculatorDisplay display = new CalculatorDisplay(1,array);
	}

}
