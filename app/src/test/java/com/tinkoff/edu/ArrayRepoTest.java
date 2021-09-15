package com.tinkoff.edu;

import com.tinkoff.edu.app.exception.AmountValidationException;
import com.tinkoff.edu.app.exception.FioDataLengthException;
import com.tinkoff.edu.app.controller.LoanCalcController;
import com.tinkoff.edu.app.exception.MonthsDataValidationException;
import com.tinkoff.edu.app.repository.ArrayLoanCalcRepository;
import com.tinkoff.edu.app.request.LoanRequest;
import com.tinkoff.edu.app.request.LoanType;
import com.tinkoff.edu.app.response.LoanResponse;
import com.tinkoff.edu.app.response.ResponseType;
import com.tinkoff.edu.app.service.LoanCalcService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayRepoTest {
    private ArrayLoanCalcRepository repository;
    private LoanRequest request;
    private LoanCalcController controller;

    @BeforeEach
    public void init() {
        request = new LoanRequest(LoanType.PERSON, 10, 1000, "Русаков Иван Иванович-Петрович");
        controller = new LoanCalcController(new LoanCalcService(new ArrayLoanCalcRepository()));
        repository = new ArrayLoanCalcRepository();
    }

    @Test
    @DisplayName("Проверка возможности получения статуса заявки по UUID ")
    public void shouldGetLoanStatusByUuid() throws FioDataLengthException, MonthsDataValidationException, AmountValidationException {
        LoanResponse response = controller.createRequest(this.request);
        repository.writeResponse(response);
        System.out.println(response.getResponseId());
        repository.getStatus(response.getResponseId());
        assertEquals(ResponseType.APPROVED, repository.getStatus(response.getResponseId()));
    }

    @Test
    @DisplayName("Проверка возможности установки статуса заявки по UUID с DENIED на APPROVED")
    public void shouldSetLoanStatusByUuid() throws FioDataLengthException, MonthsDataValidationException, AmountValidationException {
        LoanResponse response = controller.createRequest(this.request);
        repository.writeResponse(response);
        repository.setStatus(response.getResponseId(), ResponseType.DENIED);
        assertEquals(ResponseType.DENIED, repository.getStatus(response.getResponseId()));
    }

    @Test
    @DisplayName("Проверка возврата NOTFOUND при получении статуса заявки по несуществующему UUID")
    public void shouldGetLoanStatusByNotFoundUuid() {
        UUID uuid = UUID.fromString("123e4567-e89b-12d3-a456-4266554400");
        repository.getStatus(uuid);
        assertEquals(ResponseType.NOTFOUND, repository.getStatus(uuid));
    }

    @Test
    @DisplayName("Проверка установки статуса по несуществующему UUID")
    public void shouldSetLoanStatusByUuidNotFound() {
        UUID uuid = UUID.fromString("123e4567-e89b-12d3-a456-426655440000");
        repository.setStatus(uuid, ResponseType.APPROVED);
        assertEquals(ResponseType.NOTFOUND, repository.getStatus(uuid));
    }
}
