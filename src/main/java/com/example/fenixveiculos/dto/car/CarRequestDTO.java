package com.example.fenixveiculos.dto.car;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = false)
public class CarRequestDTO extends CarDTO {

	private final Long brandId;
}
