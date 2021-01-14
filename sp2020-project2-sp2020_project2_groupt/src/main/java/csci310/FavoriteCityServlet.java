package csci310;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/FavoriteCityServlet")
public class FavoriteCityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cityName = (String)request.getParameter("cityName");
		String source = (String) request.getParameter("favoritesSource"); 
		System.out.println(cityName);
		System.out.println(source);

		HttpSession session = request.getSession();
		ArrayList<String> favorites = (ArrayList<String>) session.getAttribute("favoriteCities"); 
		if (favorites == null) { 
			favorites = new ArrayList<String>();
			favorites.add(cityName);
			System.out.println("Created new favorites list with " + cityName);
		}
		
		else if (favorites.contains(cityName)) { 
			// remove it 
			favorites.remove(cityName);
			System.out.println("Removed" + cityName + "from  favorites. Favorites now has " + favorites.size() + " cities.");
		} else { 
			// add it to the list 
			favorites.add(cityName);
			System.out.println("Added " + cityName + "to  favorites. Favorites now has " + favorites.size() + " cities.");
		}
		// dispatch back to where it came from 
		session.setAttribute("favoriteCities", favorites);
		
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(source);
		
		try {
			dispatch.forward(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
