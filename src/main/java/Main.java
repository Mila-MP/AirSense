
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

        Inhaler inhaler1 = new Inhaler("Reliever");
        Location loc1 = new Location("London","My home");
        User user1 = new User("Asthma Sufferer","20","F",inhaler1, loc1);
        GetSpecies species = new GetSpecies();
        GetLocalAuthorities la = new GetLocalAuthorities();
        System.out.println(species.print());
        System.out.println(la.print());
    }

}