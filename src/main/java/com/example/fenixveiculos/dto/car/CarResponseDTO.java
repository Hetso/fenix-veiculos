package com.example.fenixveiculos.dto.car;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

@Value
@NonFinal
@SuperBuilder
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = false)
public class CarResponseDTO extends CarDTO {

	private final Long id;

}
