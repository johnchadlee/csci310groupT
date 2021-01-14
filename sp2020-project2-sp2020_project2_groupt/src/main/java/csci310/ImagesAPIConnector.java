package csci310;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImagesAPIConnector { 

    private static final String API_KEY = "AIzaSyAjHJK_QQJeQ3HOlP-rHtCEt65I5HgYgCw";
    private static final String SEARCH_ENGINE_IDENTIFIER = "010861374683234688951:jtaglqkbvbh";

    public static List<String> getImageLinks(String searchQuery) {
        String query = "https://www.googleapis.com/customsearch/v1?key=" + API_KEY +
                "&cx=" + SEARCH_ENGINE_IDENTIFIER + "&q=" + searchQuery.replaceAll("\\s","+") +
                "&searchType=image";
        try {
            return getImageLinks((HttpURLConnection) new URL(query).openConnection());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

    }

    // Split into seperate method for dependency injection
    public static List<String> getImageLinks(HttpURLConnection connection) {
        BufferedReader br;
        String line;
        StringBuffer sb = new StringBuffer();

        ArrayList<String> result = new ArrayList<>();

        try {

            //connection setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // get status code
            int status = connection.getResponseCode();
            if (status>299) {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }
            while((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

        } catch(MalformedURLException mue) {
            System.out.println(mue.getMessage());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } finally {
            connection.disconnect();
        }

        try {
            JsonObject response = new JsonParser().parse(sb.toString()).getAsJsonObject();
            JsonArray imageArr = response.getAsJsonArray("items");

            for (int i = 0; i < 5; i++) {
                result.add(imageArr.get(i).getAsJsonObject().get("link").getAsString());
            }


        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
