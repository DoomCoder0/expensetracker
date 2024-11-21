package org.example.utils;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiHandler {
    // change this url if necessary
    private static final String SPRINGBOOT_URL = "http://localhost:8080";

    public enum RequestMethod{POST, GET, PUT, DELETE}

    public static HttpURLConnection fetchApiResponse(String apiPath, RequestMethod requestMethod, JsonObject jsonData){
        try{
            // attempt to create connection
            URL url = new URL(SPRINGBOOT_URL + apiPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // set request method to get
            conn.setRequestMethod(requestMethod.toString());

            if(requestMethod == RequestMethod.POST){
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);

                // Send JSON data to the server by writing it to the output stream
                try (OutputStream os = conn.getOutputStream()) {
                    // Convert the JSON data to a byte array
                    byte[] input = jsonData.toString().getBytes(StandardCharsets.UTF_8);

                    // Write the byte array to the output stream
                    os.write(input, 0, input.length);
                }
            }

            return conn;
        }catch(IOException e){
            e.printStackTrace();
        }

        // could not make connection
        return null;
    }
}
