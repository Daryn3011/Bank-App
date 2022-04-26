package com.example.testproject.models;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
public class ServiceRequest {
    private String nameOfService;
    private BigInteger amountOfMoney;
    private Date date;
}
