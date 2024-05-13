package br.com.bookrestaurant.usecase.reserve;

import br.com.bookrestaurant.entity.reserve.ReserveEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.com.bookrestaurant.utilsbytests.Util;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.junit.jupiter.api.Assertions;
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

    @Nested
    class FindReserve {
        @Test
        @Severity(SeverityLevel.MINOR)
        void testFindByRestaurantAndDate() {
            LocalDateTime date = LocalDateTime.of(2024,5,12,11,23,59);
            Map<String, Object> params = ReserveUseCase.findByRestaurantAndDate(Util.getUUID(), date);
            assertThat(params).isNotNull().isInstanceOf(Map.class);
            assertThat(params.get("date")).isNotNull().isEqualTo(date);
            assertThat(params.get("restaurantId")).isNotNull().isEqualTo(Util.getUUID());
        }

        @Test
        @Severity(SeverityLevel.MINOR)
        void testLocateReserves() {
            List<ReserveEntity> reserves = ReserveUseCase.locateReserves(Arrays.asList(Util.buildReserve()));
            assertThat(reserves).isNotNull().isInstanceOf(List.class);
        }
    }

    @Nested
    class UpdateReserve {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        void testUpdateStatus() {
            Map<String, Object> params = ReserveUseCase.updateStatus(Util.getUUID(), "F");
            assertThat(params).isNotNull().isInstanceOf(Map.class);
            assertThat(params.get("status")).isNotNull().isEqualTo("F");
            assertThat(params.get("id")).isNotNull().isEqualTo(Util.getUUID());
        }
    }

    @Test
    void testInstance() {
        Assertions.assertThrows(IllegalStateException.class, () -> new ReserveUseCase());
    }

}
