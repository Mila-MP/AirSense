package AirSenseUI;

import GetData.GetLocalAuthorities;
import StoreData.Inhaler;
import StoreData.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//Importing all required packages


public class Profile extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel nameLabel = new JLabel("Name:");
    JLabel ageLabel = new JLabel("Age:");
    JLabel genderLabel = new JLabel("Gender:");
    JLabel locLabel = new JLabel("My current location:  ");
    JTextField nameField = new JTextField();
    JTextField ageField = new JTextField();
    JTextField genderField = new JTextField();
    JTable profileinfo = new JTable();

    //Creating Labels + textfields for the user details
    JButton InsertButton = new JButton("Insert details");
    static JComboBox<String> boroughs;

    public Profile() throws IOException, SQLException {
        GetLocalAuthorities la = new GetLocalAuthorities();
        String str = la.print();
        String[] choices = str.split("\n");
        boroughs = new JComboBox<>(choices);
        //Dropdown box to pick the users location from list of boroughs in London

        //InsertButton.addActionListener(e -> {
            String name = nameField.getText();
            String age = ageField.getText();
            String gender = genderField.getText();

// ActionListener to assign the user inputted values once button is clicked

            String[] columnNames = {"Name",
                    "Age",
                    "Gender"};
            Object[][] data = {
                    {name, age,
                            gender},
            };

            JTable profileinfo = new JTable(data, columnNames);
            profileinfo.setPreferredScrollableViewportSize(new Dimension(500, 40));
            profileinfo.setFillsViewportHeight(true);


            setLayout(new GridBagLayout()); //Layout setup
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(nameLabel, gbc);
            gbc.gridx = 1;
            gbc.gridy = 0;
            add(nameField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            add(ageLabel, gbc);
            gbc.gridx = 1;
            gbc.gridy = 1;
            add(ageField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            add(genderLabel, gbc);
            gbc.gridx = 1;
            gbc.gridy = 2;
            add(genderField, gbc);
            gbc.gridx = 1;
            gbc.gridy = 3;
            add(InsertButton, gbc);
            gbc.gridx = 0;
            gbc.gridy = 4;
            add(locLabel, gbc);
            gbc.gridx = 1;
            gbc.gridy = 4;
            add(boroughs, gbc);
            gbc.gridx = 1;
            gbc.gridy = 5;
            add(profileinfo, gbc);

        }

    }