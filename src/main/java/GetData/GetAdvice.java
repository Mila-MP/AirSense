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
 * The GetAdvice class provides access to health advice related to pollution
 * from the London Air API. The advice provided depends on the parameter
 * pollution index, and are for at-risks individuals (e.g. asthma and other
 * lung conditions).
 */
public class GetAdvice {
    /**
     * Contains the response from the get request in JSON format.
     */
    String responseBody;
    /**
     * Pollution Index on which the advice provided depends.
     */
    int index; // Pollution index
    
     /* Reference 1: https://devtut.github.io/java/httpurlconnection.html#get-response-body-from-a-url-as-a-string */
    public GetAdvice(int index) throws IOException {
        this.index = index;
        String indexString = Integer.toString(index);
        URL url = new URL("https://api.erg.ic.ac.uk/AirQuality/Information/IndexHealthAdvice/AirQualityIndex="+indexString+"/Json");
        // Establishes the connection
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestMethod("GET");
        request.connect();
        // Gets response in JSON format and stores it in the String responseBody
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
    /* end of reference 1*/

    /**
     *
     * @return String containing the advice associated with the index provided when
     * initialising a GetAdvice object.
     */
    public String print(){
        String advice = "";
        // Navigates through responseBody to access the JSON object containing the advice.
        JSONObject obj = new JSONObject(responseBody);
        JSONObject airQualityIndexHealthAdvice = obj.getJSONObject("AirQualityIndexHealthAdvice");
        JSONObject airQualityBanding = airQualityIndexHealthAdvice.getJSONObject("AirQualityBanding");
        JSONArray healthAdvice = airQualityBanding.getJSONArray("HealthAdvice");
        for (int i = 0; i < healthAdvice.length(); i++){
            JSONObject population = healthAdvice.getJSONObject(i);
            if (population.get("@Population").equals("At-risk individuals")){
                advice = population.getString("@Advice");
            }
        }
        return advice;
    }
}
