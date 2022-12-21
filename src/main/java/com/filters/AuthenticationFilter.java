package com.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(servletNames = {"home", "new-test", "products-admin", "users-admin",
                            "edit-test", "delete-test", "view-test"})
public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //cast the request and response into the Http specific classes
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //Get the session from the request object
        HttpSession session = request.getSession();

        //check if user attribute does not exist (indicate not logged in)
        if (session.getAttribute("username") == null) {
            //redirect to login page if not logged in. Sending error message.
            request.setAttribute("message", "Please login before accessing.");
            request.getRequestDispatcher("/index.jsp").forward(request,response);
        }

        //sending back to servlet.
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
