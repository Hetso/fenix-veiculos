package com.example.fenixveiculos.dto.car;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = false)
public class CarRequestDTO extends CarDTO {

	@NotNull(message = "Brand ID is required")
	@Min(value = 1, message = "Brand id invalid")
	private final Long brandId;

}
