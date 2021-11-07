package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private int duration;
    private ZonedDateTime createDate;
    private ZonedDateTime lastUpdateDate;
    private Set<Tag> tags;
}
