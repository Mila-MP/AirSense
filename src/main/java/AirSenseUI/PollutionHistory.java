package AirSenseUI;

import GetData.GetLocalAuthorities;
import GetData.GetPlot;
import GetData.GetPollutionIndex;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class PollutionHistory extends JPanel {
    JButton showButton = new JButton("Show chart");
    JButton clearButton = new JButton("Clear");
    JLabel yearLabel = new JLabel("Year: ");
    JLabel boroughLabel = new JLabel("Borough: ");
    JLabel siteLabel = new JLabel("Site: ");
    JLabel speciesLabel = new JLabel("Species: ");
    JPanel buttonPanel = new JPanel();
    JPanel yearPanel = new JPanel();
    JPanel boroughPanel = new JPanel();
    JPanel sitePanel = new JPanel();
    JPanel speciesPanel = new JPanel();
    ChartPanel plotPanel;
    GridBagConstraints gbc = new GridBagConstraints();
    public PollutionHistory() throws IOException {
        // Initialises ComboBoxes
        GetLocalAuthorities la = new GetLocalAuthorities();
        String[] boroughList = la.print().split("\n");
        String[] yearsList = new String[24];
        for (int i = 0; i <= 23; i++){
            yearsList[i] = Integer.toString(i + 1999);
        }
        JComboBox<String> yearsCB = new JComboBox<>(yearsList);
        JComboBox<String> boroughsCB = new JComboBox<>(boroughList);
        JComboBox<String> sitesCB = new JComboBox<>();
        JComboBox<String> speciesCB = new JComboBox<>();

        // Sub-panels Configuration
        buttonPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(showButton,gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(clearButton,gbc);

        yearPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        yearPanel.add(yearLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        yearPanel.add(yearsCB,gbc);

        boroughPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        boroughPanel.add(boroughLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        boroughPanel.add(boroughsCB,gbc);

        sitePanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        sitePanel.add(siteLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        sitePanel.add(sitesCB,gbc);

        speciesPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        speciesPanel.add(speciesLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        speciesPanel.add(speciesCB,gbc);

        // Main Panel Configuration
        setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(yearPanel,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(boroughPanel,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(sitePanel,gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(speciesPanel,gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(buttonPanel,gbc);

        // Button/ComboBox Configuration
        boroughsCB.addActionListener(e -> {
            int localAuthorityID = boroughsCB.getSelectedIndex();
            try {
                GetPollutionIndex sites = new GetPollutionIndex(localAuthorityID+1);
                String str2 = sites.getSite();
                String[] choices2 = str2.split("\n");
                DefaultComboBoxModel<String> siteModel = new DefaultComboBoxModel<>(choices2);
                sitesCB.setModel(siteModel);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        sitesCB.addActionListener(e -> {
            int localAuthorityID = boroughsCB.getSelectedIndex();
            int siteIndex = sitesCB.getSelectedIndex();
            try {
                GetPollutionIndex species = new GetPollutionIndex(localAuthorityID+1);
                species.getIndex(siteIndex);
                String str = species.getSpecies(siteIndex);
                String[] choices = str.split("\n");
                DefaultComboBoxModel<String> speciesModel = new DefaultComboBoxModel<>(choices);
                speciesCB.setModel(speciesModel);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        showButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    String year = (String) yearsCB.getSelectedItem();
                    String site = (String) sitesCB.getSelectedItem();
                    String siteCode = site.substring(0, 3);
                    String species = (String) speciesCB.getSelectedItem();
                    GetPlot plot = new GetPlot(siteCode, year, species);
                    String title = "Number of days in low, moderate and high bands \n in " + site.substring(4) +
                            " for " + species + " (" + year + ")";
                    plotPanel = plot.makePlot(title);
                    gbc.gridx = 0;
                    gbc.gridy = 6;
                    showButton.setEnabled(false);
                    add(plotPanel,gbc);

                }
                catch(Exception e2){
                }
            }
        });

        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    remove(plotPanel);
                    DefaultComboBoxModel<String> emptyModel = new DefaultComboBoxModel<>();
                    sitesCB.setModel(emptyModel);
                    speciesCB.setModel(emptyModel);
                    showButton.setEnabled(true);
                }
                catch(Exception e3){}
            }
        });
    }
}

