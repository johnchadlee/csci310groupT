package csci310.servlet.favorites;

import com.google.gson.JsonObject;
import csci310.servlet.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;

public class FavoritesRemoveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // handle POST request
        HttpSession session = req.getSession();
        try {
            // handle POST request
            JsonObject postBody = ServletUtils.readPostBodyAsJson(req.getReader());

            JsonObject favEntry = new JsonObject();
            String city = postBody.getAsJsonPrimitive("city").getAsString();
            String country = postBody.getAsJsonPrimitive("country").getAsString();
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
            favorites.remove(favEntry);
            // Implicitly returns 200 status
        } catch (IllegalStateException | ClassCastException | NullPointerException e) {
            resp.setStatus(400);
        }
    }

}