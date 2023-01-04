package AirSenseUI;

import GetData.GetLocalAuthorities;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Profile extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();
    String name;
    String location;
    JLabel nameLabel = new JLabel("Name:");
    JLabel locLabel = new JLabel("My current location:  ");
    JTextField nameField = new JTextField();
    static JComboBox<String> boroughs;

    public Profile() throws IOException {
        GetLocalAuthorities la = new GetLocalAuthorities();
        String str = la.print();
        String[] choices = str.split("\n");
        boroughs = new JComboBox<>(choices);

        setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(nameField,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(locLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(boroughs,gbc);

    }




    public static void main (String[] args) {
        try {
            String url = "jdbc:postgresql://localhost:5433/postgres";
            Connection conn = DriverManager.getConnection(url,"postgres","AirSense");
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO users_ " +
                    "VALUES ('MyName', 'reliever', '20', 'female', 'Harrow')");


            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }
}
