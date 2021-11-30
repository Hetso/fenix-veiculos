package com.example.fenixveiculos.dto.auth;

import com.example.fenixveiculos.dto.user.UserResponseDTO;

import lombok.Data;

@Data
public class AuthenticationResponseDTO {

	private final String token;
	private UserResponseDTO user;

}
