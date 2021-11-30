package com.example.fenixveiculos.dto.car;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@NoArgsConstructor(force = true)
public class CarImageRequestDTO {

	@NotNull(message = "Car id is required")
	private final Long carId;

	@NotBlank(message = "Image path is required")
	private final String imagePath;
}
