package com.example.fenixveiculos.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.example.fenixveiculos.dto.car.CarBrandFullResponseDTO;
import com.example.fenixveiculos.dto.car.CarBrandRequestDTO;
import com.example.fenixveiculos.dto.car.CarBrandResponseDTO;
import com.example.fenixveiculos.dto.car.CarFullResponseDTO;
import com.example.fenixveiculos.dto.car.CarRequestDTO;
import com.example.fenixveiculos.model.CarBrandModel;
import com.example.fenixveiculos.model.CarModel;
import com.example.fenixveiculos.repository.CarBrandRepository;
import com.example.fenixveiculos.repository.CarRepository;
import com.example.fenixveiculos.utils.MapperUtils;

import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarService {

	private final CarRepository carRepository;
	private final CarBrandRepository brandRepository;

//	-- CARS

	public List<CarFullResponseDTO> findAllCars() {
		return MapperUtils.convert(carRepository.findAll(),
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
	public void deleteCar(long id) {
		carRepository.deleteById(id);
	}

// 	-- BRANDS

	public boolean existBrand(String name) {
		return brandRepository.findByName(name).isPresent();
	}

	public List<CarBrandResponseDTO> findAllBrands() {
		return MapperUtils.convert(brandRepository.findAll(),
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
	public void deleteBrand(long id) {
		brandRepository.deleteById(id);
	}
}
