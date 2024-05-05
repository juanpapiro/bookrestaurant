package br.com.bookrestaurant.infraestructure.gateway.evaluate.impl;

import br.com.bookrestaurant.entity.evaluate.EvaluateEntity;
import br.com.bookrestaurant.external.db.DataBaseJpa;
import br.com.bookrestaurant.infraestructure.gateway.impl.evaluate.EvaluateGateway;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.IDataBase;
import br.com.bookrestaurant.utilsbytests.Util;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class EvaluateGatewayIT {

    private EvaluateGateway evaluateGateway;

    @Autowired
    private DataBaseJpa dataBaseJpa;

    @BeforeEach
    void setup() {
        evaluateGateway = new EvaluateGateway(dataBaseJpa);
    }

    @Nested
    class RegisterEvaluate {
        @Test
        @Severity(SeverityLevel.BLOCKER)
        void testShouldRegisterAddress() {
            EvaluateEntity evaluateEntity = evaluateGateway.registryEvaluate(Util.buildEvaluateEntity());
            assertThat(evaluateEntity).isNotNull().isInstanceOf(EvaluateEntity.class);
            assertThat(evaluateEntity.getEvaluateId()).isNotNull().isInstanceOf(UUID.class);
        }
    }

}
