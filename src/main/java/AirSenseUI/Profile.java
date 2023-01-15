package AirSenseUI;

import GetData.GetLocalAuthorities;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * The Profile class provides the user interface for the profile tab.
 */
public class Profile extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel nameLabel = new JLabel("Name:");
    JLabel nameLabel2 = new JLabel();
    JLabel ageLabel = new JLabel("Age:");
    JLabel ageLabel2 = new JLabel();
    JLabel genderLabel = new JLabel("Gender:");
    JLabel genderLabel2 = new JLabel();
    JLabel locLabel = new JLabel("My current location:  ");
    JTextField nameField = new JTextField();
    JTextField ageField = new JTextField();
    JTextField genderField = new JTextField();
    JButton InsertButton = new JButton("Insert details");
    static JComboBox<String> boroughCB;

    public Profile() throws IOException{
        // Combo box initialisation
        GetLocalAuthorities  boroughs = new GetLocalAuthorities();
        String boroughString = boroughs.print();
        String[] boroughList = boroughString.split("\n");
        boroughCB = new JComboBox<>(boroughList);

        // Main panel configuration
        setLayout(new GridBagLayout()); //Layout setup
        gbc.gridx = 0; gbc.gridy = 0; add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; add(nameField, gbc);
        gbc.gridx = 2; gbc.gridy = 0; add(nameLabel2, gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(ageLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; add(ageField, gbc);
        gbc.gridx = 2; gbc.gridy = 1; add(ageLabel2, gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(genderLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; add(genderField, gbc);
        gbc.gridx = 2; gbc.gridy = 2; add(genderLabel2, gbc);
        gbc.gridx = 1; gbc.gridy = 3; add(InsertButton, gbc);
        gbc.gridx = 0; gbc.gridy = 4; add(locLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4; add(boroughCB, gbc);

        InsertButton.addActionListener(e -> {
            String name = nameField.getText();
            nameLabel2.setText(name);
            String age = ageField.getText();
            ageLabel2.setText(age);
            String gender = genderField.getText();
            genderLabel2.setText(gender);
        });
    }
}