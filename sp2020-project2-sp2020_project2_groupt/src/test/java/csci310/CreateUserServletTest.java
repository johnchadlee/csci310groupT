package csci310;

import static org.junit.Assert.assertTrue;

import java.io.*;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.apache.commons.io.FileUtils;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class CreateUserServletTest extends Mockito{
	
//	 @Mock
//	 RequestDispatcher dispatcher;
//	 
//	 @Before
//	 protected void setUp() throws Exception {
//	  MockitoAnnotations.initMocks(this);
//	 }

    @Test
    public void canCreateAndLoginUser() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);   
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);   
        
        when(request.getParameter("email")).thenReturn("pearl");
        when(request.getParameter("password")).thenReturn("pearl123");
        when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(dispatcher);

        
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new CreateUserServlet().doPost(request, response);

        verify(dispatcher).forward(request, response);
        verify(request, atLeast(1)).getParameter("email");
        verify(request, atLeast(1)).getParameter("password");
        
        assertTrue(stringWriter.toString().contains("created user"));
        assertTrue(stringWriter.toString().contains("pearl"));
        
        writer.flush(); 
    }

    @Test
    public void invalidUser() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class); 
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);   
        HttpSession session = mock(HttpSession.class);

        when(request.getParameter("username")).thenReturn("pearml");
        when(request.getParameter("password")).thenReturn("password123");
        when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        
        new UserLoginServlet().doPost(request, response);
        assertTrue(stringWriter.toString().contains("false"));
    }
    
    @Test
    public void incorrectPassword() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);  
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);   
        HttpSession session = mock(HttpSession.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        
        when(request.getParameter("username")).thenReturn("ppiyawiroj");
        when(request.getParameter("password")).thenReturn("wrong-password");
        when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        
        new UserLoginServlet().doPost(request, response);
        
        assertTrue(stringWriter.toString().contains("false"));
        writer.flush(); 
    }
    
    @Test
    public void sqlInjectionFail() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class); 
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);   
        HttpSession session = mock(HttpSession.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        
        when(request.getParameter("username")).thenReturn("' or ''='");
        when(request.getParameter("password")).thenReturn("' or ''='");
        when(request.getSession()).thenReturn(session);
        
        when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(dispatcher);
        new ValidateUserServlet().doPost(request, response);
        
        assertTrue(stringWriter.toString().contains("false"));
        writer.flush(); 
    }
}