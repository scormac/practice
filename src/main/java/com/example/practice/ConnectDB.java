package com.example.practice;

import java.sql.*;

public class ConnectDB {
    String url = "jdbc:mysql://localhost:3306/practice";
    String username = "root";
    String password = "root";

    public Connection getConnection(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
//            System.out.println("Connection Established successfully");
            return connection;
        }
        catch (SQLException e){
            System.out.println("Error: " + e);
            return null;
        }

    }

}