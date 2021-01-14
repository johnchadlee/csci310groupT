package csci310.servlet.search;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.google.gson.Gson;

import csci310.WeatherAPIConnector;
import csci310.model.SearchForecast;
import csci310.servlet.activity.Activities;

/**
 * Servlet implementation class SearchServlet
 */
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String paramNames[] = {"location"};
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, JSONException, IOException {
		HashMap<String, Object> responseObject = new HashMap<String, Object>();
		response.setContentType("text/html;charset=UTF-8");
		ArrayList<String> params = new ArrayList<String>();
		for(String param : paramNames) {
			params.add(request.getParameter(param).trim());
		}
		if(!params.get(0).isEmpty()) {
			FileWriter fw = new FileWriter("myfile.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.append(params.get(0));
			bw.append(";");
			bw.close();
	        //DBConnection.addSearchHistory(username, password, search);
		}
		HashSet<String> errors = new HashSet<String>();
		for(int i = 0; i < 1; i++) {
			if (params.get(i).isEmpty()) {
				errors.add(paramNames[i]);
			}
		}
		Date filler = new Date();
		SearchForecast sf = new SearchForecast("",filler,0,"","");
		if (!params.get(0).isEmpty()) {
			sf = WeatherAPIConnector.getSearchForecast(params.get(0),WeatherAPIConnector.getCurrentDate());
			if(sf.getLocation().contentEquals("") || sf.getLocation().contentEquals("error")) {
				errors.add(paramNames[0]);
			}
		}
		Map<String, String> out = new HashMap<String, String>();
		if(errors.size()>0) {
			out.put("errorMsg", "Invalid location. Failed to retrieve weather data.");
		}else {
			out.put("temp", Float.toString(sf.getTemperatureRange()));
			out.put("location", sf.getLocation());
			out.put("desc", sf.getDesc());
			out.put("icon", sf.getIcon());
		}
		
		Gson gson = new Gson();
		String json = gson.toJson(out);
		response.getWriter().write(json);
		request.setAttribute("json", json);
		request.getRequestDispatcher("main-search.jsp").forward(request, response);
	}

}
