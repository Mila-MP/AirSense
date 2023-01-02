package GetData;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetPlot {
    String responseBody;
    String siteCode;
    String year;
    String species;
    public GetPlot(String siteCode, String year, String species) throws IOException {
        this.siteCode = siteCode;
        this.year = year;
        this.species = species;
        URL url = new URL("http://api.erg.ic.ac.uk/AirQuality/Annual/MonitoringReport/SiteCode=" + siteCode + "/Year=" + year + "/Json");
        // Establish connection
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestMethod("GET");
        request.connect();
        // Get response
        InputStream is = request.getInputStream();
        BufferedReader bf_reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = bf_reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
                request.disconnect();
            } catch (IOException e) {
            }
        }
        responseBody = sb.toString();
    }

    public ChartPanel makePlot(String title){
        JFreeChart chart = ChartFactory.createBarChart(
                title,
                "Bands",
                "Number of days",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel cp = new ChartPanel(chart);
        cp.setPreferredSize(new Dimension(800,400));
        return cp;
    }

    public DefaultCategoryDataset createDataset(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String[] months = new String[]{
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov","Dec"};
        JSONObject obj = new JSONObject(responseBody);
        try {
            JSONObject siteReport = obj.getJSONObject("SiteReport");
            JSONArray reportItem = siteReport.getJSONArray("ReportItem");
            for (int i = 0; i < reportItem.length(); i++) {
                JSONObject item = reportItem.getJSONObject(i);
                if (item.getString("@SpeciesCode").equals(species)) {
                    if (item.getString("@ReportItemName").equals("Low days:")) {
                        for (int j = 0; j < 12; j++) {
                            String month = "@Month" + (j + 1);
                            double value = Integer.parseInt(item.getString(month));
                            if (value == -999) {}
                            else {
                                dataset.addValue(value, "Low days", months[j]);
                            }
                        }
                    }
                    if (item.getString("@ReportItemName").equals("Moderate days:")) {
                        for (int j = 0; j < 12; j++) {
                            String month = "@Month" + (j + 1);
                            double value = Integer.parseInt(item.getString(month));
                            if (value == -999) {}
                            else {
                                dataset.addValue(value, "Moderate days", months[j]);
                            }
                        }
                    }
                    if (item.getString("@ReportItemName").equals("High days:")) {
                        for (int j = 0; j < 12; j++) {
                            String month = "@Month" + (j + 1);
                            double value = Integer.parseInt(item.getString(month));
                            if (value == -999){}
                            else {
                                dataset.addValue(value, "High days", months[j]);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e){}
        return dataset;
    }
}
