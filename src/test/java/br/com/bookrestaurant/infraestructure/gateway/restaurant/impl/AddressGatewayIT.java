package br.com.bookrestaurant.infraestructure.gateway.restaurant.impl;

import br.com.bookrestaurant.entity.restaurant.Address;
import br.com.bookrestaurant.external.db.DataBaseJpa;
import br.com.bookrestaurant.infraestructure.gateway.impl.restaurant.AddressGateway;
import br.com.bookrestaurant.utilsbytests.Util;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class AddressGatewayIT {

    private AddressGateway addressGateway;

    @Autowired
    private DataBaseJpa dataBaseJpa;

    @BeforeEach
    void setup() {
        addressGateway = new AddressGateway(dataBaseJpa);
    }


    @Nested
    class RegisterRestaurant {
        @Test
        void testShouldRegisterAddress() {
            Address address = addressGateway.registerAddress(Util.buildAddres(), Util.getUUID());
            assertThat(address).isNotNull().isInstanceOf(Address.class);
        }
    }

}
