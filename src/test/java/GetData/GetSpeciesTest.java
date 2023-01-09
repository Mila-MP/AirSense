package GetData;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GetSpeciesTest {

    @Test
    void print() throws IOException {
        GetSpecies species = new GetSpecies();
        assertEquals(
                """
                        CO: Carbon Monoxide
                        NO2: Nitrogen Dioxide
                        O3: Ozone
                        PM10: PM10 Particulate
                        PM25: PM2.5 Particulate
                        SO2: Sulphur Dioxide
                        """,
                        species.print());
    }
}