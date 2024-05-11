package br.com.bookrestaurant.usecase.reserve;

import br.com.bookrestaurant.entity.reserve.ReserveEntity;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.bookrestaurant.utilsbytests.Util;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReserveUseCaseTest {

    @Nested
    class RegisterReserve {
        @Test
        @Severity(SeverityLevel.BLOCKER)
        void testShoudPermitRegisterReserve() {
            ReserveEntity reserveEntity = ReserveUseCase
                    .registerReserve(UUID.fromString("ba94f4d5-b0a6-4745-adac-1456619ecca8"),
                            LocalDateTime.now(), 4, Util.buildClient());
            assertThat(reserveEntity).isNotNull().isInstanceOf(ReserveEntity.class);
        }
    }

}
