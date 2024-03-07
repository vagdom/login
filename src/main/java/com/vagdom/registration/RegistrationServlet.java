package com.vagdom.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		//out.print("It Works !");
		String uname = request.getParameter("name");
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		String re_pass = request.getParameter("re_pass");
		String umobile = request.getParameter("contact");
		
		RequestDispatcher dispatcher = null;
		Connection con = null;
		//out.print(uname + ", " + email + ", " + pass + ", " + re_pass+ ", " + umobile);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/youtube?useSSL=false","root","password");
            PreparedStatement pst = con.prepareStatement("INSERT INTO USERS(uname, upwd, uemail, umobile) VALUES (?, ?, ?, ?);");
            pst.setString(1, uname);
            pst.setString(2, pass);
            pst.setString(3, email);
            pst.setString(4, umobile);
            
            int rowCount = pst.executeUpdate();
            dispatcher = request.getRequestDispatcher("registration.jsp");
            
            if (rowCount > 0) {
            	request.setAttribute("status", "success");
            } else {
            	request.setAttribute("status", "failed");
            }
            
            dispatcher.forward(request, response);
            
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}

}
