package com.carrental.presentation.request;

import java.util.List;

public record CreateCarRequest(String manufacturer, String model, int productionYear, List<Long> categories) {
}
