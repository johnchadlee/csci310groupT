package csci310;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import csci310.LikesConnector;

public class AddLikesServlet extends HttpServlet 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NullPointerException {  
		final String paramNames[] = {"cityname", "sourcePage"};
		ArrayList<String> params = new ArrayList<String>();

		for(String param : paramNames) {
			params.add(request.getParameter(param).trim());
			System.out.println(request.getParameter(param));
		}
		
		String city=params.get(0);
		String sourcePage=params.get(1);

		LikesConnector lc = new LikesConnector();
		lc.addLikes(city);
		lc.updateArrayLists();
	
	     	HttpSession session=request.getSession();  
	     	session.setAttribute("likes",lc.getLikes(city));  
	        RequestDispatcher dispatcher = request.getRequestDispatcher(sourcePage);
	        dispatcher.forward(request, response);

	}
	
	
	
	
	
	
	
	
	
	
	
	
}
