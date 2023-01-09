import AirSenseUI.PollutionNews;
import AirSenseUI.UI;
import GetData.GetNews;
import GetData.GetNewsScraper;
import StoreData.Inhaler;
import com.formdev.flatlaf.intellijthemes.FlatSolarizedDarkIJTheme;
import javax.swing.*;


public class Main {


    public static void main(String[] args) throws Exception {

        Inhaler test_inhaler = new Inhaler("test","2012-11-11",200);


        // Initialises Look and Feel
        
        try {
            UIManager.setLookAndFeel(new FlatSolarizedDarkIJTheme());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        // Creates frame
        JFrame frame = new JFrame("AirSense");
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        UI page = new UI();
        frame.add(page);

        


    }
}






