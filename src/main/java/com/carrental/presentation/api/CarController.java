package com.carrental.presentation.api;

import com.carrental.domain.CarService;
import com.carrental.domain.CategoryService;
import com.carrental.domain.model.Car;
import com.carrental.domain.model.CarCreate;
import com.carrental.domain.model.RentalStatus;
import com.carrental.presentation.api.request.CreateCarRequest;
import com.carrental.presentation.api.response.CarResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Car Management API", description = "자동차 관리 API")
public class CarController {

	private final CarService carService;

	private final CategoryService categoryService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/cars")
	@Operation(summary = "자동차 등록", description = "새로운 자동차를 등록합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "자동차 등록 성공",
					content = {@Content(schema = @Schema(implementation = CarResponse.class))}),
			@ApiResponse(responseCode = "400", description = "자동차 등록 시 카테고리가 없을 경우 실패",
					content = @Content(mediaType = "application/json",
							examples = @ExampleObject(value = """
                            {
                                "type": "about:blank",
                                "title": "Bad Request",
                                "status": 400,
                                "detail": "신규 자동차는 카테고리가 필요합니다.",
                                "instance": "/api/cars"
                            }""")))
	})
	public CarResponse create(
			@RequestBody @Parameter(description = "자동차 생성 요청 객체", required = true) CreateCarRequest request) {
		var carCreate = new CarCreate(
				request.manufacturer(),
				request.model(),
				request.productionYear(),
				request.categories()
		);

		var categoriesByIds = categoryService.getCategoriesByIds(carCreate.categories());

		Car car = carService.create(carCreate, categoriesByIds);
		return CarResponse.from(car);
	}

	@GetMapping("/cars/{carId}/availability")
	@Operation(summary = "자동차 대여 가능 여부 조회", description = "특정 자동차의 대여 가능 여부를 조회합니다.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "대여 가능 여부 조회 성공"),
			@ApiResponse(responseCode = "404", description = "잘못된 자동차 ID로 인해 조회에 실패",
					content = @Content(mediaType = "application/json",
							examples = @ExampleObject(value = """
                            {
                                "type": "about:blank",
                                "title": "Not Found",
                                "status": 404,
                                "detail": "자동차가 존재하지 않습니다: 33",
                                "instance": "/api/cars/33/availability"
                            }""")))
	})
	public boolean checkAvailability(
			@PathVariable(name = "carId") @Parameter(description = "자동차 ID", required = true, example = "1") Long carId) {
		return carService.isCarAvailable(carId);
	}

	@GetMapping("/cars/search")
	@Operation(summary = "자동차 검색", description = "제조사, 모델명, 생산년도로 자동차를 검색합니다.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "자동차 검색 성공")
	})
	public List<CarResponse> searchCars(
			@RequestParam(name = "manufacturer", required = false) @Parameter(description = "제조사", example = "현대") String manufacturer,
			@RequestParam(name = "model", required = false) @Parameter(description = "모델명", example = "코나") String model,
			@RequestParam(name = "productionYear", required = false) @Parameter(description = "생산년도", example = "2024") Integer productionYear) {
		return carService.searchCars(manufacturer, model, productionYear);
	}

	@PatchMapping("/cars/{carId}/rental-status")
	@Operation(summary = "자동차 대여 상태 수정", description = "특정 자동차의 대여 상태를 수정합니다.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "대여 상태 수정 성공"),
			@ApiResponse(responseCode = "404", description = "잘못된 자동차 ID로 인해 조회에 실패",
					content = @Content(mediaType = "application/json",
							examples = @ExampleObject(value = """
                            {
                                "type": "about:blank",
                                "title": "Not Found",
                                "status": 404,
                                "detail": "자동차가 존재하지 않습니다: 33",
                                "instance": "/api/cars/33/rental-status"
                            }""")))
	})
	public boolean updateRentalStatus(
			@PathVariable(name = "carId") @Parameter(description = "자동차 ID", required = true, example = "1") Long carId,
			@RequestParam(name = "status") @Parameter(description = "대여 상태", required = true) RentalStatus status) {
		carService.updateRentalStatus(carId, status);
		return true;
	}
}
