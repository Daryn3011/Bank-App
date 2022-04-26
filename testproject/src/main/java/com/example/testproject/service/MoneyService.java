package com.example.testproject.service;

import java.util.Date;

import com.example.testproject.Entity.Service;
import com.example.testproject.models.ServiceRequest;
import com.example.testproject.Repository.ServiceRepository;

import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class MoneyService {

    @Autowired
    ServiceRepository serviceRepository;

    public Service create(ServiceRequest request) {
        request.setDate(new Date());
        return serviceRepository.save(new Service(request));
    }
}
