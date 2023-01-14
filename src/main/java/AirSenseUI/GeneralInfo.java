package AirSenseUI;

import GetData.GetHealthRisks;
import GetData.GetSpecies;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * The GeneralInfo class provides the user interface for the General Info tab.
 */
public class GeneralInfo extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel question = new JLabel("What do you want to know?");
    JButton clearButton = new JButton("Clear");
    JTextArea info = new JTextArea();
    
    public GeneralInfo() throws IOException {

        // Combo box Configuration
        String[] questions = {"What are the different types of pollutant measured?",
                "What are the health effects of the different pollutants?",
                "Where does the data used in this app come from?",
                "What do the pollution indices and pollution band mean?",
                "How can I see the pollution levels in London before today?"};
        JComboBox<String> questionsCB = new JComboBox<>(questions);
        GetSpecies species = new GetSpecies();
        String speciesString = species.print();
        String[] speciesList = speciesString.split("\n");
        JComboBox<String> speciesCB = new JComboBox<>(speciesList);

        // Text area configuration
        info.setEditable(false);
        info.setLineWrap(true);
        info.setWrapStyleWord(true);

        // Main panel configuration
        setLayout(new GridBagLayout());
        gbc.gridx = 0; gbc.gridy = 0; add(question,gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(questionsCB,gbc);
        gbc.gridx = 1; gbc.gridy = 1; add(clearButton,gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(speciesCB,gbc); speciesCB.setVisible(false);
        gbc.gridx = 0; gbc.gridy = 3; add(info,gbc);

        // Button/ComboBox Configuration
        questionsCB.addActionListener(e -> {
            String question = questionsCB.getItemAt(questionsCB.getSelectedIndex());

            if (question.equals(questions[0])) {
                info.setSize(200, 200);
                speciesCB.setVisible(false);
                info.setVisible(true);
                info.setText(speciesString);

            } else if (question.equals(questions[1])) {
                info.setVisible(false);
                info.setSize(500, 500);
                speciesCB.setVisible(true);

            } else if (question.equals(questions[2])) {
                info.setSize(600, 500);
                info.setVisible(true);
                info.setText("All the data used in the app is taken from London Air" +
                        " (https://www.londonair.org.uk/LondonAir/Default.aspx), the website of the" +
                        " London Air Quality Network (LAQN). The network has pollution measuring sites" +
                        " all throughout London. The measurement can then be accessed through London Air's" +
                        " Application Programming Interface (API) and displayed in AirSense in a way that " +
                        " we hope will help asthma sufferers in their day to day life.");

            } else if (question.equals(questions[3])) {
                // Fill
            } else if (question.equals(questions[4])){
                // Fill
            }
        });
        
        speciesCB.addActionListener(e -> {
            int index = speciesCB.getSelectedIndex();
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
            speciesCB.setVisible(false);
        });
    }
}
