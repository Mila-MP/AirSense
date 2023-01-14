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
    // NOTE!! Change the password based on what you set it yourself - I have not yet figured out how to store on Heroku
    public Connection conn = DriverManager.getConnection(dbUrl, "postgres", "Il8S741v");

    public Inhaler(String inhaler_name, String expiry, int quantity) throws SQLException, ClassNotFoundException {
        this.inhaler_name = inhaler_name;
        this.inhaler_expiry = expiry;
        this.puffs_left = quantity;
    }

    public void add_inhaler() throws ClassNotFoundException, SQLException {
        /* The aim of this function is to add an inhaler to the database
        */
        // Registering the driver
        Class.forName("org.postgresql.Driver");

        Statement s1 = conn.createStatement();
        s1.executeUpdate("INSERT INTO inhalers (inhaler_type,expiry_date,quantity) VALUES ('"+this.inhaler_name+"','"+this.inhaler_expiry+"',"+this.puffs_left+")");
        s1.close();
    }

    public Boolean use_count() throws SQLException, ClassNotFoundException {
        /* The aim of this function is to count how many times the user uses their inhaler in a week
         will return false if less than 3 times, and return true if more than 3 times a week
         */
        // Retrieving the data values
        Class.forName("org.postgresql.Driver");
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("select * from use_data");

        System.out.println("Here we are comparing the dates of recorded uses");
        usage_count = 0;
        while (rs.next()) {
            // Checking if date in table is 7 days before date it is currently
            if (rs.getTimestamp("use_date").toLocalDateTime().isBefore(LocalDateTime.now().minus(Duration.ofDays(7))) == false) {
                usage_count = usage_count + 1;
            }
        }
        System.out.println("The final usage count is:"+usage_count);
        if (usage_count >= 3) {
            return true;
        } else {

            return false;
        }

    }
    public Boolean quantity_warning() throws ClassNotFoundException, SQLException {
        /* This function returns true if the quantity of an inhaler in the inhaler table runs below 25 puffs left*/
        // Retrieving the data values
        Class.forName("org.postgresql.Driver");
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("select * from use_data");
        Boolean warning = false;
        while(rs.next()){
            if(rs.getInt("quantity") < 25){
                warning = true;
            }
        }
        return warning;
    }

    public void use_input(int puffs_taken) throws Exception {
        /* The aim of this function is to input the uses of the user
        The table written in Postgresql has the following inputs:
        no_of_puffs - which defaults to 1
        use_date - which takes the current time in which use_input is called

        If function is called again within 30 minutes of its last call, it will add the puffs_taken of the current input
        to the last input
         */
        this.puffs_taken = puffs_taken;


        /* Formatting the database correctly
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);*/

        /* Need to change the date format, so we can group uses together */
        // Change to correct format
        LocalDateTime current_time = LocalDateTime.now();
        LocalDateTime check_time = current_time.minusMinutes(30);
        System.out.println(current_time);


        try {
            // Registering the driver
            Class.forName("org.postgresql.Driver");

            Statement s = conn.createStatement();

            // Updating the quantity on the inhaler table
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
            //Rewrite of retrieving values
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
                    if (last_date.isBefore(temp_time.minus(Duration.ofMinutes(30)))==false) {
                        // If it is, we now combine this input and the last input
                        System.out.println("We are combining the inputs");
                        System.out.println(last_id);

                        int current_puffs = rs.getInt("no_of_puffs");
                        int new_puff_no = current_puffs + this.puffs_taken;

                    /* Changing the number of puffs taken in the table for the current use - Use executeUpdate
                    as we are not expecting to have any values returned
                     */
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

/* Creating a tables - input this into your query console

        create table inhalers(
        id serial primary key,
        inhaler_type varchar(128) NOT NULL,
        expiry_date varchar(128) NOT NULL,
        quantity int NOT NULL
        );

        create table use_data(
        id serial primary key,
        use_date DATE NOT NULL DEFAULT CURRENT_DATE,
        no_of_puffs int NOT NULL
        );

   /* Table reset
   TRUNCATE TABLE use_data;

insert into use_data(use_date,no_of_puffs) values(CURRENT_DATE - integer '9',3);
insert into use_data(use_date,no_of_puffs) values(CURRENT_DATE - integer '7',2);
insert into use_data(use_date,no_of_puffs) values(CURRENT_DATE - integer '5',1);

TRUNCATE TABLE inhalers;
 */


