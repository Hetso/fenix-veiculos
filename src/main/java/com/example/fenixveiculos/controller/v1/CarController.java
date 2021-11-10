package com.example.fenixveiculos.controller.v1;

import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fenixveiculos.dto.CarDTO;
import com.example.fenixveiculos.model.CarModel;
import com.example.fenixveiculos.service.CarService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "Cars")
@RequestMapping(value = "/api/v1/cars", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CarController {

	private final CarService carService;

	@GetMapping
	@Operation(summary = "Get all cars (with simple search filter)")
	public ResponseEntity<List<CarModel>> getAllCars(
			@RequestParam(name = "simpleSearch", required = false) String simpleSearch) {

		if (simpleSearch != null && !simpleSearch.isEmpty()) {
			return ResponseEntity.ok(carService.findAllCars(simpleSearch));
		}

		return ResponseEntity.ok(carService.findAllCars());
	}

	@GetMapping(value = "/{id:[0-9]+}")
	@Operation(summary = "Get a car by id")
	public ResponseEntity<Optional<CarModel>> getCarById(
			@PathVariable("id") Long id) {
		Optional<CarModel> car = carService.findCarById(id);

		if (car.isPresent()) {
			return ResponseEntity.ok(carService.findCarById(id));
		}

		return ResponseEntity.noContent().build();
	}

	@PostMapping
	@Operation(summary = "Create a new car")
	public ResponseEntity<CarModel> createCar(@RequestBody CarDTO carDTO) {
		return ResponseEntity.ok(carService.createCar(carDTO));
	}

	@PutMapping(value = "/{id:[0-9]+}")
	@Operation(summary = "Update a car by id")
	public ResponseEntity<String> updateCar(@PathVariable("id") Long id,
			@RequestBody CarDTO carDTO) {

		if (carService.findCarById(id).isPresent()) {
			return ResponseEntity.ok("Updated");
		}

		return ResponseEntity.badRequest().body("Invalid car ID");
	}

	@DeleteMapping(value = "/{id:[0-9]+}")
	@Operation(summary = "Delete a car by id")
	public ResponseEntity<String> deleteCar(@PathVariable("id") Long id) {

		if (carService.findCarById(id).isPresent()) {
			carService.deleteCar(id);
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.badRequest().body("Invalid car ID");
	}
}
