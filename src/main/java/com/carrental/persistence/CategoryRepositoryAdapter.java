package com.carrental.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.carrental.domain.model.Category;
import com.carrental.domain.model.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryRepository {
	private final CategoryJpaRepository categoryJpaRepository;

	@Override
	public Optional<Category> findByName(String name) {
		return categoryJpaRepository.findByName(name);
	}
}
