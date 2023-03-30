package model;

import db.DbConnection;

import java.sql.*;

public class User {

    int id;
    String name;

    String second_name;

    String patronymic;

    int age;
    int citizenship;

    String email;

    String login;
    String password;


    public int is_admin;

    boolean is_enter = false;
    boolean is_voted = false;

    public User() {

    }

//    public void setValues(String _name, String _login, String _password){
//        name = "'"+ _name + "'";
//        login  = "'"+ _login + "'";
//        password = _password;
//
//    }

    public boolean enter(String login, String password) throws SQLException {
        try {
            DbConnection connection = new DbConnection();
            connection.dbConnection();
            String query = "SELECT * from Users WHERE (login = " + "'" + login + "'" + ") AND (password = " + "'" + password + "'" + ")";
            PreparedStatement stmt = connection.conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            return false;
        }
    }

    public int admin(String login, String password) throws SQLException {
        try {
            DbConnection connection = new DbConnection();
            connection.dbConnection();
            String query = "SELECT is_admin from Users WHERE (login = " + "'" + login + "'" + ") AND (password = " + "'" + password + "'" + ")";
            PreparedStatement stmt = connection.conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                is_admin = resultSet.getInt("is_admin");
                System.out.println(is_admin);
            }
            return is_admin;
        } catch (SQLException ex) {
            System.out.println(ex);
            return 0;

        }
    }

    public void addUser(String _first_name, String _second_name, String _patronymic, String _email, String _login, String _password, int _citizenship, int _age, int _is_admin) throws SQLException {
        try {
            DbConnection connection = new DbConnection();
            connection.dbConnection();
            String query = " insert into Users (first_name,second_name,patronymic,email, login, password, citizenship, age, is_admin)"
                    + " values ('" + _first_name + "','" + _second_name + "','" + _patronymic + "','" + _email + "','" + _login + "','" + _password + "'," + _citizenship + "," + _age + "," + _is_admin + ")";

            PreparedStatement preparedStmt = connection.conn.prepareStatement(query);
//            preparedStmt.setString(1, _first_name);
//            preparedStmt.setString(2, _second_name);
//            preparedStmt.setString(3, _patronymic);
//            preparedStmt.setString(4, _email);
//            preparedStmt.setString(5, _login);
//            preparedStmt.setString(6, _password);
//            preparedStmt.setInt(7, _citizenship);
//            preparedStmt.setInt(8, _age);
//            preparedStmt.setInt(9, _is_admin);
            preparedStmt.execute();

        } catch (SQLException se) {
            System.out.println(se);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return this.age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setIs_admin(int is_admin) {
        this.is_admin = is_admin;
    }

    public int getIs_admin() {
        return is_admin;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return this.login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setIs_enter(boolean enter) {
        this.is_enter = enter;
    }

    public boolean getIs_enter() {
        return this.is_enter;
    }

    public void setIs_voted(boolean voted) {
        this.is_voted = voted;
    }

    public boolean getIs_voted() {
        return this.is_voted;
    }

    public void setCitizenship(int citizenship) {
        this.citizenship = citizenship;
    }

    public int getCitizenship() {
        return citizenship;
    }
}
