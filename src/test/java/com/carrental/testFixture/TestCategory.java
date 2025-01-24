package com.carrental.testFixture;

import com.carrental.domain.model.Category;

public final class TestCategory extends Category {
	private final Long id;

	public TestCategory(Long id, String name) {
		super(name);
		this.id = id;
	}

	@Override
	public Long getId() {
		return this.id;
	}
}