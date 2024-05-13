package br.com.bookrestaurant.external.config;

import br.com.bookrestaurant.entity.reserve.exception.ReserveInvalidException;
import br.com.bookrestaurant.entity.reserve.exception.ReserveNotFoundException;
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

    @Test
    void testErrorReserveNotFound() {
        ResponseEntity<ErrorApi> errorResp = errorsHandlerConfig.errorReserveNotFound(new ReserveNotFoundException());
        assertThat(errorResp).isNotNull().isInstanceOf(ResponseEntity.class)
                .extracting("statusCode").isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testErrorReserveInvalid() {
        ResponseEntity<ErrorApi> errorResp = errorsHandlerConfig.errorReserveInvalid(new ReserveInvalidException("Inválido"));
        assertThat(errorResp).isNotNull().isInstanceOf(ResponseEntity.class)
                .extracting("statusCode").isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
