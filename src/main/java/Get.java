import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class get {
    public String str;
    public get(String str){
        this.str = str;
    }
    public String getInfo() throws IOException{
        // Set input string to URL object
        URL url = new URL(str);
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
        }
        catch (IOException e) {
        }
        finally {
            try {
                is.close();
                request.disconnect();
            }
            catch (IOException e) {
            }
        }
        String responseBody = sb.toString();
        responseBody = responseBody.substring(32,responseBody.length()-3);
        JSONArray albums = new JSONArray(responseBody);
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < albums.length(); i++){
            JSONObject album = albums.getJSONObject(i);
            String speciesCode = album.getString("@SpeciesCode");
            String speciesName = album.getString("@SpeciesName");
            sb2.append(speciesCode + ": "+ speciesName + "\n");
        }
        String finalString = sb2.toString();
        return finalString;
    }
}
