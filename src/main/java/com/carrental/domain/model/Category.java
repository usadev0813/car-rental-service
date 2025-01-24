package com.carrental.domain.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Long id;

	@Column(name = "name", nullable = false, unique = true, length = 50)
	private String name;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CarCategory> carCategories = new ArrayList<>();

	public Category(String name) {
		this.name = name;
	}
}
