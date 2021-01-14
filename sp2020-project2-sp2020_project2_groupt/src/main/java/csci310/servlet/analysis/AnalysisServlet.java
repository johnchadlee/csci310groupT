package csci310.servlet.analysis;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import csci310.ImagesAPIConnector;
import csci310.WeatherAPIConnector;
import csci310.model.AnalysisForecast;
import csci310.model.FutureForecast;
import csci310.model.SearchForecast;

public class AnalysisServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	//doget
    	JsonObject response = new JsonObject();
    	
    	try {
    		String city = req.getParameter("city").trim();
    		String country = req.getParameter("country").trim();
    		
    		// Get current weather data
    		SearchForecast sf = new SearchForecast("", new Date(), 0, "", "");
			sf = WeatherAPIConnector.getSearchForecast(city, WeatherAPIConnector.getCurrentDate());
						
			HashMap<String, String> hm0 = new HashMap<String, String>();
			hm0.put("temp", Float.toString(sf.getTemperatureRange()));
			hm0.put("desc", sf.getDesc());
			hm0.put("icon", sf.getIcon());    		
			
			JsonArray jsonCurrent = new Gson().toJsonTree(hm0).getAsJsonArray();
    		response.add("current", jsonCurrent);    		

    		// Get forecast data
    		AnalysisForecast af = WeatherAPIConnector.getAnalysisForecast(city);
    		List<FutureForecast> forecasts = af.getNext5Days();
    		
    		HashMap<String, String> hm1 = new HashMap<String, String>();
    		for (FutureForecast f : forecasts) {
    			hm1.put("date", f.getDate().toString());
    			hm1.put("max_temp", Float.toString(f.getMaxTemp()));
    			hm1.put("min_temp", Float.toString(f.getMinTemp()));
    			hm1.put("icon", "rain");
    		}
    		JsonArray jsonForecast = new Gson().toJsonTree(hm1).getAsJsonArray();
    		response.add("forecast", jsonForecast);    		

    		// Get historic data
    		// Gave up implementing this after realizing this will make 365 calls to DarkSky API
    		// to get a single historic data for a city :) 
    		
    		// Get images
    		List<String> images = ImagesAPIConnector.getImageLinks(city + " " + country);
    		HashMap<String, String> hm2 = new HashMap<String, String>();
    		
    		int hm2Size = hm2.size();
    		for (int i = 0; i < hm2Size; ++i) {
    			hm2.put(Integer.toString(i), images.get(i));
    		}
    		JsonArray jsonImages = new Gson().toJsonTree(hm2).getAsJsonArray();
    		response.add("images", jsonImages);
    		
    		resp.setStatus(200);
    		
        } catch (ClassCastException e) {
            resp.setStatus(530);
        }

    	

		JSONObject obj = new JSONObject(response.toString().trim());
		
		req.setAttribute("json", obj);
		req.getRequestDispatcher("weather-analysis.jsp").forward(req, resp);

    	resp.getWriter().write(response.toString());
    }
}
