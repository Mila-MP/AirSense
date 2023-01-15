package GetData;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class GetLocalAuthoritiesTest {

    @Test
    void print() throws IOException {
    GetLocalAuthorities la = new GetLocalAuthorities();
    assertEquals("""
                    Barking and Dagenham
                    Barnet
                    Bexley
                    Brent
                    Bromley
                    Camden
                    City of London
                    Croydon
                    Ealing
                    Enfield
                    Greenwich
                    Hackney
                    Hammersmith and Fulham
                    Haringey
                    Harrow
                    Havering
                    Hillingdon
                    Hounslow
                    Islington
                    Kensington and Chelsea
                    Kingston
                    Lambeth
                    Lewisham
                    Merton
                    Newham
                    Redbridge
                    Richmond
                    Southwark
                    Sutton
                    Tower Hamlets
                    Waltham Forest
                    Wandsworth
                    Westminster
                    """,
            la.print());
    }
}