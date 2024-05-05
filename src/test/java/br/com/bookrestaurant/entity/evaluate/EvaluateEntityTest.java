package br.com.bookrestaurant.entity.evaluate;

import br.com.bookrestaurant.entity.evaluate.exception.EvaluateInvalidException;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.utilsbytests.Util;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class EvaluateEntityTest {

    @Test
    void testShouldPermitBuildEvaluateEntity() {
        UUID evaluateId = UUID.randomUUID();
        EvaluateEntity evaluateEntity = new EvaluateEntityBuilder()
                .addInfos("Muito bom", 4, Util.buildRestaurantEntitySaved())
                .addId(evaluateId)
                .build();
        assertThat(evaluateEntity).isNotNull().isInstanceOf(EvaluateEntity.class);
        assertThat(evaluateEntity.getEvaluateId()).isEqualTo(evaluateId);
        assertThat(evaluateEntity.getComment()).isEqualTo("Muito bom");
        assertThat(evaluateEntity.getEvaluation()).isEqualTo(4);
        assertThat(evaluateEntity.getRestaurantEntity()).isNotNull().isInstanceOf(RestaurantEntity.class);
    }

    @Test
    void testShouldNotPermitBuildEvaluateEntityWithNotInfos() {
        assertThatThrownBy(() -> new EvaluateEntityBuilder().addId(Util.getUUID()).build())
                .isInstanceOf(EvaluateInvalidException.class)
                .hasMessage("Falha ao criar nova avaliação de restaurante");
    }

}
