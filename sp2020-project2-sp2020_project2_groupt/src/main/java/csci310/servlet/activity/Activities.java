package csci310.servlet.activity;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Activities {

	public Activities() {
		// TODO Auto-generated constructor stub
	}


//	private static String filenamemac = "src//main//java//csci310//servlet//activity//activities.txt";
//
//	private static String filename = "\\src\\main\\java\\csci310\\servlet\\activity\\activities.txt";

	private static String filename = "src/main/java/csci310/servlet/activity/activities.txt";
		
	public static List<String> getActivityList(){
//		System.out.println("pwd: " + System.getProperty("user.dir"));
		File file = new File(filename); 
		List<String> activityList = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			  
			String st = "";
			br.readLine();
			st = br.readLine(); // hot:
			while (!st.equals("temperate:")) {
				activityList.add(st);
				st = br.readLine();
			}
			st = br.readLine();
			while (!st.equals("cold:")) {
				activityList.add(st);
				st = br.readLine();
			}
			while ((st = br.readLine()) != null) {
				activityList.add(st);
			}
		}
		catch(IOException e) {
			System.out.println(e);
		}
		return activityList;
	}

	public static String getCategory(String activity) {
		File file = new File(filename); 
		try {
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			  
			String st = "";
		
			br.readLine(); // hot:
			while (!st.equals("temperate:")) {
				st = br.readLine();
				if(st.equals(activity)) {
					return "hot";
				}
			}
			br.readLine();
			while (!st.equals("cold:")) {
				st = br.readLine();
				if(st.equals(activity)) {
					return "temperate";
				}
			}
			br.readLine();
			while ((st = br.readLine()) != null) {
				if(st.equals(activity)) {
					return "cold";
				}
			}
		}
		catch(IOException e) {
			System.out.println(e);
		}
		return "none";
	}
}
