package com.carrental.domain.model;

import java.util.Optional;

public interface CategoryRepository {
	Optional<Category> findById(Long id);
}
