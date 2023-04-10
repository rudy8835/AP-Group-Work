package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Actions;

public class AssignWindow {
	
	public AssignWindow() {
	JFrame assign = new JFrame();
	assign.setResizable(false);
	assign.setSize(300, 400);
	assign.setLayout(new BorderLayout()); // set BorderLayout layout manager
	

	JPanel topPanel = new JPanel(new GridLayout(15, 10)); // create a panel with 2 rows and 2 columns
	
	JLabel assignedAdvisor = new JLabel("Advisor ID:");
	JTextField aa = new JTextField();
	JLabel assignStudent = new JLabel("Student Query/Complaint ID:");
	JTextField as = new JTextField();
	topPanel.add(assignedAdvisor);
	topPanel.add(aa);
	topPanel.add(assignStudent);
	topPanel.add(as);
	assign.add(topPanel, BorderLayout.NORTH); // add the panel to the top (north) of the frame

	JButton makeAssign = new JButton("Make Assignment");
	
	Font font = new Font("Arial", Font.PLAIN, 12);
	makeAssign.setFont(font);
	
	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)); // create a panel with FlowLayout
	makeAssign.setPreferredSize(new Dimension(80, 50)); // set the preferred size of the button
	buttonPanel.add(makeAssign); // add the button to the panel
	assign.add(buttonPanel, BorderLayout.CENTER); // add the panel to the center of the frame
	assign.add(makeAssign, BorderLayout.CENTER); // add the button to the center of the frame
	
	makeAssign.addActionListener(new ActionListener() {
		   
		  
	    public void actionPerformed(ActionEvent e) {
	    	

	    	//contentPanel2.removeAll();
	    	//contentPanel2.updateUI();
	    	
	    	
	        String advisorID = (String) aa.getText();
	        String QueryID = (String) as.getText();
	        Actions act = new Actions();
	        
	      
	        boolean isAssigned = act.assignAdvisor(advisorID, QueryID);
	        if (isAssigned) {
	            JOptionPane.showMessageDialog(null, "Advisor assigned to student!");
	        } else {
	            JOptionPane.showMessageDialog(null, "QueryId already assigned to an advisor!");
	        }
	       
	       //contentPanel2.setVisible(true);
	           	
	    }	
	});
	
	assign.setVisible(true);
	}
	
	public static void main(String[]args) {
		//new AssignWindow();
	}
}
