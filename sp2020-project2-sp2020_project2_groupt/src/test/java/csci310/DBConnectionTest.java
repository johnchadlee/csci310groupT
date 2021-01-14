package csci310;

	 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class DBConnectionTest {

	@InjectMocks
	private DBConnection dbConnection;
	@Mock
	private Connection mockConnection;
	@Mock
	private PreparedStatement preparedStatement;
	@Mock
	private Statement mockStatement;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

//	@Test
//	public void testMockDBConnection2() throws Exception {
//		Mockito.when(dbConnection.getConnection()).thenReturn(mockConnection);
//		Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);
//		Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
//		int value = dbConnection.createUser("","");
//		Assert.assertEquals(value, 1);
//	}
	
//	@Test
//	public void testMockDBConnection() throws Exception {
//
//		Mockito.when(DBConnection.getDBConnection()).thenReturn(mockConnection);
//		
//		Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
//		Mockito.when(mockConnection.createStatement().executeUpdate("")).thenReturn(1);
//		int value = dbConnection.executeUpdate("");
//		Assert.assertEquals(value, 1);
//		Mockito.verify(mockConnection.createStatement(), Mockito.times(1));
//	}
	
//	@Test
//	public void testCreateUser() throws Exception {
//		Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
//		Mockito.when(mockConnection.createStatement().executeUpdate("")).thenReturn(1);
//		int value = dbConnection.executeUpdate("");
//		Assert.assertEquals(value, 1);
//		Mockito.verify(mockConnection.createStatement(), Mockito.times(1));
//	}
}
