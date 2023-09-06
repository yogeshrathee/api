package com.example.app_api.Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {
    private String firstName;
    private String lastName;
    private String street;
    private String address;
    private String city;
    private String state;
    private String email;
    private String phone;
}
