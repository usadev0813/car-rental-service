package com.carrental.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carrental.domain.model.CarCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarCategoryJpaRepository extends JpaRepository<CarCategory, Long> {
    @Query("SELECT cc FROM CarCategory cc JOIN FETCH cc.car WHERE cc.category.id = :categoryId")
    List<CarCategory> findByCategoryId(@Param("categoryId") Long categoryId);
}
