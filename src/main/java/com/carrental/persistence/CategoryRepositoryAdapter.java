package com.carrental.persistence;

import java.util.List;
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
	public Optional<Category> findById(Long id) {
		return categoryJpaRepository.findById(id);
	}

	@Override
	public List<Category> findAllById(List<Long> categoryIds) {
		return categoryJpaRepository.findAllById(categoryIds);
	}
}
