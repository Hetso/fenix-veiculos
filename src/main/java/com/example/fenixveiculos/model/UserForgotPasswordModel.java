package com.example.fenixveiculos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_forgot_password")
@NoArgsConstructor
@AllArgsConstructor
public class UserForgotPasswordModel {

	@Id
	@Column(name = "token", nullable = false)
	private String token;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private UserModel user;

}
