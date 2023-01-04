package AirSenseUI;

import StoreData.Inhaler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import StoreData.Inhaler;

public class InhalerUse extends JPanel {

    public JTable use_table;
    public JTextField puffs;
    JPanel tablePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JButton used = new JButton("I just used my Inhaler");
    public String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
    // NOTE!! Change the password based on what you set it yourself - I have not yet figured out how to store on Heroku
    public Connection conn = DriverManager.getConnection(dbUrl, "postgres", "airsense");

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
            model.insertRow(0, new Object[]{rs2.getString("use_date"), rs2.getInt("no_of_puffs")});
        }
        return model;
    }

    public InhalerUse() throws SQLException, ClassNotFoundException {
        setLayout(new GridLayout(2, 1));


        JTable use_table = new JTable(refresh_model());

        // add new row to the table - actionListener and rowCount - rowCount in table

        JScrollPane scrollPane = new JScrollPane(use_table);
        use_table.setFillsViewportHeight(true);
        tablePanel.add(scrollPane);
        buttonPanel.add(used);
        add(tablePanel);
        add(buttonPanel);

        // Creating the input for no. of puffs
        JTextField puffs = new JTextField();

        used.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Creation of inhaler with qualities found in the first row of myInhalers
                Statement s = null;
                try {
                    s = conn.createStatement();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String query2 = "SELECT * FROM inhalers";
                ResultSet rs2 = null;
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
                Inhaler current = null;
                try {
                    current = new Inhaler(rs2.getString("inhaler_type"), rs2.getString("expiry_date"), rs2.getInt("quantity"));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    current.use_count();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                String msg;
                try {
                    if (current.use_count() == true) {
                        msg = "Based on NHS guidance you should contact your doctor";
                    } else {
                        msg = "Thank you for your input";
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

                JLabel txt = new JLabel(msg);
                tablePanel.add(txt);

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
                try {
                    if (current.use_count() == true) {
                        txt.setText("Based on NHS guidance you should contact your doctor");
                    } else {
                        txt.setText("Thank you for your input");
                    }


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

    }

}
