package com.carrental.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.carrental.domain.CarService;
import com.carrental.domain.model.Car;
import com.carrental.domain.model.CarCreate;
import com.carrental.domain.model.CarRepository;
import com.carrental.domain.model.Category;
import com.carrental.domain.model.RentalStatus;
import com.carrental.presentation.response.CarResponse;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

	@InjectMocks
	private CarService carService;

	@Mock
	private CarRepository carRepository;

	@Test
	void 자동차_조회_성공() {
		Long carId = 1L;
		Car car = new Car("Hyundai", "Tucson", 2023);
		when(carRepository.findById(carId)).thenReturn(Optional.of(car));

		Car result = carService.getCar(carId);

		assertThat(result).isEqualTo(car);
		verify(carRepository).findById(carId);
	}

	@Test
	void 자동차_조회_실패_존재하지_않는_자동차() {
		Long carId = 1L;
		when(carRepository.findById(carId)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> carService.getCar(carId))
			.isInstanceOf(NoSuchElementException.class)
			.hasMessageContaining("자동차가 존재하지 않습니다");
		verify(carRepository).findById(carId);
	}

	@Test
	void 자동차_생성_성공() {
		CarCreate carCreate = new CarCreate("Hyundai", "Tucson", 2023, List.of(1L, 2L));
		List<Category> categories = List.of(new Category("SUV"), new Category("Compact"));
		Car car = new Car(carCreate);
		when(carRepository.save(any(Car.class))).thenReturn(car);

		Car result = carService.create(carCreate, categories);

		assertThat(result).isEqualTo(car);
		verify(carRepository).save(any(Car.class));
	}

	@Test
	void 자동차_대여_가능_확인() {
		Long carId = 1L;
		Car car = new Car("Hyundai", "Tucson", 2023);
		car.updateRentalStatus(RentalStatus.AVAILABLE);
		when(carRepository.findById(carId)).thenReturn(Optional.of(car));

		boolean result = carService.isCarAvailable(carId);

		assertThat(result).isTrue();
	}

	@Test
	void 자동차_대여_불가_확인() {
		Long carId = 1L;
		Car car = new Car("Hyundai", "Tucson", 2023);
		car.updateRentalStatus(RentalStatus.UNAVAILABLE);
		when(carRepository.findById(carId)).thenReturn(Optional.of(car));

		boolean result = carService.isCarAvailable(carId);

		assertThat(result).isFalse();
	}

	@Test
	void 자동차_검색_성공() {
		String manufacturer = "Hyundai";
		String model = null;
		Integer productionYear = null;
		List<Car> cars = List.of(new Car("Hyundai", "Tucson", 2023));
		when(carRepository.findByCriteria(manufacturer, model, productionYear)).thenReturn(cars);

		List<CarResponse> result = carService.searchCars(manufacturer, model, productionYear);

		assertThat(result).hasSize(1);
		assertThat(result.get(0).manufacturer()).isEqualTo("Hyundai");
		verify(carRepository).findByCriteria(manufacturer, model, productionYear);
	}

	@Test
	void 자동차_렌탈_상태_업데이트() {
		Long carId = 1L;
		RentalStatus newStatus = RentalStatus.UNAVAILABLE;
		Car car = new Car("Hyundai", "Tucson", 2023);
		when(carRepository.findById(carId)).thenReturn(Optional.of(car));
		when(carRepository.save(any(Car.class))).thenReturn(car);

		carService.updateRentalStatus(carId, newStatus);

		assertThat(car.getRentalStatus()).isEqualTo(newStatus);
		verify(carRepository).save(car);
	}
}
