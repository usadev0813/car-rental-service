package com.carrental.domain;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.carrental.domain.model.Car;
import com.carrental.domain.model.CarCreate;
import com.carrental.domain.model.CarRepository;
import com.carrental.domain.model.Category;
import com.carrental.domain.model.RentalStatus;
import com.carrental.presentation.response.CarResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarService {
	private final CarRepository carRepository;

	public Car getCar(Long carId) {
		return carRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("자동차가 존재하지 않습니다: " + carId));
	}

	@Transactional
	public Car create(CarCreate create, List<Category> categories) {
		// 새로운 자동차 생성
		var newCar = new Car(create);

		// 카테고리를 추가
		categories.forEach(newCar::addCategory);

		// 자동차와 카테고리 저장
		return carRepository.save(newCar);
	}

	public boolean isCarAvailable(Long carId) {
		return this.getCar(carId).isRentalable();
	}

	public List<CarResponse> searchCars(String manufacturer, String model, Integer productionYear) {
		List<Car> cars = carRepository.findByCriteria(manufacturer, model, productionYear);
		return cars.stream()
			.map(CarResponse::from)
			.toList();
	}

	@Transactional
	public void updateRentalStatus(Long carId, RentalStatus status) {
		Car car = this.getCar(carId);
		car.updateRentalStatus(status);
		carRepository.save(car);
	}
}
