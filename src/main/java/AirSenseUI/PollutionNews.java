package AirSenseUI;
import GetData.GetNewsScraper;
import GetData.GetNews;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * The PollutionNews class provides the user interface for the News tab.
 */
public class PollutionNews extends JPanel{
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel welcome = new JLabel("On this page, you can view news relating to air pollution.");
    JLabel choose = new JLabel("Choose the news heading you wish to view");
    JButton okButton = new JButton("OK");
    JEditorPane pane = new JEditorPane();
    URL image;
    JLabel im;

    public PollutionNews() throws IOException {

        setLayout(new GridBagLayout());

        GetNews news = new GetNews();
        String str = news.getNewsTitles();
        String[] choices = str.split("\n");
        JComboBox<String> cb = new JComboBox<>(choices);

        gbc.gridx = 0; gbc.gridy = 0; add(welcome,gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(choose,gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(cb,gbc);
        gbc.gridx = 0; gbc.gridy = 3; add(okButton,gbc);

        okButton.addActionListener(e -> {
            if(im != null){
                im.setVisible(false); //removes images from previous
            }

            int index = cb.getSelectedIndex();
            try{
                GetNews n = new GetNews();
                String ID = n.getNewsID(index); //fetches the id corresponding to the news article

                GetNewsScraper news2 = new GetNewsScraper(ID);

                List<String> my_images = news2.extractImages(); //gets images from the news article
                String content = news2.extractHTML(); //gets content from the news article

                pane.setContentType("text/html");
                pane.setText(content);
                gbc.gridx=0; gbc.gridy=4; add(pane,gbc);
                pane.setEditable(false);

                // Get images
                if (my_images.size() >0) {
                    try {
                        image = new URL(my_images.get(0));
                        im = new JLabel(new ImageIcon(image));

                        gbc.gridx = 0; gbc.gridy = 5; add(im, gbc);
                        im.setVisible(true);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            }catch (IOException ex){
                throw new RuntimeException(ex);
            }
        });

        // Hyperlinks
        pane.addHyperlinkListener(e1 -> {
            if(e1.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
                // Removing the <br> from earlier formatting
                String edited_link = e1.getURL().toString().replace("<br>", "");
                try{
                    URL link_url = new URL(edited_link);
                    Desktop.getDesktop().browse(link_url.toURI());

                } catch (IOException | URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
