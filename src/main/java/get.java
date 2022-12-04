import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class get {
    private String str;
    public get(String str){
        this.str = str;

    }
    protected Object getInfo() throws IOException, ParseException{
        URL url = new URL(str);
        HttpURLConnection request1 = (HttpURLConnection) url.openConnection();
        request1.setRequestMethod("GET");
        request1.connect();
        InputStream is = request1.getInputStream();
        BufferedReader bf_reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
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
            }
            catch (IOException e) {
            }
        }
        String responseBody = sb.toString();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(responseBody);
        return obj;
    }
}
