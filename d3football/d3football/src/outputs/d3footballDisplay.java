package outputs;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import datastructures.qArray;
import functions.Calculator;
import functions.JDBCMySQLDemo;
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
		randomButton.setText("Create Random Data");
		randomButton.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {

        		int length = Runner.downs.length;		
    			
    			for(int i = 0; i < length;i++){
    				cArray array = new cArray();
    				array.randomizeArray(false);
    				
    				ExpectedPointsDisplay display = new ExpectedPointsDisplay(Runner.downs[i],array);
    				display.label.setText("Generated Random Expected Points .CSV File, and Displaying Results");
    				try {
						Writer cw = new Writer(Runner.downs[i], array);
					} catch (FileNotFoundException e) {
						outputText.setText(e.getMessage());						
						e.printStackTrace();
					} catch (IOException e) {
						outputText.setText(e.getMessage());
						e.printStackTrace();
					}	

            	}
            }

		}
        );
		JButton databaseButton = new JButton();
		databaseButton.setText("Use Database");
		databaseButton.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
            	outputText.setText("Not Actually Implemented, But Here's the Demo Code");
            	JDBCMySQLDemo demo = new JDBCMySQLDemo();
            	
            	/*
    			qArray query = Query.Query();
    			cArray calculation = Calculator.calculation(query);    			
    			
    			int length = Runner.downs.length;		
    			
    			for(int i = 0; i < length;i++){	
    				ExpectedPointsDisplay display = new ExpectedPointsDisplay(Runner.downs[i],calculation);
    				if(Runner.deBugMode) System.out.println("Down " + i + "Display created");
    				display.label.setText("With MySql Database, Generating .CSV File, and Displaying Results");				
    				try {
						Writer cw = new Writer(Runner.downs[i], calculation);
					} catch (FileNotFoundException e) {
						label.setText(e.getMessage());						
						e.printStackTrace();
					} catch (IOException e) {
						label.setText(e.getMessage());
						e.printStackTrace();
					}
    				if(Runner.deBugMode) System.out.println("Down " + i + "Writer created");
    			}
    			*/
            	}
            }
        );
		
		JButton readCSV = new JButton();
		readCSV.setText("Read compatible .CSV File");
		
		readCSV.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
				updateText("Not Implemented yet, ya dangus!");
            }
		}
        );

		

		JPanel pane = new JPanel();
		pane.setLayout(new GridLayout(1,3));
		pane.add(databaseButton);
		pane.add(randomButton);
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
		JLabel centeredText = new JLabel(outputText.getText(), SwingConstants.CENTER);
		centeredText.setFont(new Font("Dialog", Font.BOLD, 28));
		outputText = centeredText;

	}
}