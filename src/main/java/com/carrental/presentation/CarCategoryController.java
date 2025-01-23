package com.carrental.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrental.domain.CarCategoryService;
import com.carrental.domain.CarService;
import com.carrental.domain.CategoryService;
import com.carrental.presentation.request.UpdateCarCategoriesRequest;
import com.carrental.presentation.response.CarWithCategoryResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CarCategoryController {

	private final CarCategoryService carCategoryService;
	private final CarService carService;
	private final CategoryService categoryService;

	@GetMapping("/categories/{categoryId}/cars")
	public List<CarWithCategoryResponse> getCarsByCategoryId(@PathVariable Long categoryId) {
		return carCategoryService.getCarsByCategoryId(categoryId).stream()
			.map(carCategoryDetails -> CarWithCategoryResponse.from(carCategoryDetails.car(),
				carCategoryDetails.categories()))
			.toList();
	}

	@PatchMapping("/cars/{carId}/categories")
	public void updateCarCategories(@PathVariable Long carId, @RequestBody UpdateCarCategoriesRequest request) {
		var car = carService.getCar(carId);
		var categories = categoryService.getCategoriesByIds(request.categoryIds());
		carCategoryService.updateCarCategories(car, categories);
	}
}

