package com.carrental.domain.model;

import java.util.List;

public interface CarCategoryRepository {
	List<CarCategory> saveAll(List<CarCategory> carCategories);

	List<CarCategory> findByCategoryId(Long categoryId);

}
