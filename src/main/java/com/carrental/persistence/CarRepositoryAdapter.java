package com.carrental.persistence;

import org.springframework.stereotype.Repository;

import com.carrental.domain.model.Car;
import com.carrental.domain.model.CarRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CarRepositoryAdapter implements CarRepository {
	private final CarJpaRepository carJpaRepository;

	@Override
	public Car save(Car car) {
		return carJpaRepository.save(car);
	}

	@Override
	public Optional<Car> findById(Long carId) {
		return carJpaRepository.findById(carId);
	}
}
