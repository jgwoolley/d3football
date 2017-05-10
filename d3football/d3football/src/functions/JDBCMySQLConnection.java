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

//Step 1: Use interfaces from java.sql package 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class JDBCMySQLConnection {
    //static reference to itself
    private static JDBCMySQLConnection instance = new JDBCMySQLConnection();
    public static final String URL = "jdbc:mysql://localhost:3306/football?autoReconnect=true&useSSL=false";
    public static final String USER = "root";
    public static final String PASSWORD = "memes123Abc!@";
    public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver"; 
    
    private JDBCMySQLConnection() {
        try {
            //Step 2: Load MySQL Java driver
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
     
    private Connection createConnection() {
 
        Connection connection = null;
        try {
            //Step 3: Establish Java MySQL connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("ERROR: Unable to Connect to Database.");
        }
        return connection;
    }   
     
    public static Connection getConnection() {
        return instance.createConnection();
    }
}