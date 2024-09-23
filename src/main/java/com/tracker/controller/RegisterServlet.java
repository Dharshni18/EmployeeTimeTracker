package com.tracker.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.tracker.dao.UserDAO;
import com.tracker.dbconnection.DBConnection;
import com.tracker.model.User;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
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

	        User user = new User();
	        user.setUsername(username);
	        user.setPassword(password);
	        user.setRoleId(1); // Assuming role_id 1 is for Associates

	        try {
	            userDAO.addUser(user);
	            response.sendRedirect("login.jsp");
	        } catch (SQLException e) {
	        	 response.sendRedirect("register.jsp?error=username not available");
	            
	           
	        }
	    }

}
