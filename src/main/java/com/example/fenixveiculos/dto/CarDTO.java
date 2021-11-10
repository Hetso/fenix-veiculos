package com.example.fenixveiculos.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {

	private String brand;
	private String model;
	private Integer year;
	private BigDecimal price;
	private String color;
	private String description;
	private String imagePath;

}
