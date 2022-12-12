package AirSenseUI;

import javax.swing.*;
import java.awt.*;

public class WelcomePage extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel welcome = new JLabel();
    JLabel airSense = new JLabel();
    JTextPane description = new JTextPane();


    public WelcomePage(){
        welcome.setText("Welcome to AirSense");
        airSense.setText("An app specially designed to help asthma sufferers with their day-to-day life");
        description.setText("With AirSense, you will be able to:\n" +
                "* Log the use of your inhaler to get alerted when you use it too often or when you are going to run out.\n" +
                "* Look up pollution levels of different species in your current location or any other Borough in London, with warning about which area to avoid\n" +
                "* Get information about the different health risks associated with exposure to these species. \n" +
                "* Read news related to asthma and pollution in London.");
        description.setEditable(false);

        setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(welcome,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(airSense,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(description,gbc);
    }
    public static String convertToMultiline(String orig)
    {
        return "<html>" + orig.replaceAll("\n", "<br>");
    }
}
