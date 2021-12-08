package com.example.fenixveiculos.dto.user;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = false)
public class UserResponseDTO extends UserDTO {

	private final Long id;
	private final boolean isActive;
}
