package csci310;
import javax.servlet.http.HttpServlet;  
import java.io.IOException;  
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  

public class LoginServlet extends HttpServlet {  
    /**
     * hu
     * hello
	 * Logs in user and authenticates via sending token
	 */
	public static final String CREDENTIALS_STRING = "jdbc:mysql://130.211.227.217:3306/Project2";
	static Connection connection = null;
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)  
                    throws ServletException, IOException {  
        response.setContentType("text/html");  
        
        PrintWriter out=response.getWriter();  
        request.getRequestDispatcher("login.jsp").include(request, response);  
       
        String email=request.getParameter("email");  
        String password=request.getParameter("password");  
        long timeNow = System.currentTimeMillis();
        String time=Long. toString(timeNow);

        try{  
        	
    		Class.forName("com.mysql.jdbc.Driver");
    		String url= String.format(CREDENTIALS_STRING);
    		connection = DriverManager.getConnection(url, "groupt", "project2"); // groupt/project2 are the username/pass to connect to the sql instance
    		PreparedStatement ps=connection.prepareStatement(  
    				"select * from User where username =? and password =?");  
    		Boolean successfulLogin=false;
    		ps.setString(1,email);  
    		ps.setString(2,password);  
    		ResultSet rs=ps.executeQuery();  
    		successfulLogin=rs.next();  
    		
    		if(successfulLogin) {
          	String passcode=TOTP.generateTOTP("42934723470474", time, "6");
          	System.out.println(passcode);
    			ps=connection.prepareStatement(  
        				"Update User \n" + 
        				"Set totp = ?\n" + 
        				"Where username = ?\n" + 
        				"And password = ?");  
        		ps.setString(1,passcode);  
        		ps.setString(2,email); 
        		ps.setString(3,password);  
        		ps.executeUpdate();  
        		Boolean successfulUpdate=rs.next();  
        		if(!successfulUpdate)
        			SendEmail.Send("weatherplanningauthentication@gmail.com", email, passcode );
        		
       	     	HttpSession session=request.getSession();  
       	     	session.setAttribute("email",email);  
       	        RequestDispatcher dispatcher = request.getRequestDispatcher("authentication.jsp");
       	        dispatcher.forward(request, response);
    		}

        }catch(Exception e1){System.out.println(e1);}  
                
//        out.print("Sorry, email or password error!");  
//        request.getRequestDispatcher("login.html").include(request, response); 
  
        out.close();  
    }  
} 