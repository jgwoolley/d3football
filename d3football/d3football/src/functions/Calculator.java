package functions;

import runner.*;
import datastructures.*;
import functions.*;
import outputs.*;

import java.util.List;
import java.util.Arrays;

public class Calculator {
	public static int yardlineSize = Runner.yardlineSize;
	public static int distanceSize = Runner.distanceSize;
	public static int playSize = Runner.playSize;
	
	
	/* returns number corresponding to the play that has the highest expected points value
	 * given the distance to 1st down and starting yardline
	 */

	/* returns number corresponding to the play that has the highest expected points value
	 * given the distance to 1st down and starting yardline
	 */
	
	public static cArray calculation(qArray input, pArray per) {
		cArray output = new cArray();
		cArray counter = new cArray(0);
		
		for(int i = 0; i < input.size(); i++){
			qPlay play = input.getPlay(i);
			Double temp = output.getExpectedPoints(play.getYardline(), play.getDistance(), play.getPlay());
			output.setExpectedPoints(play.getYardline(), play.getDistance(), play.getPlay(),play.getNextScore());
			
			temp = counter.getExpectedPoints(play.getYardline(), play.getDistance(), play.getPlay());
			temp++;
			counter.setExpectedPoints(play.getYardline(), play.getDistance(), play.getPlay(), temp);

		}
		
		for(int yardline = 0; yardline < yardlineSize;yardline++){
			for(int distance = 0; distance < distanceSize;distance++){
				for(int play = 0; play < playSize; play++){
					double temp = output.getExpectedPoints(yardline, distance, play);
					
					if(play == 0){//Field Goal
						temp=temp * per.getPerFG(yardline);
					}
					else if(play==1){//Go For It
						temp=temp * per.getPerGFI(distance);
					}
					//Punt has no success rate					
				}
			}
		}
		
		return output;	
	}
}		