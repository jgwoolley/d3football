package outputs;

import runner.*;
import datastructures.*;
import functions.*;
import outputs.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


public class Writer extends FileWriter{
	public Writer(int down, cArray input) throws IOException, FileNotFoundException{		
		super(new File(down + "down.csv"));
		File file = new File(down + "down.csv");
		
		if(!file.exists()){
			file.createNewFile();
			if(Runner.deBugMode) System.out.println("New File " + file.toString() + " Created");
		}
		else{
			if(Runner.deBugMode) System.out.println(file.toString() + " Exists, Yay!");
		}
		
		if(Runner.deBugMode) System.out.println(file.toString() + " Writing Begins");
		
		for(int yardline = 0; yardline < cArray.yardlineSize;yardline++){
			for(int distance = 0; distance < cArray.distanceSize;distance++){
				for(int play = 0; play < cArray.playSize; play++){
					double temp = input.getExpectedPoints(yardline, distance, play);
					append("" + temp);

					if(yardline!=cArray.yardlineSize | distance!=cArray.distanceSize){
						append(",");
					}
				}
			}
			append(System.lineSeparator());   
		}
		
		close();
		
		if(Runner.deBugMode) System.out.println(file.toString() + " Writing Ends");

	}
}