package com.nineleaps.zuul.gateway.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping(value ="/say")
    public String sayHello(){
        return "Hello";
    }

    @GetMapping(value ="/basicAuth")
    public String authenticateUser(){
        return "You're authenticated successfully !!";
    }

}
