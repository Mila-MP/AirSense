import org.json.simple.parser.ParseException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        get species=new get("http://api.erg.ic.ac.uk/AirQuality/Information/Species/Json");
        System.out.println(species.getInfo());
    }

}