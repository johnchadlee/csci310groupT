package csci310.servlet.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class ActivityPlanningActivities
 */
public class ActivityPlanningActivities extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> activities = Activities.getActivityList();
		Map<String, Object> out = new HashMap<String, Object>();
		out.put("activities", activities);
		Gson gson = new Gson();
		String json = gson.toJson(out);
		response.getWriter().write(json);
	}
}
