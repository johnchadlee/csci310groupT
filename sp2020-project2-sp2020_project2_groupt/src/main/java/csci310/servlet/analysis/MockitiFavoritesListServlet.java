package csci310.servlet.analysis;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




public class MockitiFavoritesListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    
    	resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    	
	    String response = "[{\"id\": \"1001\",\"city\": \"Bangkok\", \"country\": \"Thailand\"}, {\"id\": \"1002\",\"city\": \"Cape Town\",\"country\": \"South Africa\"},{\"id\": \"1003\",\"city\": \"Los Angeles\",\"country\": \"United States\"},{\"id\": \"1004\",\"city\": \"Seoul\",\"country\": \"South Korea\"},{\"id\": \"1005\",\"city\": \"Tokyo\",\"country\": \"Japan\"}]";
	    
 	    resp.getWriter().write(response.toString());
 	    
    }
}
