package com.example.softwareTracker.util;

import com.example.softwareTracker.dto.LoginRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


@Component
public class LdapUtil {

    private final RestTemplate restTemplate = new RestTemplate();


    public boolean validateCredentials(LoginRequestDTO loginRequestDTO) {
        try {
            URL url = new URL("http://localhost:5233/api/Auth/authenticate");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            connection.setRequestProperty("Content-Type", "application/json");

            String requestBody = "{\"username\": \"" + loginRequestDTO.getUsername()+ "\", \"password\": \"" + loginRequestDTO.getPassword() +"\"}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            int responseCode = connection.getResponseCode();
            System.out.println("Response code: " + responseCode);
            connection.disconnect();
            return responseCode == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }
}
