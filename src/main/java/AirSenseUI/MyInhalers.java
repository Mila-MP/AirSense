package AirSenseUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import StoreData.Inhaler;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;

public class MyInhalers extends JPanel{

    public String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
    // NOTE!! Change the password based on what you set it yourself - I have not yet figured out how to store on Heroku
    public Connection conn = DriverManager.getConnection(dbUrl, "postgres", "airsense");
    JButton refresh = new JButton("Refresh Inhaler data");
    JTextField inhaler_name = new JTextField("inhaler name");
    JTextField quantity_input = new JTextField();
    JLabel quantity_label = new JLabel();
    JButton add_inhaler_b = new JButton("Add inhaler");
    public JButton DeleteInhalerButton = new JButton("Delete current Inhaler");
    JTable tableMyInhalers = new JTable ();

    JLabel expiry_label = new JLabel("Expiry date, yyyy-MM-dd");
    JFormattedTextField expiry_date = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));

    public MyInhalers() throws SQLException, ClassNotFoundException {

        setLayout(new FlowLayout());
        quantity_label.setForeground(Color.red);

        // Table first made when the app is started

        tableMyInhalers.setModel(refresh_model());
        JScrollPane scrollPane = new JScrollPane(tableMyInhalers);
        tableMyInhalers.setFillsViewportHeight(true);
        add(scrollPane);
        add(refresh);

        EmptyTableCheck();

        if (expiry_warning()){
            JOptionPane.showMessageDialog(null,"You inhaler is about to expire, please get another one","Alert",JOptionPane.WARNING_MESSAGE);

        }


        // https://www.tutorialspoint.com/how-can-we-make-jtextfield-accept-only-numbers-in-java
        quantity_input.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
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

        refresh.addActionListener(e -> {
            try {
                tableMyInhalers.setModel(refresh_model());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        DeleteInhalerButton.addActionListener(e -> {
            try {
                DeleteInhalerProfile();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            try {
                tableMyInhalers.setModel(refresh_model());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            remove(DeleteInhalerButton);

            try {
                System.out.print("I am running an EmptyTableCheck");
                EmptyTableCheck();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        add_inhaler_b.addActionListener(e -> {
            System.out.println("I have clicked add inhaler button");
            String name = inhaler_name.getText();
            String expiry = expiry_date.getText();
            int quantity = Integer.parseInt(quantity_input.getText());
            try {
                Inhaler current = new Inhaler(name,expiry,quantity);
                current.add_inhaler();
            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            remove(inhaler_name);
            remove(expiry_label);
            remove(expiry_date);
            remove(quantity_label);
            remove(quantity_input);
            remove(add_inhaler_b);

            add(DeleteInhalerButton);

            try {
                tableMyInhalers.setModel(refresh_model());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });

    }

    public Boolean expiry_warning() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("select * from inhalers");

        boolean warning = false;

        while(rs.next()){
            LocalDate date = LocalDate.parse(rs.getString("expiry_date"));
            if(date.isBefore(LocalDate.now().minus(Period.ofMonths(1)))){
                warning = true;
            }
        }

        return warning;
    }
    public DefaultTableModel refresh_model() throws SQLException {
        /* The purpose of this function is to refresh the table displayed on the UI. It does this by creating a new table model
        which can then be assigned to the appropriate JTable
         */
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Inhaler");
        model.addColumn("Expiry date (yyyy-MM-dd)");
        model.addColumn("Quantity");

        Statement s = conn.createStatement();
        String query2 = "SELECT * FROM inhalers";

        ResultSet rs2 = s.executeQuery(query2);
        while(rs2.next()){
            model.insertRow(0, new Object[] {rs2.getString("inhaler_type"),rs2.getString("expiry_date"),rs2.getInt("quantity")});
        }
       return model;
    }



    public void EmptyTableCheck() throws SQLException {
        // If inhalers is empty
        Statement s = conn.createStatement();
        ResultSet rs_empty = s.executeQuery("SELECT count(*) AS rowcount FROM inhalers");
        rs_empty.next();
        int row_count = rs_empty.getInt("rowcount");
        System.out.println("This is inhaler row count:"+row_count);
        if (row_count == 0){
            System.out.println("I am in the if statement");
            add(inhaler_name);
            add(expiry_label);
            add(expiry_date);
            add(quantity_label);
            add(quantity_input);
            add(add_inhaler_b);


        }
        else{
            add(DeleteInhalerButton);
        }
    }

    public void DeleteInhalerProfile() throws SQLException {
        System.out.println("I am here");
        Statement s = conn.createStatement();
        s.executeUpdate("TRUNCATE TABLE inhalers");
        s.executeUpdate("TRUNCATE TABLE use_data");
    }
}
