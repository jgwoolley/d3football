import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/* Requires MySQL-connector. 
 * To add, right-click project file in eclipse >> Build path >> Add External Archives.
 * Requires access to MySQL database.
 * Should be refactored to use get methods to access data instead of returning data structure.
 */
public class DataMapper {
	private String url;
	private String username;
	private String password;
	private Connection connection;
	private ResultSet results;
	// Due to testing issues, this works if a play is a punt, field goal and drive at the same time
	// TO DO: check if it works with the real data
	private String query = "SELECT plays.distance, plays.location, " + 
			"goforit.success, field_goals.success, punts.net\n" +
			"FROM plays\n" + 
			"INNER JOIN goforit\n" +
			"ON (plays.id = goforit.play_id)\n" +
			"INNER JOIN field_goals\n" +
			"ON (plays.id = field_goals.play_id)\n" +
			"INNER JOIN punts\n" +
			"ON (plays.id = punts.play_id)\n" +
			"WHERE plays.down = 4;";
	
	DataMapper() {
		url = "jdbc:mysql://localhost/d3football?";
		username = "root";
		password = "admin";
	}
	
	DataMapper(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	
	/* Return list of maps for results of each type of play
	 * Key = [distance, yardline].
     * Value = [number of successes OR total net punt distance, number of attempts].
     */
	public List<HashMap<List<Integer>, List<Integer>>> getMaps() {		
	    List<HashMap<List<Integer>, List<Integer>>> mapList = 
        		new ArrayList<HashMap<List<Integer>, List<Integer>>>();
		
        try {
			// Establish connection.
		    connection = DriverManager.getConnection(url + "user=" + username + 
		    									"&password="+password);
		    
		    // Query and get results.
			Statement statement = connection.createStatement();
		    results = statement.executeQuery(query);
		   
		    // Extract data from results row by row
		    mapList = parseResults();
		    
        } catch (SQLException ex) {
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
        
		return mapList;
	}
	
	private List<HashMap<List<Integer>, List<Integer>>> parseResults() {
		// Maps for the results of each play option.
        // Key = [distance, yardline].
        // Value = [number of successes OR total net punt distance, number of attempts].
        HashMap<List<Integer>, List<Integer>> driveMap = new HashMap<>();
	    HashMap<List<Integer>, List<Integer>> fieldMap = new HashMap<>();
	    HashMap<List<Integer>, List<Integer>> puntMap = new HashMap<>();
	    
	    // List to hold previous 3 maps. Return value.
	    List<HashMap<List<Integer>, List<Integer>>> mapList = 
        		new ArrayList<HashMap<List<Integer>, List<Integer>>>();
	    
	    // Temp list variables which will hold map keys and values.
	    List<Integer> keyList = null;
	    List<Integer> newValList = null;
	    List<Integer> oldValList = null;
		
	    try {
			while(results.next()){
		    	// Get column values
		        Integer distance = results.getInt("distance");
		        Integer location = results.getInt("location");
		        Integer driveSuccess = results.getInt("goforit.success");
		        Integer fieldSuccess = results.getInt("field_goals.success");
		        Integer puntNet = results.getInt("net");		        
		        
		        // Create map key to be used for all 3 maps.
		        keyList = Arrays.asList(distance, location);
		        
		        // Add results to drive map if play is a drive attemp.
		        if (driveSuccess != null) {
		        	// If key not in map yet, create new entry.
			        if (!driveMap.containsKey(keyList)) {
			        	driveMap.put(keyList, Arrays.asList(driveSuccess, 1));
			        }
			        // If key already in map, replace the value with an updated list.
			        else {
			        	oldValList = driveMap.get(keyList);
			        	newValList = Arrays.asList(oldValList.get(0)+driveSuccess,
			        												oldValList.get(1)+1);
			        	driveMap.put(keyList, newValList);
			        }
		        }
		        
		        // Add results to field goal map if play is a field goal attempt.
			    if (fieldSuccess != null) {
		        	if (!fieldMap.containsKey(keyList)) {
			        	fieldMap.put(keyList, Arrays.asList(fieldSuccess, 1));
			        }
			        else {
			        	oldValList = fieldMap.get(keyList);
			        	newValList = Arrays.asList(oldValList.get(0)+fieldSuccess,
			        												oldValList.get(1)+1);
			        	fieldMap.put(keyList, newValList);
			        }
				}
			        
		        // Add results to punt map if play is a punt.
			    if (puntNet != null) {
			        if (!puntMap.containsKey(keyList)) {
			        	puntMap.put(keyList, Arrays.asList(puntNet, 1));
			        }
			        else {
			        	oldValList = puntMap.get(keyList);
			        	newValList = Arrays.asList(oldValList.get(0)+puntNet,
			        												oldValList.get(1)+1);
			        	puntMap.put(keyList, newValList);
			        }
			    }
		    }
			
	    } catch (SQLException ex) {
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	    
	    mapList.add(driveMap);
		mapList.add(fieldMap);
		mapList.add(puntMap);
		return mapList;
	}
	
	public static void main(String[] args) {
		DataMapper dMapper = new DataMapper();
		List<HashMap<List<Integer>, List<Integer>>> mapList = dMapper.getMaps();
		HashMap<List<Integer>, List<Integer>> driveMap = mapList.get(0);
		System.out.println("Drive Results");
		for(List<Integer> key : driveMap.keySet()) {
			System.out.println("Distance: " + key.get(0) + "  Yardline: " + key.get(1) +
								"  Successes: " + driveMap.get(key).get(0) +
								"  Attempts: " + driveMap.get(key).get(1));
		}
	}
}
