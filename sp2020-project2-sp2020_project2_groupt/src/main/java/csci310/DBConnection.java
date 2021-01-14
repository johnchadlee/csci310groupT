package csci310;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnection {
	
	// TODO : Change these fields for appropriate database

	
	static String dburl = "jdbc:mysql://130.211.227.217:3306/Project2";
	static String dbusername = "groupt";
	static String dbpassword = "project2";

	public static boolean createUser(String username, String password) {
		boolean flag = true;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(dburl, dbusername, dbpassword);
			PreparedStatement ps = con.prepareStatement("insert into User(username, password) values (?, ?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.execute();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	public static boolean checkfirstlogin(String username, String password) {
		boolean flag = true;
		//spec met: all users need to input OTP everytime.
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection con = DriverManager.getConnection(dburl, dbusername, dbpassword);
//			PreparedStatement ps = con.prepareStatement("select * from User where username=? and password=? and totp IS NOT NULL");
//            ps.setString(1, username);
//            ps.setString(2, password);
//            ps.execute();
//            ResultSet rs = ps.executeQuery();
//			if (rs.next()) {
//				flag = true;
//			
//			} else {
//				flag = false;
//			}
//		} catch (Exception e) {
//			flag = false;
//		}
		flag = false; 
		return flag;
	}

	public static boolean validateUser(String username, String password) {
		boolean st = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(dburl, dbusername, dbpassword);
			PreparedStatement ps = con.prepareStatement("select * from User where username=? and password=?");
            ps.setString(1, username);
            ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			st = rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return st;
	}
	
	public static String getSearchHistory(String username, String password) {
		String result = "";
		int id = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(dburl, dbusername, dbpassword);
			PreparedStatement ps = con.prepareStatement("select * from User where username=? and password=?");
            ps.setString(1, username);
            ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				id = rs.getInt("user_id");
				PreparedStatement ps2 = con.prepareStatement("select * from SearchHistory where user_id=?");
				ps2.setInt(1, id);
				ResultSet rs2 = ps2.executeQuery();
				while(rs2.next()){
					result = result + rs2.getString("search");
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void addSearchHistory(String username, String password, String search) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(dburl, dbusername, dbpassword);
			PreparedStatement ps = con.prepareStatement("select * from User where username=? and password=?");
            ps.setString(1, username);
            ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				int id = rs.getInt("user_id");
				PreparedStatement ps2 = con.prepareStatement("insert into SearchHistory(search, user_id) values (?, ?)");
				ps2.setString(1, search);
				ps2.setString(2, Integer.toString(id));
				ps2.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
