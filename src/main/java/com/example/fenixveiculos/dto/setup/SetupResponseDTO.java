package com.example.fenixveiculos.dto.setup;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@NoArgsConstructor(force = true)
public class SetupResponseDTO {

	private final String token;
}
