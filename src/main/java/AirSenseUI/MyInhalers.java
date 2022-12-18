package AirSenseUI;

import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.time.format.DateTimeFormatter;
//import java.time.LocalDateTime;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
import java.sql.*;

public class MyInhalers extends JPanel{
    protected JTable inhaler_table;
    protected JTable use_history;
    public String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
    // NOTE!! Change the password based on what you set it yourself - I have not yet figured out how to store on Heroku
    public Connection conn = DriverManager.getConnection(dbUrl, "postgres", "Il8S741v");

    public MyInhalers() throws SQLException {

        String[] columnNames = {"Inhaler Type", "Expiry date", "Quantity remaining"};
        Object[][] data = {{"Reliever", "12-12-2020", "200"}};
        inhaler_table = new JTable(data, columnNames);

        // Data to be displayed in the JTable

        // Grabbing data from Postgresql table
        try {
            Statement s = conn.createStatement();
            String sqlStr = "SELECT * FROM inhalers WHERE id>0;";
            ResultSet rs = s.executeQuery(sqlStr);

            // Row count
            int rowCount = 0;
            if (rs.last()) {
            }
            /*while (rs.next()) {
                String temp_type = String.valueOf(rs.getString("inhaler_type"));
                String temp_expiry = String.valueOf(rs.getString("expiry_date"));
                String temp_quantity = String.valueOf(rs.getInt("quantity"));

                Object[] row = { temp_type, temp_expiry, temp_quantity};

                data.(row);
            System.out.println(rs.getString("inhaler_type") + rs.getString("expiry_date") + rs.getInt("quantity"));
            } */
            // Formatting it correctly into the JTable

        } catch (Exception e) {
        }

        JScrollPane scrollPane = new JScrollPane(inhaler_table);
        inhaler_table.setFillsViewportHeight(true);
        add(scrollPane);
    }
}