package com.carrental.presentation.api.response;

import com.carrental.domain.model.Car;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "자동차 응답 객체",
		example = """
        {
          "id": 11,
          "manufacturer": "현대",
          "model": "코나",
          "productionYear": 2024
        }
        """)
public record CarResponse(
		@Schema(description = "자동차 ID", example = "11") Long id,
		@Schema(description = "제조사", example = "현대") String manufacturer,
		@Schema(description = "모델명", example = "코나") String model,
		@Schema(description = "생산년도", example = "2024") int productionYear
) {
	public static CarResponse from(Car car) {
		return new CarResponse(car.getId(), car.getManufacturer(), car.getModel(), car.getProductionYear());
	}
}