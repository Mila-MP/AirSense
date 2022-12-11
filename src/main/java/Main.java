import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {


    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("AirSense");
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PollutionInfo page = new PollutionInfo();
        frame.add(page.getMainPanel());

        Inhaler myreliever = new Inhaler("reliver", "12/12/12", 200);
        myreliever.add_inhaler();

        myreliever.use_count(3);
            }


        }




