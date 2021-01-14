package csci310.servlet.vacation;

import com.google.gson.Gson;

import csci310.Locations;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@WebServlet("/VacationPlanningSearch")
public class VacationPlanningSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String paramNames[] = {"precipType", "tempRangeLow", "tempRangeHigh", "numResults", "SetDistance", "latitude","longitude"};


	
    public VacationPlanningSearch() {
        super(); 
        
        // TODO Auto-generated constructor stub 
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NullPointerException {
		HashMap<String, Object> responseObject = new HashMap<String, Object>();
		ArrayList<String> params = new ArrayList<String>();
		for(String param : paramNames) {
			params.add(request.getParameter(param).trim());
		}
		HashSet<String> errors = new HashSet<String>();
		for(int i = 0; i < 7; i++) {
			if (params.get(i).isEmpty()) {
				errors.add(paramNames[i]);
			}
		}
		if(!errors.contains(paramNames[1]) && !errors.contains(paramNames[2])) {
			int tempRangeLow = Integer.parseInt(params.get(1));
			int tempRangeHigh = Integer.parseInt(params.get(2));
			
			if(tempRangeLow > tempRangeHigh) {
				errors.add(paramNames[1]);
				errors.add(paramNames[2]);
			}
		}
		
		boolean numResultsIsAnInt = true;
		int numResults = 0;
		try {
			Integer.parseInt(params.get(3));
		}
		catch(NumberFormatException nfe) {
			numResultsIsAnInt = false;
		}
		if(numResultsIsAnInt) {
			numResults = Integer.parseInt(params.get(3));
			if(numResults < 0) {
				errors.add(paramNames[3]);
			}
		}
		else {
			errors.add(paramNames[3]);
		}
		ArrayList<String> errorList = new ArrayList<String>();
		for(String e : errors) {
			errorList.add(e);
		}
		if(errorList.size() > 0) {
			responseObject.put("success", false);
			responseObject.put("errors", errorList);
		}
		else {
			responseObject.put("success", true);
			int dist = Integer.parseInt(params.get(4));
//			List<String> cities = Locations.getCi();
			//(lowTemp, highTemp, n, lat, lon, dist, precipType)
			double lat = Double.parseDouble(params.get(5));
			double lon = Double.parseDouble(params.get(6));
			List<HashMap<String, Object>> h = Locations.getValidCities(Float.parseFloat(params.get(1)), Float.parseFloat(params.get(2)), numResults, lat,lon,dist, params.get(0));

			HashMap<String, Object> deg = new HashMap<String, Object>();
			deg.put("farenheit", h);
			deg.put("celsius", h);
			HashMap<String, Object> results = new HashMap<String, Object>();
//			results.put("results", )
//			JsonReader.setLenient(true);
			responseObject.put("results", deg);
		}
		
		Gson gson = new Gson();
		String responseString = gson.toJson(responseObject);
		JSONObject obj = new JSONObject(responseString);

		// System.out.println(responseString);
		response.getWriter().write(responseString);
		request.setAttribute("json", obj);
		request.getRequestDispatcher("vacationPlanning.jsp").forward(request, response);

	}
}

