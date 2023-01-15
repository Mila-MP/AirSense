package GetData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * The GetNewsScraper class uses jSoup to data scrape the London Air news articles.
 * If the web page updates then so will our app, however this currently only works for articles before 2018.
 */

/* reference 2: https://www.youtube.com/watch?v=QzOJnNXH0uU */
public class GetNewsScraper{
    WebClient webclient = new WebClient();
    HtmlPage page;
    /**
     * ID of the desired news article
     */
    String ID;
    String target;
    char punctuation;

    public GetNewsScraper(String ID) throws IOException {
        this.ID = ID;
        String root = "https://www.londonair.org.uk/london/asp/news.asp?NewsId=";
        String url = root + ID;
        page = getWebPage(url);
    }

    public HtmlPage getWebPage(String url) throws IOException{
        webclient.getOptions().setCssEnabled(false);
        webclient.getOptions().setJavaScriptEnabled(false);
        return webclient.getPage(url);
    }
    /* end of reference 2 */

    
    public String extractHTML(){
        final String url = page.getUrl().toString();
        try{
            final Document document = Jsoup.connect(url).get();
            StringBuilder content = new StringBuilder();
            Elements row = document.select("div.news-wrapper"); // div.news-wrapper obtained from 'inspect' on web browser
            content.append(row);
            return formatting(content);

        }
        catch(Exception ex){
            ex.printStackTrace();
            return "error";
        }
    }
    

    public String formatting (StringBuilder sb){
        // the scraped text goes through two types of formatting
        // so that the text won't run off the page (as it's prone to do with gridbaglayout)
        StringBuilder initial_format = formatString(sb, "<p>", '.');
        StringBuilder final_format = formatString(initial_format, "<br>", ',');
        return final_format.toString();
    }


    public StringBuilder formatString(StringBuilder sb, String target, char punctuation ){
        
        // searches through the test looking for either full stops or commas
        // if the gap between full stops or commas is too long (ie if the text runs off the page)
        // add a <p> or <br> tag to make a new line.
        // searching for commas/full stops ensures the new line will be at a natural point in the sentence
        // and not in the middle of a word.
        
        this.target = target;
        this.punctuation = punctuation;
        List<Integer> positions = new ArrayList<>();
        int j = 0;
        while(j<sb.lastIndexOf(target)+1){
            int next = sb.indexOf(target,j);
            if (!positions.contains(next)) {
                positions.add(next);
            }
            j=j+1;
        }

        int array_size = positions.size();
        for(int l =0; l<array_size-1; l++){

            int upper = positions.get(l+1);
            int lower = positions.get(l);
            int difference = upper - lower;

            if(punctuation == '.') {
                if (difference > 150) {
                    for (int k = lower; k < upper; k++) {
                        if (sb.charAt(k) == punctuation) {
                            sb.insert(k + 1, "<br>");
                        }
                    }
                }
            }
            if(punctuation == ','){
                // this if statement takes into account sentences that are very long
                // i.e. gap between full stops is so long that we need to look for commas instead.
                if(difference > 290){
                    int mean = (upper+lower)/2;
                    int in = sb.indexOf(",",mean);
                    sb.insert(in +1, "<br>");
                }
            }
        }

        // removes image so it's not displayed twice
        int in = sb.indexOf("!important;");
        if (in != -1){
            return sb.delete(0,in+13);
        }
        else{
            return sb;
        }
    }

    public List<String> extractImages(){
        List<String> images = new ArrayList<>();
        String url = page.getUrl().toString();
        try{
            final Document document= Jsoup.connect(url).get();
            Elements pic =document.select("img");
            if (pic.size() >= 3){
                // There are images that consistently appear that we are not interested in displaying
                // for example the Newsletter icon at the bottom of every page.
                // so we remove images that aren't relevant (last two and first):
                pic.remove(pic.size()-1);
                pic.remove(pic.size()-1);
                pic.remove(0);
                for(Element link : pic){
                    images.add(link.absUrl("src"));
                }
            }
            return images;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return images;
        }
    }
}
