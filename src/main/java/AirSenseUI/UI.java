package AirSenseUI;

import javax.swing.*;
import java.io.IOException;

public class UI extends JPanel{
    WelcomePage welcomePanel = new WelcomePage();
    Profile profilePanel = new Profile();
    GeneralInfo infoPanel = new GeneralInfo();
    MyInhalers inhalerPanel = new MyInhalers();
    PollutionIndex pollutionPanel = new PollutionIndex();
    PollutionNews newsPanel = new PollutionNews();

    PollutionHistory pollutionHistory = new PollutionHistory();
    JTabbedPane tabbedPane = new JTabbedPane();







    public UI() throws IOException {

        tabbedPane.addTab("Welcome Page",welcomePanel);
        tabbedPane.addTab("Profile",profilePanel);
        tabbedPane.addTab("General Information",infoPanel);
        tabbedPane.addTab("My Inhalers",inhalerPanel);
        tabbedPane.addTab("Pollution Indices",pollutionPanel);
        tabbedPane.addTab("News",newsPanel);
        tabbedPane.addTab("Pollution History",pollutionHistory);

        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        add(tabbedPane);
    }

}
