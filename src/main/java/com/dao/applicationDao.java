package com.dao;

import com.beans.products;
import com.beans.test;
import com.beans.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class applicationDao {
    public void createUser(user userObject) {

        try {
            //Connect to DB
            Connection connection = DBConnection.getInstance();

            //Write query
            String createQuery = "INSERT INTO user (`firstname`,`lastname`,`username`,`password`) VALUES (?,?,?,?)";

            //Set parameters with PreparedStatement
            PreparedStatement statement = connection.prepareStatement(createQuery);
            statement.setString(1, userObject.getFirstName());
            statement.setString(2, userObject.getLastName());
            statement.setString(3, userObject.getUsername());
            statement.setString(4, userObject.getPassword());

            //Execute the Statement
            statement.executeUpdate();

            //close the connection
            statement.close();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean verifyUserExists(String username) {
        boolean existingUser = false;

        try {
            //connect to database
            Connection connection = DBConnection.getInstance();

            //query the database with username and password
            String userQuery = "SELECT * FROM user WHERE username=?";

            //prepare statement
            PreparedStatement statement = connection.prepareStatement(userQuery);
            statement.setString(1,username);

            //execute query
            ResultSet set = statement.executeQuery();

            //validation of return
            while (set.next()){
                existingUser=true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return existingUser;
    }

    public boolean validateUser(String username, String password) {

        boolean isValidUser = false;

        try {
            //connect to database
            Connection connection = DBConnection.getInstance();

            //query the database with username and password
            String userQuery = "SELECT * FROM user WHERE username=? AND password=?";

            //prepare statement
            PreparedStatement statement = connection.prepareStatement(userQuery);
            statement.setString(1, username);
            statement.setString(2, password);

            //execute query
            ResultSet set = statement.executeQuery();

            //validation of return
            while (set.next()){
                isValidUser=true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return isValidUser;
    }

    public user retrieveUser(String username) {

        user userObject = new user();

        try {
        //connect to database
        Connection connection = DBConnection.getInstance();

        //create query
        String query = "SELECT * FROM user WHERE username=?";

        //prepare query
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);

        //execute query
        ResultSet set = statement.executeQuery();

        //fill the returned result in a bean
        while (set.next()){
            userObject.setFirstName(set.getString("firstName"));
            userObject.setLastName(set.getString("lastName"));
            userObject.setUsername(set.getString("username"));
            userObject.setPassword("");
            userObject.setAdmin(set.getBoolean("isAdmin"));
        }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return userObject;
    }

    public List<products> retrieveProductList() {
        products product;
        List<products> productsList = new ArrayList<>();

        try {
            //connect to database
            Connection connection = DBConnection.getInstance();

            //create query
            String query = "SELECT * FROM products";

            //prepare query
            PreparedStatement statement = connection.prepareStatement(query);

            //execute query
            ResultSet set = statement.executeQuery();

            //fill the returned result in a bean
            while (set.next()){
                product = new products();
                product.setId(set.getInt("id"));
                product.setName(set.getString("name"));
                productsList.add(product);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return productsList;
    }

    public products retrieveProductById(int productSelection) {

        products product = new products();

        try {
            //connect to database
            Connection connection = DBConnection.getInstance();

            //create query
            String query = "SELECT * FROM products WHERE id=?";

            //prepare query
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, productSelection);

            //execute query
            ResultSet set = statement.executeQuery();

            //fill the returned result in a bean
            while (set.next()){
                product.setId(set.getInt("id"));
                product.setName(set.getString("name"));
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public List<test> retrieveTestListByProductId(int productSelection) {
        test testEntry;
        List<test> testsList = new ArrayList<>();

        try {
            //connect to database
            Connection connection = DBConnection.getInstance();

            //create query
            String query =  "select t.id, t.name, t.units from qualityset qs" +
                            " join test t on qs.test_id = t.id" +
                            " join products p on qs.products_id = p.id where p.id = ? order by name;";

            //prepare query
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, productSelection);

            //execute query
            ResultSet set = statement.executeQuery();

            //fill the returned result in a bean
            while (set.next()){
                testEntry = new test();
                testEntry.setId(set.getInt("id"));
                testEntry.setName(set.getString("name"));
                testEntry.setUnits(set.getString("units"));
                testsList.add(testEntry);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return testsList;

    }
}