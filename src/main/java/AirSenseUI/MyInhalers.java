package AirSenseUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import StoreData.Inhaler;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;

/**
 * The MyInhalers class provides the user interface for the MyInhalers tab.
 */
public class MyInhalers extends JPanel{

    public String dbUrl = "jdbc:postgresql://ec2-3-229-161-70.compute-1.amazonaws.com:5432/d4fdh0dvfc4v3r";
    public Connection conn = DriverManager.getConnection(dbUrl,
            "orexdsnjebnlrh",
            "684b6442280ff5e797fcf680b5be53d48a0df862c38694dd7d14c7b6c4c3ccd0");
    JButton refreshButton = new JButton("Refresh Inhaler data");
    JButton addInhalerButton = new JButton("Add inhaler");
    public JButton deleteInhalerButton = new JButton("Delete current Inhaler");
    JTextField inhalerNameTF = new JTextField();
    JFormattedTextField expiryDateTF = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
    JTextField quantityTF = new JTextField();
    JLabel inhalerNameLabel = new JLabel("Inhaler Name: ");
    JLabel expiryLabel = new JLabel("Expiry Date: ");
    JLabel quantityLabel = new JLabel("Inhaler Quantity: ");
    JLabel digitsWarning = new JLabel();
    JTable tableMyInhalers = new JTable ();
    JPanel infoPanel = new JPanel();
    GridBagConstraints gbc = new GridBagConstraints();

    public MyInhalers() throws SQLException, ClassNotFoundException {

        digitsWarning.setForeground(Color.red);
        infoPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0; gbc.gridy = 0; infoPanel.add(refreshButton,gbc);
        
        // Table first made when the app is started
        tableMyInhalers.setModel(refreshModel());
        JScrollPane scrollPane = new JScrollPane(tableMyInhalers);
        tableMyInhalers.setFillsViewportHeight(true);
        setLayout(new GridLayout(1,2));
        add(scrollPane);
        add(infoPanel);


        if (expiry_warning()){
            JOptionPane.showMessageDialog(null,
                    "You inhaler is about to expire, please get another one",
                    "Alert",
                    JOptionPane.WARNING_MESSAGE);
        }

        emptyTableCheck();

        /* Reference 3 - taken from https://www.tutorialspoint.com/how-can-we-make-jtextfield-accept-only-numbers-in-java */
        quantityTF.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyCode() == 8 ||
                        ke.getKeyCode() == 46 || ke.getKeyCode() == 37 || ke.getKeyCode() == 39) {
                    quantityTF.setEditable(true);
                    digitsWarning.setText("");
                }
                else {
                    quantityTF.setEditable(false);
                    digitsWarning.setText("* Enter only numeric digits(0-9)");
                }
            }
        });
        /* End of reference 3*/

        refreshButton.addActionListener(e -> {
            try {
                tableMyInhalers.setModel(refreshModel());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        deleteInhalerButton.addActionListener(e -> {
            try {
                deleteInhalerProfile();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            try {
                tableMyInhalers.setModel(refreshModel());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            infoPanel.remove(deleteInhalerButton);

            try {
                emptyTableCheck();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        addInhalerButton.addActionListener(e -> {
            String name = inhalerNameTF.getText();
            String expiry = expiryDateTF.getText();
            int quantity = Integer.parseInt(quantityTF.getText());
            try {
                Inhaler current = new Inhaler(name,expiry,quantity);
                current.add_inhaler();
            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            infoPanel.remove(inhalerNameLabel);
            infoPanel.remove(inhalerNameTF);
            infoPanel.remove(expiryLabel);
            infoPanel.remove(expiryDateTF);
            infoPanel.remove(digitsWarning);
            infoPanel.remove(quantityLabel);
            infoPanel.remove(quantityTF);
            infoPanel.remove(addInhalerButton);
            gbc.gridx = 1; gbc.gridy = 0; infoPanel.add(deleteInhalerButton, gbc);

            try {
                tableMyInhalers.setModel(refreshModel());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public Boolean expiry_warning() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("select * from inhalers");

        boolean warning = false;

        while(rs.next()){
            LocalDate date = LocalDate.parse(rs.getString("expiry_date"));
            if(date.isBefore(LocalDate.now().minus(Period.ofMonths(1)))){
                warning = true;
            }
        }

        return warning;
    }

    /**
     * The returnModel method refreshes the table displayed on the UI.
     * @return refreshed table model
     */
    public DefaultTableModel refreshModel() throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Inhaler");
        model.addColumn("Expiry date");
        model.addColumn("Quantity");

        Statement s = conn.createStatement();
        String query2 = "SELECT * FROM inhalers";

        ResultSet rs2 = s.executeQuery(query2);
        while(rs2.next()){
            model.insertRow(0, new Object[] {rs2.getString("inhaler_type"),
                    rs2.getString("expiry_date"),
                    rs2.getInt("quantity")});
        }
       return model;
    }

    /**
     * The emptyTableCheck method check if the table is empty.
     */
    public void emptyTableCheck() throws SQLException {
        // If inhalers is empty
        Statement s = conn.createStatement();
        ResultSet rs_empty = s.executeQuery("SELECT count(*) AS rowcount FROM inhalers");
        rs_empty.next();
        int row_count = rs_empty.getInt("rowcount");
        if (row_count == 0){

            gbc.gridx = 0; gbc.gridy = 1; infoPanel.add(inhalerNameLabel, gbc);
            gbc.gridx = 1; gbc.gridy = 1; infoPanel.add(inhalerNameTF, gbc);
            gbc.gridx = 0; gbc.gridy = 2; infoPanel.add(expiryLabel, gbc);
            gbc.gridx = 1; gbc.gridy = 2; infoPanel.add(expiryDateTF, gbc);
            gbc.gridx = 2; gbc.gridy = 3; infoPanel.add(digitsWarning, gbc);
            gbc.gridx = 0; gbc.gridy = 3; infoPanel.add(quantityLabel,gbc);
            gbc.gridx = 1; gbc.gridy = 3; infoPanel.add(quantityTF, gbc);
            gbc.gridx = 2; gbc.gridy = 3; infoPanel.add(digitsWarning, gbc);
            gbc.gridx = 0; gbc.gridy = 4; infoPanel.add(addInhalerButton, gbc);
        }
        else{
            gbc.gridx = 1; gbc.gridy = 0; infoPanel.add(deleteInhalerButton, gbc);
        }
    }


    /**
     * The deleteInhalerProfile method deletes the inhaler in the table.
     */
    public void deleteInhalerProfile() throws SQLException {
        Statement s = conn.createStatement();
        s.executeUpdate("TRUNCATE TABLE inhalers");
        s.executeUpdate("TRUNCATE TABLE use_data");
    }
}
