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
    public String indices;

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

    public String getIndex(int siteIndex){
        String indexString;
        indices = "";
        if (responseBody.equals("")){
            indexString = "No measurements here";
        }
        else {
            StringBuilder sbIndexString = new StringBuilder();
            StringBuilder sbIndices = new StringBuilder();
            JSONObject response = new JSONObject(responseBody);
            JSONObject dailyAirQualityIndex = response.getJSONObject("DailyAirQualityIndex");
            String date = dailyAirQualityIndex.getString("@MonitoringIndexDate");
            sbIndexString.append("On the ").append(date).append(":\n");
            JSONObject localAuthority = dailyAirQualityIndex.getJSONObject("LocalAuthority");
            try { // if more than one measurement site
                JSONArray sites = localAuthority.getJSONArray("Site");
                JSONObject site = sites.getJSONObject(siteIndex);
                String siteName = site.getString("@SiteName");
                sbIndexString.append("in ").append(siteName).append(", the pollution indices are:\n");

                    try { // if more than one species measured
                        JSONArray species = site.getJSONArray("Species");
                        for (int j = 0; j < species.length(); j++) {
                            JSONObject specie = species.getJSONObject(j);
                            String speciesCode = specie.getString("@SpeciesCode");
                            String airQualityIndex = specie.getString("@AirQualityIndex");
                            sbIndexString.append(speciesCode).append(": ").append(airQualityIndex).append("\n");
                            sbIndices.append(airQualityIndex);
                        }
                    } catch (Exception e1) { // if only one species measured
                        JSONObject specie = site.getJSONObject("Species");
                        String speciesCode = specie.getString("@SpeciesCode");
                        String airQualityIndex = specie.getString("@AirQualityIndex");
                        sbIndexString.append(speciesCode).append(": ").append(airQualityIndex).append("\n");
                        sbIndices.append(airQualityIndex);
                    }

                indexString = sbIndexString.toString();
                indices = sbIndices.toString();

            } catch (Exception e2) { // if only one measurement site
                JSONObject site = localAuthority.getJSONObject("Site");
                String siteName = site.getString("@SiteName");
                sbIndexString.append("in ").append(siteName).append(", the pollution indices are:\n");
                try { // if more than one species measured
                    JSONArray species = site.getJSONArray("Species");
                    for (int j = 0; j < species.length(); j++) {
                        JSONObject specie = species.getJSONObject(j);
                        String speciesCode = specie.getString("@SpeciesCode");
                        String airQualityIndex = specie.getString("@AirQualityIndex");
                        sbIndexString.append(speciesCode).append(": ").append(airQualityIndex).append("\n");
                        sbIndices.append(airQualityIndex);
                    }
                } catch (Exception e3) { // if only one species measured
                    JSONObject specie = site.getJSONObject("Species");
                    String speciesCode = specie.getString("@SpeciesCode");
                    String airQualityIndex = specie.getString("@AirQualityIndex");
                    sbIndexString.append(speciesCode).append(": ").append(airQualityIndex).append("\n");
                    sbIndices.append(airQualityIndex);
                }
            indexString = sbIndexString.toString();
            indices = sbIndices.toString();
            }
        }
        return indexString;
    }

    public String getSite(){
        String siteString;
        if (responseBody.equals("")){
            siteString = "No sites here";
        }
        else{
            StringBuilder sbSites = new StringBuilder();
            JSONObject response = new JSONObject(responseBody);
            JSONObject dailyAirQualityIndex = response.getJSONObject("DailyAirQualityIndex");
            JSONObject localAuthority = dailyAirQualityIndex.getJSONObject("LocalAuthority");

            try { // if more than one measurement site
                JSONArray sites = localAuthority.getJSONArray("Site");
                for (int i = 0; i < sites.length(); i++) {
                    JSONObject site = sites.getJSONObject(i);
                    String siteName = site.getString("@SiteName");
                    String siteCode = site.getString("@SiteCode");
                    sbSites.append(siteCode).append(": ").append(siteName).append("\n");
                }
                siteString = sbSites.toString();

            } catch (Exception e2) { // if only one measurement site
                JSONObject site = localAuthority.getJSONObject("Site");
                String siteName = site.getString("@SiteName");
                String siteCode = site.getString("@SiteCode");
                sbSites.append(siteCode).append(": ").append(siteName).append("\n");
                siteString = sbSites.toString();
            }
        }
        return siteString;
    }

    public String getSpecies(int siteIndex){
        String speciesString;
        if (responseBody.equals("")){
            speciesString = "No measurements here";
        }
        else {
            StringBuilder sbSpecies = new StringBuilder();
            JSONObject response = new JSONObject(responseBody);
            JSONObject dailyAirQualityIndex = response.getJSONObject("DailyAirQualityIndex");
            JSONObject localAuthority = dailyAirQualityIndex.getJSONObject("LocalAuthority");
            try { // if more than one measurement site
                JSONArray sites = localAuthority.getJSONArray("Site");
                JSONObject site = sites.getJSONObject(siteIndex);

                try { // if more than one species measured
                    JSONArray species = site.getJSONArray("Species");
                    for (int j = 0; j < species.length(); j++) {
                        JSONObject specie = species.getJSONObject(j);
                        String speciesCode = specie.getString("@SpeciesCode");
                        sbSpecies.append(speciesCode).append("\n");
                    }
                } catch (Exception e1) { // if only one species measured
                    JSONObject specie = site.getJSONObject("Species");
                    String speciesCode = specie.getString("@SpeciesCode");
                    sbSpecies.append(speciesCode).append("\n");
                }

                speciesString = sbSpecies.toString();

            } catch (Exception e2) { // if only one measurement site
                JSONObject site = localAuthority.getJSONObject("Site");
                try { // if more than one species measured
                    JSONArray species = site.getJSONArray("Species");
                    for (int j = 0; j < species.length(); j++) {
                        JSONObject specie = species.getJSONObject(j);
                        String speciesCode = specie.getString("@SpeciesCode");
                        sbSpecies.append(speciesCode).append("\n");
                    }
                } catch (Exception e3) { // if only one species measured
                    JSONObject specie = site.getJSONObject("Species");
                    String speciesCode = specie.getString("@SpeciesCode");
                    sbSpecies.append(speciesCode).append("\n");
                }
                speciesString = sbSpecies.toString();
            }
        }
        return speciesString;

    }
}
