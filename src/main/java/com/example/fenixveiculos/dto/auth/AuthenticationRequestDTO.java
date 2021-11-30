package com.example.fenixveiculos.dto.auth;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {

	private String login;

	private String password;

}
