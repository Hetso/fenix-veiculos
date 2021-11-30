package com.example.fenixveiculos.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.fenixveiculos.dto.CarBrandFullResponseDTO;
import com.example.fenixveiculos.dto.CarBrandRequestDTO;
import com.example.fenixveiculos.dto.CarBrandResponseDTO;
import com.example.fenixveiculos.dto.CarDTO;
import com.example.fenixveiculos.model.CarBrandModel;
import com.example.fenixveiculos.model.CarModel;
import com.example.fenixveiculos.repository.CarBrandRepository;
import com.example.fenixveiculos.repository.CarRepository;
import com.example.fenixveiculos.utils.MapperUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarService {

	private final CarRepository carRepository;
	private final CarBrandRepository brandRepository;
	private final ModelMapper modelMapper;

//	-- CARS

	public List<CarModel> findAllCars() {
		return carRepository.findAll();
	}

	public List<CarModel> findAllCars(String simpleSearch) {
		return carRepository.findAllBySearch(simpleSearch);
	}

	public Optional<CarModel> findCarById(long id) {
		return carRepository.findById(id);
	}

	@Transactional
	public CarModel createCar(CarDTO carDTO) {
		CarModel carToSave = modelMapper.map(carDTO,
				CarModel.class);
		carToSave.setBrand(new CarBrandModel(carDTO.getBrandId()));

		return carRepository.save(carToSave);
	}

	@Transactional
	public CarModel updateCar(CarDTO carDTO, long id) {
		CarModel carToUpdate = modelMapper.map(carDTO, CarModel.class);
		carToUpdate.setId(id);

		return carRepository.save(carToUpdate);
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
	public CarBrandResponseDTO createBrand(CarBrandRequestDTO dto) {
		return MapperUtils.convert(
				brandRepository
						.save(MapperUtils.convert(dto, CarBrandModel.class)),
				CarBrandResponseDTO.class);
	}

	@Transactional
	public CarBrandResponseDTO updateBrand(CarBrandRequestDTO dto, long id) {
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
