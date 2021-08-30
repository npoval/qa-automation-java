package com.tinkoff.edu;

import com.tinkoff.edu.app.controller.LoanCalcController;
import com.tinkoff.edu.app.repository.VariableLoanCalcRepository;
import com.tinkoff.edu.app.request.LoanRequest;
import com.tinkoff.edu.app.request.LoanType;
import com.tinkoff.edu.app.response.LoanResponse;
import com.tinkoff.edu.app.response.ResponseType;
import com.tinkoff.edu.app.service.IpNotFriendlyService;
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
    }

    @Test
    @DisplayName("Проверка идентификатора requestId, если тип заявки не IP")
    public void shouldGet1WhenNotIpRequest() {
        controller = new LoanCalcController(new LoanCalcService(new VariableLoanCalcRepository()));
        LoanResponse response = controller.createRequest(request);
        assertEquals(1, response.getResponseId());
    }

    @Test
    @DisplayName("Проверка инкремента идентификатора requestId при любом вызове, если тип заявки не IP")
    public void shouldGetIncrementedIdWhenAnyCall() {
        int NON_DEFAULT_ANY_ID = 2;
        controller = new LoanCalcController(new LoanCalcService(new VariableLoanCalcRepository(NON_DEFAULT_ANY_ID)));
        assertEquals(3, controller.createRequest(request).getResponseId());
    }

    @Test
    @DisplayName("Проверка апрува заявки c типом PERSON при валидном запросе на граничных значениях суммы и месяца")
    public void shouldGetApprovePersonWhenValidRequestBoundaryMonthsAndAmount() {
        controller = new LoanCalcController(new LoanCalcService(new VariableLoanCalcRepository(0)));
        int approvingMonths = 12;
        int approvingAmount = 10000;
        request = new LoanRequest(LoanType.PERSON, approvingMonths, approvingAmount);
        LoanResponse response = controller.createRequest(this.request);
        assertEquals(new LoanResponse(ResponseType.APPROVED, 1), response);
    }

    @Test
    @DisplayName("Проверка апрува заявки c типом OOO при валидном запросе")
    public void shouldGetApproveOooWhenValidRequest() {
        controller = new LoanCalcController(new LoanCalcService(new VariableLoanCalcRepository(0)));
        int approvingMonths = 1;
        int approvingAmount = 10001;
        request = new LoanRequest(LoanType.OOO, approvingMonths, approvingAmount);
        LoanResponse response = controller.createRequest(this.request);
        assertEquals(new LoanResponse(ResponseType.APPROVED, 1), response);
    }

    @Test
    @DisplayName("Проверка отклонения заявки c типом ООО при валидном запросе с некорректным Months")
    public void shouldGetDeniedPOooWhenValidRequestDeniedMonths() {
        controller = new LoanCalcController(new LoanCalcService(new VariableLoanCalcRepository(0)));
        int deniedMonths = 13;
        int approvingAmount = 10001;
        request = new LoanRequest(LoanType.OOO, deniedMonths, approvingAmount);
        LoanResponse response = controller.createRequest(this.request);
        assertEquals(new LoanResponse(ResponseType.DENIED, 1), response);
    }

    @Test
    @DisplayName("Проверка отклонения заявки c типом PERSON при валидном запросе с некорректным Amount")
    public void shouldGetDeniedPersonWhenValidRequestDeniedAmount() {
        controller = new LoanCalcController(new LoanCalcService(new VariableLoanCalcRepository(0)));
        int approvingMonths = 10;
        int deniedAmount = 12000;
        request = new LoanRequest(LoanType.PERSON, approvingMonths, deniedAmount);
        LoanResponse response = controller.createRequest(this.request);
        assertEquals(new LoanResponse(ResponseType.DENIED, 1), response);
    }

    @Test
    @DisplayName("Проверка возврата - 1 при отрицательном значении месяца")
    public void shouldGetErrorWhenNegativeMonthsRequest() {
        controller = new LoanCalcController(new LoanCalcService(new VariableLoanCalcRepository(0)));
        int negativeMonths = -2;
        int approvingAmount = 10000;
        request = new LoanRequest(LoanType.OOO, negativeMonths, approvingAmount);
        LoanResponse response = controller.createRequest(this.request);
        assertEquals(new LoanResponse(ResponseType.ERROR, -1), response);
    }

    @Test
    @DisplayName("Проверка возврата - 1 при отрицательном значении суммы")
    public void shouldGetErrorWhenNegativeAmount() {
        controller = new LoanCalcController(new LoanCalcService(new VariableLoanCalcRepository(0)));
        int approvingMonths = 5;
        int negativeAmount = -3000;
        request = new LoanRequest(LoanType.PERSON, approvingMonths, negativeAmount);
        LoanResponse response = controller.createRequest(this.request);
        assertEquals(new LoanResponse(ResponseType.ERROR, -1), response);
    }

    @Test
    @DisplayName("Проверка отклонения заявки при типе IP")
    public void shouldGetDeniedWhenIpRequest() {
        controller = new LoanCalcController(new LoanCalcService(new VariableLoanCalcRepository(0)));
        int approvingMonths = 12;
        int approvingAmount = 10000;
        request = new LoanRequest(LoanType.IP, approvingMonths, approvingAmount);
        LoanResponse response = controller.createRequest(this.request);
        assertEquals(new LoanResponse(ResponseType.DENIED, 1), response);
    }

    @Test
    @DisplayName("Проверка возврата ошибки -1 при нулевом месяце")
    public void shouldGetErrorWhenZeroMonthsRequest() {
        controller = new LoanCalcController(new LoanCalcService(new VariableLoanCalcRepository(0)));
        int zeroMonths = 0;
        int approvingAmount = 100000;
        request = new LoanRequest(LoanType.OOO, zeroMonths, approvingAmount);
        LoanResponse response = controller.createRequest(this.request);
        assertEquals(new LoanResponse(ResponseType.ERROR, -1), response);
    }

    @Test
    @DisplayName("Проверка возврата ошибки -1 при отрицательной сумме")
    public void shouldGetErrorWhenZeroAmountRequest() {
        controller = new LoanCalcController(new LoanCalcService(new VariableLoanCalcRepository(0)));
        int approvingMonths = 10;
        int negativeAmount = -1266;
        request = new LoanRequest(LoanType.OOO, negativeAmount, approvingMonths);
        LoanResponse response = controller.createRequest(this.request);
        assertEquals(new LoanResponse(ResponseType.ERROR, -1), response);
    }

    @Test
    @DisplayName("Проверка возврата -1 при вызове IpNotFriendlyService с типом заявки IP")
    public void TestRequestIpIfWhenIpNotFriendlyService() {
        IpNotFriendlyService service = new IpNotFriendlyService(new VariableLoanCalcRepository(3));
        request = new LoanRequest(LoanType.IP, 1000, 5);
        int requestId = service.createRequest(request);
        assertEquals(-1, requestId);
    }

    @Test
    @DisplayName("Проверка возврата 1 при вызове IpNotFriendlyService с типом заявки не IP")
    public void TestRequestNotIpIfWhenIpNotFriendlyService() {
        IpNotFriendlyService service = new IpNotFriendlyService(new VariableLoanCalcRepository(0));
        request = new LoanRequest(LoanType.PERSON, 1000, 5);
        int requestId = service.createRequest(request);
        assertEquals(1, requestId);
    }

    @Test
    @DisplayName("Проверка возврата -1 при пустом реквесте")
    public void shouldGetNullRequest() {
        controller = new LoanCalcController(new LoanCalcService(new VariableLoanCalcRepository(0)));
        request = null;
        LoanResponse response = controller.createRequest(this.request);
        assertEquals(new LoanResponse(ResponseType.ERROR, -1), response);
    }

    @Test
    @DisplayName("Проверка отмены заявки c типом OOO, когда не проходит проверка по Amount")
    public void shouldGetDeniedOooWhenValidRequestDeniedAmount() {
        controller = new LoanCalcController(new LoanCalcService(new VariableLoanCalcRepository(0)));
        int approvingMonths = 11;
        int deniedAmount = 10000;
        request = new LoanRequest(LoanType.OOO, approvingMonths, deniedAmount);
        LoanResponse response = controller.createRequest(this.request);
        assertEquals(new LoanResponse(ResponseType.DENIED, 1), response);
    }

    @Test
    @DisplayName("Проверка апрува заявки c типом PERSON при валидном запросе не с граничными approvingMonths и approvingAmount")
    public void shouldGetApprovePersonWhenValidRequest() {
        controller = new LoanCalcController(new LoanCalcService(new VariableLoanCalcRepository(0)));
        int approvingMonths = 10;
        int approvingAmount = 1000;
        request = new LoanRequest(LoanType.PERSON, approvingMonths, approvingAmount);
        LoanResponse response = controller.createRequest(this.request);
        assertEquals(new LoanResponse(ResponseType.APPROVED, 1), response);
    }
}



