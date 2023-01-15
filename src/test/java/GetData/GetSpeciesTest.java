package GetData;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class GetSpeciesTest {

    @Test
    void print() throws IOException {
        GetSpecies species = new GetSpecies();
        assertEquals("CO: Carbon Monoxide\n" +
                        "NO2: Nitrogen Dioxide\n" +
                        "O3: Ozone\n" +
                        "PM10: PM10 Particulate\n" +
                        "PM25: PM2.5 Particulate\n" +
                        "SO2: Sulphur Dioxide\n",
                        species.print());
    }
}