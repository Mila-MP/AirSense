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
import java.util.ArrayList;

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
         /* Reference 1: https://devtut.github.io/java/httpurlconnection.html#get-response-body-from-a-url-as-a-string */
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
    /* end of reference 1*/

    public DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String measurementDate;
        String monthNumber;
        ArrayList<Double> jan = new ArrayList<>();
        Double meanJan;
        ArrayList<Double> feb = new ArrayList<>();
        Double meanFeb;
        ArrayList<Double> mar = new ArrayList<>();
        Double meanMar;
        ArrayList<Double> apr = new ArrayList<>();
        Double meanApr;
        ArrayList<Double> may = new ArrayList<>();
        Double meanMay;
        ArrayList<Double> jun = new ArrayList<>();
        Double meanJun;
        ArrayList<Double> jul = new ArrayList<>();
        Double meanJul;
        ArrayList<Double> aug = new ArrayList<>();
        Double meanAug;
        ArrayList<Double> sep = new ArrayList<>();
        Double meanSep;
        ArrayList<Double> oct = new ArrayList<>();
        Double meanOct;
        ArrayList<Double> nov = new ArrayList<>();
        Double meanNov;
        ArrayList<Double> dec = new ArrayList<>();
        Double meanDec;

        // Navigates through responseBody to access the relevant data
        JSONObject obj = new JSONObject(responseBody);
        JSONObject rawAQdata = obj.getJSONObject("RawAQData");
        JSONArray data = rawAQdata.getJSONArray("Data");
        for (int i = 0; i < data.length(); i++){
            JSONObject obj2 = data.getJSONObject(i);
            if (obj2.getString("@Value").isEmpty()){}
            // If @Value string isn't empty, adds data to relevant month list
            else {
                measurementDate = obj2.getString("@MeasurementDateGMT");
                monthNumber = measurementDate.substring(5, 7);
                switch (Integer.parseInt(monthNumber)) {
                    case 1:
                        jan.add(Double.parseDouble(obj2.getString("@Value")));
                        break;
                    case 2:
                        feb.add(Double.parseDouble(obj2.getString("@Value")));
                        break;
                    case 3:
                        mar.add(Double.parseDouble(obj2.getString("@Value")));
                        break;
                    case 4:
                        apr.add(Double.parseDouble(obj2.getString("@Value")));
                        break;
                    case 5:
                        may.add(Double.parseDouble(obj2.getString("@Value")));
                        break;
                    case 6:
                        jun.add(Double.parseDouble(obj2.getString("@Value")));
                        break;
                    case 7:
                        jul.add(Double.parseDouble(obj2.getString("@Value")));
                        break;
                    case 8:
                        aug.add(Double.parseDouble(obj2.getString("@Value")));
                        break;
                    case 9:
                        sep.add(Double.parseDouble(obj2.getString("@Value")));
                        break;
                    case 10:
                        oct.add(Double.parseDouble(obj2.getString("@Value")));
                        break;
                    case 11:
                        nov.add(Double.parseDouble(obj2.getString("@Value")));
                        break;
                    case 12: dec.add(Double.parseDouble(obj2.getString("@Value")));
                        break;
                }
            }
        }
        meanJan = getMean(jan);
        meanFeb = getMean(feb);
        meanMar = getMean(mar);
        meanApr = getMean(apr);
        meanMay = getMean(may);
        meanJun = getMean(jun);
        meanJul = getMean(jul);
        meanAug = getMean(aug);
        meanSep = getMean(sep);
        meanOct = getMean(oct);
        meanNov = getMean(nov);
        meanDec = getMean(dec);

        dataset.addValue(meanJan, species, "Jan");
        dataset.addValue(meanFeb, species, "Feb");
        dataset.addValue(meanMar, species, "Mar");
        dataset.addValue(meanApr, species, "Apr");
        dataset.addValue(meanMay, species, "May");
        dataset.addValue(meanJun, species, "Jun");
        dataset.addValue(meanJul, species, "Jul");
        dataset.addValue(meanAug, species, "Aug");
        dataset.addValue(meanSep, species, "Sep");
        dataset.addValue(meanOct, species, "Oct");
        dataset.addValue(meanNov, species, "Nov");
        dataset.addValue(meanDec, species, "Dec");

        return dataset;
    }

    /**
     *
     * @param arr The array
     * @return Mean of arr
     */
    public Double getMean(ArrayList<Double> arr){
        double tot = 0;
        int N = arr.size();
        double mean;
        for (Double value : arr) {
            tot += value;
        }
        mean = tot/N;
        return mean;
    }

    /**
     *
     * @param title Title of the chart
     * @return ChartPanel with line chart
     */
    public ChartPanel makePlot(String title){
        JFreeChart chart = ChartFactory.createLineChart(
                title,
                "Monthly Average",
                "Concentration",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel cp = new ChartPanel(chart);
        cp.setPreferredSize(new Dimension(800,400));
        return cp;
    }
}
