import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException{
        // Initialises Look and Feel
        try {
            UIManager.setLookAndFeel( new FlatDarkLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        // Creates frame
        JFrame frame= new JFrame("AirSense");
        frame.setSize(500,500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PollutionIndex page = new PollutionIndex();
        frame.add(page);
    }

}