import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class PollutionInfo {
    JPanel mainPanel = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JLabel welcome = new JLabel("Welcome to AirSense");
    JLabel question = new JLabel("What do you want to know?");
    JButton okButton = new JButton(("OK"));

    JLabel species = new JLabel();
    public PollutionInfo() throws IOException {
        mainPanel.setLayout(new GridLayout(3,1));
        panel1.setLayout(new GridLayout(1,1));
        panel2.setLayout(new GridLayout(1,3));
        panel3.setLayout(new GridLayout(1,1));

        panel1.add(welcome);

        panel2.add(question);
        String[] choices = {"The different types of pollutants", "The pollution levels in my current location"};
        final JComboBox<String> cb = new JComboBox<>(choices);
        panel2.add(cb);
        panel2.add(okButton);

        panel3.add(species);

        mainPanel.add(panel1);
        mainPanel.add(panel2);
        mainPanel.add(panel3);

        okButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String choice = cb.getItemAt(cb.getSelectedIndex());
                if (choice == "The different types of pollutants") {
                    get info = new get("http://api.erg.ic.ac.uk/AirQuality/Information/Species/Json");
                    try {
                        species.setText(info.getInfo());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }
    public JPanel getMainPanel(){
        return mainPanel;
    }
}
