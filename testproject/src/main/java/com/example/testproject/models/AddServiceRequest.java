package com.example.testproject.models;

import lombok.Data;

import java.util.List;

@Data
public class AddServiceRequest {
    private Integer userId;
    private List<Integer> serviceId;
}
