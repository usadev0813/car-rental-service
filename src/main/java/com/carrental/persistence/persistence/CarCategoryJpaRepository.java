package com.carrental.persistence.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.carrental.domain.model.Car;
import com.carrental.domain.model.CarCategory;

public interface CarCategoryJpaRepository extends JpaRepository<CarCategory, Long> {
	@Query("SELECT cc FROM CarCategory cc JOIN FETCH cc.car WHERE cc.category.id = :categoryId")
	List<CarCategory> findByCategoryId(@Param("categoryId") Long categoryId);

	@Modifying
	@Query("DELETE FROM CarCategory cc WHERE cc.car = :car")
	void deleteByCar(@Param("car") Car car);
}
