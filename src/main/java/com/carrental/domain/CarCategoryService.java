package com.carrental.domain;

import com.carrental.domain.model.CarCategory;
import com.carrental.domain.model.CarCategoryRepository;
import com.carrental.presentation.response.CarWithCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarCategoryService {

    private final CarCategoryRepository carCategoryRepository;

    public List<CarWithCategoryResponse> getCarsByCategoryId(Long categoryId) {
        // 카테고리 ID로 CarCategory 조회
        List<CarCategory> carCategories = carCategoryRepository.findByCategoryId(categoryId);

        // Car 객체를 기준으로 그룹화 후 카테고리 이름 리스트 생성
        Map<Long, List<CarCategory>> groupedByCar = carCategories.stream()
                .collect(Collectors.groupingBy(cc -> cc.getCar().getId()));

        // 그룹화된 데이터를 CarWithCategoriesResponse로 변환
        return groupedByCar.values().stream()
                .map(carCategoryList -> {
                    CarCategory firstEntry = carCategoryList.get(0);
                    List<String> categoryNames = carCategoryList.stream()
                            .map(cc -> cc.getCategory().getName())
                            .toList();

                    return CarWithCategoryResponse.from(
                            firstEntry.getCar(),
                            categoryNames
                    );
                })
                .toList();
    }
}

