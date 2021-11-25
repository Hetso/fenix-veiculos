package com.example.fenixveiculos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.fenixveiculos.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>,
		JpaSpecificationExecutor<UserModel> {

	Optional<UserModel> findByUsernameOrEmail(String username, String email);

}
