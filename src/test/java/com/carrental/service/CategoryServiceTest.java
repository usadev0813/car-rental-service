package com.carrental.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.carrental.domain.CategoryService;
import com.carrental.domain.model.Category;
import com.carrental.domain.model.CategoryRepository;
import com.carrental.testFixture.TestCategory;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

	@InjectMocks
	private CategoryService categoryService;

	@Mock
	private CategoryRepository categoryRepository;

	private List<Long> categoryIds;
	private List<Category> categories;

	@BeforeEach
	void setUp() {
		categoryIds = List.of(1L, 2L);
		categories = List.of(
			new TestCategory(1L, "SUV"),
			new TestCategory(2L, "Compact")
		);
	}

	@Test
	void 카테고리_조회_성공() {
		// given
		when(categoryRepository.findAllById(categoryIds)).thenReturn(categories);

		// when
		List<Category> result = categoryService.getCategoriesByIds(categoryIds);

		// then
		assertThat(result).hasSize(2);
		assertThat(result).extracting(Category::getId).containsExactlyInAnyOrder(1L, 2L);
		verify(categoryRepository).findAllById(categoryIds);
	}

	@Test
	void 존재하지_않는_카테고리_예외_발생() {
		// given
		List<Long> extendedCategoryIds = List.of(1L, 2L, 3L); // 카테고리 ID 중 3번은 존재하지 않음
		when(categoryRepository.findAllById(extendedCategoryIds)).thenReturn(categories);

		// when & then
		assertThatThrownBy(() -> categoryService.getCategoriesByIds(extendedCategoryIds))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("존재하지 않는 카테고리가 포함되어 있습니다: [3]");

		verify(categoryRepository).findAllById(extendedCategoryIds);
	}
}
