package com.example.fenixveiculos.dto.user;

import javax.validation.constraints.NotBlank;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@NoArgsConstructor(force = true)
public class UserChangePasswordDTO {

	@NotBlank(message = "Current password is required")
	private final String currentPassword;

	@NotBlank(message = "New password is required")
	private final String newPassword;
}
