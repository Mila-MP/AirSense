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
 * The GetLocalAuthorities class provides access to a list of all the boroughs
 * of London. The boroughs are retrieved from the London Air API.
 */
public class GetLocalAuthorities{
    /**
     * Contains the response from the get request in JSON format.
     */
    String responseBody;
    public GetLocalAuthorities() throws IOException{
        URL url = new URL("http://api.erg.ic.ac.uk/AirQuality/Information/MonitoringLocalAuthority/GroupName=London/Json");
         /* Reference 1: https://devtut.github.io/java/httpurlconnection.html#get-response-body-from-a-url-as-a-string */
        // Establishes the connection.
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestMethod("GET");
        request.connect();
        // Gets response in JSON format and stores it in the String responseBody.
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
     * @return String containing a list of all the London Boroughs.
     */
    public String print(){
        String boroughList;
        // Navigates through responseBody to access the JSON objects each containing a borough.
        JSONObject obj = new JSONObject(responseBody);
        JSONObject localAuthorities = obj.getJSONObject("LocalAuthorities");
        JSONArray localAuthority = localAuthorities.getJSONArray("LocalAuthority");
        StringBuilder sbBoroughs = new StringBuilder();
        for (int i = 0; i < localAuthority.length(); i++){
            JSONObject obj2 = localAuthority.getJSONObject(i);
            String authorityName = obj2.getString("@LocalAuthorityName");
            sbBoroughs.append(authorityName).append("\n");
        }
        boroughList = sbBoroughs.toString();
        return boroughList;
    }
}
