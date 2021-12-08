package com.example.fenixveiculos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fenixveiculos.model.UserForgotPasswordModel;

public interface UserForgotPasswordRepository
		extends JpaRepository<UserForgotPasswordModel, String> {

}
