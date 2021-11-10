package com.example.fenixveiculos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CarDTO {

    private final String brand;
    private final String model;
    private final Integer year;
    private final BigDecimal price;
    private final String color;
    private final String description;
    private final String imagePath;

}
