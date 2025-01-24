package com.carrental.persistence.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.carrental.domain.model.Car;
import com.carrental.domain.model.CarCategory;
import com.carrental.domain.model.CarCategoryRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CarCategoryRepositoryAdapter implements CarCategoryRepository {
	private final CarCategoryJpaRepository carCategoryJpaRepository;

	@Override
	public List<CarCategory> saveAll(List<CarCategory> carCategories) {
		return carCategoryJpaRepository.saveAll(carCategories);
	}

	@Override
	public List<CarCategory> findByCategoryId(Long categoryId) {
		return carCategoryJpaRepository.findByCategoryId(categoryId);
	}

	@Override
	public void deleteByCar(Car car) {
		carCategoryJpaRepository.deleteByCar(car);
	}
}
