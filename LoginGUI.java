package driver;
import java.awt.*;

import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;
import java.sql.DriverManager;

import javax.swing.JOptionPane;
import java.sql.Connection;
public class LoginGUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;
    private JLabel userLabel, passLabel;
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;

    public LoginGUI() {
        super("Login Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,200);

        // Create the panel and set its layout
        panel = new JPanel();
        panel.setLayout(new GridLayout(3,2));

        // Add the username label and text field
        userLabel = new JLabel("Username:");
        panel.add(userLabel);
        userField = new JTextField();
        panel.add(userField);

        // Add the password label and text field
        passLabel = new JLabel("Password:");
        panel.add(passLabel);
        passField = new JPasswordField();
        panel.add(passField);

        // Add the login button and its action listener
        loginButton = new JButton("Login");
        panel.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {LoginGUI loginGUI = new LoginGUI();
                    // Connect to the database
                
                    String url = "jdbc:mysql://localhost:3306/students";
                    String user = "root";
                    String password = "";
                    Connection con = DriverManager.getConnection(url, user, password);

                    // Create the SQL statement and execute it
                    Statement stmt = con.createStatement();
                    String sql = "SELECT * FROM studentlogin WHERE ID ='" + userField.getText() + "' AND Password='" + passField.getText() + "'";
                    ResultSet rs = stmt.executeQuery(sql);

                    // Check if the login is successful
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Login successful");
                        loginGUI.setVisible(false);

                        StudentServicesGUI  studentServicesGUI = new StudentServicesGUI();         
                        studentServicesGUI.setVisible(true);
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
        add(panel);

        // Center the frame on the screen
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.setVisible(true);
        
    }
}
