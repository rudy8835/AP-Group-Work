package view;
//Kemar Savoury 1801489
//Dujon Goulboure 1901713
//Jameal Wood 1704320
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import controller.Actions;
import server.ChatServer;
import server.ChatServerGUI;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JToggleButton; 
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class ssAdvisor extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frmStudentServicesAdvisor;
	private JTextField textField;
	 

	

	
	//The main frame of the dashboard where everything will be built on
	ssAdvisor() {
		
		
		
		frmStudentServicesAdvisor = new JFrame();
		frmStudentServicesAdvisor.setTitle("Student Services Advisor");
		frmStudentServicesAdvisor.setForeground(new Color(255, 255, 255));
		frmStudentServicesAdvisor.getContentPane().setBackground(new Color(134,201,255));
		frmStudentServicesAdvisor.setLayout(null);
		frmStudentServicesAdvisor.setResizable(false);
		frmStudentServicesAdvisor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmStudentServicesAdvisor.setVisible(true);
		
		//frmStudentServicesAdvisor.getContentPane().setBackground(new Color(13,234,22));
		//This is the blue panel to the left. Just decoration
		JPanel panel = new JPanel();
		panel.setBackground(new Color(134,201,255));
		panel.setBounds(800, 0, 195, 540);
		
		//Status Switch. SHows is advisor is available or no
				JToggleButton sSwitch = new JToggleButton("Online");
				sSwitch.setSelected(true);
				Border border = BorderFactory.createLineBorder(Color.white);
				sSwitch.setBackground(new Color(50, 205, 50));
				sSwitch.setForeground(Color.white);
				sSwitch.setFocusable(false);
				sSwitch.setBorder(border);
				sSwitch.setBounds(855, 562, 65, 25);
				
				//panel.add(sSwitch);
				//
				
				
		frmStudentServicesAdvisor.add(panel);
		frmStudentServicesAdvisor.add(sSwitch);
		
		JPanel contentPanel = new JPanel();
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBounds(10, 200, 785, 390);
		frmStudentServicesAdvisor.add(contentPanel);
		//frmStudentServicesAdvisor.getContentPane().add(contentPanel);
		
		//Main header. Title on screen
		JLabel mTitle = new JLabel("Student Service Advisor");
		mTitle.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		mTitle.setHorizontalAlignment(SwingConstants.CENTER);
		mTitle.setBounds(120, 11, 595, 42);
		frmStudentServicesAdvisor.add(mTitle);
		
		//search by text
		JLabel searchby = new JLabel("search by:");
		searchby.setFont(new Font("Times New Roman", Font.ITALIC, 13));
		searchby.setHorizontalAlignment(SwingConstants.LEFT);
		searchby.setBounds(515, 121, 595, 14);
		frmStudentServicesAdvisor.add(searchby);
		
		JButton Submit = new JButton("Search");
		Submit.setBounds(700, 146, 80, 19);
		Submit.setBackground(new Color(134,201,255));
		Submit.setForeground(new Color(240, 248, 255));
		frmStudentServicesAdvisor.add(Submit);
		
		//View all complaints button
		JButton cButton = new JButton("View ALL Complaints and Queries");
		
		
		
		
		cButton.setBounds(10, 146, 193, 23);
		frmStudentServicesAdvisor.add(cButton);
		cButton.setBackground(new Color(134,201,255));
		cButton.setForeground(new Color(240, 248, 255));
		cButton.setFocusable(false);
		
		//Text box for student's ID
		textField = new JTextField();
		textField.setBounds(580, 146, 121, 20);
		frmStudentServicesAdvisor.add(textField);
		textField.setColumns(10);
		
		//Label for student ID text box
		JLabel sTextbox = new JLabel("Student ID");
		sTextbox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		sTextbox.setBounds(515, 146, 98, 20);
		frmStudentServicesAdvisor.add(sTextbox);
		
		
		frmStudentServicesAdvisor.setSize(1000, 640);
		frmStudentServicesAdvisor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sSwitch.setFocusable(false);
		
		
		//
		JLabel lblResults = new JLabel("results:");
		lblResults.setHorizontalAlignment(SwingConstants.LEFT);
		lblResults.setFont(new Font("Times New Roman", Font.ITALIC, 13));
		lblResults.setBounds(10, 180, 390, 14);
		frmStudentServicesAdvisor.add(lblResults);
		
		
		sSwitch.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Start the server in a separate thread
		        Thread serverThread = new Thread(new Runnable() {
		            public void run() {
		                try {
		                    ChatServer chat = new ChatServer();
		                    chat.main(null);
		                } catch (IOException ex) {
		                    ex.printStackTrace();
		                }
		            }
		        });
		        serverThread.start();
		        
		        // Start the GUI in the EDT
		        SwingUtilities.invokeLater(new Runnable() {
		            public void run() {
		                ChatServerGUI.main(null);
		            }
		        });
		    }
		    
		});


		
		Submit.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		    	contentPanel.removeAll();
			    contentPanel.updateUI();
		    	int convertkey = 0;
			    
			    if(textField.getText().isEmpty())
			    	convertkey = -1;
			    
			    else
			    {
			    	try {
			    	convertkey = Integer.parseInt(textField.getText());
			    	
			    	}catch (NumberFormatException ex) {
			    		convertkey = -1;
			    	}
			    }
			    
			    Actions act = new Actions();
			    try {
			    	ResultSet rs = null;
			    	rs = act.Search(convertkey);
			    	
			    	 if (!rs.next()) {
				        	String msg = "No Results!";
				        	
				        	if(convertkey == -1)
				        		msg = "Your Input was invalid! Search ID's consist of only Numbers. Try again :)";
				        	//
				        	JLabel NoResults = new JLabel(msg);
				        	NoResults.setForeground(Color.red);
				        	JPanel tempPanel = new JPanel();
				        	tempPanel.setPreferredSize(new Dimension(785, 390));
				        	tempPanel.setBackground(new Color(255,255,255));
				        	tempPanel.add(NoResults);
				        	contentPanel.add(tempPanel);
				        	
				        }
				        
				       
				        
				        else {
				        	
				        rs = act.Search(convertkey);
				        
				        ResultSetMetaData info = rs.getMetaData();
				        
				        
				        int columnCount = info.getColumnCount();
				        
		                
		                String[] columnNames = new String[columnCount];
		                
		                for (int i = 1; i <= columnCount; i++) {
		                    columnNames[i-1] = info.getColumnName(i);
		                }

		                
		                
		                Object[][] data = new Object[100][columnCount];
		                int rowCount = 0;
		                while (rs.next()) {
		                    for (int i = 1; i <= columnCount; i++) {
		                    	
		                        data[rowCount][i-1] = rs.getObject(i);
		                    }
		                    rowCount++;
		                
		                }
		               
				        
		                // Create a JTable with the data model and add it to the panel
		                JTable table = new JTable(data, columnNames);
		                contentPanel.add(new JScrollPane(table));
		                table.setPreferredScrollableViewportSize(new Dimension(765, 365));
		                table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		        		table.setDefaultEditor(Object.class, null);
		        		frmStudentServicesAdvisor.add(contentPanel);
		        		frmStudentServicesAdvisor.setVisible(true);
		        		
		        		rs.close();
				        }
		        		
				       }catch(SQLException x) {
				    	   x.printStackTrace();
				       }
				       
				       contentPanel.setVisible(true);
				       
				    }
				    
				});
		
		
	
	}
	public static void main(String[] args) {
		new ssAdvisor();
}
}