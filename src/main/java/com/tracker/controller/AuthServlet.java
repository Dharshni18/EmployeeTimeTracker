package com.tracker.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.tracker.dao.UserDAO;
import com.tracker.dbconnection.DBConnection;
import com.tracker.model.User;

/**
 * Servlet implementation class AuthServlet
 */
public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        Connection connection = DBConnection.getConnection();
		userDAO = new UserDAO(connection);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            User user = userDAO.getUserByUsername(username,password);
            if (user != null && user.getRoleId()==1) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect("index.jsp");
            } else if(user!=null&&user.getRoleId() == 2){
            	HttpSession session = request.getSession();
                session.setAttribute("user", user);
            	response.sendRedirect("adminDashboard.jsp");
            }
            else {
                response.sendRedirect("login.jsp?error=Invalid+credentials");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

}
