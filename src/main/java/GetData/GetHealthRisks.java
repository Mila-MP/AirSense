package GetData;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetHealthRisks {
    String responseBody;
    String species;

    public GetHealthRisks(String species) throws IOException {
        this.species = species;
        URL url = new URL("http://api.erg.ic.ac.uk/AirQuality/Information/Species/SpeciesCode="+species+"/Json");
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
        StringBuilder sb = new StringBuilder();
        JSONObject obj = new JSONObject(responseBody);
        JSONObject airQualitySpecies = obj.getJSONObject("AirQualitySpecies");
        JSONObject species = airQualitySpecies.getJSONObject("Species");
        String description = species.getString("@Description");
        String healthEffect = species.getString("@HealthEffect");
        sb.append(description).append(" ").append(healthEffect);
        return sb.toString();
    }

}
