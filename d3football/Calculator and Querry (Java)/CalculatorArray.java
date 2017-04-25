import java.awt.Color;
import java.util.Random;

public class CalculatorArray{
	private Boolean isRealData;
	private double[][][] CalculatorArray;
	private Random rand;
	
	public static int yardlineSize = CalculatorRunner.yardlineSize;
	public static int distanceSize = CalculatorRunner.distanceSize;
	public static int playSize = CalculatorRunner.playSize;
	
	public CalculatorArray(){
		isRealData = false;
		CalculatorArray = new double[yardlineSize][][];
		rand = new Random();
		
		for(int yardline = 0; yardline < yardlineSize;yardline++){
			CalculatorArray[yardline] = new double[distanceSize][];    			    			
			for(int distance = 0; distance < distanceSize;distance++){
				CalculatorArray[yardline][distance] = new double[playSize];
				for(int play = 0; play < playSize; play++){
					CalculatorArray[yardline][distance][play] = 0;
				}
			}
		}		
	}

	public void randomizeArray(){
		isRealData = false;
		
		for(int yardline = 0; yardline < yardlineSize;yardline++){
			CalculatorArray[yardline] = new double[distanceSize][];    			    			
			for(int distance = 0; distance < distanceSize;distance++){
				CalculatorArray[yardline][distance] = new double[playSize];
				for(int play = 0; play < playSize; play++){
					CalculatorArray[yardline][distance][play] = rand.nextInt(100) - 50;// randomly generated expected points
				}
			}
		}		
		
	}
	
	public Boolean getIsRealData(){
		return isRealData;
	}
	
	public Random getRandom(){
		return rand;
	}
	
	public double[][][] getCloneArray(){
		return CalculatorArray.clone();
	}
	
	public void setExpectedPoints(int yardline, int distance, int play, double ExpectedPoints){
		CalculatorArray[yardline][distance][play] = ExpectedPoints;
	}
	
	public double[][] getDistances(int yardline){
		return CalculatorArray[yardline];
	}
	
	public double[] getPlays(int yardline, int distance){
		return CalculatorArray[yardline][distance];
	}
	
	public double getExpectedPoints(int yardline, int distance, int play){
		return CalculatorArray[yardline][distance][play];
	}
	
	public double[][] getDistance(int yardlineIndex){
		return CalculatorArray[yardlineIndex];
	}
	
}
