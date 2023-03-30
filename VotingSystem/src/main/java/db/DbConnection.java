package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    public Connection conn;
    public void dbConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
             conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/votingDb", "root", "ArtemMac792++"
            );
            System.out.println("You are connected");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void dbClose() throws SQLException {
        conn.close();
        if (conn == null){
            System.out.println("Connection is done");
        }
    }
}