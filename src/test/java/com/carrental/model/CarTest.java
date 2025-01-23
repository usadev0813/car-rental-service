package com.carrental.model;

import com.carrental.domain.model.Car;
import com.carrental.domain.model.CarCreate;
import com.carrental.domain.model.RentalStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CarTest {

    @Test
    void 신규_자동차_생성시_기본_렌탈_상태는_대여_가능() {
        // when
        Car sut = new Car("현대", "소나타", 2022);

        // then
        assertThat(sut.getRentalStatus()).isEqualTo(RentalStatus.AVAILABLE);
    }

    @Test
    void 렌탈_상태_Null_업데이트_실패() {
        // given
        Car sut = new Car("현대", "소나타", 2022);

        // when & then
        assertThatThrownBy(() -> sut.updateRentalStatus(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("렌탈 상태는 null일 수 없습니다.");
    }

    @Test
    void 다양한_렌탈_상태에_따른_대여_가능성_확인() {
        // given
        Car availableCar = new Car("현대", "소나타", 2022);
        Car underRepairCar = new Car("기아", "K5", 2021);
        Car unavailableCar = new Car("BMW", "520d", 2023);

        // when
        underRepairCar.updateRentalStatus(RentalStatus.UNDER_REPAIR);
        unavailableCar.updateRentalStatus(RentalStatus.UNAVAILABLE);

        // then
        assertThat(availableCar.isRentalable()).isTrue();
        assertThat(underRepairCar.isRentalable()).isFalse();
        assertThat(unavailableCar.isRentalable()).isFalse();
    }
}