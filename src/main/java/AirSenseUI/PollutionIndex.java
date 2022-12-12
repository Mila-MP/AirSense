package AirSenseUI;
import GetData.GetLocalAuthorities;
import GetData.GetPollutionIndex;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class PollutionIndex extends JPanel{
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel empty1 = new JLabel(convertToMultiline("\n"));
    JLabel empty2 = new JLabel(convertToMultiline("\n"));
    JLabel empty3 = new JLabel(convertToMultiline("\n"));
    JLabel welcome = new JLabel("On this page, you can find out the pollution levels in your current location or in the areas you want to visit");
    JLabel choose = new JLabel("Choose the Borough you wish to visit and press \"ok\" to see the latest measurements there");
    JButton okButton = new JButton("OK");
    JButton showButton = new JButton("Show me the pollution indices in my current location");
    JLabel showCurrentLoc = new JLabel();
    JLabel info = new JLabel();
    Font title = new Font("Ubuntu",Font.PLAIN,15);
    Font body = new Font("Ubuntu", Font.PLAIN,13);

    public PollutionIndex() throws IOException {

        GetLocalAuthorities la = new GetLocalAuthorities();
        String str = la.print();
        String[] choices = str.split("\n");
        JComboBox<String> cb = new JComboBox<>(choices);

        welcome.setFont(title);
        choose.setFont(body);
        info.setFont(body);

        setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(welcome,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(empty1,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(showButton,gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(showCurrentLoc,gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(empty2,gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(choose,gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(empty3,gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(cb,gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        add(okButton,gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        add(info,gbc);

        showButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int boroughID = Profile.boroughs.getSelectedIndex()+1;
                try {
                    GetPollutionIndex index = new GetPollutionIndex(boroughID);
                    showCurrentLoc.setVisible(true);
                    showCurrentLoc.setText(convertToMultiline("Current location:\n"+index.print()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        okButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int boroughID = cb.getSelectedIndex()+1;
                try {
                    GetPollutionIndex index = new GetPollutionIndex(boroughID);
                    info.setVisible(true);
                    info.setText(convertToMultiline(index.print()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }
    public static String convertToMultiline(String orig)
    {
        return "<html>" + orig.replaceAll("\n", "<br>");
    }
}
