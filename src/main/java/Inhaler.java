

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Inhaler {

    public String inhaler_expiry;
    public String inhaler_name;
    public int puffs_left;
    public int puffs_taken;

    public String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
    public Connection conn = DriverManager.getConnection(dbUrl, "postgres", "airsense");

    public Inhaler(String inhaler_type, String expiry, int quantity) throws SQLException {
        this.inhaler_name = inhaler_type;
        this.inhaler_expiry = expiry;
        this.puffs_left = quantity;


    }

    public void add_inhaler() {

        try {
            // Registering the driver
            Class.forName("org.postgresql.Driver");

            System.out.println("Inserting data into inhaler");
            Statement s = conn.createStatement();
            String sqlStr = "INSERT INTO inhalers (inhaler_type,expiry_date,quantity) VALUES ('reliver2','0202',6)";
            System.out.println(sqlStr);

            s.executeUpdate(sqlStr);

            conn.commit();
            s.close();
            conn.close();
        } catch (Exception e) {
        }

    }

    public void use_count(int puffs_taken) throws Exception {
        this.puffs_taken = puffs_taken;
        int count = 0;

        // Formatting the database correctly
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        // Current date
        Date todayDate = new Date();

        try {
            // Registering the driver
            Class.forName("org.postgresql.Driver");

            String sqlStr = "INSERT INTO use_data (no_of_puffs) VALUES (3)";
            System.out.println(sqlStr);

            Statement s = conn.createStatement();

            s.executeUpdate(sqlStr);


            ResultSet rs = s.executeQuery("select * from use_data");

            // Retrieving values
            System.out.println("Here we are comparing the dates of recorded uses");
            while (rs.next()) {
                 long c = (int) (todayDate.getTime() - rs.getDate("use_date").getTime());
                 long days = TimeUnit.MILLISECONDS.toDays(c);
                System.out.println(days);
                if (days < 7){
                    count = count + 1;
                }
            }


            s.close();
            conn.close();


        } finally {
            if (count > 3){
                System.out.println("Based on NHS guidance it is recommended that you see your doctor about your asthma");
            }
            else{
                System.out.println("Thank you for your input");
            }

        System.out.println("You have used your inhaler a total of " + count + " times this week");
        }

    }

}



