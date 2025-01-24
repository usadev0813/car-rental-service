package com.carrental.domain;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.carrental.domain.model.Car;
import com.carrental.domain.model.CarCategory;
import com.carrental.domain.model.CarCategoryDetails;
import com.carrental.domain.model.CarCategoryRepository;
import com.carrental.domain.model.Category;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarCategoryService {

	private final CarCategoryRepository carCategoryRepository;

	/**
	 * 특정 카테고리에 속한 자동차 목록을 조회합니다.
	 *
	 * @param categoryId 조회할 카테고리의 ID
	 * @return 자동차와 해당 자동차가 속한 카테고리 이름 목록을 포함하는 객체 리스트
	 */
	public List<CarCategoryDetails> getCarsByCategoryId(Long categoryId) {
		List<CarCategory> carCategories = carCategoryRepository.findByCategoryId(categoryId);

		// 자동차별로 그룹화하여 각 자동차의 카테고리 이름 목록 생성
		return carCategories.stream()
			.collect(Collectors.groupingBy(CarCategory::getCar)) // 자동차별로 그룹화
			.entrySet()
			.stream()
			.map(entry -> {
				var car = entry.getKey(); // 그룹화된 자동차 객체
				var categories = entry.getValue().stream()
					.map(carCategory -> carCategory.getCategory().getName())
					.toList();

				return new CarCategoryDetails(car, categories);
			})
			.toList();
	}

	/**
	 * 특정 자동차의 카테고리를 업데이트합니다.
	 *
	 * @param car       업데이트할 자동차 객체
	 * @param categories 새로운 카테고리 리스트
	 */
	@Transactional
	public void updateCarCategories(Car car, List<Category> categories) {
		carCategoryRepository.deleteByCar(car);

		List<CarCategory> newCarCategories = categories.stream()
			.map(category -> new CarCategory(car, category))
			.toList();

		carCategoryRepository.saveAll(newCarCategories);
	}
}
