package com.carrental.presentation.api.request;

import java.util.List;

public record CreateCarRequest(String manufacturer, String model, int productionYear, List<Long> categories) {
}
