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
    public String dbUrl = "jdbc:postgresql://ec2-3-229-161-70.compute-1.amazonaws.com:5432/d4fdh0dvfc4v3r";
    public Connection conn = DriverManager.getConnection(dbUrl, "orexdsnjebnlrh", "684b6442280ff5e797fcf680b5be53d48a0df862c38694dd7d14c7b6c4c3ccd0");

    public Inhaler(String inhaler_name, String expiry, int quantity) throws SQLException, ClassNotFoundException {
        this.inhaler_name = inhaler_name;
        this.inhaler_expiry = expiry;
        this.puffs_left = quantity;
    }

    /**
     * The add_inhaler method adds an inhaler to the database.
     * @throws ClassNotFoundException
     * @throws SQLException
     *
     */
    public void add_inhaler() throws ClassNotFoundException, SQLException {
        // Registering the driver
        Class.forName("org.postgresql.Driver");
        Statement s1 = conn.createStatement();
        s1.executeUpdate("INSERT INTO inhalers (inhaler_type,expiry_date,quantity) VALUES ('"+this.inhaler_name+"','"+this.inhaler_expiry+"',"+this.puffs_left+")");
        s1.close();
    }

    /**
     *
     * @return false if the inhaler is used less than 3 times in a week, true if inhaler is used
     * 3 times or more a week.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Boolean use_count() throws SQLException, ClassNotFoundException {
        // Retrieving the data values
        Class.forName("org.postgresql.Driver");
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("select * from use_data");

        usage_count = 0;
        while (rs.next()) {
            // Checking if date in table is 7 days before date it is currently
            if (rs.getTimestamp("use_date").toLocalDateTime().isBefore(LocalDateTime.now().minus(Duration.ofDays(7))) == false) {
                usage_count = usage_count + 1;
            }
        }
        if (usage_count >= 3) {
            return true;
        } else {
            return false;
        }

    }

    /**
     *
     * @return true if the quantity of inhaler is below 25, false otherwise.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Boolean quantity_warning() throws ClassNotFoundException, SQLException {
        // Retrieving the data values
        Class.forName("org.postgresql.Driver");
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("select * from inhalers");
        Boolean warning = false;
        while(rs.next()){
            if(rs.getInt("quantity") < 25){
                warning = true;
            }
        }
        return warning;
    }

    /**
     * Inputs the inhaler uses of the user in the table. If the function is called again within
     * 30 minutes of its last call, it will add 1 to puffs_taken.
     * @param puffs_taken defaults to one
     * @throws Exception
     */
    public void use_input(int puffs_taken) throws Exception {
        this.puffs_taken = puffs_taken;

        LocalDateTime current_time = LocalDateTime.now();
        LocalDateTime check_time = current_time.minusMinutes(30);

        try {
            // Registering the driver
            Class.forName("org.postgresql.Driver");
            Statement s = conn.createStatement();

            // Retrieving the current quantity
            ResultSet rs1 = s.executeQuery("SELECT * FROM inhalers WHERE inhaler_type ='"+this.inhaler_name+"'");

            // Calculating new quantity
            rs1.next();
            int updated_puff_no = rs1.getInt("quantity")-this.puffs_taken;

            // Updating table with new quantity
            s.executeUpdate("UPDATE inhalers SET quantity = "+updated_puff_no+" WHERE inhaler_type ='"+this.inhaler_name+"'");

            // Selecting the latest use
            ResultSet rs = s.executeQuery("select * from use_data ORDER BY use_date DESC LIMIT 1");
            //Rewrite of retrieving values
            // Empty database check
            ResultSet rs_empty = s.executeQuery("SELECT count(*) FROM use_data");
            rs_empty.next();
            if (rs_empty.getInt(1) == 0){
                String sqlStr = "INSERT INTO use_data (no_of_puffs) VALUES ("+puffs_taken+")";
                s.executeUpdate(sqlStr);
            }
            else {
                while (rs.next()) {
                    LocalDateTime last_date = rs.getTimestamp("use_date").toLocalDateTime();
                    LocalDateTime temp_time = LocalDateTime.now();
                    int last_id = rs.getInt("id");

                    // Checking if the current input is within 30 minutes
                    if (last_date.isBefore(temp_time.minus(Duration.ofMinutes(30)))==false) {
                        // If it is, we now combine this input and the last input
                        int current_puffs = rs.getInt("no_of_puffs");
                        int new_puff_no = current_puffs + this.puffs_taken;
                        s.executeUpdate("UPDATE use_data SET no_of_puffs = " + new_puff_no + " WHERE id = " + last_id + ";");
                    } else {
                        // Inputting the current use
                        String sqlStr = "INSERT INTO use_data (no_of_puffs) VALUES (" + puffs_taken + ")";
                        s.executeUpdate(sqlStr);
                    }
                }
            }
            s.close();
        } catch(Exception e){

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


