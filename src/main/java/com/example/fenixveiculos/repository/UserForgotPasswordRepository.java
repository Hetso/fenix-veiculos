package com.example.fenixveiculos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.fenixveiculos.model.UserForgotPasswordModel;

public interface UserForgotPasswordRepository
		extends JpaRepository<UserForgotPasswordModel, String> {

	@Query(value = "SELECT * FROM user_forgot_password WHERE user_id = :userId", nativeQuery = true)
	Optional<UserForgotPasswordModel> findByUserId(
			@Param("userId") long userId);

	@Modifying
	@Query(value = "UPDATE user_forgot_password SET token = :token WHERE user_id = :userId", nativeQuery = true)
	int updateToken(@Param("userId") long userId,
			@Param("token") String token);
}
