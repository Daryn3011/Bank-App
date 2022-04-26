package com.example.testproject.controllers;

import com.example.testproject.Entity.Service;
import com.example.testproject.models.ServiceRequest;
import com.example.testproject.service.MoneyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bankService")
public class MoneyController {
    @Autowired
    MoneyService moneyService;

    @PostMapping
    public Service create(@RequestBody ServiceRequest service){
        return moneyService.create(service);
    }
}
