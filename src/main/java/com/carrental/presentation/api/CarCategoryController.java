package com.carrental.presentation.api;

import com.carrental.domain.CarCategoryService;
import com.carrental.domain.CarService;
import com.carrental.domain.CategoryService;
import com.carrental.presentation.api.request.UpdateCarCategoriesRequest;
import com.carrental.presentation.api.response.CarWithCategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Car Category Management API", description = "자동차와 카테고리 관리 API")
public class CarCategoryController {

    private final CarCategoryService carCategoryService;
    private final CarService carService;
    private final CategoryService categoryService;

    @GetMapping("/categories/{categoryId}/cars")
    @Operation(summary = "카테고리에 속한 자동차 조회", description = "특정 카테고리에 속한 자동차 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카테고리 내 자동차 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarWithCategoryResponse.class),
                            examples = @ExampleObject(value = """
                                    [
                                        {
                                            "id": 1,
                                            "manufacturer": "현대",
                                            "model": "코나",
                                            "productionYear": 2024,
                                            "categories": ["미니SUV"]
                                        }
                                    ]"""))),
            @ApiResponse(responseCode = "404", description = "카테고리를 찾을 수 없음",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                        "type": "about:blank",
                                        "title": "카테고리를 찾을 수 없음",
                                        "status": 404,
                                        "detail": "카테고리 ID를 찾을 수 없습니다.",
                                        "timestamp": 1672531200000
                                    }""")))
    })
    public List<CarWithCategoryResponse> getCarsByCategoryId(@PathVariable(name = "categoryId") @Parameter(description = "카테고리 ID", required = true, example = "1") Long categoryId) {
        return carCategoryService.getCarsByCategoryId(categoryId).stream()
                .map(carCategoryDetails -> CarWithCategoryResponse.from(carCategoryDetails.car(),
                        carCategoryDetails.categories()))
                .toList();
    }

    @PatchMapping("/cars/{carId}/categories")
    @Operation(summary = "자동차 카테고리 수정", description = "특정 자동차의 카테고리를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "자동차 카테고리 수정 성공"),
            @ApiResponse(responseCode = "404", description = "자동차 또는 카테고리를 찾을 수 없음",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
										"type": "about:blank",
										"title": "Not Found",
										"status": 404,
										"detail": "자동차가 존재하지 않습니다: 33",
										"instance": "/api/cars/33/categories"
                                    }""")))
    })
    public boolean updateCarCategories(
            @PathVariable(name = "carId") @Parameter(description = "자동차 ID", required = true, example = "1") Long carId,
            @RequestBody @Parameter(description = "수정할 카테고리 ID 목록", required = true) UpdateCarCategoriesRequest request) {
        var car = carService.getCar(carId);
        var categories = categoryService.getCategoriesByIds(request.categoryIds());
        carCategoryService.updateCarCategories(car, categories);
		return true;
    }
}
