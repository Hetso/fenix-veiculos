package com.example.fenixveiculos.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Sort;
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
import com.example.fenixveiculos.model.CarImageModel;
import com.example.fenixveiculos.model.CarModel;
import com.example.fenixveiculos.repository.CarBrandRepository;
import com.example.fenixveiculos.repository.CarImageRepository;
import com.example.fenixveiculos.repository.CarRepository;
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

//	-- CARS

	public List<CarFullResponseDTO> findAllCars() {
		return MapperUtils.convert(
				carRepository.findAll(Sort.by(Sort.Direction.DESC, "id")),
				CarFullResponseDTO.class);
	}

	public List<CarFullResponseDTO> findAllCars(String simpleSearch) {
		return MapperUtils.convert(carRepository.findAllBySearch(simpleSearch),
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
		carToUpdate.setCoverImage(carRepository.findCoverImageById(id));

		return MapperUtils.convert(carRepository.save(carToUpdate),
				CarFullResponseDTO.class);
	}

	@Transactional
	public void deleteCar(long id) {
		carRepository.deleteById(id);
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

// 	-- BRANDS

	public boolean existBrand(String name) {
		return brandRepository.findByName(name).isPresent();
	}

	public List<CarBrandResponseDTO> findAllBrands() {
		return MapperUtils.convert(
				brandRepository.findAll(Sort.by(Sort.Direction.DESC, "id")),
				CarBrandResponseDTO.class);
	}

	public List<CarBrandResponseDTO> findAllBrands(String search) {
		return MapperUtils.convert(brandRepository.searchBrands(search),
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
		brandToUpdate.setLogo(brandRepository.findLogoById(id));

		return MapperUtils.convert(brandRepository.save(brandToUpdate),
				CarBrandResponseDTO.class);
	}

	@Transactional
	public void deleteBrand(long id) {
		brandRepository.deleteById(id);
	}

	@Transactional
	public void updateBrandLogo(long brandId, String logo) {
		brandRepository.updateLogo(logo, brandId);
	}
}
