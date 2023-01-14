package StoreData;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;

public class Inhaler {
    public String inhaler_expiry;
    public String inhaler_name;
    public int puffs_left;
    public int puffs_taken;
    public int usage_count;
    public String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
    public Connection conn = DriverManager.getConnection(dbUrl, "postgres", "airsense");

    public Inhaler(String inhaler_name, String expiry, int quantity) throws SQLException, ClassNotFoundException {
        this.inhaler_name = inhaler_name;
        this.inhaler_expiry = expiry;
        this.puffs_left = quantity;
    }

    /**
     * The aim of this function is to add an inhaler to the database
     */
    public void add_inhaler() throws ClassNotFoundException, SQLException {
        // Registering the driver
        Class.forName("org.postgresql.Driver");

        Statement s1 = conn.createStatement();
        s1.executeUpdate("INSERT INTO inhalers (inhaler_type,expiry_date,quantity) VALUES ('"+this.inhaler_name+"','"+this.inhaler_expiry+"',"+this.puffs_left+")");
        s1.close();
    }

    /**
     * The aim of this function is to count how many times the user uses their inhaler in a week
     * @return will return false if less than 3 times, and return true if more than 3 times a week
     */

    public Boolean use_count() throws SQLException, ClassNotFoundException {

        // Retrieving the data values
        Class.forName("org.postgresql.Driver");
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("select * from use_data");
        usage_count = 0;
        while (rs.next()) {
            // Checking if date in table is 7 days before date it is currently
            if (!rs.getTimestamp("use_date").toLocalDateTime().isBefore(LocalDateTime.now().minus(Duration.ofDays(7)))) {
                usage_count = usage_count + 1;
            }
        }
        return usage_count >= 3;

    }

    /**
     * This function returns true if the quantity of an inhaler in the inhaler table runs below 25 puffs left
     * @return true if warning needs to take place
     */
    public Boolean quantity_warning() throws ClassNotFoundException, SQLException {

        // Retrieving the data values
        Class.forName("org.postgresql.Driver");
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("select * from use_data");
        boolean warning = false;
        while(rs.next()){
            if(rs.getInt("quantity") < 25){
                warning = true;
            }
        }
        return warning;
    }

    /**
     * The aim of this function is to input the uses of the user
     *   If function is called again within 30 minutes of its last call, it will add the puffs_taken of the
     *   current input to the last input
     * @param puffs_taken no_of_puffs - which defaults to 1
     */
    public void use_input(int puffs_taken) throws Exception {

        this.puffs_taken = puffs_taken;


        // Change date to correct format
        LocalDateTime current_time = LocalDateTime.now();
        System.out.println(current_time);


        try {
            // Registering the driver
            Class.forName("org.postgresql.Driver");

            Statement s = conn.createStatement();

            // Retrieving the current quantity
            ResultSet rs1 = s.executeQuery("SELECT * FROM inhalers WHERE inhaler_type ='"+this.inhaler_name+"'");

            // Calculating new quantity
            rs1.next();
            int updated_puff_no = rs1.getInt("quantity")-this.puffs_taken;
            System.out.print(updated_puff_no);

            // Updating table with new quantity
            s.executeUpdate("UPDATE inhalers SET quantity = "+updated_puff_no+" WHERE inhaler_type ='"+this.inhaler_name+"'");

            // Selecting the latest use
            ResultSet rs = s.executeQuery("select * from use_data ORDER BY use_date DESC LIMIT 1");


            // Empty database check
            ResultSet rs_empty = s.executeQuery("SELECT count(*) FROM use_data");
            rs_empty.next();
            if (rs_empty.getInt(1) == 0){
                String sqlStr = "INSERT INTO use_data (no_of_puffs) VALUES ("+puffs_taken+")";
                System.out.println(sqlStr);
                s.executeUpdate(sqlStr);
            }
            else {
                while (rs.next()) {
                    System.out.println(rs.getTimestamp("use_date"));
                    LocalDateTime last_date = rs.getTimestamp("use_date").toLocalDateTime();
                    LocalDateTime temp_time = LocalDateTime.now();
                    System.out.println("This is the last date:" + last_date);
                    System.out.println("This is the current date minus 30 mins:" + temp_time.minus(Duration.ofMinutes(30)));

                    int last_id = rs.getInt("id");

                    /* Checking if the current input is within 30 minutes */
                    System.out.println(last_date.isBefore(temp_time.minus(Duration.ofMinutes(30))));
                    System.out.println("Here we are checking if we should group  the inputs together");
                    if (!last_date.isBefore(temp_time.minus(Duration.ofMinutes(30)))) {
                        // If it is, we now combine this input and the last input
                        System.out.println("We are combining the inputs");
                        System.out.println(last_id);

                        int current_puffs = rs.getInt("no_of_puffs");
                        int new_puff_no = current_puffs + this.puffs_taken;

                    /* Changing the number of puffs taken in the table for the current use */
                        s.executeUpdate("UPDATE use_data SET no_of_puffs = " + new_puff_no + " WHERE id = " + last_id + ";");
                    } else {
                        System.out.println("We are NOT combining the inputs");

                        // Inputting the current use
                        String sqlStr = "INSERT INTO use_data (no_of_puffs) VALUES (" + puffs_taken + ")";
                        System.out.println(sqlStr);
                        s.executeUpdate(sqlStr);
                    }
                }
            }


            s.close();



        } finally {
            Boolean output_msg = use_count();

            if (output_msg){
                System.out.println("Based on NHS guidance it is recommended that you see your doctor about your asthma, you have used your inhaler a total of " +usage_count+" times this week");
            }
            else{
                System.out.println("Thank you for your input, you have used your inhaler a total of "+usage_count+" times this week");
            }
        }

    }

}



