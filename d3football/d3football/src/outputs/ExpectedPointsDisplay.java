package outputs;

import runner.*;
import datastructures.*;
import functions.*;
import outputs.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

//Think about using layered panes
//Think about scrolling upper pane 


public class ExpectedPointsDisplay extends JFrame{
	private static int yardlineSize = Runner.yardlineSize;
	private static int distanceSize = Runner.distanceSize;
	private static int playSize = Runner.playSize;
	public JLabel label;
	
	
	public ExpectedPointsDisplay(int down, cArray input){		
		super("down " + down);		
		if(Runner.deBugMode) System.out.println("Display Creation Begins");		
		this.setSize(1024, 768); //1024, 768; 800, 600
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());		
		CalculatorPanel panel = new CalculatorPanel(input);
		JScrollPane scroll = new JScrollPane(panel);
		
        ImageIcon appIcon = new ImageIcon("lib/Frogger.gif");
        setIconImage(appIcon.getImage());  		
		
		label = new JLabel();
		label.setText("Hello");
		label.setFont(new Font("Serif", Font.BOLD, 25));
		
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,label,scroll);		
		
		this.add(split);
		this.setVisible(true);
		
		if(Runner.deBugMode) System.out.println("Display Creation Ends");
	}
	
	private class CalculatorPanel extends JPanel{
		private CalculatorPanel(cArray input){
			super();
			JButton[][] buttons = new JButton[yardlineSize+1][distanceSize+1];
			this.setLayout(new GridLayout(buttons.length,buttons[0].length));
			for(int yardline = 0; yardline < buttons.length; yardline++) {//yardline
				for(int distance = 0; distance < buttons[0].length; distance++){//distance
					if(yardline==0){
						if(yardline==distance){
							buttons[yardline][distance] = new JButton();
						}
						else{
							buttons[yardline][distance] = new JButton("" + distance);
							buttons[yardline][distance].setToolTipText("Distance " + distance);
						}
					}
					else if(distance==0){
						buttons[yardline][distance] = new JButton("" + yardline);
						buttons[yardline][distance].setToolTipText("Yardline " + yardline);
					}
					else{
						buttons[yardline][distance] = new CalculatorButton(yardline - 1,distance - 1, input.getPlays(yardline - 1, distance - 1), label);
					}
					add(buttons[yardline][distance]);
				}
			}
		}
	}
	private class CalculatorButton extends JButton{
		private int yardline;
		private int distance;
		private double[] expectedPoints;
		private int index;
		private String toolTip;
		
		private CalculatorButton(int yardline, int distance, double[] expectedPoints, JLabel label){
			super();
			this.yardline = yardline;
			this.distance = distance;
			this.expectedPoints = expectedPoints;
			index=0;
			toolTip = "";
			for(int x = 0; x < expectedPoints.length;x++){
				if(expectedPoints[index] < expectedPoints[x]){
					index = x;
				}
				toolTip+=expectedPoints[x];
				if(x < expectedPoints.length - 1){
					toolTip+=", ";
				}
			}
			this.setText("" + expectedPoints[index]);
			this.setFont(new Font("Serif", Font.BOLD, 25));
			this.setToolTipText(toolTip);
			
			if(expectedPoints[index] >= Runner.EPmin && expectedPoints[index] <= Runner.EPmax){
				setBackground(Runner.colors[index]);
				setForeground(Color.BLACK);
				this.addActionListener(new CalcListener());
			}
			else{
				this.setText("invalid");
				this.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {						
					}
				});
				setBackground(Color.BLACK);
				setForeground(Color.WHITE);
				this.addActionListener(new CalcListener());
			}
		}
		
		private class CalcListener implements ActionListener{

			public void actionPerformed(ActionEvent arg0) {
				String output = "For yardline " + yardline + " and distance " + (distance + 1) + ": ";
				if(distance + 1 == distanceSize){
					output = "For yardline " + yardline + " and distance " + distance + "+: ";
				}				
				
				for(int x = 0; x < expectedPoints.length;x++){
					if(expectedPoints[index] < expectedPoints[x]){
						index = x;
					}
					output+=Runner.playNames[x] + " " + expectedPoints[x];
					if(x < expectedPoints.length - 1){
						output+=" | ";
					}
				}
				//label.setText(label.getText() + output);				
				label.setText(output);
			}
			
		}
	}
}
