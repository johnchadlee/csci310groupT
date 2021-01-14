

package csci310;

import java.io.*;
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
@WebServlet("/getSearchServlet")
public class getSearchHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HashMap<String, String> localStore = new HashMap<String, String>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getSearchHistoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		FileReader reader = new FileReader("myfile.txt");
    	BufferedReader br = new BufferedReader(reader);
    	String line;
    	String res = "";
    	while ((line = br.readLine()) != null) {
    		res = res+line;
    	}
		response.getWriter().write(res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String history = DBConnection.getSearchHistory(username, password);
        out.println("searchHistory returned");
       // String[] result = history.split(";");
<<<<<<< HEAD
        response.getWriter().write(history);*/
    }
}
