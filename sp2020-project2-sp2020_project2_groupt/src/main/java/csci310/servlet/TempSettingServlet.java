package csci310.servlet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import csci310.servlet.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;

public class TempSettingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // handle GET request
        HttpSession session = req.getSession();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            String tempSetting = (String) session.getAttribute("tempUnits");
            if (tempSetting == null) { // If session not yet initialized
                tempSetting = "F"; // Default F
                session.setAttribute("tempUnits", tempSetting);
            }
            JsonObject response = new JsonObject();
            response.add("tempUnits", new JsonPrimitive(tempSetting));

            resp.getWriter().write(response.toString());

        } catch (ClassCastException e) {
            resp.setStatus(400);
        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            // handle POST request
            JsonObject postBody = ServletUtils.readPostBodyAsJson(req.getReader());
            String tempUnitsUpdate = postBody.getAsJsonPrimitive("tempUnits").getAsString();
            if (!(tempUnitsUpdate.equals("F") || tempUnitsUpdate.equals("C"))) {
                resp.setStatus(400);
                return;
            }
            String tempSetting = (String) session.getAttribute("tempUnits");
            if (tempSetting == null) { // If session not yet initialized
                tempSetting = "F"; // Default F
                session.setAttribute("tempUnits", tempSetting);
            }
            session.setAttribute("tempUnits", tempUnitsUpdate);
            // Implicitly returns 200 status
        } catch (IllegalStateException | ClassCastException | NullPointerException e) {
            resp.setStatus(400);
        }
    }

}