package com.carrental.presentation;

import com.carrental.domain.CarCategoryService;
import com.carrental.presentation.response.CarWithCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CarCategoryController {

    private final CarCategoryService carCategoryService;

    @GetMapping("/categories/{categoryId}/cars")
    public ResponseEntity<List<CarWithCategoryResponse>> getCarsByCategoryId(@PathVariable Long categoryId) {
        List<CarWithCategoryResponse> cars = carCategoryService.getCarsByCategoryId(categoryId);
        return ResponseEntity.ok(cars);
    }
}

