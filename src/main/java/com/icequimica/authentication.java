package com.icequimica;

import com.beans.user;
import com.dao.applicationDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Sign up / Log in / Logout pages.
 **/

public class authentication extends HttpServlet {

    // Signup Servlet
    @WebServlet(name = "signup", value = "/signup")
    public static class signup extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            // Loads signup jsp file
            request.getRequestDispatcher("/signup.jsp").include(request, response);

        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            //filled bean with information from Post request
            user userObject = new user();
            userObject.setFirstName(request.getParameter("firstName"));
            userObject.setLastName(request.getParameter("lastName"));
            userObject.setUsername(request.getParameter("username"));
            userObject.setPassword(request.getParameter("password"));

            //Feedback message to user after submit.
            String message = "";

            //verification if username already present in the database.
            applicationDao dao = new applicationDao();
            if (dao.verifyUserExists(userObject.getUsername())) {
                message = "Username already exists. Please enter a different username.";
            }
            else {
                //called the dao method to create user
                dao.createUser(userObject);
                message = "User created successfully.";
            }

            //sending feedback message as attribute to JSP page.
            request.setAttribute("message",message);

            doGet(request, response);
        }
    }

    // Login Servlet
    @WebServlet(name = "login", value = "/login")
    public static class login extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // Loads index jsp file
            request.getRequestDispatcher("/index.jsp").forward(request, response);

        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            user userObject = new user();
            userObject.setUsername(request.getParameter("username"));
            userObject.setPassword(request.getParameter("password"));

            //call Dao to verify if user in DB
            applicationDao dao = new applicationDao();
            boolean isValidUser = dao.validateUser(userObject.getUsername(), userObject.getPassword());

            //check if user is valid
            if (isValidUser){
                //Set up HTTP session object
                HttpSession session = request.getSession();

                //Retrieve user information and set in a bean
                userObject = dao.getUser(userObject.getUsername());

                //Set user bean as an attribute in the session
                session.setAttribute("user", userObject);
                session.setAttribute("username", userObject.getUsername());

                //Load home page with session activity
                //request.setAttribute("user", userObject);
                request.getRequestDispatcher("/home.jsp").forward(request, response);
            }
            else {
                //redirect to login with error message
                request.setAttribute("message", "Invalid username and password combination");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        }
    }

    @WebServlet(name = "logout", value = "/logout")
    public static class Logout extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            //Obtain the session object (if current is existing)
            HttpSession session = request.getSession();

            //Close the session by invalidate
            session.invalidate();

            //Redirect to login page with a thank you message
            request.setAttribute("message","You logged out successfully. Thank you.");
            request.getRequestDispatcher("index.jsp").include(request, response);

        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doGet(request, response);
        }
    }
}