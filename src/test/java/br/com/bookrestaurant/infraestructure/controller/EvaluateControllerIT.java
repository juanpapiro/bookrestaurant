package br.com.bookrestaurant.infraestructure.controller;

import br.com.bookrestaurant.entity.evaluate.EvaluateEntity;
import br.com.bookrestaurant.external.db.DataBaseJpa;
import br.com.bookrestaurant.infraestructure.presenter.evaluate.EvaluateRecord;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class EvaluateControllerIT {

    private EvaluateController evaluateController;

    @Autowired
    private DataBaseJpa databaseJpa;

    @BeforeEach
    void setup() {
        evaluateController = new EvaluateController();
    }


    @Nested
    class RegisterEvaluate {
        @Test
        void testShouldPermitRegisterEvaluate() {
            EvaluateRecord evaluateRecord = new EvaluateRecord("Muuuuito bom",4,
                    UUID.fromString("118cd4e6-73fb-43a4-bc36-ed777289c28f"));
            EvaluateEntity evaluateEntity = evaluateController.register(evaluateRecord, databaseJpa);
            assertThat(evaluateEntity).isNotNull().isInstanceOf(EvaluateEntity.class);
            assertThat(evaluateEntity.getEvaluateId()).isNotNull().isInstanceOf(UUID.class);
        }
    }

}
