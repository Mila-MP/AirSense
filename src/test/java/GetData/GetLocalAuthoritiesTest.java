package GetData;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class GetLocalAuthoritiesTest {

    @Test
    void print() throws IOException {
    GetLocalAuthorities la = new GetLocalAuthorities();
    assertEquals("Barking and Dagenham\n" +
                    "Barnet\n" +
                    "Bexley\n" +
                    "Brent\n" +
                    "Bromley\n" +
                    "Camden\n" +
                    "City of London\n" +
                    "Croydon\n" +
                    "Ealing\n" +
                    "Enfield\n" +
                    "Greenwich\n" +
                    "Hackney\n" +
                    "Hammersmith and Fulham\n" +
                    "Haringey\n" +
                    "Harrow\n" +
                    "Havering\n" +
                    "Hillingdon\n" +
                    "Hounslow\n" +
                    "Islington\n" +
                    "Kensington and Chelsea\n" +
                    "Kingston\n" +
                    "Lambeth\n" +
                    "Lewisham\n" +
                    "Merton\n" +
                    "Newham\n" +
                    "Redbridge\n" +
                    "Richmond\n" +
                    "Southwark\n" +
                    "Sutton\n" +
                    "Tower Hamlets\n" +
                    "Waltham Forest\n" +
                    "Wandsworth\n" +
                    "Westminster\n",
            la.print());
    }
}