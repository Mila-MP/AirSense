package AirSenseUI;

import GetData.GetLineChart;
import java.io.IOException;

/**
 * The LineChart class provides the user interface for the Line Chart tab.
 * It inherits from BarChart.
 */
public class LineChart extends BarChart {
    public LineChart() throws IOException {
    }

    @Override
    protected void showChart() throws IOException {
        String year = (String) yearsCB.getSelectedItem();
        String site = (String) sitesCB.getSelectedItem();
        assert site != null;
        String siteCode = site.substring(0, 3);
        String species = (String) speciesCB.getSelectedItem();
        String title = species + " concentration in " + site.substring(4) + " (" + year + ")";

        GetLineChart plot = new GetLineChart(siteCode, year, species);
        plotPanel = plot.makePlot(title);
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(plotPanel, gbc);

        showButton.setEnabled(false);
    }
}
