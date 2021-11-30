package com.example.fenixveiculos.controller.v1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.fenixveiculos.dto.car.CarBrandRequestDTO;
import com.example.fenixveiculos.dto.car.CarBrandResponseDTO;
import com.example.fenixveiculos.dto.car.CarFullResponseDTO;
import com.example.fenixveiculos.dto.car.CarImageRequestDTO;
import com.example.fenixveiculos.dto.car.CarImageUploadResponseDTO;
import com.example.fenixveiculos.dto.car.CarRequestDTO;
import com.example.fenixveiculos.dto.file.FileUploadResponseDTO;
import com.example.fenixveiculos.service.CarService;
import com.example.fenixveiculos.service.FileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "Cars")
@RequestMapping(value = "/api/v1/cars", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CarController {

	private final CarService carService;
	private final FileService fileService;

// -- CARS

	@GetMapping
	@Operation(summary = "Get all cars (with simple search filter)")
	public ResponseEntity<List<CarFullResponseDTO>> getAllCars(
			@RequestParam(name = "simpleSearch", required = false) String simpleSearch) {

		if (simpleSearch != null && !simpleSearch.isEmpty()) {
			return ResponseEntity.ok(carService.findAllCars(simpleSearch));
		}

		return ResponseEntity.ok(carService.findAllCars());
	}

	@GetMapping(value = "/{id:[0-9]+}")
	@Operation(summary = "Get a car by id")
	public ResponseEntity<CarFullResponseDTO> getCarById(
			@PathVariable("id") Long id) {
		CarFullResponseDTO car = carService.findCarById(id);

		if (car != null) {
			return ResponseEntity.ok(car);
		}

		return ResponseEntity.noContent().build();
	}

	@PostMapping
	@Operation(summary = "Create a new car")
	public ResponseEntity<CarFullResponseDTO> createCar(
			@Validated @RequestBody CarRequestDTO dto) {

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(carService.createCar(dto));
	}

	@PutMapping(value = "/{id:[0-9]+}")
	@Operation(summary = "Update a car by id")
	public ResponseEntity<CarFullResponseDTO> updateCar(
			@PathVariable("id") Long id,
			@Validated @RequestBody CarRequestDTO dto) {

		if (carService.findCarById(id) != null) {
			return ResponseEntity.ok(carService.updateCar(dto, id));
		}

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Invalid car id");
	}

	@DeleteMapping(value = "/{id:[0-9]+}")
	@Operation(summary = "Delete a car by id")
	public ResponseEntity<String> deleteCar(@PathVariable("id") Long id) {

		if (carService.findCarById(id) != null) {
			carService.deleteCar(id);
			return ResponseEntity.noContent().build();
		}

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Invalid car id");
	}

	@PostMapping(value = "/{id:[0-9]+}/images")
	@Operation(summary = "Upload car images and cover image")
	public ResponseEntity<CarImageUploadResponseDTO> uploadCarImages(
			@PathVariable("id") Long id,
			@RequestParam(value = "coverImage") MultipartFile coverImage,
			@RequestParam(value = "images", required = false) MultipartFile[] images) {

		String imageName = null;
		List<String> imagesNames = new ArrayList<String>();

		final String CAR_PATH_SUFIX = "/cars";

		boolean canUpload = true;
		boolean hasImages = false;

		if (images != null && images.length > 0
				&& images[0].getOriginalFilename() != null
				&& !images[0].getOriginalFilename().isBlank()) {

			canUpload = fileService.isImagesValid(images);
			hasImages = true;
		}

		// Impede que faça upload do coverImage e depois dê erro nas images
		if (canUpload) {
			imageName = fileService.uploadImage(coverImage, CAR_PATH_SUFIX);

			carService.updateCarImageCover(imageName, id);

			if (hasImages) {
				imagesNames = fileService.uploadImages(images, CAR_PATH_SUFIX);

				imagesNames.forEach(image -> {
					carService.saveCarImage(CarImageRequestDTO.builder()
							.carId(id).imagePath(image).build());
				});
			}

			return ResponseEntity.ok(CarImageUploadResponseDTO.builder()
					.coverImage(imageName)
					.images(imagesNames.stream()
							.map(name -> FileUploadResponseDTO.builder()
									.fileName(name).build())
							.collect(Collectors.toList()))
					.build());
		}

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Invalid images or cover image");
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
			@Validated @RequestBody CarBrandRequestDTO dto) {

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
			@Validated @RequestBody CarBrandRequestDTO dto) {

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

// -- ALL 

	@GetMapping("/images/{fileName}")
	@Operation(summary = "Get a car or brand image file")
	public ResponseEntity<Resource> getImageFile(
			@PathVariable("fileName") String fileName,
			HttpServletRequest request) {
		Resource image = fileService.getImageAsResource(fileName, "/cars");

		if (image != null) {
			String contentType = null;
			try {
				contentType = request.getServletContext()
						.getMimeType(image.getFile().getAbsolutePath());
			} catch (IOException ex) {
			}

			// Fallback to the default content type if type could not be
			// determined
			if (contentType == null) {
				contentType = "application/octet-stream";
			}

			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(contentType))
					.header(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=\"" + image.getFilename()
									+ "\"")
					.body(image);
		}

		return ResponseEntity.notFound().build();
	}
}
