package view;
//import org.apache.logging.log4j.LogManager; 
//import org.apache.logging.log4j.Logger;
import java.awt.*;

import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
public class LoginGUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JPanel innerpanel;
	private JPanel headerPanel;
    private JLabel userLabel, passLabel;
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;
    //private static final Logger logger = LogManager.getLogger(LoginGUI.class);
    
    public LoginGUI() {
    	
        super("Login");
        //logger.info(" Test Info message");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,640);

        // Create the panel and set its layout
        panel = new JPanel();
        headerPanel = new JPanel();
        innerpanel = new JPanel();
        JLabel headerText = new JLabel("SIGN IN");
        Font font = new Font("Arial", Font.BOLD, 24);
        headerText.setFont(font);
        headerText.setForeground(Color.white);
        headerText.setPreferredSize(new Dimension(100,80));
        
        
        
        headerPanel.add(headerText,BorderLayout.CENTER);
        
        
        
        
        panel.setBackground(new Color(134,201,255));
      
        headerPanel.setBackground(new Color(134,201,255));
        //this.setBackground(new Color(255,255,255));
        
        headerPanel.setPreferredSize(new Dimension(1000,100));
        innerpanel.setPreferredSize(new Dimension(200,300));
        innerpanel.setBackground(new Color(255,255,255));
        //panel.setLayout(BorderLayout.CENTER);
        

        // Add the username label and text field
        userLabel = new JLabel("Username:");
        innerpanel.add(userLabel);
        userField = new JTextField();
        userField.setPreferredSize(new Dimension(140, 25));
        innerpanel.add(userField);

        // Add the password label and text field
        passLabel = new JLabel("Password:");
        innerpanel.add(passLabel);
        passField = new JPasswordField();
        passField.setPreferredSize(new Dimension(140, 25));
        innerpanel.add(passField);

        // Add the login button and its action listener
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(140, 25));
        innerpanel.add(loginButton);
        
        JRadioButton radioButton = new JRadioButton("Student");
        JRadioButton radioButton1 = new JRadioButton("Supervisor");
        JRadioButton radioButton2 = new JRadioButton("Advisor");
        
        
        radioButton.setSelected(true);
        
        ButtonGroup buttonGroup = new ButtonGroup();
        radioButton.setBackground(new Color(255,255,255));
        radioButton2.setBackground(new Color(255,255,255));
        radioButton1.setBackground(new Color(255,255,255));
        
      
        buttonGroup.add(radioButton);
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        
        innerpanel.add(radioButton);
        innerpanel.add(radioButton1);
        innerpanel.add(radioButton2);
       
        //this.createConnection();
		//this.configureStreams();
        
        loginButton.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
                try {
                	LoginGUI loginGUI = new LoginGUI();
                    // Connect to the database
                	
                	String selectedText = null;
                    if (radioButton1.isSelected()) {
                      selectedText = radioButton1.getText();
                    } else if (radioButton2.isSelected()) {
                      selectedText = radioButton2.getText();
                    } else if (radioButton.isSelected()) {
                      selectedText = radioButton.getText();
                    }
                	
                    String url = "jdbc:mysql://localhost:4306/query";
                    String user = "usr_root";
                    String password = "2023";
                    Connection con = DriverManager.getConnection(url, user, password);

                    String sql = "SELECT * FROM logindb WHERE ID = ? AND Password = ? AND Account_Type = ?";
                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.setString(1, userField.getText());
                    String getid = userField.getText();
                    statement.setString(2, passField.getText());
                    statement.setString(3, selectedText);
                    ResultSet rs = statement.executeQuery();
                    
                    // Check if the login is successful
                    if (rs.next()) {
                    	loginGUI.setVisible(false);
                        JOptionPane.showMessageDialog(null, "Login successful");
                        
                        
                        if(selectedText.equals("Student")) {
                        new StudentServicesGUI(getid);         
                        
                        }
                        else 
                        	 if(selectedText.equals("Advisor")) {
                        		 new ssAdvisor();
                        	 }
                                 else 
                                	 if(selectedText.equals("Supervisor")) {
                                		 new SSSDashboard();      
                                	 }
                        
                        } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password");
                        
                    }

                    // Close the database connection
                    con.close();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        
        // Add the panel to the frame
        panel.add(innerpanel,BorderLayout.SOUTH);
        this.add(headerPanel,BorderLayout.NORTH);
        this.add(panel,BorderLayout.CENTER);

        // Center the frame on the screen
        setLocationRelativeTo(null);
        
       
    }
/*    
private void createConnection() {
		
		try {
			connectionSocket = new Socket("127.0.0.1", 8888);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
private void configureStreams() {
	try {
		//Creates an input stream to receive data from the server
		objOs = new ObjectOutputStream(connectionSocket.getOutputStream());
		//Creates an output stream to send data to the server
		objIs = new ObjectInputStream(connectionSocket.getInputStream());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public void closeConnection() {
	try {
		objOs.close();
		objIs.close();
		connectionSocket.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
*/	

    public static void main(String[] args) {
    	
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.setVisible(true);
        
        
    }
}