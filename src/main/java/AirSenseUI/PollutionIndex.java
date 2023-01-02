package AirSenseUI;
import GetData.GetLocalAuthorities;
import GetData.GetPollutionIndex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class PollutionIndex extends JPanel{
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel empty1 = new JLabel(convertToMultiline("\n"));
    JLabel empty2 = new JLabel(convertToMultiline("\n"));
    JLabel empty3 = new JLabel(convertToMultiline("\n"));
    JLabel welcome = new JLabel("On this page, you can find out the pollution levels in the areas you want to visit");
    JLabel choose = new JLabel("Choose a Borough and a measurement site to see the pollution levels at that location.");
    JLabel boroughLabel = new JLabel("Borough:");
    JLabel siteLabel = new JLabel("Measurement site:");
    JLabel info = new JLabel();
    JPanel boroughPanel = new JPanel();
    JPanel sitePanel = new JPanel();
    JButton clearButton = new JButton("Clear");
    Font title = new Font("Ubuntu",Font.PLAIN,15);
    Font body = new Font("Ubuntu", Font.PLAIN,13);

    public PollutionIndex() throws IOException {

        // ComboBox Initialisation
        GetLocalAuthorities la = new GetLocalAuthorities();
        String str = la.print();
        String[] choices = str.split("\n");
        JComboBox<String> boroughsCB = new JComboBox<>(choices);
        JComboBox<String> sitesCB= new JComboBox<>();

        // Panels
        boroughPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        boroughPanel.add(boroughLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        boroughPanel.add(boroughsCB, gbc);

        sitePanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        sitePanel.add(siteLabel);
        gbc.gridx = 1;
        gbc.gridy = 0;
        sitePanel.add(sitesCB);

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
        add(choose,gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(empty2,gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(boroughPanel,gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(sitePanel,gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(clearButton,gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(empty3,gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        add(info,gbc);

        boroughsCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int boroughID = boroughsCB.getSelectedIndex()+1;
                try {
                    GetPollutionIndex index = new GetPollutionIndex(boroughID);
                    String[] siteList = index.getSite().split("\n");
                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(siteList);
                    sitesCB.setModel(model);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        sitesCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int boroughID = boroughsCB.getSelectedIndex()+1;
                try {
                    GetPollutionIndex index = new GetPollutionIndex(boroughID);
                    info.setVisible(true);
                    info.setText(convertToMultiline(index.getIndex(sitesCB.getSelectedIndex())));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                info.setVisible(false);
                DefaultComboBoxModel<String> emptyModel = new DefaultComboBoxModel<>();
                sitesCB.setModel(emptyModel);
            }
        });

    }
    public static String convertToMultiline(String orig)
    {
        return "<html>" + orig.replaceAll("\n", "<br>");
    }
}
