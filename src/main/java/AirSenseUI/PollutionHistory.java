package AirSenseUI;

import javax.swing.*;
import java.io.IOException;

/**
 * The PollutionHistory class provides the user interface for the Pollution History tab,
 * which itself contains the Bar chart and Line chart tabs.
 */
public class PollutionHistory extends JTabbedPane {
    BarChart barChart = new BarChart();
    LineChart lineChart = new LineChart();

    public PollutionHistory() throws IOException {
        addTab("Bar Charts", barChart);
        addTab("Line Charts", lineChart);
    }

}

