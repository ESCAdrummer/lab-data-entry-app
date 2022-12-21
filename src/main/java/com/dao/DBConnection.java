package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection DBConnectionSingleton;
    private static boolean initialized = false;
    private static final String dbUser = "webapps";  //created user in local machine for dbAccess in MySQL
    private static final String dbPassword = "webapps"; //password for webapps user
    private static final String stringPath = "jdbc:mysql://localhost:3306/icequimica"; //MySQL address in my local machine.

    //private constructor for singleton design pattern.
    private DBConnection() {

    }

    public static synchronized Connection getInstance() {
        if (initialized) return DBConnectionSingleton;
        DBConnectionSingleton = getConnectionToDatabase();
        initialized = true;
        return DBConnectionSingleton;
    }
    private static Connection getConnectionToDatabase() {
        Connection connection = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver found.");

            connection = DriverManager.getConnection(stringPath, dbUser, dbPassword);
        }
        catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not present.");
            e.printStackTrace();
        }
        catch (SQLException e) {
            System.out.println("Connection to database not established.");
            e.printStackTrace();
        }
        if (connection != null) {
            System.out.println("Connection successful.");
        }

        return connection;

    }
}