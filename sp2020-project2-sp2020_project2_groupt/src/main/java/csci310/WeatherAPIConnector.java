package csci310;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import com.google.gson.*;

import csci310.model.AnalysisForecast;
import csci310.model.FutureForecast;
import csci310.model.SearchForecast;
import csci310.model.WeatherHistory;

public class WeatherAPIConnector {
	private static HttpURLConnection connection;
	private static String darkSkyKey = "7c9bbcb42f3bd632b00f1f675d44b9db";
	private static String geocodingKey = "5852d9a8de2a42";
	
	/*
	// MAIN IS PURELY FOR TESTING PURPOSES
	public static void main(String[] args) {
		
	}
	*/
	
	// Turns location/city name into latitude and longitude as input for API calls
	public static String getLatLong(String location) {
		BufferedReader br;
		String line;
		StringBuffer sb = new StringBuffer();
		try {
			//set variables
			URL url = new URL("https://us1.locationiq.com/v1/search.php?key="+geocodingKey+"&q="+location+"&format=json");
			connection = (HttpURLConnection) url.openConnection();
			//connection setup
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			
			// get status code
			int status = connection.getResponseCode();
			
			// check status, return error stream if error
			if(status>299) {
				br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();
			} else {
				br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();
			}
			
		} catch(MalformedURLException mue) {
			System.out.println(mue.getMessage());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} finally {
			connection.disconnect();
		}
		return sb.toString();
	}
	
	public static double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
		double R = 6372.8;
		double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
 
        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
	}
	
	// increment Date by n days
	public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
	
	// capitalizes first letter of every word in string
	public static String capitalize(String s) {
		String words[]=s.split("\\s");  
	    String capitalizeWord="";  
	    for(String w:words){  
	        String first=w.substring(0,1);  
	        String afterfirst=w.substring(1);  
	        capitalizeWord+=first.toUpperCase()+afterfirst+" ";  
	    }  
	    return capitalizeWord.trim();  
	}
	
	// Returns Date Format: "yyyy-MM-ddTHH:mm:ss"
	public static String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dateString = dateFormat.format(date);
		
		// delimit using space and put back together with T in between
		String[] dateStringSplit = dateString.split(" ");
		String ymd = dateStringSplit[0];
		String time = dateStringSplit[1];
		dateString = ymd+"T"+time;
		return dateString;
	}
	
	// returns a Java Date class from a pre-formatted String
	public static Date getDateFromString(String dateString) {
		
		int y = Integer.parseInt(dateString.substring(0, 4))-1900;
		int mon = Integer.parseInt(dateString.substring(5, 7))-1;
		int d = Integer.parseInt(dateString.substring(8, 10));
		int h = Integer.parseInt(dateString.substring(11, 13));
		int min = Integer.parseInt(dateString.substring(14, 16));
		int s = Integer.parseInt(dateString.substring(17, 19));
		@SuppressWarnings("deprecation")
		Date date = new Date(y,mon,d,h,min,s);
		return date;
	}
	
	// formats date into correct format
	@SuppressWarnings("deprecation")
	public static String formatDate(Date date) {
		String dateString;
		int y = date.getYear()+1900;
		int mon = date.getMonth()+1;
		int day = date.getDate();
		int h = date.getHours();
		int min = date.getMinutes();
		int s = date.getSeconds();
		dateString = y + "-" + String.format("%02d", mon) + "-" + String.format("%02d", day) + "T" + String.format("%02d", h)+":"+String.format("%02d", min) + ":" + String.format("%02d", s);
		return dateString;
	}
	
	
	//****
	// returns SearchForecast, containing location, date, and current temperature
	public static SearchForecast getSearchForecast(String location, String dateString) {
		if(location.contentEquals("")) {
			location = " ";
		}
		//capitalize location name
		location = capitalize(location);
		
		//obtain latitude/longitude JSON
		String latlong = getLatLong(location);
		
		// get first element in JSON
		JsonParser parser = new JsonParser();
		JsonElement jeLatLong = parser.parse(latlong);
		String currentWeatherData = "";
		JsonObject joLatLong;
		
		// make filler SearchForecast in case of error
		Date filler = new Date(0);
		SearchForecast sf = new SearchForecast("error",filler,-100,"","");
		try {
			// an incredibly roundabout way of checking if the city/zip is found
			joLatLong =  jeLatLong.getAsJsonObject();
			String error = joLatLong.get("error").getAsString();
			if(error != null) {
				currentWeatherData = "{ \"errorMsg\": \"Location not found\" }";
			}
		} catch(IllegalStateException ise) {
			// by default a successful API call returns a jsonarray as opposed to a jsonobject
			// although this is poor design, simply checking if it is a json object will cause an exception,
			// which puts us into the catch block, indicating a successful call
			JsonArray jaLatLong = jeLatLong.getAsJsonArray();
			joLatLong =  jaLatLong.get(0).getAsJsonObject();
			String latitude = joLatLong.get("lat").getAsString();
			String longitude = joLatLong.get("lon").getAsString();
			
			// make search using lat/lon
			currentWeatherData = makeSearch(dateString, latitude, longitude);
			JsonObject joWeatherData = parser.parse(currentWeatherData).getAsJsonObject();
			
			// enter 'currently'
			JsonObject joCurrently = joWeatherData.get("currently").getAsJsonObject();
			String desc = joCurrently.get("summary").getAsString();
			String icon = joCurrently.get("icon").getAsString();
			float temp = joCurrently.get("temperature").getAsFloat();
			Date date = getDateFromString(dateString);
			
			// create new searchforecast
			sf = new SearchForecast(location,date,temp,desc,icon);
		}
		
		return sf;
	}
	
	// returns AnalysisForecast, which contains same data as SearchForecast but with next 5 days of FutureForecasts and last 12 months of weatherHistory
	public static AnalysisForecast getAnalysisForecast(String location) {
		if(location.contentEquals("")) {
			location = " ";
		}
		location = capitalize(location);
		String dateString = getCurrentDate();
		String latlong = getLatLong(location);
		JsonParser parser = new JsonParser();
		JsonElement jeLatLong = parser.parse(latlong);
		String currentWeatherData = "";
		JsonObject joLatLong;
		
		// make filler AnalysisForecast in case of error
		Date filler = new Date(0);
		List<FutureForecast> n5D = new ArrayList<FutureForecast>();
		List<WeatherHistory> hTs = new ArrayList<WeatherHistory>();
		AnalysisForecast af = new AnalysisForecast("error",filler,-100,n5D,hTs, "", "");
		
		try {
			// an incredibly roundabout way of checking if the city/zip is found
			joLatLong = jeLatLong.getAsJsonObject();
			String error = joLatLong.get("error").getAsString();
			if(error != null) {
				currentWeatherData = "{ \"errorMsg\": \"Location not found\" }";
			}
		} catch(IllegalStateException ise) {
			// by default a successful API call returns a jsonarray as opposed to a jsonobject
			// although this is poor design, simply checking if it is a json object will cause an exception,
			// which puts us into the catch block, indicating a successful call
			JsonArray jaLatLong = jeLatLong.getAsJsonArray();
			joLatLong = jaLatLong.get(0).getAsJsonObject();
			String latitude = joLatLong.get("lat").getAsString();
			String longitude = joLatLong.get("lon").getAsString();
			float currentTemp = 0;
			
			// current temperature
			currentWeatherData = makeSearch(dateString, latitude, longitude);
			JsonObject joWeatherData = parser.parse(currentWeatherData).getAsJsonObject();
			JsonObject joCurrently = joWeatherData.get("currently").getAsJsonObject();
			String desc = joCurrently.get("summary").getAsString();
			String icon = joCurrently.get("icon").getAsString();
			currentTemp = joCurrently.get("temperature").getAsFloat();
			
			//iterate 5 times for 5 day forecast
			for(int i=0;i<5;i++) {
				FutureForecast ff = getFutureForecast(latitude,longitude, dateString);
				n5D.add(ff);
				dateString = formatDate(addDays(getDateFromString(dateString),1));
			}
			
			//iterate 12 times for 12 months of weather history
			for(int i=1;i<13;i++) {
				WeatherHistory wh = getWeatherHistory(latitude,longitude, i);
				hTs.add(wh);
			}
			
			// get current date and create analysisforecast
			Date currentDate = getDateFromString(getCurrentDate());
			af = new AnalysisForecast(location,currentDate,currentTemp, n5D, hTs, desc, icon);
		}
		
		return af;
	}
	
	// returns FutureForecast, contains high an low for a day with decription and icon for a forecast
	public static FutureForecast getFutureForecast(String latitude, String longitude, String dateString) {
		JsonParser parser = new JsonParser();
		String currentWeatherData = makeSearch(dateString, latitude, longitude);
		JsonObject joWeatherData = parser.parse(currentWeatherData).getAsJsonObject();
		
		// in 'currently' category
		JsonObject joCurrently = joWeatherData.get("currently").getAsJsonObject();
		String desc = joCurrently.get("summary").getAsString();
		String icon = joCurrently.get("icon").getAsString();
		
		// in 'daily' category
		JsonObject joDaily = joWeatherData.get("daily").getAsJsonObject();
		JsonArray joData = joDaily.get("data").getAsJsonArray();
		JsonObject firstData = joData.get(0).getAsJsonObject();
		float maxT = firstData.get("temperatureHigh").getAsFloat();
		float minT = firstData.get("temperatureLow").getAsFloat();
		
		Date date = getDateFromString(dateString);
		FutureForecast ff = new FutureForecast(date,maxT,minT,desc,icon);
		return ff;
	}
	
	// returns WeatherHistory, contains high and low for a month
	public static WeatherHistory getWeatherHistory(String latitude, String longitude, int month) {
		float avgHigh = 0;
		float avgLow = 0;
		JsonParser parser = new JsonParser();
		String m = "";
		if(month>=10) {
			m += Integer.toString(month);
		} else {
			m += "0";
			m += Integer.toString(month);
		}
		for(int i=1;i<29;i+=6) { // runs 5 times, days 1, 7, 13, 19, 25 of the month
			// This avoids going over february and still gets a good idea of lows/highs for the month
			// it also avoids this function running forever
			String dayString = "";
			if(i>=10) {
				dayString += Integer.toString(i);
			} else {
				dayString += "0";
				dayString += Integer.toString(i);
			}
			// Date Format: "YYYY-MM-DDTHH:MM:SS", checks at the end in case there are record highs and lows for the day
			String dateString = "2019-"+m+"-"+dayString+"T23:59:59";
			// Note: this will only work for 2020 looking back a single year, this will only be problematic in later years
			String currentWeatherData = makeSearch(dateString, latitude, longitude);
			JsonObject joWeatherData = parser.parse(currentWeatherData).getAsJsonObject();
			
			// in 'daily' category
			JsonObject joDaily = joWeatherData.get("daily").getAsJsonObject();
			JsonArray joData = joDaily.get("data").getAsJsonArray();
			JsonObject firstData = joData.get(0).getAsJsonObject();
			float maxT = firstData.get("temperatureMax").getAsFloat();
			float minT = firstData.get("temperatureMin").getAsFloat();
			
			// running total
			avgHigh += maxT;
			avgLow += minT;
		}
		avgHigh = avgHigh/5; //average all 28 days
		avgLow = avgLow/5;
		
		WeatherHistory wh = new WeatherHistory(avgLow,avgHigh);
		return wh;
	}
	
	// returns JSON for weather search with lat/lon and date as inputs
	public static String makeSearch(String date, String latitude, String longitude) {
		BufferedReader br;
		String line;
		StringBuffer sb = new StringBuffer();
		// Date Format: "YYYY-MM-DDTHH:MM:SS"
		try {
			//set variables
			URL url = new URL("https://api.darksky.net/forecast/"+darkSkyKey+"/"+latitude+","+longitude+","+date);
			connection = (HttpURLConnection) url.openConnection();
			
			//connection setup
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			
			// store connection status
			int status = connection.getResponseCode();
			
			// store error streams in case of unsuccessful connection
			if(status>299) {
				br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();
			} else {
				br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();
			}
			
		} catch(MalformedURLException mue) {
			System.out.println(mue.getMessage());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} finally {
			connection.disconnect();
		}
		return sb.toString();
	}

	
	public static String weatherAnimations(String description) { 
		if(description.equals("Clear")) {
			return"<g id=\"Sun\">\n" + 
					"                <circle fill=\"orange\" id='XMLID_61_' class='yellow' cx='22.4' cy='22.6' r='11'/>\n" + 
					"              <g>\n" + 
					"                <path fill=\"orange\" id='XMLID_60_' class='yellow' d='M22.6,8.1h-0.3c-0.3,0-0.6-0.3-0.6-0.6v-7c0-0.3,0.3-0.6,0.6-0.6l0.3,0c0.3,0,0.6,0.3,0.6,0.6 v7C23.2,7.8,22.9,8.1,22.6,8.1z'/>\n" + 
					"                <path fill=\"orange\" id='XMLID_59_' class='yellow' d='M22.6,36.8h-0.3c-0.3,0-0.6,0.3-0.6,0.6v7c0,0.3,0.3,0.6,0.6,0.6h0.3c0.3,0,0.6-0.3,0.6-0.6v-7 C23.2,37,22.9,36.8,22.6,36.8z'/>\n" + 
					"                <path fill=\"orange\" id='XMLID_58_' class='yellow' d='M8.1,22.3v0.3c0,0.3-0.3,0.6-0.6,0.6h-7c-0.3,0-0.6-0.3-0.6-0.6l0-0.3c0-0.3,0.3-0.6,0.6-0.6h7 C7.8,21.7,8.1,21.9,8.1,22.3z'/>\n" + 
					"                <path fill=\"orange\" id='XMLID_57_' class='yellow' d='M36.8,22.3v0.3c0,0.3,0.3,0.6,0.6,0.6h7c0.3,0,0.6-0.3,0.6-0.6v-0.3c0-0.3-0.3-0.6-0.6-0.6h-7 C37,21.7,36.8,21.9,36.8,22.3z'/>\n" + 
					"                <path fill=\"orange\" id='XMLID_56_' class='yellow' d='M11.4,31.6l0.2,0.3c0.2,0.2,0.2,0.6-0.1,0.8l-5.3,4.5c-0.2,0.2-0.6,0.2-0.8-0.1l-0.2-0.3 c-0.2-0.2-0.2-0.6,0.1-0.8l5.3-4.5C10.9,31.4,11.2,31.4,11.4,31.6z'/>\n" + 
					"                <path fill=\"orange\" id='XMLID_55_' class='yellow' d='M33.2,13l0.2,0.3c0.2,0.2,0.6,0.3,0.8,0.1l5.3-4.5c0.2-0.2,0.3-0.6,0.1-0.8l-0.2-0.3 c-0.2-0.2-0.6-0.3-0.8-0.1l-5.3,4.5C33,12.4,33,12.7,33.2,13z'/>\n" + 
					"                <path fill=\"orange\" id='XMLID_54_' class='yellow' d='M11.4,13.2l0.2-0.3c0.2-0.2,0.2-0.6-0.1-0.8L6.3,7.6C6.1,7.4,5.7,7.5,5.5,7.7L5.3,7.9 C5.1,8.2,5.1,8.5,5.4,8.7l5.3,4.5C10.9,13.5,11.2,13.5,11.4,13.2z'/>\n" + 
					"                <path fill=\"orange\" id='XMLID_53_' class='yellow' d='M33.2,31.9l0.2-0.3c0.2-0.2,0.6-0.3,0.8-0.1l5.3,4.5c0.2,0.2,0.3,0.6,0.1,0.8l-0.2,0.3 c-0.2,0.2-0.6,0.3-0.8,0.1l-5.3-4.5C33,32.5,33,32.1,33.2,31.9z'/>\n" + 
					"                <animate attributeType='CSS'\n" + 
					"                  attributeName='opacity'\n" + 
					"                  attributeType='XML'\n" + 
					"                  dur='0.5s'\n" + 
					"                  keyTimes='0;0.5;1'\n" + 
					"                  repeatCount='indefinite'\n" + 
					"                  values='1;0.6;1'\n" + 
					"                  calcMode='linear'/>\n" + 
					"              </g>\n" + 
					"            </g>";
		} else if(description.equals("few clouds") || description.equals("scatter clouds") 
				|| description.equals("broken clouds") ) {
			
			return "            <g id=\"Cloud_1\">\n" + 
					"              <g id=\"White_cloud_1\">\n" + 
					"                  <path id=\"XMLID_2_\" class=\"white\" d=\"M47.2,40H7.9C3.5,40,0,36.5,0,32.1l0,0c0-4.3,3.5-7.9,7.9-7.9h39.4c4.3,0,7.9,3.5,7.9,7.9v0 C55.1,36.5,51.6,40,47.2,40z\"/>\n" + 
					"                  <circle id=\"XMLID_3_\" class=\"white\" cx=\"17.4\" cy=\"22.8\" r=\"9.3\"/>\n" + 
					"                  <circle id=\"XMLID_4_\" class=\"white\" cx=\"34.5\" cy=\"21.1\" r=\"15.6\"/>\n" + 
					"                <animateTransform attributeName=\"transform\"\n" + 
					"                  attributeType=\"XML\"\n" + 
					"                  dur=\"6s\"\n" + 
					"                  keyTimes=\"0;0.5;1\"\n" + 
					"                  repeatCount=\"indefinite\"\n" + 
					"                  type=\"translate\"\n" + 
					"                  values=\"0;5;0\"\n" + 
					"                  calcMode=\"linear\">\n" + 
					"                </animateTransform>\n" + 
					"              </g>\n" + 
					"              <g id=\"Gray_cloud_1\">\n" + 
					"                  <path id=\"XMLID_6_\" class=\"gray\" d=\"M54.7,22.3H33.4c-3.3,0-6-2.7-6-6v0c0-3.3,2.7-6,6-6h21.3c3.3,0,6,2.7,6,6v0 C60.7,19.6,58,22.3,54.7,22.3z\"/>\n" + 
					"                  <circle id=\"XMLID_7_\" class=\"gray\" cx=\"45.7\" cy=\"10.7\" r=\"10.7\"/>\n" + 
					"                <animateTransform attributeName=\"transform\"\n" + 
					"                  attributeType=\"XML\"\n" + 
					"                  dur=\"6s\"\n" + 
					"                  keyTimes=\"0;0.5;1\"\n" + 
					"                  repeatCount=\"indefinite\"\n" + 
					"                  type=\"translate\"\n" + 
					"                  values=\"0;-3;0\"\n" + 
					"                  calcMode=\"linear\">\n" + 
					"                </animateTransform>\n" + 
					"              </g>\n" + 
					"            </g>\n";			
			
		} else if(description.equals("shower rain") || description.equals("rain") 
				|| description.equals("thunderstorm") || description.equals("mist") || description.contains("rain")) {
			
			return "            <g id=\"Cloud_2\">\n" + 
					"                <g id=\"Rain_2\">\n" + 
					"                <path id=\"rain_2_left\" class=\"white\" d=\"M20.7,46.4c0,1.7-1.4,3.1-3.1,3.1s-3.1-1.4-3.1-3.1c0-1.7,3.1-7.8,3.1-7.8 S20.7,44.7,20.7,46.4z\"></path>\n" + 
					"                    <path id=\"rain_2_mid\" class=\"white\" d=\"M31.4,46.4c0,1.7-1.4,3.1-3.1,3.1c-1.7,0-3.1-1.4-3.1-3.1c0-1.7,3.1-7.8,3.1-7.8 S31.4,44.7,31.4,46.4z\"></path>\n" + 
					"                <path id=\"rain_2_right\" class=\"white\" d=\"M41.3,46.4c0,1.7-1.4,3.1-3.1,3.1c-1.7,0-3.1-1.4-3.1-3.1c0-1.7,3.1-7.8,3.1-7.8 S41.3,44.7,41.3,46.4z\"></path>\n" + 
					"                <animateTransform attributeName=\"transform\"\n" + 
					"                  attributeType=\"XML\"\n" + 
					"                  dur=\"1s\"\n" + 
					"                  keyTimes=\"0;1\"\n" + 
					"                  repeatCount=\"indefinite\"\n" + 
					"                  type=\"translate\"\n" + 
					"                  values=\"0 0;0 10\"\n" + 
					"                  calcMode=\"linear\">\n" + 
					"                </animateTransform>\n" + 
					"                <animate attributeType=\"CSS\"\n" + 
					"                attributeName=\"opacity\"\n" + 
					"                attributeType=\"XML\"\n" + 
					"                dur=\"1s\"\n" + 
					"                keyTimes=\"0;1\"\n" + 
					"                repeatCount=\"indefinite\"\n" + 
					"                values=\"1;0\"\n" + 
					"                calcMode=\"linear\"/>\n" + 
					"                </g>\n" + 
					"                <g id=\"White_cloud_2\">\n" + 
					"                    <path id=\"XMLID_14_\" class=\"white\" d=\"M47.2,34.5H7.9c-4.3,0-7.9-3.5-7.9-7.9l0,0c0-4.3,3.5-7.9,7.9-7.9h39.4c4.3,0,7.9,3.5,7.9,7.9 v0C55.1,30.9,51.6,34.5,47.2,34.5z\"/>\n" + 
					"                    <circle id=\"XMLID_13_\" class=\"white\" cx=\"17.4\" cy=\"17.3\" r=\"9.3\"/>\n" + 
					"                    <circle id=\"XMLID_10_\" class=\"white\" cx=\"34.5\" cy=\"15.6\" r=\"15.6\"/>\n" + 
					"                </g>\n" + 
					"            </g>";
			
			
		} else if (description.equals("snow") || description.equals("mist")){
			
			return "            <g id=\"Cloud_7\">\n" + 
					"              <g id=\"White_cloud_7\">\n" + 
					"                  <path id=\"XMLID_8_\" class=\"white\" d=\"M47.2,34.5H7.9c-4.3,0-7.9-3.5-7.9-7.9l0,0c0-4.3,3.5-7.9,7.9-7.9h39.4c4.3,0,7.9,3.5,7.9,7.9 v0C55.1,30.9,51.6,34.5,47.2,34.5z\"/>\n" + 
					"                  <circle id=\"XMLID_5_\" class=\"white\" cx=\"17.4\" cy=\"17.3\" r=\"9.3\"/>\n" + 
					"                  <circle id=\"XMLID_1_\" class=\"white\" cx=\"34.5\" cy=\"15.6\" r=\"15.6\"/>\n" + 
					"              </g>\n" + 
					"              <circle class=\"white\" cx=\"37\" cy=\"43.5\" r=\"3\">\n" + 
					"                <animateTransform attributeName=\"transform\"\n" + 
					"                  attributeType=\"XML\"\n" + 
					"                  dur=\"1.5s\"\n" + 
					"                  keyTimes=\"0;0.33;0.66;1\"\n" + 
					"                  repeatCount=\"indefinite\"\n" + 
					"                  type=\"translate\"\n" + 
					"                  values=\"1 -2;3 2; 1 4; 2 6\"\n" + 
					"                  calcMode=\"linear\">\n" + 
					"                </animateTransform>\n" + 
					"              </circle>\n" + 
					"              <circle class=\"white\" cx=\"27\" cy=\"43.5\" r=\"3\">\n" + 
					"                <animateTransform attributeName=\"transform\"\n" + 
					"                  attributeType=\"XML\"\n" + 
					"                  dur=\"1.5s\"\n" + 
					"                  keyTimes=\"0;0.33;0.66;1\"\n" + 
					"                  repeatCount=\"indefinite\"\n" + 
					"                  type=\"translate\"\n" + 
					"                  values=\"1 -2;3 2; 1 4; 2 6\"\n" + 
					"                  calcMode=\"linear\">\n" + 
					"                </animateTransform>\n" + 
					"              </circle>\n" + 
					"              <circle class=\"white\" cx=\"17\" cy=\"43.5\" r=\"3\">\n" + 
					"                <animateTransform attributeName=\"transform\"\n" + 
					"                  attributeType=\"XML\"\n" + 
					"                  dur=\"1.5s\"\n" + 
					"                  keyTimes=\"0;0.33;0.66;1\"\n" + 
					"                  repeatCount=\"indefinite\"\n" + 
					"                  type=\"translate\"\n" + 
					"                  values=\"1 -2;3 2; 1 4; 2 6\"\n" + 
					"                  calcMode=\"linear\">\n" + 
					"                </animateTransform>\n" + 
					"              </circle>\n" + 
					"            </g>";
			
		} else { 
			
			return  "            <g id=\"Cloud_3\">\n" + 
					"              <g id=\"White_cloud_3\">\n" + 
					"                  <path id=\"XMLID_24_\" class=\"white\" d=\"M47.2,42.8H7.9c-4.3,0-7.9-3.5-7.9-7.9l0,0C0,30.5,3.5,27,7.9,27h39.4c4.3,0,7.9,3.5,7.9,7.9 v0C55.1,39.2,51.6,42.8,47.2,42.8z\"/>\n" + 
					"                  <circle id=\"XMLID_23_\" class=\"white\" cx=\"17.4\" cy=\"25.5\" r=\"9.3\"/>\n" + 
					"                  <circle id=\"XMLID_22_\" class=\"white\" cx=\"34.5\" cy=\"23.9\" r=\"15.6\"/>\n" + 
					"                <animateTransform attributeName=\"transform\"\n" + 
					"                  attributeType=\"XML\"\n" + 
					"                  dur=\"6s\"\n" + 
					"                  keyTimes=\"0;0.5;1\"\n" + 
					"                  repeatCount=\"indefinite\"\n" + 
					"                  type=\"translate\"\n" + 
					"                  values=\"0;5;0\"\n" + 
					"                  calcMode=\"linear\">\n" + 
					"                </animateTransform>\n" + 
					"              </g>\n" + 
					"              <g id=\"Sun_3\">\n" + 
					"                  <circle fill=\"orange\" id=\"XMLID_30_\" class=\"yellow\" cx=\"31.4\" cy=\"18.5\" r=\"9\"/>\n" + 
					"                <g>\n" + 
					"                    <path  fill=\"orange\" id=\"XMLID_31_\" class=\"yellow\" d=\"M31.4,6.6L31.4,6.6c-0.4,0-0.6-0.3-0.6-0.6V0.6C30.8,0.3,31,0,31.3,0l0.1,0 C31.7,0,32,0.3,32,0.6v5.5C32,6.4,31.7,6.6,31.4,6.6z\"/>\n" + 
					"                    <path fill=\"orange\" id=\"XMLID_34_\" class=\"yellow\" d=\"M31.4,30.1L31.4,30.1c-0.4,0-0.6,0.3-0.6,0.6v5.5c0,0.3,0.3,0.6,0.6,0.6h0.1 c0.3,0,0.6-0.3,0.6-0.6v-5.5C32,30.4,31.7,30.1,31.4,30.1z\"/>\n" + 
					"                    <path fill=\"orange\" id=\"XMLID_35_\" class=\"yellow\" d=\"M19.6,18.3L19.6,18.3c0,0.4-0.3,0.6-0.6,0.6h-5.5c-0.3,0-0.6-0.3-0.6-0.6v-0.1 c0-0.3,0.3-0.6,0.6-0.6H19C19.3,17.8,19.6,18,19.6,18.3z\"/>\n" + 
					"                    <path fill=\"orange\" id=\"XMLID_33_\" class=\"yellow\" d=\"M43.1,18.3L43.1,18.3c0,0.4,0.3,0.6,0.6,0.6h5.5c0.3,0,0.6-0.3,0.6-0.6v-0.1 c0-0.3-0.3-0.6-0.6-0.6h-5.5C43.4,17.8,43.1,18,43.1,18.3z\"/>\n" + 
					"                    <path fill=\"orange\" id=\"XMLID_37_\" class=\"yellow\" d=\"M22.4,26L22.4,26c0.3,0.3,0.2,0.7,0,0.9l-4.2,3.6c-0.2,0.2-0.6,0.2-0.8-0.1l-0.1-0.1 c-0.2-0.2-0.2-0.6,0.1-0.8l4.2-3.6C21.9,25.8,22.2,25.8,22.4,26z\"/>\n" + 
					"                    <path fill=\"orange\" id=\"XMLID_36_\" class=\"yellow\" d=\"M40.3,10.7L40.3,10.7c0.3,0.3,0.6,0.3,0.8,0.1l4.2-3.6c0.2-0.2,0.3-0.6,0.1-0.8l-0.1-0.1 c-0.2-0.2-0.6-0.3-0.8-0.1l-4.2,3.6C40.1,10.1,40,10.5,40.3,10.7z\"/>\n" + 
					"                    <path fill=\"orange\" id=\"XMLID_39_\" class=\"yellow\" d=\"M22.4,10.8L22.4,10.8c0.3-0.3,0.2-0.7,0-0.9l-4.2-3.6c-0.2-0.2-0.6-0.2-0.8,0.1l-0.1,0.1 c-0.2,0.2-0.2,0.6,0.1,0.8l4.2,3.6C21.9,11,22.2,11,22.4,10.8z\"/>\n" + 
					"                    <path fill=\"orange\" id=\"XMLID_38_\" class=\"yellow\" d=\"M40.3,26.1L40.3,26.1c0.3-0.3,0.6-0.3,0.8-0.1l4.2,3.6c0.2,0.2,0.3,0.6,0.1,0.8l-0.1,0.1 c-0.2,0.2-0.6,0.3-0.8,0.1l-4.2-3.6C40.1,26.7,40,26.3,40.3,26.1z\"/>\n" + 
					"                  <animate attributeType=\"CSS\"\n" + 
					"                    attributeName=\"opacity\"\n" + 
					"                    attributeType=\"XML\"\n" + 
					"                    dur=\"0.5s\"\n" + 
					"                    keyTimes=\"0;0.5;1\"\n" + 
					"                    repeatCount=\"indefinite\"\n" + 
					"                    values=\"1;0.6;1\"\n" + 
					"                    calcMode=\"linear\"/>\n" + 
					"                </g>\n" + 
					"              </g>\n" + 
					"              <animateTransform attributeName=\"transform\"\n" + 
					"                attributeType=\"XML\"\n" + 
					"                dur=\"2s\"\n" + 
					"                keyTimes=\"0;1\"\n" + 
					"                repeatCount=\"indefinite\"\n" + 
					"                type=\"scale\"\n" + 
					"                values=\"1;1\"\n" + 
					"                calcMode=\"linear\">\n" + 
					"              </animateTransform>\n" + 
					"             </g>\n" + 
					"             <g id=\"Gray_cloud_3\">\n" + 
					"                <path id=\"XMLID_20_\" class=\"gray\" d=\"M55.7,25.1H34.4c-3.3,0-6-2.7-6-6v0c0-3.3,2.7-6,6-6h21.3c3.3,0,6,2.7,6,6v0 C61.7,22.4,59,25.1,55.7,25.1z\"/>\n" + 
					"                <circle id=\"XMLID_19_\" class=\"gray\" cx=\"46.7\" cy=\"13.4\" r=\"10.7\"/>\n" + 
					"              <animateTransform attributeName=\"transform\"\n" + 
					"                attributeType=\"XML\"\n" + 
					"                dur=\"6s\"\n" + 
					"                keyTimes=\"0;0.5;1\"\n" + 
					"                repeatCount=\"indefinite\"\n" + 
					"                type=\"translate\"\n" + 
					"                values=\"0;-3;0\"\n" + 
					"                calcMode=\"linear\">\n" + 
					"              </animateTransform>\n" + 
					"             </g>\n" + 
					"           </g>\n";
			
			
		}
	}

	
}






