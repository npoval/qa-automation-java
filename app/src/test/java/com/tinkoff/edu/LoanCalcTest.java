package com.tinkoff.edu;

import com.tinkoff.edu.app.controller.LoanCalcController;
import com.tinkoff.edu.app.repository.ArrayLoanCalcRepository;
import com.tinkoff.edu.app.request.LoanRequest;
import com.tinkoff.edu.app.request.LoanType;
import com.tinkoff.edu.app.response.LoanResponse;
import com.tinkoff.edu.app.response.ResponseType;
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
    private ArrayLoanCalcRepository repository;


    @BeforeEach
    public void init() {
        repository = new ArrayLoanCalcRepository();
    }

    @Test
    @DisplayName("Проверка апрува заявки c типом PERSON при валидном запросе не с граничными approvingMonths и approvingAmount")
    public void shouldGetApprovePersonWhenValidRequestArrayRepo() {
        controller = new LoanCalcController(new LoanCalcService(new ArrayLoanCalcRepository()));
        int approvingMonths = 10;
        int approvingAmount = 1000;
        String fio = "Петрова Ирина Михайловна";
        request = new LoanRequest(LoanType.PERSON, approvingMonths, approvingAmount, fio);
        LoanResponse response = controller.createRequest(this.request);
        repository.writeResponse(response);
        assertEquals(ResponseType.APPROVED, repository.getStatus(response.getResponseId()));
    }

    @Test
    @DisplayName("Проверка апрува заявки c типом PERSON при валидном запросе на граничных значениях суммы и месяца")
    public void shouldGetApprovePersonWhenValidRequestBoundaryMonthsAndAmount() {
        controller = new LoanCalcController(new LoanCalcService(new ArrayLoanCalcRepository()));
        int approvingMonths = 12;
        int approvingAmount = 10000;
        String fio = "Петрова Ирина Михайловна";
        request = new LoanRequest(LoanType.PERSON, approvingMonths, approvingAmount, fio);
        LoanResponse response = controller.createRequest(this.request);
        repository.writeResponse(response);
        assertEquals(ResponseType.APPROVED, repository.getStatus(response.getResponseId()));
    }

    @Test
    @DisplayName("Проверка возврата - 1 при отрицательном значении месяца")
    public void shouldGetErrorWhenNegativeMonthsRequest() {
        controller = new LoanCalcController(new LoanCalcService(new ArrayLoanCalcRepository()));
        int negativeMonths = -2;
        int approvingAmount = 10000;
        String fio = "Петрова Ирина Михайловна";
        request = new LoanRequest(LoanType.OOO, negativeMonths, approvingAmount, fio);
        LoanResponse response = controller.createRequest(this.request);
        repository.writeResponse(response);
        assertEquals(ResponseType.ERROR, repository.getStatus(response.getResponseId()));
    }

    @Test
    @DisplayName("Проверка возврата - 1 при отрицательном значении суммы")
    public void shouldGetErrorWhenNegativeAmount() {
        controller = new LoanCalcController(new LoanCalcService(new ArrayLoanCalcRepository()));
        int approvingMonths = 5;
        int negativeAmount = -3000;
        String fio = "Петрова Ирина Михайловна";
        request = new LoanRequest(LoanType.PERSON, approvingMonths, negativeAmount, fio);
        LoanResponse response = controller.createRequest(this.request);
        repository.writeResponse(response);
        assertEquals(ResponseType.ERROR, repository.getStatus(response.getResponseId()));
    }

    @Test
    @DisplayName("Проверка возврата ошибки -1 при нулевом месяце")
    public void shouldGetErrorWhenZeroMonthsRequest() {
        controller = new LoanCalcController(new LoanCalcService(new ArrayLoanCalcRepository()));
        int zeroMonths = 0;
        int approvingAmount = 1000;
        String fio = "Петрова Ирина Михайловна";
        request = new LoanRequest(LoanType.PERSON, zeroMonths, approvingAmount, fio);
        LoanResponse response = controller.createRequest(this.request);
        repository.writeResponse(response);
        assertEquals(ResponseType.ERROR, repository.getStatus(response.getResponseId()));
    }

    @Test
    @DisplayName("Проверка возврата ошибки -1 при нулевом сумме")
    public void shouldGetErrorWhenZeroAmountRequest() {
        controller = new LoanCalcController(new LoanCalcService(new ArrayLoanCalcRepository()));
        int approvingMonths = 11;
        int zeroAmount = 0;
        String fio = "Петрова Ирина Михайловна";
        request = new LoanRequest(LoanType.PERSON, approvingMonths, zeroAmount, fio);
        LoanResponse response = controller.createRequest(this.request);
        repository.writeResponse(response);
        assertEquals(ResponseType.ERROR, repository.getStatus(response.getResponseId()));
    }

    @Test
    @DisplayName("Проверка возврата -1 при пустом реквесте")
    public void shouldGetNullRequest() {
        controller = new LoanCalcController(new LoanCalcService(new ArrayLoanCalcRepository()));
        request = null;
        LoanResponse response = controller.createRequest(this.request);
        repository.writeResponse(response);
        assertEquals(ResponseType.ERROR, repository.getStatus(response.getResponseId()));
    }

    @Test
    @DisplayName("Проверка отклонения заявки при типе IP")
    public void shouldGetDeniedWhenIpRequest() {
        controller = new LoanCalcController(new LoanCalcService(new ArrayLoanCalcRepository()));
        int approvingMonths = 12;
        int approvingAmount = 10000;
        String fio = "Петрова Ирина Михайловна";
        request = new LoanRequest(LoanType.IP, approvingMonths, approvingAmount, fio);
        LoanResponse response = controller.createRequest(this.request);
        repository.writeResponse(response);
        assertEquals(ResponseType.DENIED, repository.getStatus(response.getResponseId()));
    }

    @Test
    @DisplayName("Проверка отмены заявки c типом OOO, когда не проходит проверка по Amount")
    public void shouldGetDeniedOooWhenValidRequestDeniedAmount() {
        controller = new LoanCalcController(new LoanCalcService(new ArrayLoanCalcRepository()));
        int approvingMonths = 11;
        int deniedAmount = 10000;
        String fio = "Петрова Ирина Михайловна";
        request = new LoanRequest(LoanType.OOO, approvingMonths, deniedAmount, fio);
        LoanResponse response = controller.createRequest(this.request);
        repository.writeResponse(response);
        assertEquals(ResponseType.DENIED, repository.getStatus(response.getResponseId()));
    }

    @Test
    @DisplayName("Проверка отклонения заявки c типом ООО при валидном запросе с некорректным Months")
    public void shouldGetDeniedPOooWhenValidRequestDeniedMonths() {
        controller = new LoanCalcController(new LoanCalcService(new ArrayLoanCalcRepository()));
        int deniedMonths = 13;
        int approvingAmount = 10001;
        String fio = "Петрова Ирина Михайловна";
        request = new LoanRequest(LoanType.OOO, deniedMonths, approvingAmount, fio);
        LoanResponse response = controller.createRequest(this.request);
        repository.writeResponse(response);
        assertEquals(ResponseType.DENIED, repository.getStatus(response.getResponseId()));
    }

    @Test
    @DisplayName("Проверка отклонения заявки c типом PERSON при валидном запросе с неподходящей Amount")
    public void shouldGetDeniedPersonWhenValidRequestDeniedAmount() {
        controller = new LoanCalcController(new LoanCalcService(new ArrayLoanCalcRepository()));
        int approvingMonths = 10;
        int deniedAmount = 12000;
        String fio = "Петрова Ирина Михайловна";
        request = new LoanRequest(LoanType.PERSON, approvingMonths, deniedAmount, fio);
        LoanResponse response = controller.createRequest(this.request);
        repository.writeResponse(response);
        assertEquals(ResponseType.DENIED, repository.getStatus(response.getResponseId()));
    }

    @Test
    @DisplayName("Проверка апрува заявки c типом OOO при валидном запросе")
    public void shouldGetApproveOooWhenValidRequest() {
        controller = new LoanCalcController(new LoanCalcService(new ArrayLoanCalcRepository()));
        int approvingMonths = 1;
        int approvingAmount = 10001;
        String fio = "Петрова Ирина Михайловна";
        request = new LoanRequest(LoanType.OOO, approvingMonths, approvingAmount, fio);
        LoanResponse response = controller.createRequest(this.request);
        repository.writeResponse(response);
        assertEquals(ResponseType.APPROVED, repository.getStatus(response.getResponseId()));
    }

    @Test
    @DisplayName("Проверка отклонения заявки c типом PERSON при валидном запросе с неподходящим Months")
    public void shouldGetDeniedPersonWhenValidRequestDeniedMonths() {
        controller = new LoanCalcController(new LoanCalcService(new ArrayLoanCalcRepository()));
        int deniedMonths = 15;
        int approvingAmount = 8000;
        String fio = "Петрова Ирина Михайловна";
        request = new LoanRequest(LoanType.PERSON, deniedMonths, approvingAmount, fio);
        LoanResponse response = controller.createRequest(this.request);
        repository.writeResponse(response);
        assertEquals(ResponseType.DENIED, repository.getStatus(response.getResponseId()));
    }
}
