package AirSenseUI;

import javax.swing.*;
import java.awt.*;

public class WelcomePage extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel welcome = new JLabel();
    JLabel airSense = new JLabel();
    JTextPane description = new JTextPane();
    JSeparator verticalSeparator = new JSeparator(SwingConstants.VERTICAL);
    Color c1 = new Color(125, 194, 181);
    Font title1 = new Font("Monospaced", Font.BOLD,20);
    Font title2 = new Font("Monospaced",Font.PLAIN,15);


    public WelcomePage(){
        welcome.setText("Welcome to AirSense");
        welcome.setFont(title1);
        airSense.setText("An app specially designed to help asthma sufferers with their day-to-day life");
        airSense.setFont(title2);
        description.setText("With AirSense, you will be able to:\n" +
                "* Log the use of your inhaler to get alerted when you use it too often or when you are going to run out.\n" +
                "* Look up pollution levels of different species in your current location or any other Borough in London, with warning about which area to avoid\n" +
                "* Get information about the different health risks associated with exposure to these species. \n" +
                "* Read news related to asthma and pollution" +
                "* Get information on where the best place to live in London is for asthma sufferers.");
        description.setEditable(false);
        description.setBorder(BorderFactory.createLineBorder(c1));

        setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(welcome,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(verticalSeparator,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(airSense,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(description,gbc);
    }
    public static String convertToMultiline(String orig)
    {
        return "<html>" + orig.replaceAll("\n", "<br>");
    }
}
