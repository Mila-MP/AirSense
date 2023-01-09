import AirSenseUI.PollutionNews;
import AirSenseUI.UI;
import GetData.GetNews;
import GetData.GetNewsScraper;
import com.formdev.flatlaf.intellijthemes.FlatSolarizedDarkIJTheme;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        //StringBuilder sb=new StringBuilder("<p> Hello <p> my. name is <p> what can. i do for you <p>");
        //GetNewsScraper n = new GetNewsScraper("FogEverywhere");
        //n.formatString(sb);



        //GetNews test = new GetNews();
        //test.getnewstitles();





        //GetNewsScraper hsomething = new GetNewsScraper("ComingUpAir");
        //hsomething.extractLinks();

        //GetNews news = new GetNews();
        //news.getnewsid(0);
        //Inhaler myreliever = new Inhaler("reliver", "12/12/12", 200);
        //myreliever.add_inhaler();

        //myreliever.use_count(3);


        
            }


        }




