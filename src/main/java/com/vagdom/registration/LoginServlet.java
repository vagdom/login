package com.vagdom.registration;

import java.io.IOException;


import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//PrintWriter out = response.getWriter();
		//out.print("Inside LoginServLet doPost");
		String uemail = request.getParameter("email");
		String upwd = request.getParameter("password");
		HttpSession session = request.getSession();
		
		
		//out.print("LoginServet - Email: " +  uemail + " Password:  " + upwd);
		
		RequestDispatcher dispatcher = null;
		Connection con = null;
		
		if (uemail == null || uemail.equals("")) {
			request.setAttribute("status", "invalidEmail");
        	dispatcher = request.getRequestDispatcher("login.jsp");
        	dispatcher.forward(request, response);
		}
		
		if (upwd == null || upwd.equals("")) {
			request.setAttribute("status", "invalidPassword");
        	dispatcher = request.getRequestDispatcher("login.jsp");
        	dispatcher.forward(request, response);
		}
		
		try {
			//System.out.println("Inside Try...");
			Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/youtube?useSSL=false", "root", "password");
            PreparedStatement pst = con.prepareStatement("SELECT * FROM users WHERE uemail = ? AND upwd = ?");
            pst.setString(1, uemail);
            pst.setString(2, upwd);            
            //System.out.println("Inside Try #2...");
            ResultSet rs = pst.executeQuery();
            //System.out.println("Inside Try #3...");           
            if (rs.next()) {
            	//System.out.println("Going to Index");
            	session.setAttribute("name", rs.getString("uname"));
            	dispatcher = request.getRequestDispatcher("index.jsp");
            } else {
            	//System.out.println("Going to login");
            	request.setAttribute("status", "failed");
            	dispatcher = request.getRequestDispatcher("login.jsp");
            }
            
            dispatcher.forward(request, response);
            
		} catch (Exception e) {
			System.out.println("Printing e Stack...");
			e.printStackTrace();
		}

	}
}