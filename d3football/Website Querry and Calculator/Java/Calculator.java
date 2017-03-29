import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

public class Calculator {
	private static DataMapper dMapper;
	private static List<HashMap<List<Integer>, List<Integer>>> mapList;
	private static HashMap<List<Integer>, List<Integer>> drive;
	private static HashMap<List<Integer>, List<Integer>> field;
	private static HashMap<List<Integer>, List<Integer>> punt;
	
	Calculator() {
		dMapper = new DataMapper();
		mapList = dMapper.getMaps();
		drive = mapList.get(0);
		field = mapList.get(1);
		punt = mapList.get(2);
	}
	
	public static void main(String[] args) {
		Calculator cal = new Calculator();
		int play = cal.calculation(Arrays.asList(1, 50));
		System.out.println(play);
	}
	
	/* returns number corresponding to the play that has the highest expected points value
	 * given the distance to 1st down and starting yardline
	 */
	public int calculation(List<Integer> key) {
	   	 if(key.size() != 2 || drive.isEmpty() || punt.isEmpty() || field.isEmpty()){
	   		 return -1;
	   	 }
	   	 
	   	 int[] expectedPoints = new int[3];
	   	 expectedPoints[0]= 7*drive.get(key).get(0)/drive.get(key).get(1);
	   	 expectedPoints[1]= 0*punt.get(key).get(0)/punt.get(key).get(1); //not sure about this
	   	 expectedPoints[2]= 3*field.get(key).get(0)/field.get(key).get(1);
	   	 
	   	 int indexOfHighest = 0;
	   	 if(expectedPoints[indexOfHighest] < expectedPoints[1]){
	   		 indexOfHighest =1;
	   	 }
	   	 if(expectedPoints[indexOfHighest] < expectedPoints[2]){
	   		 indexOfHighest =2;
	   	 }   	 
	   	 
	   	 return indexOfHighest;
	}
	
}
