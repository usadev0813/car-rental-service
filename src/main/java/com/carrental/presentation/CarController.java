package com.carrental.presentation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.carrental.domain.CarService;
import com.carrental.domain.CategoryService;
import com.carrental.domain.model.Car;
import com.carrental.domain.model.CarCreate;
import com.carrental.domain.model.RentalStatus;
import com.carrental.presentation.request.CreateCarRequest;
import com.carrental.presentation.response.CarResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CarController {

	private final CarService carService;

	private final CategoryService categoryService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/cars")
	public CarResponse create(@RequestBody CreateCarRequest request) {
		var carCreate = new CarCreate(request.manufacturer(),
			request.model(),
			request.productionYear(),
			request.categories());

		var categoriesByIds = categoryService.getCategoriesByIds(carCreate.categories());

		Car car = carService.create(carCreate, categoriesByIds);
		return CarResponse.from(car);
	}

	@GetMapping("/cars/{carId}/availability")
	public boolean checkAvailability(@PathVariable(name = "carId") Long carId) {
		return carService.isCarAvailable(carId);
	}

	@GetMapping("/cars/search")
	public List<CarResponse> searchCars(
		@RequestParam(name = "manufacturer", required = false) String manufacturer,
		@RequestParam(name = "model", required = false) String model,
		@RequestParam(name = "productionYear", required = false) Integer productionYear
	) {
		return carService.searchCars(manufacturer, model, productionYear);
	}

	@PatchMapping("/cars/{carId}/rental-status")
	public void updateRentalStatus(
		@PathVariable(name = "carId") Long carId,
		@RequestParam(name = "status") RentalStatus status
	) {
		carService.updateRentalStatus(carId, status);
	}
}
