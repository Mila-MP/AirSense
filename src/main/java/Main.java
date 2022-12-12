import AirSenseUI.UI;
import com.formdev.flatlaf.intellijthemes.FlatSolarizedDarkIJTheme;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException{
        // Initialises Look and Feel
        try {
           UIManager.setLookAndFeel( new FlatSolarizedDarkIJTheme());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        // Creates frame
        JFrame frame= new JFrame("AirSense");
        frame.setSize(1000,500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        UI page = new UI();
        frame.add(page);

    }

}