package com.example.fenixveiculos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.fenixveiculos.model.CarBrandModel;

public interface CarBrandRepository extends JpaRepository<CarBrandModel, Long>,
		JpaSpecificationExecutor<CarBrandModel> {

	Optional<CarBrandModel> findByName(String name);
}
