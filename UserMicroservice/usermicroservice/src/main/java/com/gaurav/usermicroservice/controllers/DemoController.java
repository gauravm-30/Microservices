package com.gaurav.usermicroservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo-api")
public class DemoController {

    @GetMapping("/demoWelcome")
    ResponseEntity<?> welcome(){
        return new ResponseEntity<>("Welcome to the demo API", HttpStatus.OK);
    }
}
