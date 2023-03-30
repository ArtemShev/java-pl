package Controller;

import Db.DbConnection;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VotingSystem {

    public static void main(String[] args) {



    }





    public void vote (){

    }
    public void addUser(String name, String login, String password) throws SQLException {
       try{ DbConnection connection = new DbConnection();
        connection.dbConnection();
        String query = "INSERT INTO users (name, login, password)  VALUES ('" +name+ "', '"+login+"', '"+password+"');";
        PreparedStatement stmt = connection.conn.prepareStatement(query);
        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Вход выполнен");
        connection.dbClose();
    }catch(SQLException se) {
           JOptionPane.showMessageDialog(null, "Вход  не выполнен, логин занят ");
    }
    }

    public boolean enter(String login, String password) throws SQLException {
        try{
            DbConnection connection = new DbConnection();
            connection.dbConnection();

            String query = "SELECT * from users WHERE (login = "+"'"+login+ "'"+") AND (password = "+"'"+password+ "'"+")";
            PreparedStatement stmt = connection.conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            connection.dbClose();
            return resultSet.next();
        }catch(SQLException se) {
            JOptionPane.showMessageDialog(null, "Вход  не выполнен ");
            return false;
        }
    }

}
