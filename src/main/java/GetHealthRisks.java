import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetHealthRisks {
    String responseBody;

    public GetHealthRisks() throws IOException {
        URL url = new URL("http://api.erg.ic.ac.uk/AirQuality/Information/Species/Json");
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

    public String print(){
        responseBody = responseBody.substring(32,responseBody.length()-3);
        JSONArray albums = new JSONArray(responseBody);
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < albums.length(); i++){
            JSONObject album = albums.getJSONObject(i);
            String speciesName = album.getString("@SpeciesName");
            String speciesRisk = album.getString("@HealthEffect");
            sb2.append(speciesName + ": "+ speciesRisk + "\n");
        }
        String finalString = sb2.toString();
        return finalString;
    }

}
