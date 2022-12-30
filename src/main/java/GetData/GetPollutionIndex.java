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

        URL url = new URL("http://api.erg.ic.ac.uk/AirQuality/Daily/MonitoringIndex/Latest/LocalAuthorityId=" + localAuthorityID + "/Json");
        // Establish connection
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestMethod("GET");
        request.connect();
        // Get response
        try {
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
        }catch (Exception e) {
            responseBody = "";
        }
    }

    public String print(){
        String finalString;

        if (responseBody.equals("")){
            finalString = "No measurements here";
        }
        else {
            StringBuilder sb = new StringBuilder();
            JSONObject response = new JSONObject(responseBody);
            JSONObject dailyAirQualityIndex = response.getJSONObject("DailyAirQualityIndex");
            String date = dailyAirQualityIndex.getString("@MonitoringIndexDate");
            sb.append("On the " + date + ":\n");
            JSONObject localAuthority = dailyAirQualityIndex.getJSONObject("LocalAuthority");
            try { // if more than one measurement site
                JSONArray sites = localAuthority.getJSONArray("Site");
                for (int i = 0; i < sites.length(); i++) {
                    JSONObject site = sites.getJSONObject(i);
                    String siteName = site.getString("@SiteName");
                    sb.append("in " + siteName + ", the pollution indices are:\n");

                    try { // if more than one species measured
                        JSONArray species = site.getJSONArray("Species");
                        for (int j = 0; j < species.length(); j++) {
                            JSONObject specie = species.getJSONObject(j);
                            String speciesCode = specie.getString("@SpeciesCode");
                            String airQualityIndex = specie.getString("@AirQualityIndex");
                            sb.append(speciesCode + ": " + airQualityIndex + "\n");
                        }
                    } catch (Exception e1) { // if only one species measured
                        JSONObject specie = site.getJSONObject("Species");
                        String speciesCode = specie.getString("@SpeciesCode");
                        String airQualityIndex = specie.getString("@AirQualityIndex");
                        sb.append(speciesCode + ": " + airQualityIndex + "\n");
                    }
                }
                finalString = sb.toString();
            } catch (Exception e2) { // if one or no measurement site
                try { // if one measurement site
                    JSONObject site = localAuthority.getJSONObject("Site");
                    String siteName = site.getString("@SiteName");
                    sb.append("in " + siteName + ", the pollution indices are:\n");
                    try { // if more than one species measured
                        JSONArray species = site.getJSONArray("Species");
                        for (int j = 0; j < species.length(); j++) {
                            JSONObject specie = species.getJSONObject(j);
                            String speciesCode = specie.getString("@SpeciesCode");
                            String airQualityIndex = specie.getString("@AirQualityIndex");
                            sb.append(speciesCode + ": " + airQualityIndex + "\n");
                        }
                    } catch (Exception e3) { // if only one species measured
                        JSONObject specie = site.getJSONObject("Species");
                        String speciesCode = specie.getString("@SpeciesCode");
                        String airQualityIndex = specie.getString("@AirQualityIndex");
                        sb.append(speciesCode + ": " + airQualityIndex + "\n");
                    }
                } catch (Exception e4) { // if no measurement site
                    sb.setLength(0);
                    sb.append("We're sorry, there is no measurement site in this area.");
                }
                finalString = sb.toString();
            }
        }
        return finalString;
    }

    public String getSite(){
        String finalString;
        if (responseBody.equals("")){
            finalString = "No sites here";
        }
        else{
            StringBuilder sb = new StringBuilder();
            JSONObject response = new JSONObject(responseBody);
            JSONObject dailyAirQualityIndex = response.getJSONObject("DailyAirQualityIndex");
            JSONObject localAuthority = dailyAirQualityIndex.getJSONObject("LocalAuthority");

            try { // if more than one measurement site
                JSONArray sites = localAuthority.getJSONArray("Site");
                for (int i = 0; i < sites.length(); i++) {
                    JSONObject site = sites.getJSONObject(i);
                    String siteName = site.getString("@SiteName");
                    sb.append(siteName + "\n");
                }
                finalString = sb.toString();

            } catch (Exception e2) { // if only one measurement site
                JSONObject site = localAuthority.getJSONObject("Site");
                String siteName = site.getString("@SiteName");
                sb.append(siteName + "\n");
                finalString = sb.toString();
            }
        }
        return finalString;
    }
}
