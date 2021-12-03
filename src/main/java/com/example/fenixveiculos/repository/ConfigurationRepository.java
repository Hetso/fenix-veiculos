package com.example.fenixveiculos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.fenixveiculos.model.ConfigurationModel;
import com.example.fenixveiculos.model.ConfigurationModel.ConfigurationKey;

@Repository
public interface ConfigurationRepository
		extends JpaRepository<ConfigurationModel, ConfigurationKey> {

	@Modifying
	@Query(value = "INSERT IGNORE INTO configuration (`key`, `value`) VALUES (:key, :value)", nativeQuery = true)
	public int insert(
			@Param("key") String key, @Param("value") String value);

}
