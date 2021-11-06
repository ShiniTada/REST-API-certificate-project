package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuerySpecification {
    private String tagName;
    private double price;
    private int duration;
    private String certificateName;
    private String description;
    private List<String> order;
}
