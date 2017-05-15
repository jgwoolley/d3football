/*
The ultimate goal for the JDBCSQLDemo.java to be integrated with the Query.java code. The Connection code will remain a seperate function.

Before the code is integreated with the Query.java file, this Query must effectively create a qArray.java.

A qArray.java basically is just a normal array that has handler methods, that hosts many qPlay elements.

A qPlay array is basically just a normal array that has handler methods, it stores a

int nextScore
int down
int yardline
int distance
int play

This qArray is then handed to the Calculator to be processed into a cArray, which is basically just a java representation of the
soon to be created .CSV file.
 */

package functions;

import runner.*;
import datastructures.*;
import functions.*;
import outputs.*;

import java.util.ArrayList;

public class Query {
	private final static int downLength = Runner.downs.length;
	
	
	public static qArray Query(){
		ArrayList<ArrayListPlays> list = new ArrayList<ArrayListPlays>();
		/*
		 * Insert Real Query Code
		 */
		ArrayList<qPlay> puntList = JDBCMySQLPunts.getQuery();
		ArrayList<qPlay> fgList = JDBCMySQLFieldGoal.getQuery();
		ArrayList<qPlay> conversionList = JDBCMySQLConversion.getQuery();
		
		puntList.addAll(fgList);
		puntList.addAll(conversionList);
		
		qPlay[] output = new qPlay[puntList.size()];
		for(int i = 0; i > list.size(); i++){
			output[i] = list.get(i).getqPlay();
		}
						
		return new qArray(output);
	}
	
	private class ArrayListPlays{
		qPlay qPlay;
		
		private ArrayListPlays(qPlay qPlay){
			this.qPlay = qPlay;
		}

		public qPlay getqPlay() {
			return qPlay;
		}

		public void setqPlay(qPlay qPlay) {
			this.qPlay = qPlay;
		}
		
		
	}
}