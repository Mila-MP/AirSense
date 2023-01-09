
package GetData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetNews {
    // this class uses the London API to extract the news titles and IDs of each news article
    // The api only contains news articles up to 2018
    String responseBody;

    int index;

    public GetNews() throws IOException {
        // connects to the API
        URL url = new URL("http://api.erg.ic.ac.uk/AirQuality/Information/News/Skip=2/limit=20/Json");
        // LIMIT retrieves the number of elements from the stream truncated to be no longer than the given maximum size
        // SKIP discards the first n elements of a stream
        // Establish connection:
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestMethod("GET");
        request.connect();
        // Get response:
        InputStream is = request.getInputStream();
        BufferedReader bf_reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = bf_reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException ignored) {
        } finally {
            try {
                is.close();
                request.disconnect();
            } catch (IOException ignored) {
            }
        }
        responseBody = sb.toString();
        //System.out.print(responseBody);
    }

    public String getNewsTitles(){
        // Extracts news article titles (to be used to make the drop-down tab on the News page)
        responseBody = responseBody.substring(35,responseBody.length()-2);
        JSONArray albums = new JSONArray(responseBody);
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < albums.length(); i++){
            JSONObject album = albums.getJSONObject(i);
            String newsTitle = album.getString("@NewsTitle");
            sb2.append(newsTitle).append("\n");


        }

        return sb2.toString();
    }

    public String getNewsID(int index){
        // Extracts the ID for each news article
        // Used by the web scraper to navigate to desired news article (see GetNewsScraper)
        this.index = index;
        responseBody = responseBody.substring(35,responseBody.length()-2);
        JSONArray albums = new JSONArray(responseBody);
        List<String> ids = new ArrayList<>();

        for (int i = 0; i < albums.length(); i++){
            JSONObject album = albums.getJSONObject(i);
            String newsID = album.getString("@NewsId");
            ids.add(newsID);

        }



        return ids.get(index);
    }

}


