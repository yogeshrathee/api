package com.example.app_api;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class ApiClient {

    public static void main(String[] args) {
        // Define the access token
        String accessToken = "your_access_token_here"; // Replace with your actual access token

        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Define the URL of the protected resource
        String resourceUrl = "https://api.example.com/resource"; // Replace with your resource URL

        // Create headers with the access token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        // Create an HttpEntity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make an HTTP GET request with the access token
        ResponseEntity<String> response = restTemplate.exchange(resourceUrl, HttpMethod.GET, entity, String.class);

        // Handle the response
        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            System.out.println("Response Body:\n" + responseBody);
        } else {
            System.err.println("Error: " + response.getStatusCode());
            System.err.println("Response Body:\n" + response.getBody());
        }
    }
}

