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
    JLabel welcome = new JLabel("On this page, you can find out the pollution levels in the areas you want to visit");
    JLabel choose = new JLabel("Choose the Borough you wish to visit and press \"ok\" to see the latest measurements there");
    JButton okButton = new JButton("OK");
    JLabel info = new JLabel();

    public PollutionIndex() throws IOException {
        setLayout(new GridBagLayout());
        GetLocalAuthorities la = new GetLocalAuthorities();
        String str = la.print();
        String[] choices = str.split("\n");
        JComboBox<String> cb = new JComboBox<>(choices);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(welcome,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(choose,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(cb,gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(okButton,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(info,gbc);

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
