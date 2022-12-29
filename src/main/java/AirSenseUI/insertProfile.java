package AirSenseUI;

import java.sql.*;

/**
 * JdbcInsert1.java - Demonstrates how to INSERT data into an SQL
 *                    database using Java JDBC.
 */
class JdbcInsert1 {

    public static void main (String[] args) {
        try {
            String url = "jdbc:postgresql://localhost:5433/postgres";
            Connection conn = DriverManager.getConnection(url,"postgres","AirSense");
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO users_ " +
                    "VALUES ('MyName', 'reliever', '20', 'female', 'Harrow')");


            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }
}

