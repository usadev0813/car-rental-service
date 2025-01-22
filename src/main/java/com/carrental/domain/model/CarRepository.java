package com.carrental.domain.model;

import java.util.Optional;

public interface CarRepository {
	Car save(Car car);

    Optional<Car> findById(Long carId);
}
