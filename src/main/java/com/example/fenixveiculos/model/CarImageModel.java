package com.example.fenixveiculos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "car_image")
@NoArgsConstructor
@AllArgsConstructor
public class CarImageModel {

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "car_id", nullable = false)
	private CarModel car;

	@Id
	@Column(name = "image_path")
	private String imagePath;
}
