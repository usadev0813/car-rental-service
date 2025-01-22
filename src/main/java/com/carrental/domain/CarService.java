package com.carrental.domain;

import org.springframework.stereotype.Service;

import com.carrental.domain.model.Car;
import com.carrental.domain.model.CarCreate;
import com.carrental.domain.model.CarRepository;
import com.carrental.domain.model.Category;
import com.carrental.domain.model.CategoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarService {
	private final CarRepository carRepository;
	private final CategoryRepository categoryRepository;

	@Transactional
	public Car create(CarCreate create) {
		// 새로운 자동차 생성
		var newCar = new Car(create);

		// 카테고리를 추가
		create.categories().forEach(categoryId -> {
			Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다: " + categoryId));
			newCar.addCategory(category);
		});

		// 자동차와 카테고리 저장
		return carRepository.save(newCar);
	}

	public boolean isCarAvailable(Long carId) {
		Car car = carRepository.findById(carId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 자동차 입니다: " + carId));
		return car.isRentalable();
	}
}
