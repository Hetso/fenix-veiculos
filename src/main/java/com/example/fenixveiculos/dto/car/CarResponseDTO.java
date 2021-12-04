package com.example.fenixveiculos.dto.car;

import java.util.Set;

import com.example.fenixveiculos.model.CarImageModel;

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
	private final String coverImage;
	private final Set<CarImageModel> images;

}
