/* Only for the presentation */

package AirSenseUI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class InhalerUse2 extends JPanel{
    JButton used = new JButton("I just used my reliever");
    JTable table = new JTable();
    JTextField number = new JTextField();
    DefaultTableModel model = new DefaultTableModel();
    int count = 0;

    public InhalerUse2(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        // Table Configuration
        Object[] columns = {"Date", "Number of puffs"};
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        table.setShowGrid(true);


        add(new JScrollPane(table));
        add(number);
        add(used);

        Object[] row = new Object[2];

        used.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                count += 1;
                LocalDateTime now = LocalDateTime.now();
                row[0] = dtf.format(now);
                row[1] = number.getText();
                model.addRow(row);

            }
        });

    }
}

