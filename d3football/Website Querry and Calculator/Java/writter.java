import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class writter {
    //Get machine name
		
    public static String user = "jgwoolley";
    public static File down1= new File("C:\\Users\\" + user + "\\workspace\\XML Writter\\src\\down1.csv");    
    public static File down2= new File("C:\\Users\\" + user + "\\workspace\\XML Writter\\src\\down2.csv");    
    public static File down3= new File("C:\\Users\\" + user + "\\workspace\\XML Writter\\src\\down3.csv");    
    public static File down4= new File("C:\\Users\\" + user + "\\workspace\\XML Writter\\src\\down4.csv");    

    public static void main(String [ ] args){
        int[][][][] temp = GetRandomInput();
        try {
            writer(temp);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }      

    }

    public static void writer(int[][][][] arrayXMLinput)throws FileNotFoundException{        
        
        File[] files = new File[4];
                
        files[0] = down1;
        files[1] = down2;
        files[2] = down3;
        files[3] = down4;        
        
        try {
            for(File fl:files){
                FileWriter writer = new FileWriter(fl);
                writer.append("");
                writer.close();
            }
        } catch (IOException e) {
                e.printStackTrace();
        }
        
        try {
            for(int i = 0; i < 4;i++){
                FileWriter writer = new FileWriter(files[i]);
                
                for(int[][] yardline:arrayXMLinput[i]){
                    for(int[] distance:yardline){
                        for(int play = 0; play < 4;play++){
                            Integer temp = new Integer(distance[play]);
                            writer.append(temp.toString());
                            writer.append(",");
                        }                       
                    }
                    writer.append(System.lineSeparator());                                                                  
                }

                writer.close();
                System.out.println(files[i].toString() + " is complete");
            }            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static int[][][][] GetRandomInput(){
        Random rand = new Random();
        
        int[][][][] arrayXMLinput = new int[4][][][];        
        
        for(int down = 0; down <4;down++){
            arrayXMLinput[down] = new int[99][][];                                
            for(int yardline = 0; yardline < 99;yardline++){
                arrayXMLinput[down][yardline] = new int[15][];                                
                for(int distance = 0; distance < 15;distance++){
                    arrayXMLinput[down][yardline][distance] = new int[4];
                    for(int play = 0; play < 4; play++){
                        arrayXMLinput[down][yardline][distance][play] = rand.nextInt(100) - 50;// randomly generated expected points
                    }
                }
            }            
        }
        
        return arrayXMLinput;
    }
    
}


 {
    //Get machine name
		
    public static String user = "jgwoolley";
    public static File down1= new File("C:\\Users\\" + user + "\\workspace\\XML Writter\\src\\down1.csv");    
    public static File down2= new File("C:\\Users\\" + user + "\\workspace\\XML Writter\\src\\down2.csv");    
    public static File down3= new File("C:\\Users\\" + user + "\\workspace\\XML Writter\\src\\down3.csv");    
    public static File down4= new File("C:\\Users\\" + user + "\\workspace\\XML Writter\\src\\down4.csv");    

    public static void main(String [ ] args){
        int[][][][] temp = GetRandomInput();
        try {
            writer(temp);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }      

    }

    public static void writer(int[][][][] arrayXMLinput)throws FileNotFoundException{        
        
        File[] files = new File[4];
                
        files[0] = down1;
        files[1] = down2;
        files[2] = down3;
        files[3] = down4;        
        
        try {
            for(File fl:files){
                FileWriter writer = new FileWriter(fl);
                writer.append("");
                writer.close();
            }
        } catch (IOException e) {
                e.printStackTrace();
        }
        
        try {
            for(int i = 0; i < 4;i++){
                FileWriter writer = new FileWriter(files[i]);
                
                for(int[][] yardline:arrayXMLinput[i]){
                    for(int[] distance:yardline){
                        for(int play = 0; play < 4;play++){
                            Integer temp = new Integer(distance[play]);
                            writer.append(temp.toString());
                            writer.append(",");
                        }                       
                    }
                    writer.append(System.lineSeparator());                                                                  
                }

                writer.close();
                System.out.println(files[i].toString() + " is complete");
            }            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static int[][][][] GetRandomInput(){
        Random rand = new Random();
        
        int[][][][] arrayXMLinput = new int[4][][][];        
        
        for(int down = 0; down <4;down++){
            arrayXMLinput[down] = new int[99][][];                                
            for(int yardline = 0; yardline < 99;yardline++){
                arrayXMLinput[down][yardline] = new int[15][];                                
                for(int distance = 0; distance < 15;distance++){
                    arrayXMLinput[down][yardline][distance] = new int[4];
                    for(int play = 0; play < 4; play++){
                        arrayXMLinput[down][yardline][distance][play] = rand.nextInt(100) - 50;// randomly generated expected points
                    }
                }
            }            
        }
        
        return arrayXMLinput;
    }
    
}


 {
    //Get machine name
		
    public static String user = "jgwoolley";
    public static File down1= new File("C:\\Users\\" + user + "\\workspace\\XML Writter\\src\\down1.csv");    
    public static File down2= new File("C:\\Users\\" + user + "\\workspace\\XML Writter\\src\\down2.csv");    
    public static File down3= new File("C:\\Users\\" + user + "\\workspace\\XML Writter\\src\\down3.csv");    
    public static File down4= new File("C:\\Users\\" + user + "\\workspace\\XML Writter\\src\\down4.csv");    

    public static void main(String [ ] args){
        int[][][][] temp = GetRandomInput();
        try {
            writer(temp);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }      

    }

    public static void writer(int[][][][] arrayXMLinput)throws FileNotFoundException{        
        
        File[] files = new File[4];
                
        files[0] = down1;
        files[1] = down2;
        files[2] = down3;
        files[3] = down4;        
        
        try {
            for(File fl:files){
                FileWriter writer = new FileWriter(fl);
                writer.append("");
                writer.close();
            }
        } catch (IOException e) {
                e.printStackTrace();
        }
        
        try {
            for(int i = 0; i < 4;i++){
                FileWriter writer = new FileWriter(files[i]);
                
                for(int[][] yardline:arrayXMLinput[i]){
                    for(int[] distance:yardline){
                        for(int play = 0; play < 4;play++){
                            Integer temp = new Integer(distance[play]);
                            writer.append(temp.toString());
                            writer.append(",");
                        }                       
                    }
                    writer.append(System.lineSeparator());                                                                  
                }

                writer.close();
                System.out.println(files[i].toString() + " is complete");
            }            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static int[][][][] GetRandomInput(){
        Random rand = new Random();
        
        int[][][][] arrayXMLinput = new int[4][][][];        
        
        for(int down = 0; down <4;down++){
            arrayXMLinput[down] = new int[99][][];                                
            for(int yardline = 0; yardline < 99;yardline++){
                arrayXMLinput[down][yardline] = new int[15][];                                
                for(int distance = 0; distance < 15;distance++){
                    arrayXMLinput[down][yardline][distance] = new int[4];
                    for(int play = 0; play < 4; play++){
                        arrayXMLinput[down][yardline][distance][play] = rand.nextInt(100) - 50;// randomly generated expected points
                    }
                }
            }            
        }
        
        return arrayXMLinput;
    }
    
}


