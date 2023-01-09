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

public class GetLineChart {
    /**
     * Contains the response from the get request in JSON format.
     */
    String responseBody;
    /**
     * Data for the line chart will be retrieved for this site.
     */
    String siteCode;
    /**
     * Data for the line chart will be retrieved for this year.
     */
    String year;
    /**
     * Data for the line chart will be retrieved for this species.
     */
    String species;

    public GetLineChart(String siteCode, String year, String species) throws IOException {
        this.siteCode = siteCode;
        this.year = year;
        this.species = species;
        // Formats start date and end date for the get request
        String startDate = year + "-01-01";
        String endDate = year + "-12-31";
        URL url = new URL("https://api.erg.ic.ac.uk/AirQuality/Data/SiteSpecies/SiteCode="+
                siteCode+"/SpeciesCode="+species+"/StartDate="+startDate+"/EndDate="+endDate+"/Json");

        // Establishes the connection
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestMethod("GET");
        request.connect();

        // Gets response in JSON format and stores it in the String responseBody
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

    public DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String measurementDate;
        String monthNumber;
        String[] months = new String[]{
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        // Navigates through responseBody to access the relevant data
        JSONObject obj = new JSONObject(responseBody);
        JSONObject rawAQdata = obj.getJSONObject("RawAQData");
        JSONArray data = rawAQdata.getJSONArray("Data");
        for (int i = 0; i < data.length(); i++){
            JSONObject obj2 = data.getJSONObject(i);
            measurementDate = obj2.getString("@MeasurementDateGMT");
            monthNumber = measurementDate.substring(5,7);
        }
        return dataset;
    }

    /**
     *
     * @param title Title of the chart
     * @return ChartPanel with line chart
     */
    public ChartPanel makePlot(String title){
        JFreeChart chart = ChartFactory.createLineChart(
                title,
                species,
                "Pollution Index",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel cp = new ChartPanel(chart);
        cp.setPreferredSize(new Dimension(800,400));
        return cp;
    }
}
