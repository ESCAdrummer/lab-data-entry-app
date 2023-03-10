package com.icequimica;

import java.io.*;

import com.beans.user;
import com.dao.applicationDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "users-admin", value = "/users-admin")
public class userMaintenance extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //retrieve user from current session and load in a bean
        applicationDao dao = new applicationDao();
        user userObject = dao.getUser((String) request.getSession().getAttribute("username"));

        //If user has access then send to the jsp
        if (userObject.isAdmin())  {
            request.setAttribute("admin", userObject.isAdmin());
        }

        request.setAttribute("user", userObject);
        request.getRequestDispatcher("/users/usersmain.jsp").forward(request,response);


    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}