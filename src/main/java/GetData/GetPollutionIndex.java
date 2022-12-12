package GetData;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetPollutionIndex {
    int localAuthorityID;
    String responseBody;

    public GetPollutionIndex(int localAuthorityID) throws IOException {
        this.localAuthorityID = localAuthorityID;
        URL url = new URL("http://api.erg.ic.ac.uk/AirQuality/Daily/MonitoringIndex/Latest/GroupName=London/Json");
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
    public String print() throws IOException {
        StringBuilder sb2 = new StringBuilder();
        String finalString;
        JSONObject obj1 = new JSONObject(responseBody);
        JSONObject obj2 = obj1.getJSONObject("DailyAirQualityIndex");
        String date = obj2.getString("@MonitoringIndexDate");
        sb2.append("On the " + date + ":\n");
        JSONArray arr1 = obj2.getJSONArray("LocalAuthority");
        JSONObject obj3 = arr1.getJSONObject(localAuthorityID - 1);
        try { // if more than one measurement site
            JSONArray sites = obj3.getJSONArray("Site");
            for (int i = 0; i < sites.length(); i++) {
                JSONObject site = sites.getJSONObject(i);
                String siteName = site.getString("@SiteName");
                sb2.append("in " + siteName + ", the pollution indices are:\n");

                try { // if more than one species measured
                    JSONArray species = site.getJSONArray("Species");
                    for (int j = 0; j < species.length(); j++){
                        JSONObject specie = species.getJSONObject(j);
                        String speciesCode = specie.getString("@SpeciesCode");
                        String airQualityIndex = specie.getString("@AirQualityIndex");
                        sb2.append(speciesCode + ": " + airQualityIndex + "\n");
                    }
                } catch (Exception e1) { // if only one species measured
                    JSONObject specie = site.getJSONObject("Species");
                    String speciesCode = specie.getString("@SpeciesCode");
                    String airQualityIndex = specie.getString("@AirQualityIndex");
                    sb2.append(speciesCode + ": " + airQualityIndex + "\n");
                }
            }
            finalString = sb2.toString();
        } catch (Exception e2){ // if one or no measurement site
            try{ // if one measurement site
                JSONObject site = obj3.getJSONObject("Site");
                String siteName = site.getString("@SiteName");
                sb2.append("in " + siteName + ", the pollution indices are:\n");
                try{ // if more than one species measured
                    JSONArray species = site.getJSONArray("Species");
                    for (int j = 0; j < species.length(); j++){
                        JSONObject specie = species.getJSONObject(j);
                        String speciesCode = specie.getString("@SpeciesCode");
                        String airQualityIndex = specie.getString("@AirQualityIndex");
                        sb2.append(speciesCode + ": " + airQualityIndex + "\n");
                    }
                } catch (Exception e3){ // if only one species measured
                    JSONObject specie = site.getJSONObject("Species");
                    String speciesCode = specie.getString("@SpeciesCode");
                    String airQualityIndex = specie.getString("@AirQualityIndex");
                    sb2.append(speciesCode + ": " + airQualityIndex + "\n");
                }
            } catch (Exception e4){ // if no measurement site
                sb2.setLength(0);
                sb2.append("We're sorry, there is no measurement site in this area.");
            }
            finalString = sb2.toString();
        }
        return finalString;
    }
}
