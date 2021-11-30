package com.example.fenixveiculos.dto.car;

import javax.validation.constraints.NotBlank;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

@Value
@NonFinal
@SuperBuilder
@NoArgsConstructor(force = true)
public abstract class CarBrandDTO {

	@NotBlank(message = "Name is required")
	private final String name;

}
