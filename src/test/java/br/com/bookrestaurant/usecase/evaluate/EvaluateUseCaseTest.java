package br.com.bookrestaurant.usecase.evaluate;

import br.com.bookrestaurant.entity.evaluate.EvaluateEntity;
import br.com.bookrestaurant.entity.evaluate.exception.EvaluateInvalidException;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.utilsbytests.Util;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


class EvaluateUseCaseTest {

    @Test
    void testRegister() {
        EvaluateEntity evaluateEntity = EvaluateUseCase.register("Muito Bom", 4, Util.getUUID(), Util.buildRestaurantEntitySaved());
        assertThat(evaluateEntity).isNotNull().isInstanceOf(EvaluateEntity.class);
        assertThat(evaluateEntity.getComment()).isEqualTo("Muito Bom");
        assertThat(evaluateEntity.getEvaluation()).isEqualTo(4);
        assertThat(evaluateEntity.getRestaurantEntity()).isNotNull().isInstanceOf(RestaurantEntity.class);
    }

    @Test
    void testRegisterInvalidWhenBeforeEvaluation() {
        assertThatThrownBy(() -> EvaluateUseCase.register("Muito Bom", 0, Util.getUUID(), Util.buildRestaurantEntitySaved()))
                .isInstanceOf(EvaluateInvalidException.class)
                .hasMessage("Nota de avaliação deve ser de 1 a 5");
    }

    @Test
    void testRegisterInvalidWhenAfterEvaluation() {
        assertThatThrownBy(() -> EvaluateUseCase.register("Muito Bom", 6, Util.getUUID(), Util.buildRestaurantEntitySaved()))
                .isInstanceOf(EvaluateInvalidException.class)
                .hasMessage("Nota de avaliação deve ser de 1 a 5");
    }

}
