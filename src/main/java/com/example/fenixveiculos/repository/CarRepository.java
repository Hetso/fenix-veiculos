package com.example.fenixveiculos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.fenixveiculos.model.CarModel;

@Repository
public interface CarRepository extends JpaRepository<CarModel, Long>,
		JpaSpecificationExecutor<CarModel> {

	@Query(value = "SELECT * "
			+ "FROM car AS c "
			+ "INNER JOIN car_brand AS cb ON c.brand_id = cb.id "
			+ "WHERE CONCAT(cb.name, ' ', c.model, ' ', c.color, ' ', c.year, ' ', c.description, ' ', c.price) LIKE %:simpleSearch%", nativeQuery = true)
	List<CarModel> findAllBySearch(@Param("simpleSearch") String simpleSearch);

	@Modifying
	@Query(value = "UPDATE car SET cover_image = :coverImage WHERE id = :carId", nativeQuery = true)
	public int updateCoverImage(@Param("coverImage") String coverImage,
			@Param("carId") long carId);

	@Query(value = "SELECT cover_image from car where id = :id", nativeQuery = true)
	public String findCoverImageById(@Param("id") long id);

}
