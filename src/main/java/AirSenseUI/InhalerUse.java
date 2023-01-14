package AirSenseUI;

import StoreData.Inhaler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;

public class InhalerUse extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();
    JPanel buttonPanel = new JPanel();
    JButton usedButton = new JButton("I just used my Inhaler");
    JButton refreshButton = new JButton("Refresh page");

    public String dbUrl = "jdbc:postgresql://localhost:5433/postgres";
    // NOTE!! Change the password based on what you set it yourself - I have not yet figured out how to store on Heroku
    public Connection conn = DriverManager.getConnection(dbUrl, "postgres", "AirSense");


    public InhalerUse() throws SQLException{
        JTable use_table = new JTable(refresh_model());
        JScrollPane scrollPane = new JScrollPane(use_table);
        use_table.setFillsViewportHeight(true);

        JLabel txt = new JLabel();

        buttonPanel.setLayout(new GridLayout(3,1));
        buttonPanel.add(usedButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(txt);

        setLayout(new GridBagLayout());
        gbc.gridx = 0; gbc.gridy = 0; add(scrollPane,gbc);
        gbc.gridx = 1; gbc.gridy = 0; add(buttonPanel,gbc);

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

            } catch (SQLException ex) {
                JFrame f=new JFrame();
                JOptionPane.showMessageDialog(f,"You Haven't Logged An Inhaler Yet.","Alert",JOptionPane.WARNING_MESSAGE);

                System.out.println("Yes");
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            try {
                current.use_count();
            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            try {
                if (current.use_count()) {
                    txt.setText("Based on NHS guidance you should contact your doctor");
                } else {
                    txt.setText("Thank you for your input");
                }
            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            try {
                current.use_input(1);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            // Refresh of table
            try {
                use_table.setModel(refresh_model());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });
        refreshButton.addActionListener(e -> {
            try {
                use_table.setModel(refresh_model());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

    }
    public DefaultTableModel refresh_model() throws SQLException {
        /* The purpose of this function is to refresh the table displayed on the UI. It does this by creating a new table model
        which can then be assigned to the appropriate JTable
         */
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
