package com.carrental.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carrental.domain.model.CarCategory;

public interface CarCategoryJpaRepository extends JpaRepository<CarCategory, Long> {
}
