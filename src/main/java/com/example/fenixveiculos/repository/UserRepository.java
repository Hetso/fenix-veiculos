package com.example.fenixveiculos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.fenixveiculos.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>,
		JpaSpecificationExecutor<UserModel> {

	Optional<UserModel> findByUsernameOrEmail(String username, String email);

	@Modifying
	@Query(value = "UPDATE user SET is_active = :isActive WHERE id = :id", nativeQuery = true)
	public int updateUserActiveStatus(@Param("id") long id,
			@Param("isActive") boolean isActive);

	@Modifying
	@Query(value = "UPDATE user SET password = :password WHERE id = :id", nativeQuery = true)
	public int updateUserPassword(@Param("id") long id,
			@Param("password") String password);
}
