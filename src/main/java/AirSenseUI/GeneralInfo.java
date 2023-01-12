package AirSenseUI;

import GetData.GetHealthRisks;
import GetData.GetSpecies;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GeneralInfo extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel question = new JLabel("What do you want to know?");
    JButton clearButton = new JButton("Clear");
    JTextArea info = new JTextArea();


    public GeneralInfo() throws IOException {

        String[] choices = {"The different types of pollutants measured","Health effects of the different pollutants"};

        JComboBox<String> cb = new JComboBox<>(choices);

        GetSpecies species = new GetSpecies();
        String[] speciesList = species.print().split("\n");
        JComboBox<String> cb2 = new JComboBox<>(speciesList);

        info.setEditable(false);
        info.setLineWrap(true);
        info.setWrapStyleWord(true);

        setLayout(new GridBagLayout());
        gbc.gridx = 0; gbc.gridy = 0; add(question,gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(cb,gbc);
        gbc.gridx = 1; gbc.gridy = 1; add(clearButton,gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(cb2,gbc); cb2.setVisible(false);
        gbc.gridx = 0; gbc.gridy = 3; add(info,gbc);

        cb.addActionListener(e -> {
            String choice = cb.getItemAt(cb.getSelectedIndex());
            if (choice.equals("Health effects of the different pollutants")) {
                info.setVisible(false);
                info.setSize(500, 500);
                cb2.setVisible(true);
            } else if (choice.equals("The different types of pollutants measured")) {
                info.setSize(200, 200);
                cb2.setVisible(false);
                try {
                    GetSpecies a = new GetSpecies();
                    String species1 = a.print();
                    info.setVisible(true);
                    info.setText(species1);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


                cb2.addActionListener(e -> {
                    int index = cb2.getSelectedIndex();
                    switch (index) {
                        case 0:
                            try {
                                GetHealthRisks risk = new GetHealthRisks("CO");
                                info.setText(risk.print());

                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                        case 1:
                            try {
                                GetHealthRisks risk = new GetHealthRisks("NO2");
                                info.setText(risk.print());

                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                        case 2:
                            try {
                                GetHealthRisks risk = new GetHealthRisks("O3");
                                info.setText(risk.print());
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                        case 3:
                            try {
                                GetHealthRisks risk = new GetHealthRisks("PM10");
                                info.setText(risk.print());
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                        case 4:
                            try {
                                GetHealthRisks risk = new GetHealthRisks("PM25");
                                info.setText(risk.print());
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                        case 5:
                            try {
                                GetHealthRisks risk = new GetHealthRisks("SO2");
                                info.setText(risk.print());
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                    }
                    info.setVisible(true);
                });

                clearButton.addActionListener(e -> {
                    info.setVisible(false);
                    cb2.setVisible(false);
                });
            }
        }
