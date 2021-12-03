package com.example.fenixveiculos.dto.setup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.example.fenixveiculos.utils.RegexUtils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@NoArgsConstructor(force = true)
public class SetupRequestDTO {

	@NotNull
	private final SetupUserDTO user;

	@Value
	@AllArgsConstructor
	@NoArgsConstructor(force = true)
	public class SetupUserDTO {

		@NotBlank(message = "Username is required")
		private final String username;

		@NotBlank(message = "Password is required")
		private final String password;

		@NotBlank(message = "Email is required")
		@Pattern(regexp = RegexUtils.EMAIL, message = "Invalid email")
		private final String email;
	}
}
