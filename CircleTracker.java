import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CircleTracker extends JFrame{

	//related to arrays
	private Circle[] circles = new Circle[20];
	private int currRec = 0;
	private int currIndex = -1;

	private int cirR;
	private String cirC;

	private String temp;	

	private JButton addButton = new JButton("Add");
	private JButton modifyButton = new JButton("Modify");
	private JButton averageButton = new JButton("Average");

	private JButton firstButton = new JButton("<<");
	private JButton previousButton = new JButton("<");
	private JButton nextButton = new JButton(">");
	private JButton lastButton = new JButton(">>");

	private JLabel radiusLabel = new JLabel("            Radius:");
	private JLabel colorLabel = new JLabel("            Color:");
	private JLabel countLabel = new JLabel("       Record Count:  ");
	private JLabel indexLabel = new JLabel("       Index:  ");

	private JTextField radiusField = new JTextField("");
	private JTextField colorField = new JTextField("");
	private JTextField countField = new JTextField("0");
	private JTextField indexField = new JTextField("-1");

	private JTextField commentField = new JTextField("  Close this app, and your data dies.  Mwahhahaa.");

	public CircleTracker(){

		//prevent changes to some fields
		countField.setEditable(false);
		indexField.setEditable(false);
		commentField.setEditable(false);
		countField.setBackground(Color.lightGray);
		indexField.setBackground(Color.lightGray);
		commentField.setBackground(Color.lightGray);
	
		//create the Jpanels
		JPanel holdingPanel = new JPanel(new GridLayout(4, 1, 5, 5));
		JPanel optionsPanel = new JPanel();
		JPanel dataPanel = new JPanel(new GridLayout(2, 4, 20, 5));
		JPanel navPanel = new JPanel();
		JPanel commentPanel = new JPanel(new GridLayout(1, 1));

		//add the controls and fields
		optionsPanel.add(addButton);
		optionsPanel.add(modifyButton);
		optionsPanel.add(averageButton);

		dataPanel.add(radiusLabel);
		dataPanel.add(radiusField);
		dataPanel.add(countLabel);
		dataPanel.add(countField);
		dataPanel.add(colorLabel);
		dataPanel.add(colorField);	
		dataPanel.add(indexLabel);		
		dataPanel.add(indexField);

		navPanel.add(firstButton);
		navPanel.add(previousButton);
		navPanel.add(nextButton);
		navPanel.add(lastButton);

		commentPanel.add(commentField);

		holdingPanel.add(optionsPanel);
		holdingPanel.add(dataPanel);
		holdingPanel.add(navPanel);
		holdingPanel.add(commentPanel);

		//get the container and place the JPanels
		Container pane = getContentPane();
		pane.add(holdingPanel);

		//add listeners
		addButton.addActionListener(new AddListener());
		modifyButton.addActionListener(new ModifyListener());
		averageButton.addActionListener(new AverageListener());
		
		firstButton.addActionListener(new FirstListener());
		previousButton.addActionListener(new PreviousListener());
		nextButton.addActionListener(new NextListener());
		lastButton.addActionListener(new LastListener());

	}

	//Listeners
	//Add
	private class AddListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(currRec == 20){
				commentField.setText("  Can't add anymore records.");
				return;
			}
				
			//get data and build string if to state error if any.
			//validate data
			String message = validateData();
			if(!message.equals("")){
				commentField.setText(message);
				return;
			}
			
			circles[currRec] = new Circle(cirR, cirC);
			currRec++;
			currIndex = currRec - 1;

			updateDisplay(currIndex);
			commentField.setText("  Data added.");

			
		}
	}

	//Modify
	private class ModifyListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(currRec == 0){
				commentField.setText("  There is no record to modify.");
				return;
			}

			//validate data
			String message = validateData();
			if(!message.equals("")){
				commentField.setText(message);
				return;
			}

			circles[currIndex].modify(cirR, cirC);

			updateDisplay(currIndex);
			commentField.setText("  Data modified.");
		
		}
	}

	//Average
	private class AverageListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(currRec == 0){
				commentField.setText("  There is no record to average.");
				return;
			}
			
			double avg = 0;
			for(int c = 0; c < currRec; c++){
				avg = avg + (double)(circles[c].getRadius());
			}
			avg = avg / currRec;
			if(currRec == 1)
				commentField.setText("  There is only one circle, so the average radius is " + avg + ".");
			else
				commentField.setText("  The average radius of " + currRec + " circles is " + avg + ".");
		
			//used for testing.  delete.
			dispData();

			
		}
	}

	//First
	private class FirstListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			//is there data?
			if(currRec == 0){
				commentField.setText("  Please add in a circle first.");
				return;
			}

			currIndex = 0;

			updateDisplay(currIndex);
			commentField.setText("  First record.");
		}
	}

	//Previous
	private class PreviousListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			//is there data?
			if(currRec == 0){
				commentField.setText("  Please add in a circle first.");
				return;
			}
			if(currIndex == 0){
				commentField.setText("  Already at the first record.");
				return;	
			}

			currIndex--;

			updateDisplay(currIndex);
			commentField.setText("  Previous record.");		
		}
	}
		
	//Next
	private class NextListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(currRec == 0){
				commentField.setText("  Please add in a circle first.");
				return;
			}
			if(currIndex == (currRec - 1)){
				commentField.setText("  You're already at the last record.");
				return;	
			}

			currIndex++;

			updateDisplay(currIndex);
			commentField.setText("  Next record.");
		}
	}

	//Last
	private class LastListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(currRec == 0){
				commentField.setText("  Please add in a circle first.");
				return;
			}
			currIndex = currRec - 1;
			updateDisplay(currIndex);
			commentField.setText("  Last record.");
			
			
		}
	}

	public static void main(String[] args){
		CircleTracker CTrack = new CircleTracker();
		//STD
		//CTrack.setSize(500,500);
		CTrack.setTitle("Circle Tracker");
		CTrack.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CTrack.pack();
		CTrack.setVisible(true);

	}
	
	public String validateData(){
		//check radius
		String msg = "";
		try{
			cirR = Integer.parseInt(radiusField.getText());
			if(cirR <= 0) msg = "  Radius can't be less than or equal to 0.";
		}
		catch (Exception e){
			msg = "  Radius is not a number.";
		}

		//check for valid colors
		cirC = colorField.getText().trim().toUpperCase();
		if(!(cirC.equals("BLACK") || cirC.equals("RED") || cirC.equals("GREEN") || cirC.equals("BLUE")))
			msg = msg + "  Color must be BLACK, RED, GREEN or BLUE.";

		return msg;	
	}

	public void updateDisplay(int cIndex){
		radiusField.setText("" + circles[cIndex].getRadius());
		colorField.setText("" + circles[cIndex].getColor());
		countField.setText("" + currRec);
		indexField.setText("" + currIndex);
	}

	//used for testing
	public void dispData(){
		for(int c = 0; c < circles.length; c++){
			String str = "Index:   " + c + "  " + circles[c];
			System.out.println(str);
		}			
	}
}
