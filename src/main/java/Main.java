import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException{
        JFrame frame= new JFrame("AirSense");
        frame.setSize(500,500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PollutionInfo page = new PollutionInfo();
        frame.add(page.getMainPanel());
    }

}