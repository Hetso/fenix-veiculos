package com.example.fenixveiculos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.fenixveiculos.model.CarBrandModel;

public interface CarBrandRepository extends JpaRepository<CarBrandModel, Long>,
		JpaSpecificationExecutor<CarBrandModel> {

	Optional<CarBrandModel> findByName(String name);

	@Modifying
	@Query(value = "UPDATE car_brand SET logo = :logo WHERE id = :brandId", nativeQuery = true)
	public int updateLogo(@Param("logo") String logo,
			@Param("brandId") long brandId);
}
