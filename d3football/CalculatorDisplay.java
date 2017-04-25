import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class CalculatorDisplay extends JFrame{
	public static int yardlineSize = CalculatorRunner.yardlineSize;
	public static int distanceSize = CalculatorRunner.distanceSize;
	public static int playSize = CalculatorRunner.playSize;
	
	
	public CalculatorDisplay(int down, CalculatorArray input){
		super("down " + down);
		this.setSize(1024, 768); //1024, 768; 800, 600
		this.setResizable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		
		JScrollPane scroll = new JScrollPane();
		
		//CalculatorLabel label = new CalculatorLabel();		
		//scroll.add(label);
		
		CalculatorPanel panel = new CalculatorPanel(input);
		this.add(panel);
		
		//this.add(scroll);
		
	}
	
	private class CalculatorPanel extends JPanel{
		private CalculatorPanel(CalculatorArray input){
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
						buttons[yardline][distance] = new CalculatorButton(input.getPlays(yardline - 1, distance - 1));
					}
					add(buttons[yardline][distance]);
				}
			}			
		}
	}
	private class CalculatorButton extends JButton{
		@SuppressWarnings("unused")
		double[] expectedPoints;
		int index;
		String toolTip;
		
		private CalculatorButton(double[] expectedPoints){
			super();			
			this.expectedPoints = expectedPoints;
			index=0;
			toolTip = "";
			for(int x = 0; x < expectedPoints.length;x++){
				if(expectedPoints[index] < expectedPoints[x]){
					index = x;
				}
				toolTip+=expectedPoints[x];
				if(x != expectedPoints.length){
					toolTip+=", ";
				}
			}
			this.setText("" + expectedPoints[index]);
			this.setToolTipText(toolTip);
			
			setBackground(CalculatorRunner.colors[index]);
			setForeground(Color.BLACK);
			this.addActionListener(new CalcListener());
		}
		
		private class CalcListener implements ActionListener{

			public void actionPerformed(ActionEvent arg0) {
				String output = "";
				for(int x = 0; x < expectedPoints.length;x++){
					if(expectedPoints[index] < expectedPoints[x]){
						index = x;
					}
					output+=CalculatorRunner.plays[x] + " " + expectedPoints[x];
					if(x != expectedPoints.length){
						output+=", ";
					}
				}
				System.out.println(output);				
			}
			
		}
	}
	

	
	private class CalculatorLabel extends JLabel{
		
		private CalculatorLabel(){
			super();
			this.setText("Currently this label is vestigial, and might end up getting deleted");
		}
	}
}
