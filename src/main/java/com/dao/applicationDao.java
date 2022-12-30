package com.dao;

import com.beans.products;
import com.beans.test;
import com.beans.testResults;
import com.beans.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public user getUser(String username) {

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
            userObject.setId(set.getInt("id"));
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

    public List<products> getProductList() {
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

    public products getProductById(int productSelection) {

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

    public List<test> getTestListByProductId(int productSelection) {
        test testEntry;
        List<test> testsList = new ArrayList<>();

        try {
            //connect to database
            Connection connection = DBConnection.getInstance();

            //create query
            String query =  "select t.id, t.name, t.units from qualitySet qs" +
                            " join test t on qs.test_id = t.id" +
                            " join products p on qs.products_id = p.id where p.id = ? order by name";

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

    public void setTestResult(int userId, int productId, Integer testId, Double value) {

        try {
            //Connect to DB
            Connection connection = DBConnection.getInstance();

            //Write query
            String createQuery = "insert into testResults (date,user_id,products_id,test_id,value) values (?,?,?,?,?)";

            //Set parameters with PreparedStatement
                //Setting current date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String currentDate = dateFormat.format(new Date());
            PreparedStatement statement = connection.prepareStatement(createQuery);
            statement.setString(1, currentDate);
            statement.setInt(2, userId);
            statement.setInt(3, productId);
            statement.setInt(4, testId);
            statement.setDouble(5, value);

            //Execute the Statement
            statement.executeUpdate();

            //close the connection
            statement.close();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<String> getProductsWithTestResults() {

        List<String> productList = new ArrayList<>();
        try {
            //connect to database
            Connection connection = DBConnection.getInstance();

            //create query
            String query =  "select name as productName from testResults TR " +
                    "join products P on TR.products_id = P.id " +
                    "group by productName order by productName";

            //prepare query
            PreparedStatement statement = connection.prepareStatement(query);

            //execute query
            ResultSet set = statement.executeQuery();

            //fill the returned result in a bean
            while (set.next()){
                productList.add(set.getString("productName"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<String> getUsersWithTestResults() {
        List<String> usersList = new ArrayList<>();

        try {
            //connect to database
            Connection connection = DBConnection.getInstance();

            //create query
            String query =  "select Concat(U.firstName,\" \", U.lastName) as fullName from testResults TR " +
                    "join user U on TR.user_id = U.id " +
                    "group by fullName order by fullName";

            //prepare query
            PreparedStatement statement = connection.prepareStatement(query);

            //execute query
            ResultSet set = statement.executeQuery();

            //fill the returned result in a bean
            while (set.next()){
                usersList.add(set.getString("fullName"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return usersList;
    }

    public List<testResults> getTestResultsByDateRange(String fromDate, String toDate) {
        testResults testResultsEntry;
        List<testResults> dateTestList = new ArrayList<>();

        try {
            //connect to database
            Connection connection = DBConnection.getInstance();

            //create query
            String query =  "select TR.id as id, TR.date as date, U.username as username, P.name as product, " +
                    "T.name as test, TR.value, T.units from testResults TR " +
                    "join user U on TR.user_id = U.id " +
                    "join products P on TR.products_id = P.id " +
                    "join test T on TR.test_id = T.id " +
                    "where date between ? and ? " +
                    "order by date desc, product asc, id asc, username asc;";

            //prepare query
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, fromDate);
            statement.setString(2, toDate);

            //execute query
            ResultSet set = statement.executeQuery();

            //fill the returned result in a bean
            while (set.next()){
                testResultsEntry = new testResults();
                testResultsEntry.setId(set.getInt("id"));
                testResultsEntry.setDate(set.getString("date"));
                testResultsEntry.setUsername(set.getString("username"));
                testResultsEntry.setProductName(set.getString("product"));
                testResultsEntry.setTestName(set.getString("test"));
                testResultsEntry.setValue(set.getDouble("value"));
                testResultsEntry.setUnits(set.getString("units"));
                dateTestList.add(testResultsEntry);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return dateTestList;
    }

    public List<testResults> getTestResultsByProduct(String productSelection) {
        List<testResults> productTestList = new ArrayList<>();
        testResults testResultsEntry;

        try {
            //connect to database
            Connection connection = DBConnection.getInstance();

            //create query
            String query =  "select TR.id as id, TR.date as date, U.username as username, P.name as product, " +
                    "T.name as test, TR.value, T.units from testResults TR " +
                    "join user U on TR.user_id = U.id " +
                    "join products P on TR.products_id = P.id " +
                    "join test T on TR.test_id = T.id " +
                    "where P.name = ? " +
                    "order by date desc, product asc, id asc, username asc";

            //prepare query
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, productSelection);

            //execute query
            ResultSet set = statement.executeQuery();

            //fill the returned result in a bean
            while (set.next()){
                testResultsEntry = new testResults();
                testResultsEntry.setId(set.getInt("id"));
                testResultsEntry.setDate(set.getString("date"));
                testResultsEntry.setUsername(set.getString("username"));
                testResultsEntry.setProductName(set.getString("product"));
                testResultsEntry.setTestName(set.getString("test"));
                testResultsEntry.setValue(set.getDouble("value"));
                testResultsEntry.setUnits(set.getString("units"));
                productTestList.add(testResultsEntry);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return productTestList;
    }

    public List<testResults> getTestResultsByUser(String userSelection) {
        List<testResults> userTestList = new ArrayList<>();
        testResults testResultsEntry;

        try {
            //connect to database
            Connection connection = DBConnection.getInstance();

            //create query
            String query =  "select TR.id as id, TR.date as date, U.username as username, P.name as product, " +
                    "T.name as test, TR.value, T.units from testResults TR " +
                    "join user U on TR.user_id = U.id " +
                    "join products P on TR.products_id = P.id " +
                    "join test T on TR.test_id = T.id " +
                    "where Concat(U.firstName,\" \", U.lastName) = ? " +
                    "order by date desc, product asc, id asc, username asc";

            //prepare query
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userSelection);

            //execute query
            ResultSet set = statement.executeQuery();

            //fill the returned result in a bean
            while (set.next()){
                testResultsEntry = new testResults();
                testResultsEntry.setId(set.getInt("id"));
                testResultsEntry.setDate(set.getString("date"));
                testResultsEntry.setUsername(set.getString("username"));
                testResultsEntry.setProductName(set.getString("product"));
                testResultsEntry.setTestName(set.getString("test"));
                testResultsEntry.setValue(set.getDouble("value"));
                testResultsEntry.setUnits(set.getString("units"));
                userTestList.add(testResultsEntry);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return userTestList;
    }
}