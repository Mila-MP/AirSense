package AirSenseUI;

import StoreData.Inhaler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;

/**
 * The InhalerUse class provides the user interface for the Inhaler Use tab.
 */
public class InhalerUse extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();
    JPanel buttonPanel = new JPanel();
    JButton usedButton = new JButton("I just used my Inhaler");
    JButton refreshButton = new JButton("Refresh page");
    JLabel txt = new JLabel();
    public String dbUrl = "jdbc:postgresql://ec2-3-229-161-70.compute-1.amazonaws.com:5432/d4fdh0dvfc4v3r";
    public Connection conn = DriverManager.getConnection(dbUrl, "orexdsnjebnlrh", "684b6442280ff5e797fcf680b5be53d48a0df862c38694dd7d14c7b6c4c3ccd0");

    public InhalerUse() throws SQLException{
        // Table initialisation
        JTable useTable = new JTable(refreshModel());
        JScrollPane scrollPane = new JScrollPane(useTable);
        useTable.setFillsViewportHeight(true);

        // Sub panel configuration
        buttonPanel.setLayout(new GridLayout(3,1));
        buttonPanel.add(usedButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(txt);

        // Main panel configuration
        setLayout(new GridBagLayout());
        gbc.gridx = 0; gbc.gridy = 0; add(scrollPane,gbc);
        gbc.gridx = 1; gbc.gridy = 0; add(buttonPanel,gbc);

        // Button configuration
        usedButton.addActionListener(e -> {
            // Creation of inhaler with qualities found in the first row of myInhalers
            Statement s;

            try {
                s = conn.createStatement();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            String query2 = "SELECT * FROM inhalers";
            ResultSet rs2;
            try {
                rs2 = s.executeQuery(query2);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            try {
                rs2.next();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            Inhaler current;
            try {
                current = new Inhaler(rs2.getString("inhaler_type"), rs2.getString("expiry_date"), rs2.getInt("quantity"));
                current.use_input(1);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "You Haven't Logged An Inhaler Yet.", "Alert", JOptionPane.WARNING_MESSAGE);
                throw new RuntimeException(ex);
            }

            try {
                if (current.use_count()) {
                    JOptionPane.showMessageDialog(null,"Based on NHS guidance you should contact your doctor","Warning",JOptionPane.WARNING_MESSAGE);
                } else {
                    txt.setText("Thank you for your input");
                }
            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            try {
                if (current.quantity_warning()){
                    JOptionPane.showMessageDialog(null,"Your inhaler has less than 25 doses left","Warning",JOptionPane.WARNING_MESSAGE);
                }
            } catch (ClassNotFoundException | SQLException ex) {
                throw new RuntimeException(ex);
            }

            // Refresh of table
            try {
                useTable.setModel(refreshModel());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });

        refreshButton.addActionListener(e -> {
            try {
                useTable.setModel(refreshModel());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * The method refreshModel refreshes the table displayed on the UI.
     * @return table model
     */
    public DefaultTableModel refreshModel() throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Use date");
        model.addColumn("Number of puffs");

        Statement s = conn.createStatement();
        String query2 = "SELECT * FROM use_data";

        ResultSet rs2 = s.executeQuery(query2);
        while (rs2.next()) {
            // Adds each row in use_data table to the table model, use_date column is changed to an appropriate format
            model.insertRow(0, new Object[]{rs2.getTimestamp("use_date").toLocalDateTime().format(
                    DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), rs2.getInt("no_of_puffs")});
        }
        return model;
    }

}
