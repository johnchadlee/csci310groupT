package csci310;

import csci310.LoginServlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.apache.commons.io.FileUtils;


public class AuthenticationServletTest extends Mockito {

@Mock 
HttpServletRequest request;
@Mock 
HttpServletResponse response;
@Mock
HttpSession session;
@Mock
RequestDispatcher rd;

@Before
public void setUp() throws Exception {
	
	MockitoAnnotations.initMocks(this);
}

@Test

public void TestInvalidCode() throws Exception {

	when(request.getRequestDispatcher("main-search.html")).thenReturn(rd);
	when(request.getRequestDispatcher("authentication.html")).thenReturn(rd);
    when(request.getSession()).thenReturn(session);
    
    when(request.getParameter("passcode")).thenReturn("wrongpasscode");//mocking

    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    when(response.getWriter()).thenReturn(writer);//put the response in writer

    new AuthenticationServlet().doPost(request, response);

//    verify(request, atLeast(1)).getParameter("email"); // only if you want to verify username was called...
    writer.flush(); // it may not have been flushed yet...
   System.out.println(stringWriter.toString());
    assertTrue(stringWriter.toString().contains("Incorrect Passcode"));
}



}
    
