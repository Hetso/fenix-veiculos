package com.example.fenixveiculos.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.fenixveiculos.dto.CarDTO;
import com.example.fenixveiculos.model.CarModel;
import com.example.fenixveiculos.repository.CarRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarService {

	private final CarRepository carRepository;
	private final ModelMapper modelMapper;

	public List<CarModel> findAllCars() {
		return carRepository.findAll();
	}

	public Optional<CarModel> findCarById(long id) {
		return carRepository.findById(id);
	}

	@Transactional
	public CarModel createCar(CarDTO carDTO) {
		CarModel carToSave = modelMapper.map(carDTO,
				CarModel.class);
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
}
