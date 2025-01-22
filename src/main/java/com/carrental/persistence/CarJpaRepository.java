package com.carrental.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carrental.domain.model.Car;

public interface CarJpaRepository extends JpaRepository<Car, Long> {
}
