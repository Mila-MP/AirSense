package GetData;


import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class GetNewsTest {

    @Test
    void testAPI_Title() throws IOException{
        // test to make sure the API is filtered correctly (for titles)
        GetNews article = new GetNews();
        assertNotEquals(article.getNewsTitles().charAt(0), '{');

    }

    @Test
    void testAPI_ID() throws IOException{
        // test to make sure the API is filtered correctly (for IDs)
        GetNews article = new GetNews();
        assertNotEquals(article.getNewsID(1).charAt(0), '{');
    }


}