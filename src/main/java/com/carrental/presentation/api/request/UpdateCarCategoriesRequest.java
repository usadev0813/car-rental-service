package com.carrental.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "자동차 카테고리 수정 요청 객체",
        example = """
                {
                  "categoryIds": [1, 2]
                }
                """)
public record UpdateCarCategoriesRequest(
        @Schema(description = "카테고리 ID 목록", example = "[1, 2]") List<Long> categoryIds
) {
}
