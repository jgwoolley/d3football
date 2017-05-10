package runner;

import runner.*;
import datastructures.*;
import functions.*;
import outputs.*;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class Runner {
	//downs to be calculated for
	public static final int[] downs = {4};	
	//number of total yardlines
	public static final int yardlineSize = 99;
	//number of total distances per yardline
	public static final int distanceSize = 11;
	
	public static final double EPmin = -2;
	public static final double EPmax = 7;
	
	//each play is assigned a name (string)
	public static String[] playNames = {"Goal","Rebound","Volley"};	
	//total number of possible plays, limited by the elements of color.
	public static final int playSize = playNames.length;
	//each play is assigned a color
	public static Color[] colors = {Color.RED,Color.BLUE,Color.YELLOW,Color.GREEN,Color.MAGENTA,Color.MAGENTA};
	
	public static final boolean deBugMode = false;
	
	public static void main(String[] args) throws FileNotFoundException, IOException{
		
		d3footballDisplay fb = new d3footballDisplay();
		
		/*
		boolean helloIsMyDatabasethere = false;
				
		if(helloIsMyDatabasethere){
			if(deBugMode) System.out.println("Database was found");
			qArray query = Query.Query();
			cArray calculation = Calculator.calculation(query);
			
			int length = downs.length;		
			
			for(int i = 0; i < length;i++){	
				Display display = new Display(downs[i],calculation);
				if(deBugMode) System.out.println("Down " + i + "Display created");
				display.label.setText("This is impossible right now");				
				Writer cw = new Writer(downs[i], calculation);
				if(deBugMode) System.out.println("Down " + i + "Writer created");
			}
		}
		else{			
			if(deBugMode) System.out.println("Database was not found");
			
			int length = downs.length;		
			
			for(int i = 0; i < length;i++){
				cArray array = new cArray();
				array.randomizeArray(false);
				
				Display display = new Display(downs[i],array);
				display.label.setText("No Database was found. Generating Random Expected Points .CSV and Display");
				
				Writer cw = new Writer(downs[i], array);
			}
		}
		*/		
	}	
}
