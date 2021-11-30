package com.example.fenixveiculos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.fenixveiculos.model.CarImageModel;

public interface CarImageRepository
		extends JpaRepository<CarImageModel, String>,
		JpaSpecificationExecutor<CarImageModel> {

	@Modifying
	@Query(value = "DELETE FROM car_image WHERE car_id = :carId AND image_path = :imagePath", nativeQuery = true)
	public int delete(@Param("carId") Long carId,
			@Param("imagePath") String imagePath);

	public Optional<CarImageModel> findByCarIdAndImagePath(long carId,
			String imagePath);
}
