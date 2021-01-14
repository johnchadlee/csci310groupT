package csci310;

import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;


import csci310.getSearchHistoryServlet;

public class GetSearchServletTest extends Mockito{
	
	@Test
    public void canGetSearch() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);   
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);   
        
        when(request.getParameter("username")).thenReturn("abhayakr");
        when(request.getParameter("password")).thenReturn("abby");
        when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(dispatcher);

        
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new getSearchHistoryServlet().doPost(request, response);

        verify(dispatcher).forward(request, response);
        verify(request, atLeast(1)).getParameter("username");
        verify(request, atLeast(1)).getParameter("password");
        
        assertTrue(stringWriter.toString().contains("search history returned"));
        
        writer.flush(); 
    }

}
