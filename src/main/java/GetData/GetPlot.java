package GetData;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetPlot {
    String responseBody;
    public GetPlot() throws IOException {
        URL url = new URL("http://api.erg.ic.ac.uk/AirQuality/Data/SiteSpecies/SiteCode=BG1/SpeciesCode=NO2/StartDate=2022-01-01/EndDate=2022-02-01/Json");
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

    public ChartPanel getData(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        JSONObject obj = new JSONObject(responseBody);
        JSONObject obj2 = obj.getJSONObject("RawAQData");
        String species = obj2.getString("@SpeciesCode");
        JSONArray data = obj2.getJSONArray("Data");

        for (int i = 0; i < data.length(); i++){
            Double value;
            JSONObject info = data.getJSONObject(i);
            try {
                value = Double.parseDouble(info.getString("@Value"));
                String date = info.getString("@MeasurementDateGMT");
                dataset.addValue(value,species,date);
            }
            catch(Exception e){}

        }
        JFreeChart plot = ChartFactory.createLineChart(
                "JFreeChart Line Chart", // Chart title
                "Date", // X-Axis Label
                "Value", // Y-Axis Label
                dataset // Dataset for the Chart
        );
        ChartPanel cp = new ChartPanel(plot);
        return cp;
    }
}
