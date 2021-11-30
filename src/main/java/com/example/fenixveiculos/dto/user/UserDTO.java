package com.example.fenixveiculos.dto.user;

import com.example.fenixveiculos.model.UserModel.UserGender;

import lombok.Data;

@Data
abstract class UserDTO {

	private String email;

	private String username;

	private String firstname;

	private String lastname;

	private UserGender gender;

}
