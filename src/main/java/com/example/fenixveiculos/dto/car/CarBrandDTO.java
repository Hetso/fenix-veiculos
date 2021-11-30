package com.example.fenixveiculos.dto.car;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

@Value
@NonFinal
@SuperBuilder
@NoArgsConstructor(force = true)
public abstract class CarBrandDTO {

	private final String name;
}
