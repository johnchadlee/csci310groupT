
package csci310;

import java.io.IOException;
import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class addSearchServlet
 */
@WebServlet("/addSearchServlet")
public class addSearchHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HashMap<String, String> localStore = new HashMap<String, String>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addSearchHistoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	    System.out.println("doGet called");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
		String search = request.getParameter("search");
		String[] searchArray = search.split("@");
        localStore.put(searchArray[0], searchArray[1]);
        FileOutputStream outFile = null;
		System.out.println(username+password+search);
        DBConnection.addSearchHistory(username, password, search);
        out.println("Search added to search history.");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		doGet(request, response);
	}

}
