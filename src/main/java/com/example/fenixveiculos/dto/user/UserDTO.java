package com.example.fenixveiculos.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.fenixveiculos.model.UserModel.UserGender;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

@Value
@NonFinal
@SuperBuilder
@NoArgsConstructor(force = true)
public abstract class UserDTO {

	@NotBlank(message = "Email is required")
	private final String email;

	@NotBlank(message = "Username is required")
	private final String username;

	@NotBlank(message = "Firstname is required")
	private final String firstname;

	@NotBlank(message = "Lastname is required")
	private final String lastname;

	@NotNull(message = "Gender is required")
	private UserGender gender;

}
