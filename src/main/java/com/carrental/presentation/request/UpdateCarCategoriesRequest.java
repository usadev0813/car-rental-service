package com.carrental.presentation.request;

import java.util.List;

public record UpdateCarCategoriesRequest(List<Long> categoryIds) {
}
