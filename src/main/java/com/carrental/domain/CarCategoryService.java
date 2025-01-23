package com.carrental.domain;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.carrental.domain.model.CarCategory;
import com.carrental.domain.model.CarCategoryDetails;
import com.carrental.domain.model.CarCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarCategoryService {

	private final CarCategoryRepository carCategoryRepository;

	public List<CarCategoryDetails> getCarsByCategoryId(Long categoryId) {
		// 카테고리 ID에 따른 CarCategory 목록 조회
		List<CarCategory> carCategories = carCategoryRepository.findByCategoryId(categoryId);

		// 자동차별로 카테고리 정보 포함하여 반환
		return carCategories.stream()
			.collect(Collectors.groupingBy(CarCategory::getCar))
			.entrySet()
			.stream()
			.map(entry -> {
				var car = entry.getKey();
				var categories = entry.getValue().stream()
					.map(carCategory -> carCategory.getCategory().getName())
					.toList();

				return new CarCategoryDetails(car, categories);
			})
			.toList();
	}
}

