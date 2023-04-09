package view;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
public class StudentServicesGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private JComboBox<String> serviceTypeComboBox, complaintCategoryComboBox, queryCategoryComboBox;
    private JTextArea detailsTextArea;
    private JButton submitButton, viewAllButton, searchButton;
    // private JTextArea  viewAllTextArea;
    private JTable  viewAllTable;
    private Connection con;
    

    public StudentServicesGUI(String getid) {
        super("Student Services");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        // Create the panel and set its layout
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        
        // Add the service type label and combo box
        c.gridx = 0;
        c.gridy = 0;
        JLabel serviceTypeLabel = new JLabel("Service Type:");
        panel.add(serviceTypeLabel, c);
        c.gridx = 1;
        c.gridy = 0;
        serviceTypeComboBox = new JComboBox<>(new String[] { "Complaint", "Query" });
        panel.add(serviceTypeComboBox, c);

        // Add the complaint category label and combo box
        c.gridx = 0;
        c.gridy = 1;
        JLabel complaintCategoryLabel = new JLabel("Complaint Category:");
        panel.add(complaintCategoryLabel, c);
        c.gridx = 1;
        c.gridy = 1;
        complaintCategoryComboBox = new JComboBox<>(new String[] { "Missing grades", "Unresponsive professor", "Late assignment feedback", "Excessive workload", "Technical difficulties" });
        panel.add(complaintCategoryComboBox, c);

        // Add the query category label and combo box
        c.gridx = 0;
        c.gridy = 2;
        JLabel queryCategoryLabel = new JLabel("Query Category:");
        panel.add(queryCategoryLabel, c);
        c.gridx = 1;
        c.gridy = 2;
        queryCategoryComboBox = new JComboBox<>(new String[] { "Course registration", "Graduation requirements", "Financial aid", "Academic advising", "Course scheduling" });
        panel.add(queryCategoryComboBox, c);

        // Add the details label and text area
        c.gridx = 0;
        c.gridy = 3;
        JLabel detailsLabel = new JLabel("Details:");
        panel.add(detailsLabel, c);
        c.gridx = 1;
        c.gridy = 3;
        detailsTextArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(detailsTextArea);
        panel.add(scrollPane, c);

        // Add the submit button and its action listener
        c.gridx = 0;
        c.gridy = 4;
        submitButton = new JButton("Submit");
        panel.add(submitButton, c);
        c.gridx = 1;
        c.gridy = 4;
        searchButton = new JButton("Search");
        panel.add(searchButton, c);
        //c.gridx = 1;
        //c.gridy = 3;
        //viewAllTextArea = new JTextArea(5, 20);
        //JScrollPane scrollPane1 = new JScrollPane(viewAllTextArea);
        //panel.add(scrollPane1, c);

        
        try {
            String url = "jdbc:mysql://localhost:3306/Queries";
            String user = "root";
            String password = "";
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Connected");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String serviceType = (String) serviceTypeComboBox.getSelectedItem();
                String category = serviceType.equals("Complaint") ? (String) complaintCategoryComboBox.getSelectedItem()
                : (String) queryCategoryComboBox.getSelectedItem();
                String details = detailsTextArea.getText();
                

                try {
                    // Insert the service request into the database
                	String id = getid;
                    PreparedStatement pstmt = con.prepareStatement(
                    "INSERT INTO studentquery (ID,QueryType,QueryCategory, Details) VALUES (?,?,?,?)");
                    pstmt.setString(1, id);
                    pstmt.setString(2, serviceType);
                    pstmt.setString(3, category);
                    pstmt.setString(4, details);
                    pstmt.executeUpdate();

                    // Display a success message
                    JOptionPane.showMessageDialog(null, "Complaint/Query submitted successfully.");

                    // Clear the input fields
                    serviceTypeComboBox.setSelectedIndex(0);
                    complaintCategoryComboBox.setSelectedIndex(0);
                    queryCategoryComboBox.setSelectedIndex(0);
                    detailsTextArea.setText("");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        
  
        
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String queryID = JOptionPane.showInputDialog("Enter the query ID:");
                if (queryID != null) {
                    try {
                        // Retrieve the query details and its associated responses from the database
                        PreparedStatement pstmt = con.prepareStatement(
                            "SELECT * FROM studentquery WHERE QueryID = ?");
                        pstmt.setString(1, queryID);
                        ResultSet rs = pstmt.executeQuery();

                        // Create a list to store the query details and its associated responses
                        ArrayList<String[]> rows = new ArrayList<>();
                        while (rs.next()) {
                            String[] row = { rs.getString("ID"), rs.getString("QueryType"), rs.getString("QueryCategory"), rs.getString("Details"),rs.getString("Status") };
                            rows.add(row);
                        }

                        // Create a table model with the query details and its associated responses
                        DefaultTableModel tableModel = new DefaultTableModel(rows.toArray(new Object[0][]), new String[] { "ID", "Query Type", "Query Category", "Details","Status" });

                        // Create a table with the table model
                        viewAllTable = new JTable(tableModel);

                        // Display the table in a new window
                        JFrame frame = new JFrame("Query Details and Responses");
                        JScrollPane scrollPane = new JScrollPane(viewAllTable);
                        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
                        frame.pack();
                        frame.setVisible(true);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


        c.gridx = 0;
        c.gridy = 5;
        viewAllButton = new JButton("View All");
        panel.add(viewAllButton, c);

        viewAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(true);
                JFrame NFrame = new JFrame();
                NFrame.setSize(400,300);
            	try {          
                

                    // Retrieve all service requests from the database
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(" select QueryID,ID,QueryType,QueryCategory,Details,Status from studentquery WHERE ID = '"+getid+"'");
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                   
                    String[] columnNames = new String[columnCount];
                    
                    for (int i = 1; i <= columnCount; i++) { 
                        columnNames[i-1] = metaData.getColumnName(i);
                    }

                    Object[][] data = new Object[100][columnCount];
                    int rowCount = 0;
                    while (rs.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            data[rowCount][i-1] = rs.getObject(i);
                        }
                        rowCount++;
                    }
                   
                    
                    // Create a JTable with the data model and add it to the frame
                    
                    viewAllTable = new JTable(data, columnNames);
                    JScrollPane scrollPane = new JScrollPane(viewAllTable);
                    NFrame.add(scrollPane);
            		viewAllTable.setDefaultEditor(Object.class, null);

                    rs.close();
                    // Display the service requests in the JList
                  
                   /* while (rs.next()) {
           
                    	sb.append(rs.getString("serviceType")).append("\t")
                        .append(rs.getString("category")).append("\t\t")
                        .append(rs.getString("details")).append("\n");
                    
                         
                        
                   
                	String data = sb.toString();
                	
                    
                    JTextArea viewAllTextArea = new JTextArea(10, 30);
                    viewAllTextArea.setText(data);
                    // Display the JTextArea in a JOptionPane
                    JOptionPane.showMessageDialog(null, new JScrollPane(viewAllTextArea));
                    rs.close();*/
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                //setSize(400, 300);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                NFrame.setVisible(true);
         
                	
             
            }
        });
        
    


        // Add the panel to the frame
        add(panel);

        // Center the frame on the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }

    



	





	public void main(String [] args) {
    	new StudentServicesGUI("0");
    }
}