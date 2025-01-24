package com.carrental.persistence.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carrental.domain.model.Category;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {
}
