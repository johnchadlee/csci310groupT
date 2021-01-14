package csci310;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

public class UserLogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public UserLogoutServlet() {
        super();
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
        	String jspName = (String) session.getAttribute("caller");
            session.removeAttribute("user");
            response.sendRedirect(request.getContextPath() + jspName);
        }
    }
}