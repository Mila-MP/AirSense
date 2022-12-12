import AirSenseUI.UI;
import StoreData.Inhaler;
import com.formdev.flatlaf.intellijthemes.FlatSolarizedDarkIJTheme;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws Exception {
        // Initialises Look and Feel
        Inhaler myreliever = new Inhaler("reliever", "12/12/12", 200);
        myreliever.add_inhaler();
        myreliever.use_count(3);

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





