package com.example.fenixveiculos.dto.user;

import javax.validation.constraints.NotBlank;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@NoArgsConstructor(force = true)
public class RecoveryPasswordDTO {

	@NotBlank(message = "Password is required")
	private final String password;

	@NotBlank(message = "token is required")
	private final String token;

}
