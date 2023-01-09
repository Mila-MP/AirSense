package AirSenseUI;

import StoreData.Inhaler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class InhalerUse extends JPanel {

    public JTable use_table;
    public JTextField puffs;
    JPanel tablePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JButton used = new JButton("I just used my Inhaler");
    public String dbUrl = "jdbc:postgresql://localhost:5433/postgres";
    // NOTE!! Change the password based on what you set it yourself - I have not yet figured out how to store on Heroku
    public Connection conn = DriverManager.getConnection(dbUrl, "postgres", "AirSense");

    public InhalerUse() throws SQLException {
        setLayout(new GridLayout(2,1));

        java.util.Date todayDate = new Date();

        int use_count = 0;

        // Loop through all in the database


        Statement s = conn.createStatement();
        String query = "SELECT count(*) FROM use_data";
        String query2 = "SELECT * FROM use_data";
        //Executing the query
        ResultSet rs = s.executeQuery(query);
        //Retrieving the result
        rs.next();
        int count = rs.getInt(1);
        // System.out.println("Number of records in the use_data table: "+count);
        ResultSet rs2 = s.executeQuery(query2);
        String [][]use_list = new String [count][2];
        int r = 0;
        while(rs2.next()){

            String data1 = String.valueOf(rs2.getDate("use_date"));
            String data2 = String.valueOf(rs2.getInt("no_of_puffs"));
            use_list[r][0] = data1;
            use_list[r][1] = data2;
            r = r + 1;

            long c = (int) (todayDate.getTime() - rs2.getDate("use_date").getTime());
            long days = TimeUnit.MILLISECONDS.toDays(c);


            if (days < 7){
                use_count = use_count + 1;
           // model.insertRow(0, new Object[]{String.valueOf(rs2.getDate("use_date")),String.valueOf(rs2.getInt("no_of_puffs"))});

        }

            }
        String msg;
        if (use_count >= 3){
            msg = "Based on NHS guidance you should contact your doctor";
    }
            else{
            msg = "Thank you for your input";
        }
        System.out.println(use_list);

        String[] columnNames = {"use date", "number of puffs taken"};
        JTable use_table = new JTable (use_list,columnNames);

            // add new row to the table - actionListener and rowCount - rowCount in table
            JLabel txt = new JLabel(msg);
            JScrollPane scrollPane = new JScrollPane(use_table);
            use_table.setFillsViewportHeight(true);
            tablePanel.add(scrollPane);
            tablePanel.add(txt);
            buttonPanel.add(used);
            add(tablePanel);
            add(buttonPanel);

            // Creating the input for no. of puffs
            JTextField puffs = new JTextField();

            /*used.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Inhaler myreliever = null;
                    try {
                        myreliever = new Inhaler("reliever", "12/12/12", 200);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    myreliever.add_inhaler();
                    try {
                        myreliever.use_count(3);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    //tablePanel.revalidate();

                    Statement s = conn.createStatement();
                    String query = "SELECT count(*) FROM use_data";
                    String query2 = "SELECT * FROM use_data";
                    //Executing the query
                    ResultSet rs = s.executeQuery(query);
                    //Retrieving the result
                    rs.next();
                    int count = rs.getInt(1);
                    // System.out.println("Number of records in the use_data table: "+count);
                    ResultSet rs2 = s.executeQuery(query2);
                    String [][]use_list = new String [count][2];
                    int r = 0;
                    while(rs2.next()){

                        String data1 = String.valueOf(rs2.getDate("use_date"));
                        String data2 = String.valueOf(rs2.getInt("no_of_puffs"));
                        use_list[r][0] = data1;
                        use_list[r][1] = data2;
                        r = r + 1;

                        long c = (int) (todayDate.getTime() - rs2.getDate("use_date").getTime());
                        long days = TimeUnit.MILLISECONDS.toDays(c);


                        if (days < 7){
                            use_count = use_count + 1;
                            // model.insertRow(0, new Object[]{String.valueOf(rs2.getDate("use_date")),String.valueOf(rs2.getInt("no_of_puffs"))});

                        }

                    }
                    String msg;
                    if (use_count >= 3){
                        msg = "Based on NHS guidance you should contact your doctor";
                    }
                    else{
                        msg = "Thank you for your input";
                    }
                    System.out.println(use_list);

                    String[] columnNames = {"use date", "number of puffs taken"};
                    JTable use_table = new JTable (use_list,columnNames);

                    // add new row to the table - actionListener and rowCount - rowCount in table
                    JLabel txt = new JLabel(msg);
                    JScrollPane scrollPane = new JScrollPane(use_table);
                    use_table.setFillsViewportHeight(true);
                    tablePanel.add(scrollPane);
                    tablePanel.add(txt);
                    tablePanel.repaint();
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
            });*/


    }

    }

