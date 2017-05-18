package outputs;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.print.DocFlavor.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import datastructures.cArray;
import datastructures.pArray;
import datastructures.qArray;
import functions.Calculator;
import functions.Query;
import runner.Runner;

public class d3footballDisplay extends JFrame{
	JLabel outputText = new JLabel();	
	
	public d3footballDisplay(){
		super("Divison 3 Football Database Query - Adrian, Elaine, Randy, Mark, James");
		
		if(Runner.deBugMode) System.out.println("Display Creation Begins");		
		this.setSize(1024, 768); //1024, 768; 800, 600
		this.setResizable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());		
	
        ImageIcon appIcon = new ImageIcon("lib/Frogger.gif");
        setIconImage(appIcon.getImage());  
		
        updateText("Hello. Please select how you want to run our program!");
		
		JButton randomButton = new JButton();
		randomButton.setText("Generate .CSV Randomly");
		randomButton.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
            	updateText(".CSV Files Generated Randomly");
    			for(int i = 0; i < 1;i++){
    				cArray array = new cArray();
    				array.randomizeArray(false);
    				try {
						Writer cw = new Writer(i + 1, array);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}	
            	}
            }
		}
        );
		JButton databaseButton = new JButton();
		databaseButton.setText("Generate .CSV from Database");
		
		databaseButton.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
            	updateText(".CSV from Database Generated");   			
    			    			
    			for(int i = 0; i < 1;i++){			
	    			qArray query = Query.Query();
	    			cArray calculation = Calculator.calculation(query, new pArray()); 
	    			
    				try {    					    	    			
						Writer cw = new Writer(i + 1, calculation);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
    				if(Runner.deBugMode) System.out.println("Down " + i + "Writer created");
    			}
            }
        }
        );
		
		JButton readCSV = new JButton();
		readCSV.setText("Read compatible .CSV Files");
		
		readCSV.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
            	String output = "Down ";
            	            	
            	for(int i = 0; i <1;i++){
					output+=Reader.read((i+1), ((i+1)+"down.csv"));
            	}
            	updateText(output + "Reading is complete for .CSV files");
    
            }
		}
        );
		
		JButton deleteCSV = new JButton();
		deleteCSV.setText("Delete .CSV Files");
		
		deleteCSV.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
            	updateText(".CSV Files Deleted");
            	for(int i = 0; i < 4;i++){
            		File temp = new File((i+1) + "down.csv");
            		temp.delete();
            	}
            }
		}
        );				
		
		JPanel pane = new JPanel();
		pane.setLayout(new GridLayout(2,2));
		pane.add(databaseButton);
		pane.add(randomButton);
		pane.add(deleteCSV);
		pane.add(readCSV);
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,outputText,pane);
		split.setResizeWeight(0.9);
		this.setBounds(this.getX(), this.getY(), this.getWidth(), 200);
		this.add(split);
		this.setVisible(true);
		
		if(Runner.deBugMode) System.out.println("Display Creation Ends");
	}
	
	public void updateText(String newText) {
		outputText.setText(newText);
		outputText.setFont(new Font("Dialog", Font.BOLD, 18));

		/*
		JLabel centeredText = new JLabel(outputText.getText(), SwingConstants.CENTER);
		outputText = centeredText;
		*/
	}
}