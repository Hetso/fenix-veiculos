package com.example.fenixveiculos.dto.auth;

import com.example.fenixveiculos.dto.user.UserResponseDTO;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@NoArgsConstructor(force = true)
public final class AuthenticationResponseDTO {

	private final String token;
	private final UserResponseDTO user;

}
