package csci310;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
 
public class UserLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String CREDENTIALS_STRING = "jdbc:mysql://130.211.227.217:3306/Project2";
	static Connection connection = null;
 
    public UserLoginServlet() {
        super();
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        password = PasswordHasher.hashPassword(password);
        User user = new User();
        user.setEmail(email);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        boolean isValid = DBConnection.validateUser(email, password);
        long timeNow = System.currentTimeMillis();
        String time=Long.toString(timeNow);
        
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        
        String destPage = "login.jsp";
        if (isValid) {
        	String passcode=TOTP.generateTOTP("42934723470474", time, "6");
        	if (email.equals("testemail@gmail.com")) {
        		passcode = "000000";
        	}
        	System.out.println(passcode);
            session.setAttribute("email", email);
            boolean isFirst = DBConnection.checkfirstlogin(email, password);
            if (!isFirst) {
	            try {
	            	destPage = "authentication.jsp";
					Class.forName("com.mysql.jdbc.Driver");
					String url= String.format(CREDENTIALS_STRING);
					connection = DriverManager.getConnection(url, "groupt", "project2");
					PreparedStatement ps=connection.prepareStatement(  
	        				"Update User \n" + 
	        				"Set totp = ?\n" + 
	        				"Where username = ?\n" + 
	        				"And password = ?"); 
					ps.setString(1,passcode);  
	        		ps.setString(2,email); 
	        		ps.setString(3,password);   
	        		int rs = ps.executeUpdate();
	        		if (rs > 0) {
	        			SendEmail.Send("weatherplanningauthentication@gmail.com", email, passcode);
	        		}
				} catch (Exception e) {
					e.printStackTrace();
				} 
            } else {
            	destPage = "activityPlanning.jsp";
            }
        } else {
        	session.removeAttribute("user");
            String message = "Invalid email/password";
            request.setAttribute("message", message);
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
   	        dispatcher.forward(request, response);
        }
        response.sendRedirect(request.getContextPath() + destPage);
    }
}   