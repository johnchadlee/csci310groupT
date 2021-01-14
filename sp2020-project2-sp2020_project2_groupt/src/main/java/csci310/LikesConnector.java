package csci310;

import java.io.*;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LikesConnector{
	/**
	 * 
	 */
	ArrayList<String> citynames;
	ArrayList<String> countrynames;
	ArrayList<Integer> likes;
	
//	public static void main(String args[]) {
//		LikesConnector lc = new LikesConnector();
//		lc.addLikes("Paris");
//		lc.addLikes("Paris");
//		lc.addLikes("Seoul");
//		System.out.println("Paris has this many likes: "+Integer.toString(lc.getLikes("Paris")));
//		System.out.println("Seoul has this many likes: "+Integer.toString(lc.getLikes("Seoul")));
//	}

	public LikesConnector() {
		citynames = new ArrayList<String>();
		countrynames = new ArrayList<String>();
		likes = new ArrayList<Integer>();
		File file;
		BufferedReader br;
		try {
			file = new File("src/main/java/csci310/cities.txt");
			br = new BufferedReader(new FileReader(file));
			
			String st; 
			while ((st = br.readLine()) != null) {
				String[] s = st.split(",");
				citynames.add(s[0]);
				countrynames.add(s[1]);
				likes.add(Integer.parseInt(s[2]));
			}
			br.close();
		} catch(FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		} catch(IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}
	
	public void addLikes(String cityName) {
		int i = findIndex(cityName);
		if(i == -1) {
			System.out.println("City name not found.");
			return;
		}
		likes.set(i, likes.get(i)+1);
		try {
			File file = new File("src/main/java/csci310/cities.txt");
			FileWriter fw = new FileWriter(file);
			for(int z=0;z<citynames.size();z++) {
				String w = citynames.get(z) +","+countrynames.get(z)+","+Integer.toString(likes.get(z)) + "\n";
				if(z==i) {
					System.out.println(w); //debug statement
				}
				fw.write(w);
			}
			fw.close();
		}catch(IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		
		
	}
	
	
	public int getLikes(String cityName) {
		int i = findIndex(cityName);
		if(i == -1) {
			System.out.println("City name not found.");
			return 0;
		}
		return likes.get(i);
	}
	
	public void updateArrayLists() {
		File file;
		BufferedReader br;
		try {
			file = new File("src/main/java/csci310/cities.txt");
			br = new BufferedReader(new FileReader(file));
			
			String st; 
			while ((st = br.readLine()) != null) {
				String[] s = st.split(",");
				citynames.add(s[0]);
				countrynames.add(s[1]);
				likes.add(Integer.parseInt(s[2]));
			}
			br.close();
		} catch(FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		} catch(IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}
	
	public int findIndex(String cityName) {
		int i=0;
		while(i < citynames.size()) {
			if(citynames.get(i).contentEquals(cityName)) {
				break;
			}else {
				i++;
			}
		}
		if(i >= citynames.size()) {
			i = -1; //city name not found in list
		}
		return i;
	}
}
