package GetData;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


// this class is used to data scrape the London air website

public class GetNewsScraper{
    WebClient webclient = new WebClient();
    HtmlPage page;
    String ID;

    String target;
    char punctuation;

    public GetNewsScraper(String ID) throws IOException {
        // choose the desired web page
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

    public String extractHTML(){

        final String url = page.getUrl().toString();

        try{
            final Document document = Jsoup.connect(url).get();
            StringBuilder content = new StringBuilder();
            //System.out.println(document.outerHtml());
            Elements row = document.select("div.news-wrapper");
            content.append(row);
            //System.out.println(formatting(content));
            return formatting(content);

        }
        catch(Exception ex){
            ex.printStackTrace();
            return "error";

        }
    }

    public String formatting (StringBuilder sb){

        StringBuilder initial_format = formatString(sb, "<p>", '.');
        StringBuilder final_format = formatString(initial_format, "<br>", ',');

        return final_format.toString();
    }


    public StringBuilder formatString(StringBuilder sb, String target, char punctuation ){


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
        //System.out.println(positions);
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
                // removing images that aren't relevant
                pic.remove(pic.size()-1);
                pic.remove(pic.size()-1);
                pic.remove(0);
                for(Element link : pic){

                    //System.out.println(link.absUrl("src"));
                    images.add(link.absUrl("src"));

                }
            }

            //System.out.println(images);
            return images;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return images;
        }
    }


}
