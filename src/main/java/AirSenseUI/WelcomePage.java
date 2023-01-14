package AirSenseUI;

import javax.swing.*;
import java.awt.*;

/**
 * The WelcomePage class provides the user interface for the About AirSense tab.
 */
public class WelcomePage extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel welcome = new JLabel();
    JLabel airSense = new JLabel();
    JTextPane description = new JTextPane();
    JLabel empty1 = new JLabel(convertToMultiline("\n"));
    JLabel empty2 = new JLabel(convertToMultiline("\n"));
    JLabel empty3 = new JLabel(convertToMultiline("\n\n\n"));
    Font title1 = new Font("Ubuntu", Font.BOLD,20);
    Font title2 = new Font("Ubuntu",Font.PLAIN,15);
    Font body = new Font("Ubuntu",Font.PLAIN,13);

    public WelcomePage(){
        welcome.setText("Welcome to AirSense");
        welcome.setFont(title1);
        airSense.setText("An app specially designed to help asthma sufferers with their day-to-day life");
        airSense.setFont(title2);
        description.setText("With AirSense, you will be able to:\n\n" +
                "* Log the use of your inhaler to get alerted when you use it too often or when you are going to run out.\n\n" +
                "* Look up pollution levels of different species in your current location or any other Borough in London,\n" +
                "  with warning about which area to avoid.\n\n" +
                "* Get information about the different health risks associated with exposure to these species. \n\n" +
                "* Read news related to asthma and pollution. \n\n" +
                "* Get information on where the best place to live in London is for asthma sufferers.");
        description.setFont(body);
        description.setEditable(false);
        description.setBorder(BorderFactory.createBevelBorder(1));

        setLayout(new GridBagLayout());
        gbc.gridx = 0; gbc.gridy = 0; add(empty1,gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(welcome,gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(empty2,gbc);
        gbc.gridx = 0; gbc.gridy = 3; add(airSense,gbc);
        gbc.gridx = 0; gbc.gridy = 4; add(empty3,gbc);
        gbc.gridx = 0; gbc.gridy = 5; add(description,gbc);
    }

    /* Reference 2 - taken from https://stackoverflow.com/questions/2152742/java-swing-multiline-labels */
    /**
     * @param orig String
     * @return String with \n replaced with <br>
     */
    public static String convertToMultiline(String orig)
    {
        return "<html>" + orig.replaceAll("\n", "<br>");
    }
}
