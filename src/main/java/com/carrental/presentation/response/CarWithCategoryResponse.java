package com.carrental.presentation.response;
import com.carrental.domain.model.Car;

import java.util.List;

public record CarWithCategoryResponse(
        Long id,
        String manufacturer,
        String model,
        int productionYear,
        List<String> categories
) {
    public static CarWithCategoryResponse from(Car car, List<String> categories) {
        return new CarWithCategoryResponse(
                car.getId(),
                car.getManufacturer(),
                car.getModel(),
                car.getProductionYear(),
                categories
        );
    }
}
