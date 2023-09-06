package com.example.app_api.Services;

import com.example.app_api.AuthenticationException;
import com.example.app_api.BadRequestException;
import com.example.app_api.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import com.example.app_api.Models.Customer;
import org.springframework.web.server.ServerErrorException;

@Service
public class ApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.auth.url}")
    private String authUrl;

    @Value("${api.auth.username}")
    private String authUsername;

    @Value("${api.auth.password}")
    private String authPassword;

    @Value("${api.assignment.url}")
    private String apiUrl;

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private String authToken;

    public String authenticate() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(authUsername, authPassword);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(authUrl, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            authToken = response.getBody();
            return "Authentication Successful";
        } else {
            authToken = null;
            throw new AuthenticationException("Authentication Failed");
        }
    }

    public String createCustomer(Customer customer) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTH_HEADER, BEARER_PREFIX + getAuthToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Customer> requestEntity = new HttpEntity<>(customer, headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl + "?cmd=create", HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return "Customer Created";
        } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            throw new BadRequestException("Bad Request");
        } else {
            throw new ServerException("Server Error");
        }
    }

    public List<Customer> getCustomerList() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTH_HEADER, BEARER_PREFIX + getAuthToken());

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Customer[]> response = restTemplate.exchange(apiUrl + "?cmd=get_customer_list", HttpMethod.GET, requestEntity, Customer[].class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return Arrays.asList(response.getBody());
        } else {
            throw new ServerException("Server Error");
        }
    }

    public String updateCustomer(String uuid, Customer customer) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTH_HEADER, BEARER_PREFIX + getAuthToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Customer> requestEntity = new HttpEntity<>(customer, headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl + "?cmd=update&uuid=" + uuid, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return "Customer Updated";
        } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new BadRequestException("UUID not found");
        } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            throw new BadRequestException("Bad Request");
        } else {
            throw new ServerException("Server Error");
        }
    }

    public String deleteCustomer(String uuid) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTH_HEADER, BEARER_PREFIX + getAuthToken());

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl + "?cmd=delete&uuid=" + uuid, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return "Customer Deleted";
        } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new BadRequestException("UUID not found");
        } else {
            throw new ServerException("Server Error");
        }
    }

    private String getAuthToken() {
        if (authToken == null) {
            authToken = authenticate();
        }
        return authToken;
    }
}
