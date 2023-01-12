package AirSenseUI;

import GetData.GetLineChart;
import java.io.IOException;

public class LineChart extends BarChart{
    public LineChart() throws IOException {

    }

    @Override
    protected void showChart() {
        try {
            String year = (String) yearsCB.getSelectedItem();
            String site = (String) sitesCB.getSelectedItem();
            String siteCode = site.substring(0, 3);
            String species = (String) speciesCB.getSelectedItem();
            GetLineChart plot = new GetLineChart(siteCode, year, species);
            String title = species + " concentration in " + site.substring(4) + " (" + year + ")";
            plotPanel = plot.makePlot(title);
            gbc.gridx = 0;
            gbc.gridy = 6;
            showButton.setEnabled(false);
            add(plotPanel,gbc);
        }
        catch(Exception e2){
        }
    }
}
