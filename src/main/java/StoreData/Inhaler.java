package StoreData;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;


public class Inhaler {

    public String inhaler_expiry;
    public String inhaler_name;
    public int puffs_left;
    public int puffs_taken;

    public boolean warning_outcome;



    public String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
    // NOTE!! Change the password based on what you set it yourself - I have not yet figured out how to store on Heroku
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
            String sqlStr = "INSERT INTO inhalers (inhaler_type,expiry_date,quantity) VALUES ("+this.inhaler_name+ ","
                    +this.inhaler_expiry+","+this.puffs_left+")";
            System.out.println(sqlStr);

            s.executeUpdate(sqlStr);

            conn.commit();
            s.close();
            conn.close();
        } catch (Exception e) {
        }

    }

    public Boolean use_count(){
        /* The aim of this function is to count how many times the user uses their inhaler in a week
         will return false if less than 3 times, and return true if more than 3 times a week
         */

        /*
            // Retrieving values
            System.out.println("Here we are comparing the dates of recorded uses");
            while (rs.next()) {
                 long c = (int) (todayDate.getTime() - rs.getDate("use_date").getTime());
                 long days = TimeUnit.MILLISECONDS.toDays(c);
                System.out.println(days);
                if (days < 7){
                    count = count + 1;
                }
            }*/

        return warning_outcome;
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
        int count = 0;

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

            // Selecting the latest use
            ResultSet rs = s.executeQuery("select * from use_data ORDER BY use_date DESC LIMIT 1");
            //Rewrite of retrieving values
            while (rs.next()) {
                System.out.println(rs.getTimestamp("use_date"));
                LocalDateTime last_date = rs.getTimestamp("use_date").toLocalDateTime();
                LocalDateTime temp_time = LocalDateTime.now();
                System.out.println("This is the last date:"+ last_date);
                System.out.println("This is the current date minus 30 mins:"+ temp_time.minus(Duration.ofMinutes(30)));

                int last_id = rs.getInt("id");

                /* Checking if the current input is within 30 minutes */
                System.out.println(last_date.isBefore(temp_time.minus(Duration.ofMinutes(30))));
                System.out.println("Here we are checking if we should group  the inputs together");
                if (last_date.isBefore(temp_time.minus(Duration.ofMinutes(30))) == false){
                    // If it is, we now combine this input and the last input
                    System.out.println("We are combining the inputs");
                    System.out.println(last_id);

                    int current_puffs = rs.getInt("no_of_puffs");
                    int new_puff_no =  current_puffs + this.puffs_taken;

                    /* Changing the number of puffs taken in the table for the current use - Use executeUpdate
                    as we are not expecting to have any values returned
                     */
                    s.executeUpdate("UPDATE use_data SET no_of_puffs = "+new_puff_no+" WHERE id = "+last_id+";");
                }
                else{
                    System.out.println("We are NOT combining the inputs");

                    // Inputting the current use
                    String sqlStr = "INSERT INTO use_data (no_of_puffs) VALUES ("+puffs_taken+")";
                    System.out.println(sqlStr);
                    s.executeUpdate(sqlStr);
                }
            }





            s.close();
            conn.close();


        } finally {
            if (count >= 3){
                System.out.println("Based on NHS guidance it is recommended that you see your doctor about your asthma");
            }
            else{
                System.out.println("Thank you for your input");
            }
        System.out.println("You have used your inhaler a total of " + count + " times this week");
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


