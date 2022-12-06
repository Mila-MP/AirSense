import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class Example extends JFrame {

    Example() {//

        super("Simple Calculator");

        // The Main Panel where the 2 other panels will be on
        JPanel mainPanel = new JPanel(new BorderLayout());

        // The textarea will be inside this panel
        JPanel areaPanel = new JPanel(new BorderLayout());

        JTextArea area = new JTextArea(
                "This is a JTextArea -Long text to show it works -Long text to show it works- -Long text to show it works- -Long text to show it works- -Long text to show it works- -Long text to show it works-");
        area.setBorder(new LineBorder(Color.BLACK));
        area.setWrapStyleWord(true);
        area.setLineWrap(true);

        // Fill the whole space of the panel with the area
        areaPanel.add(area, BorderLayout.CENTER);

        // The buttons will be inside this panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 3, 3));
        for (int i = 0; i < 16; i++) { // Adding buttons
            buttonPanel.add(new JButton("Button" + i));
        }

        // The textarea-panel should be on top of the main panel
        mainPanel.add(areaPanel, BorderLayout.NORTH);

        // The panel with the buttons should fill the remaining space
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        getContentPane().add(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Example();
    }

}