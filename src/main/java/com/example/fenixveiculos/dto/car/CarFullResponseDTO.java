package com.example.fenixveiculos.dto.car;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = false)
public class CarFullResponseDTO extends CarResponseDTO {

	private final CarBrandResponseDTO brand;
}
