package csci310;

import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;


import csci310.servlet.search.SearchServlet;

public class addSearchServletTest extends Mockito{
	
	@Test
    public void canAddSearch() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);   
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);   
        
        when(request.getParameter("location")).thenReturn("Los Angeles");
        when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(dispatcher);


        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new SearchServlet().service(request, response);

        verify(dispatcher).forward(request, response);
        verify(request, atLeast(1)).getParameter("location");
        
        
        HttpServletRequest request2 = mock(HttpServletRequest.class);       
        HttpServletResponse response2 = mock(HttpServletResponse.class);   
        RequestDispatcher dispatcher2 = mock(RequestDispatcher.class);   
        
        when(request2.getRequestDispatcher(Mockito.anyString())).thenReturn(dispatcher2);


        StringWriter stringWriter2 = new StringWriter();
        PrintWriter writer2 = new PrintWriter(stringWriter2);
        when(response2.getWriter()).thenReturn(writer2);

        new getSearchHistoryServlet().doGet(request2, response2);
        
        String[] result = stringWriter2.toString().split(";");
        String check = result[result.length-1];
        
        assertTrue(check.equals("Los Angeles"));
        
        writer.flush(); 
    }

}
