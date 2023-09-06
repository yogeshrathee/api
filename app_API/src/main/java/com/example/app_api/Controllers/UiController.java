package com.example.app_api.Controllers;

import com.example.app_api.Models.Customer;
import com.example.app_api.Services.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UiController {

    @Autowired
    private ApiService apiService;

    @GetMapping("/login")
    public String login() {
        return "login"; // Assuming you have a login.html template
    }

    @GetMapping("/customer-list")
    public String customerList(Model model) {
        // Fetch customer data from the API
        List<Customer> customers = apiService.getCustomerList();

        // Add the customer data to the model
        model.addAttribute("customers", customers);

        return "customer-list"; // Assuming you have a customer-list.html template
    }

    @GetMapping("/add-customer")
    public String addCustomer() {
        return "add-customer"; // Assuming you have an add-customer.html template
    }
}
