package com.carrental.domain.model;

import java.util.List;

public record CarCreate(String manufacturer, String model, int productionYear, List<Long> categories) {

	public CarCreate {
		if (categories == null || categories.isEmpty()) {
			throw new IllegalArgumentException("신규 자동차는 카테고리가 필요합니다.");
		}
	}
}