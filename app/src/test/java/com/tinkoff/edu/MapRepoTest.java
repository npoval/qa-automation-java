package com.tinkoff.edu;

import com.tinkoff.edu.app.controller.LoanCalcController;
import com.tinkoff.edu.app.exception.AmountValidationException;
import com.tinkoff.edu.app.exception.FioDataLengthException;
import com.tinkoff.edu.app.exception.MonthsDataValidationException;
import com.tinkoff.edu.app.repository.LoanRequestResponse;
import com.tinkoff.edu.app.repository.MapLoanCalcRepository;
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

public class MapRepoTest {
    private MapLoanCalcRepository repository;
    private LoanRequest request;
    private LoanCalcController controller;
    private LoanRequestResponse loanRequestResponse;


    @BeforeEach
    public void init() {
        request = new LoanRequest(LoanType.PERSON, 10, 1000, "Русаков Иван Иванович-Петрович");
        repository = new MapLoanCalcRepository();
        controller = new LoanCalcController(new LoanCalcService(repository));

    }

    @Test
    @DisplayName("Проверка возможности получения статуса заявки по UUID ")
    public void shouldGetLoanStatusByUuid() throws FioDataLengthException, MonthsDataValidationException, AmountValidationException {
        LoanResponse response = controller.createRequest(this.request);
        loanRequestResponse = new LoanRequestResponse(response, request);
        repository.setLoanRequestResponseMap(response.getResponseId(),loanRequestResponse);
        System.out.println(response.getResponseId());
        repository.getStatus(response.getResponseId());
        assertEquals(ResponseType.APPROVED, repository.getStatus(response.getResponseId()));
    }

    @Test
    @DisplayName("Проверка возможности установки статуса заявки по UUID с APPROVED на DENIED")
    public void shouldSetLoanStatusByUuid() throws FioDataLengthException, MonthsDataValidationException, AmountValidationException {
        LoanResponse response = controller.createRequest(this.request);
        loanRequestResponse = new LoanRequestResponse(response, request);
        repository.setLoanRequestResponseMap(response.getResponseId(),loanRequestResponse);
        repository.setStatus(response.getResponseId(), ResponseType.DENIED);
        assertEquals(ResponseType.DENIED, repository.getStatus(response.getResponseId()));
    }

    @Test
    @DisplayName("Проверка возврата статуса NOTFOUND при запросе статуса с несуществующим UUID")
    public void shouldGetErrorByInvalidUuid()  {
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

    @Test
    @DisplayName("Проверка возврата кол-ва заявок с определенным типом из репо")
    public void shouldGetCountRequestByLoanType() throws FioDataLengthException, MonthsDataValidationException, AmountValidationException {
        for (int i = 0; i < 3; i++) {
            LoanResponse response = controller.createRequest(this.request);
            loanRequestResponse = new LoanRequestResponse(response, request);
            repository.setLoanRequestResponseMap(response.getResponseId(),loanRequestResponse);}
        assertEquals(3, repository.getCountLoanRequestByLoanType(LoanType.PERSON));
    }

    @Test
    @DisplayName("Проверка возврата кол-ва заявок с определенным типом из репо, при условии отсутствия таких заявок")
    public void shouldGetCountRequestByLoanTypeNotFound() throws FioDataLengthException, MonthsDataValidationException, AmountValidationException {
        for (int i = 0; i < 3; i++) {
            LoanResponse response = controller.createRequest(this.request);
            loanRequestResponse = new LoanRequestResponse(response, request);
            repository.setLoanRequestResponseMap(response.getResponseId(),loanRequestResponse);}
        assertEquals(0, repository.getCountLoanRequestByLoanType(LoanType.IP));
    }
}
