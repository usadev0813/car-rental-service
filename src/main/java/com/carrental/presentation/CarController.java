package com.carrental.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
		CarCreate carCreate = new CarCreate(request.car().manufacturer(),
			request.car().model(),
			request.car().productionYear(),
			request.car().categories());

		Car car = carService.create(carCreate);
		return CarResponse.from(car);
	}

	@GetMapping("/cars/{carId}")
	public boolean checkAvailability(@PathVariable Long carId) {
        return carService.isCarAvailable(carId);
	}
}
