package GetData;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The GetHealthRisks class provides access to the health risks associated
 * with a specified species. These health risks are accessed through the
 * London Air API.
 */
public class GetHealthRisks {
    /**
     * Contains the response from the get request in JSON format.
     */
    String responseBody;
    /**
     * Species on which the health risks provided depends. The possible
     * values this field can take are "CO", "NO2", "O3", "PM10", "PM25" and "SO2".
     */
    String species;

    public GetHealthRisks(String species) throws IOException {
        this.species = species;
        URL url = new URL("http://api.erg.ic.ac.uk/AirQuality/Information/Species/SpeciesCode="+species+"/Json");
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

    /**
     * @return String containing the heath risk associated with the species
     * provided when initialising a GetHealthRisk object.
     */
    public String print(){
        String healthRisk = "";
        // Navigates through responseBody to access the JSON object containing the health risk.
        JSONObject obj = new JSONObject(responseBody);
        JSONObject airQualitySpecies = obj.getJSONObject("AirQualitySpecies");
        JSONObject species = airQualitySpecies.getJSONObject("Species");
        String description = species.getString("@Description");
        String healthEffect = species.getString("@HealthEffect");
        healthRisk += description + " " + healthEffect;
        return healthRisk;
    }

}
