package br.com.bookrestaurant.external.config;

import br.com.bookrestaurant.external.dto.ErrorApi;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ErrorsHandlerConfigTest {

    @InjectMocks
    private ErrorsHandlerConfig errorsHandlerConfig;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void close() throws Exception {
        mocks.close();
    }

    @Test
    void testError() {
        ResponseEntity<ErrorApi> errorResp = errorsHandlerConfig
                .error(new RuntimeException("Falha genérica"));
        assertThat(errorResp).isNotNull().isInstanceOf(ResponseEntity.class)
                .extracting("statusCode").isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
