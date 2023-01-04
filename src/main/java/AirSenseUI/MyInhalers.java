package AirSenseUI;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import StoreData.Inhaler;

public class MyInhalers extends JPanel{
    public JFrame f;

    protected JTable inhaler_table;
    public int count;
    public boolean wait;

    public String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
    // NOTE!! Change the password based on what you set it yourself - I have not yet figured out how to store on Heroku
    public Connection conn = DriverManager.getConnection(dbUrl, "postgres", "airsense");

    JButton refresh = new JButton("Refresh Inhaler data");
    JTextField inhaler_name = new JTextField("inhaler name");
    JLabel expiry_label = new JLabel("Expiry date");
    JTextField expiry_date = new JTextField("yyyy-MM-dd");

    JTextField quantity_input = new JTextField();
    JLabel quantity_label = new JLabel();
    JButton add_inhaler = new JButton("Add inhaler");

    public DefaultTableModel refresh_model() throws SQLException {
        /* The purpose of this function is to refresh the table displayed on the UI. It does this by creating a new table model
        which can then be assigned to the appropriate JTable
         */
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Inhaler");
        model.addColumn("Expiry date");
        model.addColumn("Quantity");

        Statement s = conn.createStatement();
        String query2 = "SELECT * FROM inhalers";

        ResultSet rs2 = s.executeQuery(query2);
        while(rs2.next()){
            model.insertRow(0, new Object[] {rs2.getString("inhaler_type"),rs2.getString("expiry_date"),rs2.getInt("quantity")});
        }
       return model;
    }

    public MyInhalers() throws SQLException {

        // If inhalers is empty
        Statement s = conn.createStatement();
        ResultSet rs_empty = s.executeQuery("SELECT count(*) FROM use_data");
        int count = 0;

        if (count == 1){
            add(inhaler_name);
            add(expiry_label);
            add(expiry_date);
            add(quantity_label);
            add(quantity_input);
            add(add_inhaler);
            wait = false;

            while(wait == false){
                try {
                    Thread.sleep(200);
                } catch(InterruptedException e) {
                }
            }

            add_inhaler.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String name = inhaler_name.getText();
                    String expiry = expiry_date.getText();
                    int quantity = Integer.parseInt(quantity_input.getText());
                    try {
                        Inhaler current = new Inhaler(name,expiry,quantity);
                        current.add_inhaler();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }

                    remove(inhaler_name);
                    remove(expiry_label);
                    remove(expiry_date);
                    remove(quantity_label);
                    remove(quantity_input);
                    remove(add_inhaler);


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

        setLayout(new FlowLayout());
        quantity_label.setForeground(Color.red);

        // Table first made when the app is started
        DefaultTableModel MyInhalersModel = refresh_model();
        JTable tableMyInhalers = new JTable (MyInhalersModel);

        JScrollPane scrollPane = new JScrollPane(tableMyInhalers);
        tableMyInhalers.setFillsViewportHeight(true);
        add(scrollPane);
        add(refresh);


        // https://www.tutorialspoint.com/how-can-we-make-jtextfield-accept-only-numbers-in-java
        quantity_input.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                String value = quantity_input.getText();
                int l = value.length();
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyCode() == 8 || ke.getKeyCode() == 46
                        || ke.getKeyCode() == 37 || ke.getKeyCode() == 39) {
                    quantity_input.setEditable(true);
                    quantity_label.setText("");
                }
                else {
                    quantity_input.setEditable(false);
                    quantity_label.setText("* Enter only numeric digits(0-9)");
                }

            }
        });

        refresh.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    tableMyInhalers.setModel(refresh_model());
                } catch (SQLException ex) {
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





        /* f = new JFrame();
        String[] columnNames = {"Inhaler Type", "Expiry date", "Quantity remaining"};
        Object[][] data = {{"Reliever", "12-12-2020", "200"}};
        inhaler_table = new JTable(data, columnNames);

        // Frame Title

        // Data to be displayed in the JTable

        // Grabbing data from Postgresql table
        try {
            Statement s = conn.createStatement();
            String sqlStr = "SELECT * FROM inhalers WHERE id>0;";
            ResultSet rs = s.executeQuery(sqlStr);

            // Row count
            int rowCount = 0;
            if (rs.last()) {
            }
            /*while (rs.next()) {
                String temp_type = String.valueOf(rs.getString("inhaler_type"));
                String temp_expiry = String.valueOf(rs.getString("expiry_date"));
                String temp_quantity = String.valueOf(rs.getInt("quantity"));

                Object[] row = { temp_type, temp_expiry, temp_quantity};

                data.(row);
            System.out.println(rs.getString("inhaler_type") + rs.getString("expiry_date") + rs.getInt("quantity"));
            }
            // Formatting it correctly into the JTable

        } catch (Exception e) {
        }

        JScrollPane scrollPane = new JScrollPane(inhaler_table);
        inhaler_table.setFillsViewportHeight(true);
        add(scrollPane); */

    }
}
