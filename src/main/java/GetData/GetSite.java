package GetData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetSite {
    String responseBody;

    public GetSite() throws IOException {
        URL url = new URL("http://api.erg.ic.ac.uk/AirQuality/Information/MonitoringSites/GroupName=London/Json");
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

    public String getSiteName() {
        StringBuilder sb = new StringBuilder();
        JSONObject obj = new JSONObject(responseBody);
        JSONObject sites = obj.getJSONObject("Sites");
        JSONArray site = sites.getJSONArray("Site");
        for (int i = 0; i < site.length(); i++) {
            JSONObject monitoringSite = site.getJSONObject(i);
            if (monitoringSite.getString("@DateClosed").equals("")) { // Pass if monitoring site is closed
                String siteName = monitoringSite.getString("@SiteName");
                sb.append(siteName + "\n");
            }
        }
        String finalString = sb.toString();
        return finalString;
    }

    public String getSiteCode(){
        StringBuilder sb = new StringBuilder();
        JSONObject obj = new JSONObject(responseBody);
        JSONObject sites = obj.getJSONObject("Sites");
        JSONArray site = sites.getJSONArray("Site");
        for (int i = 0; i < site.length(); i++) {
            JSONObject monitoringSite = site.getJSONObject(i);
            if (monitoringSite.getString("DateClosed").equals("")) { // Pass if monitoring site is closed
                String siteCode = monitoringSite.getString("@SiteCode");
                sb.append(siteCode + "\n");
            }
        }
        String finalString = sb.toString();
        return finalString;
    }
}

