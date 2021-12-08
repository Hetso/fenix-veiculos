package com.example.fenixveiculos.dto.user;

import javax.validation.constraints.NotBlank;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@NoArgsConstructor(force = true)
public class ForgotPasswordDTO {

	@NotBlank(message = "Login is required")
	private final String login;
}
