package com.example.fenixveiculos.dto.car;

import javax.validation.constraints.NotNull;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@NoArgsConstructor(force = true)
public class CarBrandMoveDTO {

	@NotNull(message = "From brand id is required")
	private Long fromBrandId;

	@NotNull(message = "To brand id is required")
	private Long toBrandId;
}
