package com.carrental.domain;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.carrental.domain.model.Car;
import com.carrental.domain.model.CarCreate;
import com.carrental.domain.model.CarRepository;
import com.carrental.domain.model.Category;
import com.carrental.domain.model.RentalStatus;
import com.carrental.presentation.api.response.CarResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarService {
	private final CarRepository carRepository;

	/**
	 * 특정 자동차를 ID로 조회합니다.
	 *
	 * @param carId 조회할 자동차 ID
	 * @return 조회된 자동차 객체
	 * @throws NoSuchElementException 자동차가 존재하지 않을 경우 예외 발생
	 */
	public Car getCar(Long carId) {
		return carRepository.findById(carId)
			.orElseThrow(() -> new NoSuchElementException("자동차가 존재하지 않습니다: " + carId));
	}

	/**
	 * 새로운 자동차를 생성하고 저장합니다.
	 *
	 * @param create     자동차 생성 요청 데이터
	 * @param categories 자동차와 연결할 카테고리 목록
	 * @return 저장된 자동차 객체
	 */
	@Transactional
	public Car create(CarCreate create, List<Category> categories) {
		var newCar = new Car(create);
		categories.forEach(newCar::addCategory); // 카테고리를 자동차에 추가
		return carRepository.save(newCar);
	}

	/**
	 * 특정 자동차가 대여 가능한 상태인지 확인합니다.
	 *
	 * @param carId 확인할 자동차 ID
	 * @return 대여 가능 여부 (true: 가능, false: 불가능)
	 */
	public boolean isCarAvailable(Long carId) {
		return this.getCar(carId).isRentalable();
	}

	/**
	 * 자동차를 조건에 따라 검색합니다.
	 * 옵션들의 값이 null인 경우 전체 검색을 합니다.
	 *
	 * @param manufacturer 자동차 제조사 (옵션)
	 * @param model        자동차 모델명 (옵션)
	 * @param productionYear 자동차 생산년도 (옵션)
	 * @return 검색된 자동차 목록
	 */
	public List<CarResponse> searchCars(String manufacturer, String model, Integer productionYear) {
		List<Car> cars = carRepository.findByCriteria(manufacturer, model, productionYear);
		return cars.stream().map(CarResponse::from).toList();
	}

	/**
	 * 특정 자동차의 렌탈 상태를 업데이트합니다.
	 *
	 * @param carId  상태를 변경할 자동차 ID
	 * @param status 새 렌탈 상태
	 * @throws NoSuchElementException 자동차가 존재하지 않을 경우 예외 발생
	 */
	@Transactional
	public void updateRentalStatus(Long carId, RentalStatus status) throws IllegalArgumentException {
		Car car = this.getCar(carId); // 자동차 조회
		car.updateRentalStatus(status); // 렌탈 상태 업데이트
		carRepository.save(car); // 변경 사항 저장
	}
}
