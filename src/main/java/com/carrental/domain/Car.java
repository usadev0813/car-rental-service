package com.carrental.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String manufacturer;
    private String model;
    private int productionYear;

    @Enumerated(EnumType.STRING)
    private RentalStatus rentalStatus;

    @Builder
    public Car(String manufacturer, String model,
               int productionYear, RentalStatus rentalStatus) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.productionYear = productionYear;
        this.rentalStatus = rentalStatus;
    }

}
