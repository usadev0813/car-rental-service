package com.carrental.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "car_id")
	private Long id;

	@Column(name = "manufacturer", nullable = false, length = 100)
	private String manufacturer;

	@Column(name = "model", nullable = false, length = 100)
	private String model;

	@Column(name = "production_year", nullable = false)
	private int productionYear;

	@Enumerated(EnumType.STRING)
	@Column(name = "rental_status", nullable = false, length = 20)
	private RentalStatus rentalStatus;

	@Builder
	public Car(String manufacturer, String model, int productionYear, RentalStatus rentalStatus) {
		this.manufacturer = manufacturer;
		this.model = model;
		this.productionYear = productionYear;
		this.rentalStatus = rentalStatus;
	}
}
