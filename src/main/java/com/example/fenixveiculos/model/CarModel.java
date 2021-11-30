package com.example.fenixveiculos.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "car")
@NoArgsConstructor
@AllArgsConstructor
public class CarModel {

	public CarModel(long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "brand_id", nullable = false, insertable = false, updatable = false)
	private CarBrandModel brand;

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
