package functions;
 import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import com.theopentutorials.jdbc.db.DbUtil;
//import com.theopentutorials.jdbc.db.JDBCMySQLConnection;
//import com.theopentutorials.jdbc.to.Employee;
import java.util.ArrayList;

import datastructures.qPlay;

//import datastructures.qPlay;


public class JDBCMySQLFieldGoal {
    public final static boolean DEBUGMODE = true;


    public static void main(String[] args) throws IOException {        
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
         
        try {
            getQuery();        
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }       
    }
 
    public static ArrayList<qPlay> getQuery()  {      
        ResultSet rs = null;
        Connection connection = null;
        Statement statement = null; 
         
        ArrayList<qPlay> fgPlayArray = new ArrayList<qPlay>();

        String query = "SELECT * FROM field_goals";
        try {           
            connection = JDBCMySQLConnection.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            if(DEBUGMODE) System.out.println("query success");
            
            //if(DEBUGMODE) System.out.println("play_id = " + rs.getInt(1195));
            int j = 0;
            while (rs.next()) {
            	qPlay currPlay = new qPlay();

            	j++;
            	if(DEBUGMODE) System.out.println(j);
            	if(rs.getInt("success") == 1) {
            		//if(DEBUGMODE) System.out.println("playid: " + rs.getInt("play_id") + ", distance: " + rs.getInt("distance"));
            	} else if (rs.getInt("success") == 0){
            		int fieldGoalInt = rs.getInt("play_id");
            		String tempQuery = "SELECT * FROM plays WHERE id = " + fieldGoalInt;
            		if(DEBUGMODE) System.out.println(tempQuery);
                    ResultSet rs2 = null; Statement st2 = null; st2 = connection.createStatement(); rs2 = st2.executeQuery(tempQuery);
                     rs2.next();
                     int driveLook = rs2.getInt("drive_id");
                     
                     currPlay.setDistance(rs2.getInt("location")); 
                     currPlay.setDown(rs2.getInt("down"));
                     currPlay.setPlay(0);	// 0 is fieldgoal
                     currPlay.setYardline(rs2.getInt("distance"));
                     
            		if(DEBUGMODE) System.out.println("drive is " + driveLook);
            		tempQuery = "SELECT * FROM drives WHERE id = " + driveLook; 
                    ResultSet rs3 = null; Statement st3 = null; st3 = connection.createStatement(); rs3 = st3.executeQuery(tempQuery);

                    rs3.next();
                    int gameLook = rs3.getInt("game_id");
                    
                    tempQuery = "SELECT * FROM drives WHERE game_id = " + gameLook;
                    ResultSet rs5 = null; Statement st5 = null; st5 = connection.createStatement(); rs5 = st5.executeQuery(tempQuery);
                    rs5.next();
                    boolean isEvenDrive = false;
                    int startDrive = rs5.getInt("id");
                    if(driveLook % 2 == 0) {
                    	isEvenDrive = true;
                    }
                    if(DEBUGMODE) System.out.println("start drive is " + startDrive);
                    
            		tempQuery = "SELECT * FROM drives WHERE game_id = " + gameLook + " AND id > " + driveLook; 
                    ResultSet rs4 = null; Statement st4 = null; st4 = connection.createStatement(); rs4 = st4.executeQuery(tempQuery);

                    rs4.next();
                    int i = 0;
                    boolean keepLookingForNextScore = true;
                    while(rs4.next() && keepLookingForNextScore)
                    {
                		if(DEBUGMODE) System.out.println("i = " + i + " points: " + rs4.getInt("points"));
                		if(rs4.getInt("points") > 0) {
                			keepLookingForNextScore = false;
                			int nextScoreDrive = rs4.getInt("id");
                			if(DEBUGMODE) System.out.println("next score's drive is " + nextScoreDrive);
                			boolean isNextScoreEvenDrive = false;
                			if(nextScoreDrive % 2 == 0) {
                				isNextScoreEvenDrive = true;	
                			} 
                			if(isNextScoreEvenDrive && isEvenDrive) {
                				if(DEBUGMODE) System.out.println("same team got next score");
                    			currPlay.setNextScore(rs4.getInt("points"));

                			} else {
                				if(DEBUGMODE) System.out.println("other team score got next score");
                    			currPlay.setNextScore((-1)*rs4.getInt("points"));


                			}
                			

                		}
                		i++;
                    }
                    //done, now add play to arraylist
                    fgPlayArray.add(currPlay); 

            		
            	}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        //done
        return fgPlayArray;
    }
}
