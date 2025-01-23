package com.carrental.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrental.domain.CarCategoryService;
import com.carrental.presentation.response.CarWithCategoryResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CarCategoryController {

	private final CarCategoryService carCategoryService;

	@GetMapping("/categories/{categoryId}/cars")
	public List<CarWithCategoryResponse> getCarsByCategoryId(@PathVariable Long categoryId) {
		return carCategoryService.getCarsByCategoryId(categoryId).stream()
			.map(carCategoryDetails -> CarWithCategoryResponse.from(carCategoryDetails.car(),
				carCategoryDetails.categories()))
			.toList();
	}
}

