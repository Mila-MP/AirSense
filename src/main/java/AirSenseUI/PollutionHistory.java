package AirSenseUI;

import GetData.GetLocalAuthorities;
import GetData.GetPlot;
import GetData.GetPollutionIndex;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class PollutionHistory extends JPanel {
    JButton okButton = new JButton("OK");
    JButton okButton2 = new JButton("OK");
    GetPlot plot = new GetPlot();
    ChartPanel plotPanel;
    GridBagConstraints gbc = new GridBagConstraints();
    public PollutionHistory() throws IOException {
        plotPanel = plot.getData();
        plotPanel.setPreferredSize(new Dimension(500,500));

        GetLocalAuthorities la = new GetLocalAuthorities();
        String str1 = la.print();
        String[] choices1 = str1.split("\n");
        JComboBox<String> cb1 = new JComboBox<>(choices1);

        JComboBox<String> cb2 = new JComboBox<>();

        setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(cb1,gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(okButton,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(cb2,gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(okButton2,gbc);

        okButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = cb1.getSelectedIndex();
                try {
                    GetPollutionIndex sites = new GetPollutionIndex(index+1);
                    String str2 = sites.getSite();
                    String[] choices2 = str2.split("\n");
                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(choices2);
                    cb2.setModel(model);
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
}

