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
	
	public static cArray calculation(qArray input) {
		cArray output = new cArray();
		cArray counter = new cArray();
		
		for(int i = 0; i > input.size(); i++){
			qPlay play = input.getPlay(i);
			Double temp = output.getExpectedPoints(play.getYardline(), play.getDistance(), play.getPlay());
			temp += play.getNextScore();
			output.setExpectedPoints(play.getYardline(), play.getDistance(), play.getPlay(),play.getNextScore());
									
			//temp++;			
			output.setExpectedPoints(play.getYardline(), play.getDistance(), play.getPlay() + 1, temp);

		}
		
		for(int yardline = 0; yardline < yardlineSize;yardline++){
			for(int distance = 0; distance < distanceSize;distance++){
				for(int play = 0; play < playSize; play++){
					output.getExpectedPoints(yardline, distance, play);
				}
			}
		}


		
		
		return output;	
	}
}		
		/*
		int team; //team on offense
		int sumEP =0; //
		int numPlays=0;
		double EP=0;
		int yard;
		int nextScore;
		int scoringTeam;
		for(int i=0; i<down.length; i++){    // down 4
			for(int j=0; j<distance.length; j++){ // distance 1-11
				for(int k=0; k<yardline.length; k++){  // yard line 1-99
					for((down[i] == 4) && (1<=yardline[k] && yardline[k]<=99)){
						int newDown = down[i];
						int newYardline = yardline[k];
						if((newDown == down[i]) && (newYardline == yardline[k])){
							//retrieve nextScore & ScoringTeam;
							if(team == scoringTeam){
								sumEP += nextScore;
							}
							else{
								sumEP -= nextScore;
							}
							numPlays++;
						}
					}
				}
				EP = sumEP/numPlays);
			}
		}
	   	 return EP;
	   	 */