package com.carrental.domain;

import java.util.List;

import com.carrental.presentation.response.CarResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.carrental.domain.model.Car;
import com.carrental.domain.model.CarCategory;
import com.carrental.domain.model.CarCategoryRepository;
import com.carrental.domain.model.CarCreate;
import com.carrental.domain.model.CarRepository;
import com.carrental.domain.model.Category;
import com.carrental.domain.model.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarService {
	private final CarRepository carRepository;
	private final CarCategoryRepository carCategoryRepository;
	private final CategoryRepository categoryRepository;

	@Transactional
	public Car create(CarCreate create) {
		var newCar = new Car(create);

		List<CarCategory> list = create.categories().stream()
			.map(categoryId -> {
				Category category = categoryRepository.findById(categoryId)
					.orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다: " + categoryId));
				return new CarCategory(newCar, category);
			}).toList();

		carRepository.save(newCar);

		carCategoryRepository.saveAll(list);
		return newCar;
	}

	public boolean isCarAvailable(Long carId) {
		Car car = carRepository.findById(carId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 자동차 입니다: " + carId));
		return car.isRentalable();
	}
}
