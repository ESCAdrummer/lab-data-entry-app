package com.icequimica;

import com.beans.products;
import com.beans.test;
import com.beans.testResults;
import com.beans.user;
import com.dao.applicationDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class testServlets extends HttpServlet {

    @WebServlet(name = "new-test", value = "/new-test")
    public static class createTest extends HttpServlet{
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

            applicationDao dao = new applicationDao();
            List<products> productList = dao.getProductList();
            request.setAttribute("productList", productList);

            request.getRequestDispatcher("createTest.jsp").forward(request,response);

        }

        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            //retrieve post submit with chosen product id
            int productSelection = Integer.parseInt(request.getParameter("selection"));

            if (productSelection != 0) {
                //retrieve product from database and testList for that product
                applicationDao dao = new applicationDao();
                products product = dao.getProductById(productSelection);
                List<test> testList = dao.getTestListByProductId(productSelection);

                //set both items as attributes
                request.setAttribute("product", product);
                request.setAttribute("testList", testList);
            }

            doGet(request, response);
        }
    }

    @WebServlet(name = "view-tests", value = "/view-tests")
    public static class viewTests extends HttpServlet{
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

            applicationDao dao = new applicationDao();

            //getting filter criteria from get request
            String filter = request.getParameter("filter");

            if (filter!=null) {

                List<String> filterData;
                List<testResults> testResultSet = new ArrayList<>();

                //switch to filter according to filter criteria (date has no filtering criteria, only selection of range).
            switch (filter) {
                case "product":
                    //retrieve list of products with test results
                    filterData = dao.getProductsWithTestResults();

                    break;
                case "user":
                    //retrieve list of users with test results
                    filterData = dao.getUsersWithTestResults();
                    break;
                default:
                    //if any other info comes in get request (or null) set to null
                    filterData = null;
            }

                //sending retrieved info to display on jsp
                request.setAttribute("filter", filter);
                request.setAttribute("filterData", filterData);
                request.setAttribute("testResultSet", testResultSet);

            }

            request.getRequestDispatcher("viewTests.jsp").forward(request,response);

        }

        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            //retrieve parameters from JSP
            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");
            String productSelection = request.getParameter("productSelection");
            String userSelection = request.getParameter("userSelection");

            applicationDao dao = new applicationDao();

            List<testResults> testResultsSet = new ArrayList<>();
            String displayMessage = "";

            //case view by date
            if (fromDate!=null && toDate!=null) {
                if (!fromDate.equals("") && !toDate.equals("")) {
                    request.setAttribute("fromDate", fromDate);
                    request.setAttribute("toDate", toDate);

                    if (toDate.compareTo(fromDate) < 0) {
                        displayMessage = "'Date-from' must be before 'date-to', please enter correct dates.";
                    } else {
                        displayMessage= "Showing results for the specified dates: ";

                        testResultsSet = dao.getTestResultsByDateRange(fromDate, toDate);

                    }
                    request.setAttribute("displayMessage", displayMessage);
                }
            }

            //case view by product
            if (productSelection!=null) {
                if (!productSelection.equals("none")) {
                    request.setAttribute("displayByProduct", productSelection);

                    displayMessage = "Showing results for product: ";
                    request.setAttribute("displayMessage", displayMessage);

                    testResultsSet = dao.getTestResultsByProduct(productSelection);
                }
            }

            //case view by user
            if (userSelection!=null) {
                if (!userSelection.equals("none")) {
                    request.setAttribute("displayByUser", userSelection);

                    displayMessage = "Showing results for user: ";
                    request.setAttribute("displayMessage", displayMessage);

                    testResultsSet = dao.getTestResultsByUser(userSelection);

                }
            }
            request.setAttribute("testResultsSet",testResultsSet);
            doGet(request,response);
        }
    }

    @WebServlet(name = "tests-admin", value = "/tests-admin")
    public static class testsMaintenance extends HttpServlet{
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

            //retrieve user from current session and load in a bean
            applicationDao dao = new applicationDao();
            user userObject = dao.getUser((String) request.getSession().getAttribute("username"));

            //If user has access then send to the jsp
            if (userObject.isAdmin())  {
                request.setAttribute("admin", userObject.isAdmin());
            }

            request.setAttribute("user", userObject);
            request.getRequestDispatcher("/tests/testsmain.jsp").forward(request,response);


        }

        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doGet(request, response);
        }
    }

    @WebServlet(name = "submit-results", value = "/submit-results")
    public static class submitResults extends HttpServlet{
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

            applicationDao dao = new applicationDao();
            List<products> productList = dao.getProductList();
            request.setAttribute("productList", productList);

            request.getRequestDispatcher("createTest.jsp").forward(request,response);

        }

        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            applicationDao dao = new applicationDao();

            //retrieve product
            products product = dao.getProductById(Integer.parseInt(request.getParameter("productId")));

            //retrieve user
            user labTechnician = dao.getUser(request.getParameter("username"));

            //get list of tests with id for that product
            List<test> testList = dao.getTestListByProductId(product.getId());

            //construct hashMap with all testId#result as per test list
            Map<Integer,String> resultSet = new HashMap<>();

            //fill with values from JSP and add to list
            String variableName = "";
            List<Integer> testIdList = new ArrayList<>();
            for (test testEntry : testList) {
                variableName = "testId" + testEntry.getId() + "result";
                resultSet.put(testEntry.getId(), request.getParameter(variableName));
                testIdList.add(testEntry.getId());
            }

            //send to database
            for (Integer testId : testIdList) {
                dao.setTestResult(labTechnician.getId(), product.getId(), testId, Double.parseDouble(resultSet.get(testId)));
            }

            //Submission message
            request.setAttribute("submissionMessage", "Test results submitted successfully.");

            doGet(request, response);
        }
    }
}