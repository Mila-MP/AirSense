import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class PollutionInfo extends JPanel{
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel welcome = new JLabel("Welcome to AirSense");
    JLabel question = new JLabel("What do you want to know?");
    JButton okButton = new JButton("OK");
    JButton clearButton = new JButton("Clear");
    JLabel species = new JLabel();


    public PollutionInfo() throws IOException {
        setLayout(new GridBagLayout());
        String[] choices = {"The different types of pollutants", "The pollution levels in my current location"};
        JComboBox<String> cb = new JComboBox<>(choices);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(welcome,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(question,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(cb,gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(okButton,gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        add(clearButton,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(species,gbc);
        okButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String choice = cb.getItemAt(cb.getSelectedIndex());
                if (choice == "The different types of pollutants") {
                    try {
                        GetSpecies info = new GetSpecies();
                        species.setVisible(true);
                        species.setText(convertToMultiline(info.print()));
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

        clearButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                species.setVisible(false);
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
    public static String convertToMultiline(String orig)
    {
        return "<html>" + orig.replaceAll("\n", "<br>");
    }
}
