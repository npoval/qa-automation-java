package com.tinkoff.edu;

import com.tinkoff.edu.app.repository.ArrayLoanCalcRepository;
import com.tinkoff.edu.app.request.LoanRequest;
import com.tinkoff.edu.app.request.LoanType;
import com.tinkoff.edu.app.service.IpNotFriendlyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IpNotFriendlyServiceTest {
    private IpNotFriendlyService service;
    private LoanRequest request;

    @BeforeEach
    public void init() {
        service = new IpNotFriendlyService(new ArrayLoanCalcRepository());
    }

    @Test
    @DisplayName("Проверка возврата -1 при вызове IpNotFriendlyService с типом заявки IP")
    public void TestRequestIpIfWhenIpNotFriendlyService() {
        request = new LoanRequest(LoanType.IP, 1000, 5, "Петрова Ирина Михайловна");
        UUID requestId = service.createRequest(request);
        assertEquals(null, requestId);
    }

    @Test
    @DisplayName("Проверка возврата корректного статуса заявки при вызове IpNotFriendlyService с типом заявки не IP")
    public void TestRequestNotIpIfWhenIpNotFriendlyService() {
        request = new LoanRequest(LoanType.PERSON, 1000, 5, "Петрова Ирина Михайловна");
        UUID requestId = service.createRequest(request);
        assertTrue(requestId != null);
    }
}
