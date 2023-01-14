package AirSenseUI;

import GetData.GetAdvice;
import GetData.GetLocalAuthorities;
import GetData.GetPollutionIndex;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The PollutionIndex class provides the user interface for the Pollution Index tab.
 */
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
    JLabel warning = new JLabel();
    JPanel boroughPanel = new JPanel();
    JPanel sitePanel = new JPanel();
    JButton clearButton = new JButton("Clear");
    Font title = new Font("Ubuntu",Font.PLAIN,15);
    Font body = new Font("Ubuntu", Font.PLAIN,13);


    public PollutionIndex() throws IOException {
        // Combo Boxes Initialisation
        GetLocalAuthorities boroughs = new GetLocalAuthorities();
        String boroughString = boroughs.print();
        String[] boroughList = boroughString.split("\n");
        JComboBox<String> boroughsCB = new JComboBox<>(boroughList);
        JComboBox<String> sitesCB= new JComboBox<>();

        // Sub-panels configuration
        boroughPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0; gbc.gridy = 0; boroughPanel.add(boroughLabel,gbc);
        gbc.gridx = 1; gbc.gridy = 0; boroughPanel.add(boroughsCB, gbc);

        sitePanel.setLayout(new GridBagLayout());
        gbc.gridx = 0; gbc.gridy = 0; sitePanel.add(siteLabel);
        gbc.gridx = 1; gbc.gridy = 0; sitePanel.add(sitesCB);

        // Font configuration
        welcome.setFont(title);
        choose.setFont(body);
        info.setFont(body);

        // Main panel configuration
        setLayout(new GridBagLayout());
        gbc.gridx = 0; gbc.gridy = 0; add(welcome,gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(empty1,gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(choose,gbc);
        gbc.gridx = 0; gbc.gridy = 3; add(empty2,gbc);
        gbc.gridx = 0; gbc.gridy = 4; add(boroughPanel,gbc);
        gbc.gridx = 0; gbc.gridy = 5; add(sitePanel,gbc);
        gbc.gridx = 0; gbc.gridy = 6; add(clearButton,gbc);
        gbc.gridx = 0; gbc.gridy = 7; add(empty3,gbc);
        gbc.gridx = 0; gbc.gridy = 8; add(info,gbc);
        gbc.gridx = 0; gbc.gridy = 9; add(warning,gbc);

        // Buttons/Combo boxes Configuration
        boroughsCB.addActionListener(e -> {
            int boroughID = boroughsCB.getSelectedIndex()+1;
            try {
                GetPollutionIndex index = new GetPollutionIndex(boroughID);
                String[] siteList = index.getSite().split("\n");
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(siteList);
                sitesCB.setModel(model);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        sitesCB.addActionListener(e -> {
            int boroughID = boroughsCB.getSelectedIndex()+1;
            try {
                GetPollutionIndex pollutionIndex = new GetPollutionIndex(boroughID);
                info.setVisible(true);
                String indexString = pollutionIndex.getIndex(sitesCB.getSelectedIndex());
                info.setText(convertToMultiline(indexString));

                // Warning message
                String indices = pollutionIndex.indices;
                List<Integer> indexList=new ArrayList<>();
                for (int i = 0; i < indices.length(); i++){
                    indexList.add(Character.getNumericValue(indices.charAt(i)));
                }
                int maxIndex = Collections.max(indexList);
                GetAdvice advice = new GetAdvice(maxIndex);
                warning.setText(advice.print());

            } catch (IOException ex) {
                throw new RuntimeException(ex);
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
        // Find link for code
    }
}
