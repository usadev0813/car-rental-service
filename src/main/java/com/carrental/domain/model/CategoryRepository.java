package com.carrental.domain.model;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
	Optional<Category> findById(Long id);

	List<Category> findAllById(List<Long> categoryIds);
}
