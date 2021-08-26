package com.tinkoff.edu;

import com.tinkoff.edu.app.controller.LoanCalcController;
import com.tinkoff.edu.app.repository.StaticVariableLoanCalcRepository;
import com.tinkoff.edu.app.request.LoanRequest;
import com.tinkoff.edu.app.request.LoanType;
import com.tinkoff.edu.app.service.LoanCalcService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Loan Calc Tests
 */
public class LoanCalcTest {
    private LoanRequest request;
    private LoanCalcController controller;

    @BeforeEach
    public void init() {
        request = new LoanRequest(LoanType.OOO, 10, 1000);
        controller = new LoanCalcController(new LoanCalcService(new StaticVariableLoanCalcRepository()));
        StaticVariableLoanCalcRepository.setRequestId(0);
    }

    @Test
    @DisplayName("Проверка идентификатора requestId, если тип заявки не IP и статик репо")
    public void shouldGet1WhenNotIpRequest() {
        int requestId = controller.createRequest(request);
        assertEquals(1, requestId);
    }

    @Test
    @DisplayName("Проверка инкремента идентификатора requestId при любом вызове, если тип заявки не IP и статик репо")
    public void shouldGetIncrementedIdWhenAnyCall() {
        for (int i = 1; i < 4; i++) {
            int requestId = controller.createRequest(request);
            assertEquals(i, requestId);
        }
    }
}
