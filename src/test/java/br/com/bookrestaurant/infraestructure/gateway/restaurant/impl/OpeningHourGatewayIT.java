package br.com.bookrestaurant.infraestructure.gateway.restaurant.impl;

import br.com.bookrestaurant.entity.restaurant.OpeningHour;
import br.com.bookrestaurant.external.db.DataBaseJpa;
import br.com.bookrestaurant.infraestructure.gateway.impl.restaurant.OpeningHourGateway;
import br.com.bookrestaurant.utilsbytests.Util;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class OpeningHourGatewayIT {

    private OpeningHourGateway openingHourGateway;

    @Autowired
    private DataBaseJpa dataBaseJpa;

    @BeforeEach
    void setup() {
        openingHourGateway = new OpeningHourGateway(dataBaseJpa);
    }


    @Nested
    class RegisterRestaurant {
        @Test
        void testShouldRegisterOpeningHour() {
            OpeningHour openingHours = openingHourGateway.registerOpeningHour(
                    Util.buildOpeningHour(), Util.getUUID());
            assertThat(openingHours).isNotNull().isInstanceOf(OpeningHour.class);
        }

        @Test
        void testShouldRegisterOpeningHours() {
            List<OpeningHour> openingHours = openingHourGateway.registerOpeningHours(
                    Util.buildOpeningHours(), Util.getUUID());
            assertThat(openingHours).isNotNull().asList()
                    .element(0).isInstanceOf(OpeningHour.class);
        }
    }

}
