package csci310.servlet;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;

public class ServletUtils {
    public static JsonObject readPostBodyAsJson(BufferedReader reader) throws IOException {
        // Read from request
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        JsonParser parser = new JsonParser();
        return parser.parse(buffer.toString()).getAsJsonObject();
    }
}
