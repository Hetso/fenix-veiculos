package com.example.fenixveiculos.dto.car;

import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = false)
public final class CarBrandFullResponseDTO extends CarBrandResponseDTO {

	private Set<CarResponseDTO> cars;

}
