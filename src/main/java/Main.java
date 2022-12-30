import AirSenseUI.UI;
import com.formdev.flatlaf.intellijthemes.FlatSolarizedDarkIJTheme;
import javax.swing.*;


public class Main {

    public static void main(String[] args) throws Exception {
        // Initialises Look and Feel
        try {
            UIManager.setLookAndFeel(new FlatSolarizedDarkIJTheme());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        // Creates frame
        JFrame frame = new JFrame("AirSense");
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Puts UI on frame
        UI page = new UI();
        frame.add(page);
//        GetTest lol = new GetTest(11);
//        System.out.println(lol.print());

    }
}





