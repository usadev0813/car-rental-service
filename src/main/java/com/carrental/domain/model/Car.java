package com.carrental.domain.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

	@OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CarCategory> carCategories = new ArrayList<>();

	public Car(CarCreate create) {
		this(create.manufacturer(), create.model(), create.productionYear());
	}

	public Car(String manufacturer, String model, int productionYear) {
		this.manufacturer = manufacturer;
		this.model = model;
		this.productionYear = productionYear;
		this.rentalStatus = RentalStatus.AVAILABLE;
	}

	/**
	 * 렌탈 상태를 업데이트 합니다.
	 *
	 * @param status 변경할 렌탈 상태 (RentalStatus 열거형 값)
	 * @throws IllegalArgumentException 전달된 상태가 null인 경우 예외가 발생합니다.
	 */
	public void updateRentalStatus(RentalStatus status) {
		if (status == null) {
			throw new IllegalArgumentException("렌탈 상태는 null일 수 없습니다.");
		}
		this.rentalStatus = status;
	}

	/**
	 * 현재 렌탈 상태가 대여 가능한지 확인합니다.
	 * @return 상태가 AVAILABL일 경우 true, 그 외에는 false를 반환합니다.
	 */
	public boolean isRentalable() {
		return RentalStatus.AVAILABLE == this.rentalStatus;
	}

	public void addCategory(Category category) {
		this.carCategories.add(new CarCategory(this, category));
	}
}
