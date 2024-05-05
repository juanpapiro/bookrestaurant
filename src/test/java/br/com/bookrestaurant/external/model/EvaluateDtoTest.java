package br.com.bookrestaurant.external.model;

import br.com.bookrestaurant.external.dto.EvaluateDto;
import br.com.bookrestaurant.utilsbytests.Util;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EvaluateDtoTest {

    @Test
    void testShouldBuildEvaluateDtoAllArgsConstructor() {
        EvaluateDto evaluateDto = new EvaluateDto(Util.getUUID(),
                "Excelente, tempero diferenciado", 5, Util.getUUID(),
                LocalDateTime.of(2024,10,2,10,12,15));
        assertThat(evaluateDto).isNotNull().isInstanceOf(EvaluateDto.class);
        assertThat(evaluateDto.getEvaluateId()).isEqualTo(Util.getUUID());
        assertThat(evaluateDto.getComment()).isEqualTo("Excelente, tempero diferenciado");
        assertThat(evaluateDto.getEvaluation()).isEqualTo(5);
        assertThat(evaluateDto.getRestaurantId()).isEqualTo(Util.getUUID());
        assertThat(evaluateDto.getDateCreate()).isEqualTo(LocalDateTime.of(2024,10,2,10,12,15));
    }

    @Test
    void testShouldBuildEvaluateDtoNoArgsConstructor() {
        EvaluateDto evaluateDto = new EvaluateDto();
        evaluateDto.setEvaluateId(Util.getUUID());
        evaluateDto.setComment("Excelente, tempero diferenciado");
        evaluateDto.setEvaluation(5);
        evaluateDto.setDateCreate(LocalDateTime.of(2024,10,2,10,12,15));
        evaluateDto.setRestaurantId(Util.getUUID());
        assertThat(evaluateDto).isNotNull().isInstanceOf(EvaluateDto.class);
        assertThat(evaluateDto.getEvaluateId()).isEqualTo(Util.getUUID());
        assertThat(evaluateDto.getComment()).isEqualTo("Excelente, tempero diferenciado");
        assertThat(evaluateDto.getEvaluation()).isEqualTo(5);
        assertThat(evaluateDto.getRestaurantId()).isEqualTo(Util.getUUID());
        assertThat(evaluateDto.getDateCreate()).isEqualTo(LocalDateTime.of(2024,10,2,10,12,15));
    }

}
