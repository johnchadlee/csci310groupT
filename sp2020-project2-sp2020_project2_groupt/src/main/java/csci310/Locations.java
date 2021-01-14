package csci310;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import csci310.model.SearchForecast;
import csci310.WeatherAPIConnector.*;

public class Locations {

	public Locations() {
		// TODO Auto-generated constructor stub
	}

//	private static String filename = "src//main//java//csci310//cities.txt";
//	private static String filenamemac = "\\src\\main\\java\\csci310\\cities.txt";
	
	private static String filename = "src/main/java/csci310/cities.txt";
	
	public static List<String> getCityCountries(){
		File file = new File(filename); 
		List<String> cities = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			  
			String st;
			while ((st = br.readLine()) != null) {
				cities.add(st.trim());
			}
		}
		catch(IOException e) {
			System.out.println(e);
		}
		return cities;
	}
	
	// vacation planning with typed location
	public static List<HashMap<String, Object>> getValidCities(float lowTemp, float highTemp, int n, double lat, double lon, int dist, String precipType){
		
		if(n>150) {
			n=150;
		}
		List<HashMap<String, Object>> locations = new ArrayList<HashMap<String, Object>>();
		int i=0;
		try {
			
			JsonParser parser = new JsonParser();
			/*
			String jsonResult = WeatherAPIConnector.getLatLong(currentLocation);
			JsonArray jaLatLong = parser.parse(jsonResult).getAsJsonArray();
			JsonObject joLatLong =  jaLatLong.get(0).getAsJsonObject();
			
			//System.out.println(jsonResult); DEBUG STATEMENT
			double a_lat = joLatLong.get("lat").getAsDouble();
			double a_lon = joLatLong.get("lon").getAsDouble();
			*/
			double a_lat =  lat;
			double a_lon = lon;
			
			List<String> cities = getCityCountries();
			int end = n;
			if (cities.size() < n) {
				end = cities.size();
			}
			while(i < cities.size()) {
				if(locations.size() == end) {
					break;
				}
				String[] cc = cities.get(i).split(",");
				SearchForecast sf = WeatherAPIConnector.getSearchForecast(cc[0], WeatherAPIConnector.getCurrentDate());
				float temp = sf.getTemperatureRange();
				String icon = sf.getIcon().trim();
				if(temp != -100) {
					//System.out.print("City: " + cities.get(i) + " || Temp: " + temp + " || Icon: "+icon+" || Status:"); //debugging, city, temp, icon, and status
					i++;
				}else {
					TimeUnit.MILLISECONDS.sleep(250);
				}
				if(temp >= lowTemp && temp <= highTemp) {
					HashMap<String, Object> ret = new HashMap<String, Object>();
					LikesConnector lc = new LikesConnector();
					int likes = lc.getLikes(cc[0]);
					ret.put("city", cc[0]);
					ret.put("country", cc[1]);
					ret.put("currentTemp", (int)sf.getTemperatureRange());
					ret.put("avgMinTemp", (int)sf.getTemperatureRange() - 26);
					ret.put("avgMaxTemp", (int)sf.getTemperatureRange() + 26);
					ret.put("likes", likes);
					ret.put("favorite", false);
					
					String jsonResult = WeatherAPIConnector.getLatLong(cc[0]);
					try {
						JsonArray jaLatLong = parser.parse(jsonResult).getAsJsonArray();
						JsonObject joLatLong =  jaLatLong.get(0).getAsJsonObject();
						double b_lat = joLatLong.get("lat").getAsDouble();
						double b_lon = joLatLong.get("lon").getAsDouble();
						double d = WeatherAPIConnector.haversineDistance(a_lat, a_lon, b_lat, b_lon);
						int set = (int)d;
						if(d<=dist) {
							if(icon.contains(precipType)) {
								ret.put("distance", set);
								locations.add(ret);
								//System.out.println(" ADDED!"); //debugging, status message
							} else {
								//System.out.println(" NOT ADDED (not correct precip)"); //debugging, status message
							}
						} else {
							//System.out.println(" NOT ADDED (out of distance)"); //debugging, status message
						}
					}
					catch(Exception e) {
						//System.out.println(e.getMessage() + " (inside loop)");
					}
				} else if(temp == -100){
					
				} else{
					//System.out.println(" NOT ADDED (out of temp range)"); //debugging, status message
				}
				
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage() + " (outside loop)");
		}
		// System.out.println("DONE."); debugging, indicates completion
		return locations;
	}
	
	// 
	public static List<HashMap<String, Object>> getValidActivityCities(float lowTemp, float highTemp, int n, double lat, double lon, int dist){
		if(n>150) {
			n=150;
		}
		List<HashMap<String, Object>> locations = new ArrayList<HashMap<String, Object>>();
		int i=0;
		try {
			
			JsonParser parser = new JsonParser();
			/*
			String jsonResult = WeatherAPIConnector.getLatLong(currentLocation);
			JsonArray jaLatLong = parser.parse(jsonResult).getAsJsonArray();
			JsonObject joLatLong =  jaLatLong.get(0).getAsJsonObject();
			// System.out.println(jsonResult); DEBUG STATEMENT
			double a_lat = joLatLong.get("lat").getAsDouble();
			double a_lon = joLatLong.get("lon").getAsDouble();
			*/
			double a_lat = lat;
			double a_lon = lon;
			
			List<String> cities = getCityCountries();
			int end = n;
			if (cities.size() < n) {
				end = cities.size();
			}
			while( i < cities.size()) {
				if(locations.size() == end) {
					break;
				}
				String[] cc = cities.get(i).split(",");
				SearchForecast sf = WeatherAPIConnector.getSearchForecast(cc[0], WeatherAPIConnector.getCurrentDate());
				float temp = sf.getTemperatureRange();
				
				if(temp != -100) {
					// System.out.print("City: " + cities.get(i) + " || Temp " + temp + " || Status:"); debugging, city, temp, and status
					i++;
				}else {
					TimeUnit.MILLISECONDS.sleep(250);
				}
				
				TimeUnit.MILLISECONDS.sleep(2000);

				if(temp >= lowTemp && temp <= highTemp) {
					HashMap<String, Object> ret = new HashMap<String, Object>();
					LikesConnector lc = new LikesConnector();
					int likes = lc.getLikes(cc[0]);
					ret.put("city", cc[0]);
					ret.put("country", cc[1]);
					ret.put("currentTemp", (int)sf.getTemperatureRange());
					ret.put("avgMinTemp", (int)sf.getTemperatureRange() - 26);
					ret.put("avgMaxTemp", (int)sf.getTemperatureRange() + 26);
					ret.put("likes", likes);
					ret.put("favorite", false);
					
					String jsonResult = WeatherAPIConnector.getLatLong(cc[0]);
					System.out.println(jsonResult);
					try {
						JsonArray jaLatLong = parser.parse(jsonResult).getAsJsonArray();
						JsonObject joLatLong =  jaLatLong.get(0).getAsJsonObject();
						double b_lat = joLatLong.get("lat").getAsDouble();
						double b_lon = joLatLong.get("lon").getAsDouble();
						double d = WeatherAPIConnector.haversineDistance(a_lat, a_lon, b_lat, b_lon);
						int set = (int)d;
//						 System.out.println("Actual distance: " + d);
//						 System.out.println("Actual distance: " + set);
						if(d<=dist) {
							ret.put("distance", (int)d);
							locations.add(ret);
							 System.out.println(" ADDED!"); //debugging, status message
						} else {
							// System.out.println(" NOT ADDED (out of distance)"); debugging, status message
						}
						
					}
					catch(Exception e) {
						System.out.println(e.getMessage() + " (inside loop)");
					}
				} else if(temp == -100){
					
				} else{
					// System.out.println(" NOT ADDED (out of temp range)"); debugging, status message
				}
				
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage() + " (outside loop)");
		}
		// System.out.println("DONE."); debugging, indicates completion
		return locations;
	}
	
	

}