package com.example.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.model.DBTables;
import com.example.model.JDBCUtilities;

public class Search extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String searchString = request.getParameter("search");
		//System.out.println(searchString);
		JDBCUtilities util = (JDBCUtilities) getServletContext().getAttribute("DBUtils");
    	try {
			Connection conn = util.getConnection();
			ResultSet rs = DBTables.getMediaByDescription(conn, searchString);
			request.setAttribute("MediaSet", rs);
			RequestDispatcher view = request.getRequestDispatcher("Home.jsp");
			view.forward(request, response);
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
    	
		
	}
}
