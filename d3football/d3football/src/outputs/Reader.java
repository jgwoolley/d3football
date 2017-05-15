package outputs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import datastructures.cArray;
import runner.Runner;

public class Reader {
	
	public static String read(int num, String fileName){
		ArrayList<Double> list = new ArrayList<Double>();		
		
		try {
			Scanner sc = new Scanner(new File(fileName));
			while(sc.hasNext()){
				String data = sc.next();
				String[] values = data.split(",");
				for(int i = 0; i < values.length;i++){				
					list.add(Double.parseDouble(values[i]));
				}
			}
			sc.close();		
			} catch (FileNotFoundException e) {
				return num + " wasn't read, ";
		}

		
		
		Iterator<Double> iterator = list.iterator();
		cArray output = new cArray();
		
		for(int yardline = 0; yardline < Runner.yardlineSize;yardline++){
			for(int distance = 0; distance < Runner.distanceSize;distance++){
				for(int play = 0; play < Runner.playSize; play++){
					Double temp = iterator.next();										
					output.setExpectedPoints(yardline, distance, play, temp);
				}
			}
		}
		ExpectedPointsDisplay display = new ExpectedPointsDisplay(num, output);
		return num + " was read, ";
	}
}
