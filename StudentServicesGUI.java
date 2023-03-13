package driver;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;



import java.sql.Connection;
public class StudentServicesGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private JPanel tpanel;
    private JComboBox<String> serviceTypeComboBox, complaintCategoryComboBox, queryCategoryComboBox;
    private JTextArea detailsTextArea;
    private JButton submitButton, viewAllButton, searchButton;
    private JList<String> complaintQueryList;
   // private JTextArea  viewAllTextArea;
    private JTable  viewAllTable;
    private Connection con;
    

    public StudentServicesGUI() {
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
            String url = "jdbc:mysql://localhost:3306/students";
            String user = "root";
            String password = "";
            con = DriverManager.getConnection(url, user, password);
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
                    PreparedStatement pstmt = con.prepareStatement(
                    "INSERT INTO servicerequests (serviceType, category, details) VALUES (?, ?, ?)");
                    pstmt.setString(1, serviceType);
                    pstmt.setString(2, category);
                    pstmt.setString(3, details);
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
                String serviceType = (String) serviceTypeComboBox.getSelectedItem();
                String category = serviceType.equals("Complaint") ? (String) complaintCategoryComboBox.getSelectedItem()
                        : (String) queryCategoryComboBox.getSelectedItem();

                try {
                    // Retrieve the service requests matching the specified service type and category
                    PreparedStatement pstmt = con.prepareStatement(
                        "SELECT id, details FROM servicerequests WHERE serviceType = ? AND category = ?");
                    pstmt.setString(1, serviceType);
                    pstmt.setString(2, category);
                    ResultSet rs = pstmt.executeQuery();

                    // Display the service requests in the JList
                    
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String details = rs.getString("details");
                        
                    }
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        c.gridx = 0;
        c.gridy = 5;
        viewAllButton = new JButton("View All");
        panel.add(viewAllButton, c);

        viewAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(false);
            	try {          
                

                    // Retrieve all service requests from the database
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT  serviceType, category, details FROM servicerequests");
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
                    add(scrollPane);
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
                setSize(400, 300);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setVisible(true);
         
                	
             
            }
        });
        
    


        // Add the panel to the frame
        add(panel);

        // Center the frame on the screen
        setLocationRelativeTo(null);
    }

    
}
