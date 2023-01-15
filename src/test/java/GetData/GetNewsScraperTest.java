package GetData;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GetNewsScraperTest {
    // tests text extraction for two cases
    @Test
    void Extraction() throws IOException {
        GetNewsScraper article = new GetNewsScraper("ComingUpAir");
        assertEquals(article.extractHTML().charAt(3), 'p');
    }

    @Test
    void Extraction2() throws IOException {
        GetNewsScraper article = new GetNewsScraper("Pollutionwatch");
        assertEquals(article.extractHTML().charAt(3), 'p');
    }

    // tests image extraction for pages with known images
    @Test
    void iExtraction() throws IOException {
        GetNewsScraper article = new GetNewsScraper("GrenfellTowerfire");
        List<String> list = article.extractImages();
        assertEquals(list.get(0), "https://www.londonair.org.uk/london/images/news/LondonFire2017.jpg");
    }

//    @Test
//    void iExtraction2() throws IOException {
//        GetNewsScraper article = new GetNewsScraper("centreeventJune");
//        List<String> list = article.extractImages();
//        assertEquals(list.get(0), "https://www.londonair.org.uk/london/images/news/MRC_Festival_June_2017_new.png");
//    }
}