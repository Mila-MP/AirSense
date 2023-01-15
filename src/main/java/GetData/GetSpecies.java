package GetData;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The GetSpecies Class provides access to the species measured by the different
 * London Air measuring sites.
 */
public class GetSpecies {
    /**
     * Contains the response from the get request in JSON format
     */
    String responseBody;
    public GetSpecies() throws IOException {
        URL url = new URL("http://api.erg.ic.ac.uk/AirQuality/Information/Species/Json");
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

    /**
     *
     * @return String containing all the species measured in
     * London Air's measuring sites.
     */
    public String print(){
        StringBuilder sbSpecies = new StringBuilder();
        JSONObject obj = new JSONObject(responseBody);
        JSONObject airQualitySpecies = obj.getJSONObject("AirQualitySpecies");
        JSONArray species = airQualitySpecies.getJSONArray("Species");
        for (int i = 0; i < species.length(); i++){
            JSONObject obj2 = species.getJSONObject(i);
            String speciesCode = obj2.getString("@SpeciesCode");
            String speciesName = obj2.getString("@SpeciesName");
            sbSpecies.append(speciesCode).append(": ") .append(speciesName).append("\n");
        }
        return sbSpecies.toString();
    }
}
