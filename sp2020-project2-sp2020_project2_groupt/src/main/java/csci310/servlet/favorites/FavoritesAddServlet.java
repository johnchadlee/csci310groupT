package csci310.servlet.favorites;

import com.google.gson.JsonObject;
import csci310.servlet.ServletUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class FavoritesAddServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        
    	final String paramNames[] = {"city", "country", "favoritesSource"};//, "favoritesSource"
		ArrayList<String> params = new ArrayList<String>();

		for(String param : paramNames) {
			params.add(req.getParameter(param));
		}
		
        try {
            JsonObject favEntry = new JsonObject();
            
            String city = params.get(0);
            String country = params.get(1);
            String sourcePage=params.get(2);
            
          
            if (city == null || country == null) {
                resp.setStatus(400); // Bad request
                return;
            }

            favEntry.addProperty("city", city);
            favEntry.addProperty("country", country);
            HashSet<JsonObject> favorites = (HashSet<JsonObject>) session.getAttribute("favorites");
            if (favorites == null) { // If session not yet initialized
                favorites = new HashSet<>();
                session.setAttribute("favorites", favorites);
            }
            favorites.add(favEntry);
            
   	        RequestDispatcher dispatcher = req.getRequestDispatcher(sourcePage);
   	        dispatcher.forward(req, resp);
   	        
            // Implicitly returns 200 status
        } catch (IllegalStateException | ClassCastException | NullPointerException e) {
            resp.setStatus(400);
        }
    }

}