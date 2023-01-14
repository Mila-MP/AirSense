package AirSenseUI;

import GetData.GetBarChart;
import GetData.GetLocalAuthorities;
import GetData.GetPollutionIndex;
import org.jfree.chart.ChartPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * The BarChart class provides the user interface for the Bar Chart tab.
 */
public class BarChart extends JPanel{
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
    JComboBox<String> yearsCB;
    JComboBox<String> boroughsCB;
    JComboBox<String> sitesCB;
    JComboBox<String> speciesCB;
    DefaultComboBoxModel<String> emptyModel = new DefaultComboBoxModel<>();

    public BarChart() throws IOException {
        // Combo boxes Initialisation
        GetLocalAuthorities la = new GetLocalAuthorities();
        String[] boroughList = la.print().split("\n");
        boroughsCB = new JComboBox<>(boroughList);

        String[] yearsList = new String[24];
        for (int i = 0; i <= 23; i++){
            yearsList[i] = Integer.toString(i + 1999);
        }
        yearsCB = new JComboBox<>(yearsList);

        sitesCB = new JComboBox<>();
        speciesCB = new JComboBox<>();

        // Sub-panels Configuration
        buttonPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0; gbc.gridy = 0; buttonPanel.add(showButton,gbc);
        gbc.gridx = 1; gbc.gridy = 0; buttonPanel.add(clearButton,gbc);

        yearPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0; gbc.gridy = 0; yearPanel.add(yearLabel,gbc);
        gbc.gridx = 1; gbc.gridy = 0; yearPanel.add(yearsCB,gbc);

        boroughPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0; gbc.gridy = 0; boroughPanel.add(boroughLabel,gbc);
        gbc.gridx = 1; gbc.gridy = 0; boroughPanel.add(boroughsCB,gbc);

        sitePanel.setLayout(new GridBagLayout());
        gbc.gridx = 0; gbc.gridy = 0; sitePanel.add(siteLabel,gbc);
        gbc.gridx = 1; gbc.gridy = 0; sitePanel.add(sitesCB,gbc);

        speciesPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0; gbc.gridy = 0; speciesPanel.add(speciesLabel,gbc);
        gbc.gridx = 1; gbc.gridy = 0; speciesPanel.add(speciesCB,gbc);

        // Main Panel Configuration
        setLayout(new GridBagLayout());
        gbc.gridx = 0; gbc.gridy = 0; add(yearPanel,gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(boroughPanel,gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(sitePanel,gbc);
        gbc.gridx = 0; gbc.gridy = 3; add(speciesPanel,gbc);
        gbc.gridx = 0; gbc.gridy = 4; add(buttonPanel,gbc);

        // Button/ComboBox Configuration
        boroughsCB.addActionListener(e -> {
            int boroughIndex = boroughsCB.getSelectedIndex() + 1;
            try {
                GetPollutionIndex sites = new GetPollutionIndex(boroughIndex);
                String siteString = sites.getSite();
                String[] siteList = siteString.split("\n");
                DefaultComboBoxModel<String> siteModel = new DefaultComboBoxModel<>(siteList);
                sitesCB.setModel(siteModel);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        sitesCB.addActionListener(e -> {
            int boroughIndex = boroughsCB.getSelectedIndex() + 1;
            int siteIndex = sitesCB.getSelectedIndex();
            try {
                GetPollutionIndex species = new GetPollutionIndex(boroughIndex);
                //species.getIndex(siteIndex);
                String speciesString = species.getSpecies(siteIndex);
                String[] speciesList = speciesString.split("\n");
                DefaultComboBoxModel<String> speciesModel = new DefaultComboBoxModel<>(speciesList);
                speciesCB.setModel(speciesModel);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        showButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showChart();
            }
        });

        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clearChart();
            }
        });
    }

    /**
     * The showChart method adds the plot to the UI.
     */
    protected void showChart(){
        try {
            String year = (String) yearsCB.getSelectedItem();
            String site = (String) sitesCB.getSelectedItem();
            String siteCode = site.substring(0, 3);
            String species = (String) speciesCB.getSelectedItem();
            String title = "Number of days in low, moderate and high bands \n in " + site.substring(4) +
                    " for " + species + " (" + year + ")";

            GetBarChart plot = new GetBarChart(siteCode, year, species);
            plotPanel = plot.makePlot(title);
            plotPanel.setPreferredSize(new Dimension(800,400));
            gbc.gridx = 0; gbc.gridy = 5; add(plotPanel,gbc);

            showButton.setEnabled(false);

        }
        catch(Exception e2){}
    }

    /**
     * The clearChart method removes the chart from the UI and resets the combo boxes.
     */
    protected void clearChart(){
        try {
            sitesCB.setModel(emptyModel);
            speciesCB.setModel(emptyModel);
            showButton.setEnabled(true);
            remove(plotPanel);
        }
        catch(Exception e3){}
    }
}
