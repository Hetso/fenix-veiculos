package com.example.fenixveiculos.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.example.fenixveiculos.dto.car.CarBrandFullResponseDTO;
import com.example.fenixveiculos.dto.car.CarBrandRequestDTO;
import com.example.fenixveiculos.dto.car.CarBrandResponseDTO;
import com.example.fenixveiculos.dto.car.CarFullResponseDTO;
import com.example.fenixveiculos.dto.car.CarImageRequestDTO;
import com.example.fenixveiculos.dto.car.CarRequestDTO;
import com.example.fenixveiculos.model.CarBrandModel;
import com.example.fenixveiculos.model.CarBrandSpecification;
import com.example.fenixveiculos.model.CarBrandStatus;
import com.example.fenixveiculos.model.CarImageModel;
import com.example.fenixveiculos.model.CarModel;
import com.example.fenixveiculos.model.CarSpecification;
import com.example.fenixveiculos.repository.CarBrandRepository;
import com.example.fenixveiculos.repository.CarImageRepository;
import com.example.fenixveiculos.repository.CarRepository;
import com.example.fenixveiculos.utils.FileUtils;
import com.example.fenixveiculos.utils.MapperUtils;

import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarService {

	private final CarRepository carRepository;
	private final CarImageRepository carImageRepository;
	private final CarBrandRepository brandRepository;
	private final FileService fileService;

//	-- CARS

	public List<CarFullResponseDTO> findAllCars(CarBrandStatus brandStatus) {
		return MapperUtils.convert(
				carRepository.findAll(
						CarSpecification.isBrandDisabled(
								brandStatus == CarBrandStatus.DISABLED),
						Sort.by(Sort.Direction.DESC, "id")),
				CarFullResponseDTO.class);
	}

	public List<CarFullResponseDTO> findAllCars(CarBrandStatus brandStatus,
			String simpleSearch) {
		return MapperUtils.convert(
				carRepository.findAllBySearch(simpleSearch,
						brandStatus == CarBrandStatus.DISABLED),
				CarFullResponseDTO.class);
	}

	public CarFullResponseDTO findCarById(long id) {
		return MapperUtils.convert(carRepository.findById(id),
				CarFullResponseDTO.class);
	}

	@Transactional
	public CarFullResponseDTO createCar(@Valid CarRequestDTO dto) {
		CarModel carToSave = MapperUtils.convert(dto,
				CarModel.class);
		carToSave.setBrand(new CarBrandModel(dto.getBrandId()));
		carToSave.setId(null);

		return MapperUtils.convert(carRepository.save(carToSave),
				CarFullResponseDTO.class);
	}

	@Transactional
	public CarFullResponseDTO updateCar(@Valid CarRequestDTO dto, long id) {
		CarModel carToUpdate = MapperUtils.convert(dto,
				CarModel.class);
		carToUpdate.setId(id);

		return MapperUtils.convert(carRepository.save(carToUpdate),
				CarFullResponseDTO.class);
	}

	@Transactional
	public boolean deleteCar(long id) {
		Optional<CarModel> car = carRepository.findById(id);
		if (car.isPresent()) {
			carRepository.deleteById(id);

			// delete car images
			if (car.get().getCoverImage() != null
					&& !car.get().getCoverImage().isEmpty()) {
				fileService.deleteImage(car.get().getCoverImage(),
						FileUtils.CAR_PATH);
			}

			if (car.get().getImages() != null
					&& !car.get().getImages().isEmpty()) {
				car.get().getImages().forEach(image -> {
					fileService.deleteImage(image.getImagePath(),
							FileUtils.CAR_PATH);
				});
			}

			return true;
		}

		return false;
	}

	@Transactional
	public void updateCarCoverImage(String filename, long carId) {
		carRepository.updateCoverImage(filename, carId);
	}

	@Transactional
	public void saveCarImage(@Valid CarImageRequestDTO dto) {
		carImageRepository.save(new CarImageModel(new CarModel(dto.getCarId()),
				dto.getImagePath()));
	}

	@Transactional
	public int deleteCarImage(long carId, String imagePath) {
		return carImageRepository.delete(carId, imagePath);
	}

	public String getActualCarCoverImage(long carId) {
		return carRepository.findCoverImageById(carId);
	}

	public boolean hasCarImage(String imagePath, long carId) {
		return carImageRepository.findByCarIdAndImagePath(carId, imagePath)
				.isPresent();
	}

	@Transactional
	public boolean deleteCarsInBrand(long brandId) {
		Optional<CarBrandModel> brand = brandRepository.findById(brandId);

		if (brand.isPresent()) {

			Set<CarModel> cars = brand.get().getCars();

			if (cars != null && !cars.isEmpty()) {
				// because delete image operation for each car
				cars.forEach(car -> {
					deleteCar(car.getId());
				});
			}

			return true;
		}

		return false;
	}

// 	-- BRANDS

	public boolean existBrand(String name) {
		return brandRepository.findByName(name).isPresent();
	}

	public List<CarBrandResponseDTO> findAllBrands(CarBrandStatus status) {
		return MapperUtils.convert(
				brandRepository.findAll(
						Specification.where(CarBrandSpecification
								.isDisabled(status == CarBrandStatus.DISABLED)),
						Sort.by(Sort.Direction.DESC, "id")),
				CarBrandResponseDTO.class);
	}

	public List<CarBrandResponseDTO> findAllBrands(CarBrandStatus status,
			String search) {
		return MapperUtils.convert(
				brandRepository.findAll(
						Specification.where(CarBrandSpecification
								.name(search)
								.and(CarBrandSpecification.isDisabled(
										status == CarBrandStatus.DISABLED))),
						Sort.by(Sort.Direction.DESC, "id")),
				CarBrandResponseDTO.class);
	}

	public CarBrandFullResponseDTO findBrandById(long id) {
		return MapperUtils.convert(brandRepository.findById(id),
				CarBrandFullResponseDTO.class);

	}

	@Transactional
	public CarBrandResponseDTO createBrand(@Valid CarBrandRequestDTO dto) {
		return MapperUtils.convert(
				brandRepository
						.save(MapperUtils.convert(dto, CarBrandModel.class)),
				CarBrandResponseDTO.class);
	}

	@Transactional
	public CarBrandResponseDTO updateBrand(@Valid CarBrandRequestDTO dto,
			long id) {
		CarBrandModel brandToUpdate = MapperUtils.convert(dto,
				CarBrandModel.class);
		brandToUpdate.setId(id);

		return MapperUtils.convert(brandRepository.save(brandToUpdate),
				CarBrandResponseDTO.class);
	}

	@Transactional
	public boolean deleteBrand(long id) {
		if (brandRepository.findById(id).isPresent()) {
			brandRepository.deleteById(id);
			return true;
		}

		return false;
	}

	@Transactional
	public boolean disableBrand(long id) {
		if (brandRepository.findById(id).isPresent()) {
			brandRepository.updateBrandDisabledStatus(id, true);
			return true;
		}

		return false;
	}

	@Transactional
	public boolean enableBrand(long id) {
		if (brandRepository.findById(id).isPresent()) {
			brandRepository.updateBrandDisabledStatus(id, false);
			return true;
		}

		return false;
	}

	@Transactional
	public void updateBrandLogo(long brandId, String logo) {
		brandRepository.updateLogo(logo, brandId);
	}
}
