package com.carrental.presentation.api.request;

import java.util.List;

public record UpdateCarCategoriesRequest(List<Long> categoryIds) {
}
