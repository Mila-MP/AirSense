package GetData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetAdvice {
    String responseBody;
    int index;

    public GetAdvice(int index) throws IOException {
        this.index = index;
        String indexString = Integer.toString(index);
        URL url = new URL("https://api.erg.ic.ac.uk/AirQuality/Information/IndexHealthAdvice/AirQualityIndex="+indexString+"/Json");
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
        String advice = "";
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
