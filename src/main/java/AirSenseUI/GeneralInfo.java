package AirSenseUI;

import GetData.GetHealthRisks;
import GetData.GetSpecies;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class GeneralInfo extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel question = new JLabel("What do you want to know?");
    JButton okButton = new JButton("OK");
    JButton clearButton = new JButton("Clear");
    JLabel info = new JLabel();


    public GeneralInfo(){
        String[] choices = {"The different types of pollutants","Health effects of the different pollutants"};
        JComboBox<String> cb = new JComboBox<>(choices);

        setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(question,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(cb,gbc);
        gbc.gridx =1;
        gbc.gridy =1;
        add(okButton,gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(clearButton,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(info,gbc);


        okButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String choice = cb.getItemAt(cb.getSelectedIndex());
                if (choice == "Health effects of the different pollutants") {
                    try {
                        GetHealthRisks risks = new GetHealthRisks();
                        String healthInfo = risks.print();
                        info.setVisible(true);
                        info.setText(convertToMultiline(healthInfo));

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if (choice == "The different types of pollutants"){
                    try {
                        GetSpecies a = new GetSpecies();
                        String species = a.print();

                        info.setVisible(true);
                        info.setText(convertToMultiline(species));
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
                info.setVisible(false);
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
