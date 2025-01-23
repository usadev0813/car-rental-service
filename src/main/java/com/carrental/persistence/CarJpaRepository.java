package com.carrental.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.carrental.domain.model.Car;

public interface CarJpaRepository extends JpaRepository<Car, Long> {
	@Query("SELECT c FROM Car c " +
		"WHERE (:manufacturer IS NULL OR c.manufacturer = :manufacturer) " +
		"AND (:model IS NULL OR c.model = :model) " +
		"AND (:productionYear IS NULL OR c.productionYear = :productionYear)")
	List<Car> findByCriteria(
		@Param("manufacturer") String manufacturer,
		@Param("model") String model,
		@Param("productionYear") Integer productionYear
	);
}
