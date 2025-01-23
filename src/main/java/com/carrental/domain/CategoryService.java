package com.carrental.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import com.carrental.domain.model.Category;
import com.carrental.domain.model.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public List<Category> getCategoriesByIds(List<Long> categoryIds) {
		List<Category> categories = categoryRepository.findAllById(categoryIds);

		if (categories.size() != categoryIds.size()) {
			List<Long> missingIds = categoryIds.stream()
				.filter(id -> categories.stream().noneMatch(category -> category.getId().equals(id)))
				.toList();
			throw new IllegalArgumentException("존재하지 않는 카테고리가 포함되어 있습니다: " + missingIds);
		}

		return categories;
	}
}
