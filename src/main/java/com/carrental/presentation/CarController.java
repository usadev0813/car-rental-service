package com.carrental.presentation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.carrental.domain.CarService;
import com.carrental.domain.model.Car;
import com.carrental.domain.model.CarCreate;
import com.carrental.presentation.request.CreateCarRequest;
import com.carrental.presentation.response.CarResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CarController {
	private final CarService carService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/cars")
	public CarResponse create(@RequestBody CreateCarRequest request) {
		CarCreate carCreate = new CarCreate(request.manufacturer(),
			request.model(),
			request.productionYear(),
			request.categories());

		Car car = carService.create(carCreate);
		return CarResponse.from(car);
	}

	@GetMapping("/cars/{carId}/availability")
	public boolean checkAvailability(@PathVariable Long carId) {
		return carService.isCarAvailable(carId);
	}

	@GetMapping("/cars/search")
	public List<CarResponse> searchCars(
		@RequestParam(required = false) String manufacturer,
		@RequestParam(required = false) String model,
		@RequestParam(required = false) Integer productionYear
	) {
		return carService.searchCars(manufacturer, model, productionYear);
	}
}
