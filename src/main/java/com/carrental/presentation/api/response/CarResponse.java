package com.carrental.presentation.api.response;

import com.carrental.domain.model.Car;

public record CarResponse(Long id, String manufacturer, String model, int productionYear) {
	public static CarResponse from(Car car) {
		return new CarResponse(car.getId(), car.getManufacturer(), car.getModel(), car.getProductionYear());
	}
}
