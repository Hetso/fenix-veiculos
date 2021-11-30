package com.example.fenixveiculos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.fenixveiculos.model.CarImageModel;

public interface CarImageRepository
		extends JpaRepository<CarImageModel, String>,
		JpaSpecificationExecutor<CarImageModel> {

}
