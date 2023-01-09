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
    JLabel relieverLabel = new JLabel("Reliever:");
    JLabel ageLabel = new JLabel("Age:");
    JLabel genderLabel = new JLabel("Gender:");
    JLabel locLabel = new JLabel("My current location:  ");
    JTextField nameField = new JTextField();
    JTextField relieverField = new JTextField();
    JTextField ageField = new JTextField();
    JTextField genderField = new JTextField();
    JButton InsertButton = new JButton("Insert details");
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
        add(relieverLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(relieverField,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(ageLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(ageField,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(genderLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(genderField,gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(InsertButton,gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(locLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(boroughs,gbc);

    }




    public void main(String[] args) {
        try {
            String url = "jdbc:postgresql://localhost:5433/postgres";
            Connection conn = DriverManager.getConnection(url,"postgres","AirSense");
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO users_ " +
                    "VALUES ("+nameField+", "+relieverField+", "+ageField+", "+genderField+", "+locLabel+")");


            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}
