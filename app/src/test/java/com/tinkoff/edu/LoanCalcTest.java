package com.tinkoff.edu;

import com.tinkoff.edu.app.repository.StaticVariableLoanCalcRepository;
import com.tinkoff.edu.app.request.LoanRequest;
import com.tinkoff.edu.app.request.LoanType;
import com.tinkoff.edu.app.controller.LoanCalcController;

import com.tinkoff.edu.app.service.IpNotFriendlyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Loan Calc Tests
 */
public class LoanCalcTest {
    private LoanRequest request;
    private LoanCalcController sut;

    @BeforeEach
    public void init() {
        request = new LoanRequest(LoanType.OOO, 10, 1000);
        sut = new LoanCalcController(new IpNotFriendlyService(new StaticVariableLoanCalcRepository()));
    }

    @Test
    @DisplayName("Проверка идентификатора requestId, если тип заявки не IP и статик репо")
    public void shouldGet1WnenNotIpRequest() {
        int requestId = sut.createRequest(request);
        assertEquals(1, requestId);
    }

    @Test
    public void shouldGetIncrementedIdWnenAnyCall() {
        int defaultRequestId = StaticVariableLoanCalcRepository.getRequestId();
        int requestId = sut.createRequest(request);
        assertEquals(++defaultRequestId, requestId);
    }
}
