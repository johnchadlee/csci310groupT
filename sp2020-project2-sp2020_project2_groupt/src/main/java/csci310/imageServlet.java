package csci310;
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
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class imageServlet extends HttpServlet {


/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    req.getRequestDispatcher("weather-analysis.jsp").include(req, resp);  
	String city = req.getParameter("city").trim();
	List<String> links=ImagesAPIConnector.getImageLinks(city);
	req.setAttribute("links", links);
	req.getRequestDispatcher("weather-analysis.jsp").forward(req, resp);

}

}