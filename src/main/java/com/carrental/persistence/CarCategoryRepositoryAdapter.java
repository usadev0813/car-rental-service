package com.carrental.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

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
}
