import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Inhaler {

    public String inhaler_expiry;
    public String inhaler_name;
    public int puffs_left;

    public Inhaler(String inhaler_type, String expiry, int quantity) {
        this.inhaler_name = inhaler_type;
        this.inhaler_expiry = expiry;
        this.puffs_left = quantity;


    }

    public void add_inhaler(){
        String dbUrl = "jdbc:postgresql://localhost:5432/postgres";

        try {
            // Registering the driver
            Class.forName("org.postgresql.Driver");
            System.out.println("Connecting to the database");
            Connection conn = DriverManager.getConnection(dbUrl, "postgres", "airsense");
            System.out.println("You are now connected to the database");

            System.out.println("Inserting data into inhaler");
            Statement s = conn.createStatement();
            String sqlStr = "INSERT INTO inhalers (inhaler_type,expiry_date,quantity) VALUES ('reliver2','0202',5)";
            System.out.println(sqlStr);

            s.executeUpdate(sqlStr);

            sqlStr = "INSERT INTO inhalers (inhaler_type,expiry_date,quantity) VALUES ('reliver2','0202',5);";
            s.executeUpdate(sqlStr);
// This is not working so sad
            conn.commit();
            s.close();
            conn.close();
        } catch (Exception e) {
        }

    }



}
