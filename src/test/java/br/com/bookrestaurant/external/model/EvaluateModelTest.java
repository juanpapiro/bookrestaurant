package br.com.bookrestaurant.external.model;

import br.com.bookrestaurant.utilsbytests.Util;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EvaluateModelTest {

    @Test
    void testShouldInstanceEvaluateModelAllArgsConstrutctor() {
        EvaluateModel evaluateModel = new EvaluateModel(Util.getUUID(),
                "Muito bom", 5, Util.buildRestaurantModelForName("Mama Mia"),
                LocalDateTime.of(2024,12,1,10,0,0));
        assertion(evaluateModel);
    }

    @Test
    void testShouldInstanceEvaluateModelNoArgsConstrutctor() {
        EvaluateModel evaluateModel = new EvaluateModel();
        evaluateModel.setEvaluateId(Util.getUUID());
        evaluateModel.setComment("Muito bom");
        evaluateModel.setEvaluation(5);
        evaluateModel.setRestaurant(Util.buildRestaurantModelForName("Mama Mia"));
        evaluateModel.setDateCreate(LocalDateTime.of(2024,12,1,10,0,0));
        assertion(evaluateModel);
    }

    private void assertion(EvaluateModel evaluateModel) {
        assertThat(evaluateModel).isNotNull().isInstanceOf(EvaluateModel.class);
        assertThat(evaluateModel.getEvaluateId()).isEqualTo(Util.getUUID());
        assertThat(evaluateModel.getComment()).isEqualTo("Muito bom");
        assertThat(evaluateModel.getEvaluation()).isEqualTo(5);
        assertThat(evaluateModel.getRestaurant()).isInstanceOf(RestaurantModel.class);
        assertThat(evaluateModel.getDateCreate()).isEqualTo(LocalDateTime.of(2024,12,1,10,0,0));
    }

}
