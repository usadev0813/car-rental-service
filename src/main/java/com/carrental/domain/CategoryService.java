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

	/**
	 * 주어진 ID 목록에 해당하는 카테고리를 조회합니다.
	 *
	 * @param categoryIds 조회할 카테고리 ID 목록
	 * @return 조회된 카테고리 목록
	 * @throws IllegalArgumentException 일부 ID에 해당하는 카테고리가 없을 경우 예외 발생
	 */
	public List<Category> getCategoriesByIds(List<Long> categoryIds) {
		List<Category> categories = categoryRepository.findAllById(categoryIds);

		// 조회된 카테고리의 수가 요청된 ID의 수와 다를 경우 예외 처리
		if (categories.size() != categoryIds.size()) {
			// 존재하지 않는 카테고리 ID 목록 추출
			List<Long> missingIds = categoryIds.stream()
				.filter(id -> categories.stream().noneMatch(category -> category.getId().equals(id)))
				.toList();

			// 예외 발생
			throw new IllegalArgumentException("존재하지 않는 카테고리가 포함되어 있습니다: " + missingIds);
		}

		return categories;
	}
}
