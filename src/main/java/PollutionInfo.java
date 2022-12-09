import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class PollutionInfo {

    JPanel mainPanel = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();

    JTextArea area = new JTextArea();

    JLabel welcome = new JLabel("Welcome to AirSense");
    JLabel question = new JLabel("What do you want to know?");
    JButton okButton = new JButton(("OK"));

    JLabel species = new JLabel();

    public PollutionInfo() throws IOException {


        panel1.add(welcome);

        panel2.add(question);
        String[] choices = {"The different types of pollutants", "The pollution levels in my current location", "health effects of different pollutants",};
        final JComboBox<String> cb = new JComboBox<>(choices);
        panel2.add(cb);
        panel2.add(okButton);

        panel3.add(species);
        mainPanel.setLayout(null);

        panel1.setBounds(100,0,250,100);
        panel2.setBounds(250,500,500,250);
        panel3.setBounds(800,700,1000,100);
        area.setBounds(100,100,500,250);



        mainPanel.add(panel1);
        mainPanel.add(panel2);
        mainPanel.add(panel3);


        okButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String choice = cb.getItemAt(cb.getSelectedIndex());
                if (choice == "health effects of different pollutants") {
                    try {
                        GetHealthRisks risks = new GetHealthRisks();

                        String healthInfo = risks.print();


                        mainPanel.add(area);
                        area.setBorder(new LineBorder(Color.BLACK));
                        area.setWrapStyleWord(true);
                        area.setLineWrap(true);
                        area.setText(healthInfo);

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
