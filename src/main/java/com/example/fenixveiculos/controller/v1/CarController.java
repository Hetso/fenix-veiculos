package com.example.fenixveiculos.controller.v1;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;

import com.example.fenixveiculos.dto.CarBrandRequestDTO;
import com.example.fenixveiculos.dto.CarBrandResponseDTO;
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

// -- CARS

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
	public ResponseEntity<CarModel> getCarById(
			@PathVariable("id") Long id) {
		Optional<CarModel> car = carService.findCarById(id);

		if (car.isPresent()) {
			return ResponseEntity.ok(car.get());
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

// -- BRANDS

	@GetMapping(value = "/brands")
	@Operation(summary = "Get all car brands")
	public ResponseEntity<List<CarBrandResponseDTO>> getAllCarBrands() {

		return ResponseEntity.ok(carService.findAllBrands());
	}

	@GetMapping(value = "/brands/{id:[0-9]+}")
	@Operation(summary = "Get a car brand by id")
	public ResponseEntity<CarBrandResponseDTO> getCarBrandById(
			@PathVariable("id") Long id) {

		CarBrandResponseDTO brand = carService.findBrandById(id);

		if (brand != null) {
			return ResponseEntity.ok(brand);
		}

		return ResponseEntity.noContent().build();

	}

	@PostMapping(value = "/brands")
	@Operation(summary = "Create a new brand")
	public ResponseEntity<CarBrandResponseDTO> createCarBrand(
			@RequestBody CarBrandRequestDTO dto) {

		if (carService.existBrand(dto.getName())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"This brand already exist");
		}

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(carService.createBrand(dto));
	}

	@PutMapping(value = "/brands/{id:[0-9]+}")
	@Operation(summary = "Update a car brand by id")
	public ResponseEntity<CarBrandResponseDTO> updateCarBrand(
			@PathVariable("id") Long id,
			@RequestBody CarBrandRequestDTO dto) {

		if (carService.findBrandById(id) != null) {
			return ResponseEntity.ok(carService.updateBrand(dto, id));
		}

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Invalid car brand id");
	}

	@DeleteMapping(value = "/brands/{id:[0-9]+}")
	@Operation(summary = "Delete a car brand by id")
	public ResponseEntity<String> deleteCarBrand(@PathVariable("id") Long id) {

		if (carService.findBrandById(id) != null) {
			carService.deleteBrand(id);
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.badRequest().body("Invalid car brand ID");
	}
}
