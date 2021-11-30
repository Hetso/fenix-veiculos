package com.example.fenixveiculos.dto.car;

import java.math.BigDecimal;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

@Value
@NonFinal
@SuperBuilder
@NoArgsConstructor(force = true)
public abstract class CarDTO {

	private final String model;
	private final Integer year;
	private final BigDecimal price;
	private final String color;
	private final String description;
	private final String imagePath;

}
