package com.carrental.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "자동차 생성 요청 객체",
        example = """
                {
                  "manufacturer": "현대",
                  "model": "코나",
                  "productionYear": 2024,
                  "categories": [1, 2]
                }
                """)
public record CreateCarRequest(
        @Schema(description = "제조사", example = "현대") String manufacturer,
        @Schema(description = "모델명", example = "코나") String model,
        @Schema(description = "생산년도", example = "2024") int productionYear,
        @Schema(description = "카테고리 ID 목록", example = "[1, 2]") List<Long> categories
) {
}
