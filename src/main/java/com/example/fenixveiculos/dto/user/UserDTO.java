package com.example.fenixveiculos.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.example.fenixveiculos.model.UserModel.UserGender;
import com.example.fenixveiculos.utils.RegexUtils;

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
	@Pattern(regexp = RegexUtils.EMAIL, message = "Invalid email")
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
