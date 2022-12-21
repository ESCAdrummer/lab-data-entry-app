package com.icequimica;

import com.beans.products;
import com.beans.test;
import com.beans.user;
import com.dao.applicationDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class testServlets extends HttpServlet {

    @WebServlet(name = "new-test", value = "/new-test")
    public static class createTest extends HttpServlet{
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

            applicationDao dao = new applicationDao();
            List<products> productList = dao.retrieveProductList();
            request.setAttribute("productList", productList);

            request.getRequestDispatcher("createTest.jsp").forward(request,response);

        }

        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            //retrieve post submit with chosen product id
            int productSelection = Integer.parseInt(request.getParameter("selection"));

            if (productSelection != 0) {
                //retrieve product from database and testList for that product
                applicationDao dao = new applicationDao();
                products product = dao.retrieveProductById(productSelection);
                List<test> testList = dao.retrieveTestListByProductId(productSelection);

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

            request.getRequestDispatcher("viewTests.jsp").forward(request,response);

        }

        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            doGet(request, response);
        }
    }

    @WebServlet(name = "tests-admin", value = "/tests-admin")
    public static class testsMaintenance extends HttpServlet{
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

            //retrieve user from current session and load in a bean
            applicationDao dao = new applicationDao();
            user userObject = dao.retrieveUser((String) request.getSession().getAttribute("username"));

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

}