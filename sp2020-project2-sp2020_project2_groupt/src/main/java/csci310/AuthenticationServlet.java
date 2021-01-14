package csci310;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;  
import java.sql.*;
import javax.servlet.http.HttpSession;  

public class AuthenticationServlet extends HttpServlet {  
	public static final String CREDENTIALS_STRING = "jdbc:mysql://130.211.227.217:3306/Project2";
	static Connection connection = null;
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)  
                    throws ServletException, IOException {  
        response.setContentType("text/html");  
        PrintWriter out = response.getWriter();  
        request.getRequestDispatcher("authentication.jsp").include(request, response);  
	    HttpSession session=request.getSession();  
        String passcode = request.getParameter("passcode");  
        String username =(String)session.getAttribute("email");        
        String destpage = "";

       			try{
       			Class.forName("com.mysql.jdbc.Driver");
       	    	String url= String.format(CREDENTIALS_STRING);
       	    	connection = DriverManager.getConnection(url, "groupt", "project2"); // groupt/project2 are the username/pass to connect to the sql instance
       			PreparedStatement pS2 = connection.prepareStatement("SELECT * FROM User WHERE username = ? and totp = ?");
     			pS2.setString(1, username);
     			pS2.setString(2, passcode);
     			ResultSet rS2 = pS2.executeQuery();
     			boolean checkpasscode = rS2.next();

     			// if there is anything at all, size will increment and there should only be one user by that name
     			if (checkpasscode) {
     				System.out.println("Correct Passcode");
     				destpage = "main-search.jsp";
     			} else { // syso username and password
		    	     session.setAttribute("email",username);  
		    	     session.setAttribute("message", "***Incorrect OTP Passcode, Please Try Again!***");
		    	     destpage = "login.jsp";
		    	     
     			}
     		}
        catch(Exception e) {
			e.printStackTrace();
		}
       			
       	response.sendRedirect(request.getContextPath() + destpage);
        
        
	//if login successful generate 2fa totp
	//store in db
	//send via email
	//check if user inputs same number as in email
}
}
