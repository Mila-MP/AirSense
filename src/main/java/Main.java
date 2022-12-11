import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws IOException, SQLException {
        JFrame frame = new JFrame("AirSense");
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PollutionInfo page = new PollutionInfo();
        frame.add(page.getMainPanel());

        Inhaler myreliever = new Inhaler("reliver", "12/12/12", 200);
        myreliever.add_inhaler();


    }

}
