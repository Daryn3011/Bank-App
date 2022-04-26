package com.example.testproject.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.testproject.models.ServiceRequest;

@Entity
@Table(name = "bank_services")
@Data
@NoArgsConstructor
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nameOfService;
    private BigInteger amountOfMoney;
    private Date date;

    public Service(ServiceRequest serviceRequest) {
        this.nameOfService = serviceRequest.getNameOfService();
        this.amountOfMoney = serviceRequest.getAmountOfMoney();
        this.date = serviceRequest.getDate();
    }
}
