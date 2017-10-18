package main;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class Window {
	
	//components
	
	private JSpinner APS;
	private JTextArea toType;
	private JCheckBox shiftEnter;
	
	private void addComponents() {
		SpinnerModel model = new SpinnerNumberModel(100, 1, 1000, 1);
		APS = new JSpinner(model);
		addInput("Actions per Second:", APS);
		
		toType = new JTextArea(5, 20);
		JScrollPane typeScroll = new JScrollPane(toType);
		addInput("Typing:", typeScroll);
		
		shiftEnter = new JCheckBox();
		addInput("Hold shift for new lines:", shiftEnter);
	}
	
	public int getAPS() {
		try {
			int i = Integer.parseInt(APS.getValue().toString());
			return i < 1 ? 1 : Math.min(i, 1000);
		} catch (NumberFormatException ex) {
			System.out.println(APS.getValue().toString() + " could not be converted to an integer");
			return 1;
		}
	}
	
	public String toType() {
		return toType.getText();
	}
	
	public boolean shiftEnter() {
		return shiftEnter.isSelected();
	}
	
	//generic stuff, should probably be moved into a parent class
	
	private JPanel panel;
	
	public Window() {
		JFrame frame;
		frame = new JFrame("Auto Clicker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 400);

		panel = new JPanel();
		addComponents();
		frame.add(panel);
		
		frame.setVisible(true);
	}
	
	private void addInput(String label, Component c) {
		panel.add(new JLabel(label));
		panel.add(c);
	}
	
}
