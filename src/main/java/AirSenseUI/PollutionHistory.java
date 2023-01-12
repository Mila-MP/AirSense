package AirSenseUI;

import javax.swing.*;
import java.io.IOException;

public class PollutionHistory extends JTabbedPane {
    BarChart barChart = new BarChart();
    LineChart lineChart = new LineChart();

    public PollutionHistory() throws IOException {
        addTab("Bar Charts", barChart);
        addTab("Line Charts", lineChart);
    }

}

