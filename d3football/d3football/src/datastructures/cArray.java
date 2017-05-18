package datastructures;

import runner.*;
import datastructures.*;
import functions.*;
import outputs.*;

import java.awt.Color;
import java.util.Random;
//Make null values able to be entered; array of double + boolean class
public class cArray{
	private Random rand;
	
	public static int yardlineSize = Runner.yardlineSize;
	public static int distanceSize = Runner.distanceSize;
	public static int playSize = Runner.playSize;
	private double[][][] CalculatorArray = new double[yardlineSize][distanceSize][playSize];
	
	public cArray(){
		if(Runner.deBugMode) System.out.println("cArray Creation Begins");		
		rand = new Random();		
		for(int yardline = 0; yardline < yardlineSize;yardline++){
			CalculatorArray[yardline] = new double[distanceSize][];    			    			
			for(int distance = 0; distance < distanceSize;distance++){
				CalculatorArray[yardline][distance] = new double[playSize];
				for(int play = 0; play < playSize; play++){
					CalculatorArray[yardline][distance][play] = -3;
				}
			}
		}		
		if(Runner.deBugMode) System.out.println("cArray Creation Completed");

	}

	public void randomizeArray(boolean wantOutOfBounds){
		if(Runner.deBugMode) System.out.println("cArray Begins to be Randomized");
		
		for(int yardline = 0; yardline < yardlineSize;yardline++){
			CalculatorArray[yardline] = new double[distanceSize][];    			    			
			for(int distance = 0; distance < distanceSize;distance++){
				CalculatorArray[yardline][distance] = new double[playSize];
				for(int play = 0; play < playSize; play++){
					if(!wantOutOfBounds){
						CalculatorArray[yardline][distance][play] = rand.nextInt((int) (Runner.EPmax - Runner.EPmin)) + Runner.EPmin;// randomly generated expected points
					}
					else{
						CalculatorArray[yardline][distance][play] = rand.nextInt((int) (Runner.EPmax - Runner.EPmin + 1) ) + Runner.EPmin;// randomly generated expected points
					}					
				}
			}
		}				
		if(Runner.deBugMode) System.out.println("cArray Randomization Completed");
	}
	
	
	public Random getRandom(){
		return rand;
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
