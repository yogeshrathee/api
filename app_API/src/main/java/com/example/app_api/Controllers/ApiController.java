package com.example.app_api.Controllers;

import com.example.app_api.BadRequestException;
import com.example.app_api.Models.Customer;
import com.example.app_api.Services.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerErrorException;


import java.util.Collections;
import java.util.List;

@RestController
public class ApiController {

    @Autowired
    private ApiService apiService;

    @PostMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok(apiService.authenticate());
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomers() {
        try {
            List<Customer> customers = apiService.getCustomerList();
            return ResponseEntity.ok(customers);
        } catch (ServerErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @PostMapping("/customers")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
        try {
            String result = apiService.createCustomer(customer);
            return ResponseEntity.ok(result);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ServerErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/customers/{uuid}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String uuid) {
        try {
            String result = apiService.deleteCustomer(uuid);
            return ResponseEntity.ok(result);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (
                ServerErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/customers/{uuid}")
    public ResponseEntity<String> updateCustomer(@PathVariable String uuid, @RequestBody Customer customer) {
        try {
            String result = apiService.updateCustomer(uuid, customer);
            return ResponseEntity.ok(result);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ServerErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}