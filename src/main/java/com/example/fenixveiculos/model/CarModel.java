package com.example.fenixveiculos.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "car", uniqueConstraints = {
		@UniqueConstraint(name = "UniqueBrandModelYearColor", columnNames = {
				"brand", "model", "year", "color" })
})
@NoArgsConstructor
@AllArgsConstructor
public class CarModel {

	public CarModel(long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(name = "brand", nullable = false)
	private String brand;

	@Column(name = "model", nullable = false)
	private String model;

	@Column(name = "year", nullable = false)
	private Integer year;

	@Column(name = "price", nullable = false)
	private BigDecimal price;

	@Column(name = "color", nullable = false)
	private String color;

	@Column(name = "description")
	private String description;

}
