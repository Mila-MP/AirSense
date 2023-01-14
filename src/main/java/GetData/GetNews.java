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

/**
 * The GetNews class provides access to the news titles and IDs of news article on
 * the London Air website. The London Air API only contains news article up to 2018.
 */
public class GetNews {
    String responseBody;
    int index;

    public GetNews() throws IOException {
        URL url = new URL("http://api.erg.ic.ac.uk/AirQuality/Information/News/Skip=2/limit=20/Json");
        // Establishes the connection
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
        } catch (IOException ignored) {
        } finally {
            try {
                is.close();
                request.disconnect();
            } catch (IOException ignored) {
            }
        }
        responseBody = sb.toString();
    }

    /**
     * The getNewsTitle method extracts news article titles to be used to make the
     * drop-down menu on the News tab.
     * @return News title
     */
    public String getNewsTitles(){
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

    /**
     * The getNewsID method extracts the id for each news article. The IDs are used
     * by the web scrapper to navigate to the desired news article.
     * @param index News index
     * @return News ID
     */
    public String getNewsID(int index){
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


