package com.example.fenixveiculos.dto.car;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

@Value
@NonFinal
@SuperBuilder
@NoArgsConstructor(force = true)
public abstract class CarDTO {

	@NotBlank(message = "Model is required")
	private final String model;

	@NotNull(message = "Year is required")
	private final Integer year;

	@NotNull(message = "Price is required")
	private final BigDecimal price;

	@NotBlank(message = "Price is required")
	private final String color;

	private final String description;

}
