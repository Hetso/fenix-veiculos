package com.example.fenixveiculos.dto;

import lombok.Data;

@Data
public class AuthenticationResponseDTO {

	private final String token;
	private UserResponseDTO user;

}
