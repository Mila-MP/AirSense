package GetData;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetSpecies {
    String responseBody;

    public GetSpecies() throws IOException {
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

            StringBuilder sb = new StringBuilder();
            JSONObject obj = new JSONObject(responseBody);
            JSONObject airQualitySpecies = obj.getJSONObject("AirQualitySpecies");
            JSONArray species = airQualitySpecies.getJSONArray("Species");
            for (int i = 0; i < species.length(); i++){
                JSONObject obj2 = species.getJSONObject(i);
                String speciesCode = obj2.getString("@SpeciesCode");
                String speciesName = obj2.getString("@SpeciesName");
                sb.append(speciesCode).append(": ") .append(speciesName).append("\n");
            }
            return sb.toString();
        }


}
