package com.carrental.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carrental.domain.model.Category;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {
}
