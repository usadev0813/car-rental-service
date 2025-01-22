package com.carrental.presentation.request;

import java.util.List;

public record CreateCarRequest(Param car) {
	public record Param(String manufacturer, String model, int productionYear, List<Long> categories) {
	}
}
