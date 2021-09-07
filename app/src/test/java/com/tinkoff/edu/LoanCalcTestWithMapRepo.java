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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoanCalcTestWithMapRepo {
    private LoanRequest request;
    private LoanCalcController controller;
    private MapLoanCalcRepository repository;
    private LoanRequestResponse loanRequestResponse;

    private static Stream<Arguments> provideValidRequestAndResponse() {
        return Stream.of(
                Arguments.of(LoanType.PERSON, 10, 1000, "Петров Иван Иванович", ResponseType.APPROVED),
                Arguments.of(LoanType.PERSON, 12, 10000, "Петров Иван Иванович", ResponseType.APPROVED),
                Arguments.of(LoanType.IP, 12, 10000, "Петров Иван Иванович", ResponseType.DENIED),
                Arguments.of(LoanType.OOO, 11, 10000, "Петров Иван Иванович", ResponseType.DENIED),
                Arguments.of(LoanType.OOO, 13, 10001, "Петров Иван Иванович", ResponseType.DENIED),
                Arguments.of(LoanType.OOO, 1, 10001, "Петров Иван Иванович", ResponseType.APPROVED),
                Arguments.of(LoanType.PERSON, 10, 12000, "Петров Иван Иванович", ResponseType.DENIED),
                Arguments.of(LoanType.PERSON, 15, 8000, "Петров Иван Иванович", ResponseType.DENIED)
        );
    }

    @BeforeEach
    public void init() {
        repository = new MapLoanCalcRepository();

    }

    @ParameterizedTest
    @MethodSource("provideValidRequestAndResponse")
    @DisplayName("Проверка бизнес логики контролера по запросам с корректными параметрами")
    public void shouldGetApprovePersonWhenValidRequestArrayRepo(LoanType LoanType, int months, int amount, String fio, ResponseType expectedResponseType) throws FioDataLengthException, MonthsDataValidationException, AmountValidationException {
        controller = new LoanCalcController(new LoanCalcService(repository));
        request = new LoanRequest(LoanType, months, amount, fio);
        LoanResponse response = controller.createRequest(this.request);
        loanRequestResponse = new LoanRequestResponse(response, request);
        repository.setLoanRequestResponseMap(response.getResponseId(), loanRequestResponse);
        assertEquals(expectedResponseType, repository.getStatus(response.getResponseId()));
    }

    @Test
    @DisplayName("Проверка возврата ошибки Некорректное значение месяца! при отрицательном значении месяца")
    public void shouldGetErrorWhenNegativeMonthsRequest() {
        controller = new LoanCalcController(new LoanCalcService(repository));
        final MonthsDataValidationException thrown = assertThrows(MonthsDataValidationException.class,
                () -> controller.createRequest(new LoanRequest(LoanType.OOO, -2, 10000, "Петрова Ирина Михайловна")));
        assertEquals("Некорректное значение месяца!", thrown.getMessage());
    }

    @Test
    @DisplayName("Проверка возврата ошибки Некорректное значение суммы! при отрицательном значении суммы")
    public void shouldGetErrorWhenNegativeAmount() {
        controller = new LoanCalcController(new LoanCalcService(repository));
        final AmountValidationException thrown = assertThrows(AmountValidationException.class,
                () -> controller.createRequest(new LoanRequest(LoanType.PERSON, 5, -3000, "Петрова Ирина Михайловна")));
        assertEquals("Некорректное значение суммы!", thrown.getMessage());
    }

    @Test
    @DisplayName("Проверка возврата ошибки Некорректное значение месяца! при нулевом месяце")
    public void shouldGetErrorWhenZeroMonthsRequest() {
        controller = new LoanCalcController(new LoanCalcService(repository));
        final MonthsDataValidationException thrown = assertThrows(MonthsDataValidationException.class,
                () -> controller.createRequest(new LoanRequest(LoanType.PERSON, 0, 1000, "Петрова Ирина Михайловна")));
        assertEquals("Некорректное значение месяца!", thrown.getMessage());
    }

    @Test
    @DisplayName("Проверка возврата ошибки Некорректное значение суммы! при нулевой сумме")
    public void shouldGetErrorWhenZeroAmountRequest() {
        controller = new LoanCalcController(new LoanCalcService(repository));
        final AmountValidationException thrown = assertThrows(AmountValidationException.class,
                () -> controller.createRequest(new LoanRequest(LoanType.PERSON, 11, 0, "Петрова Ирина Михайловна")));
        assertEquals("Некорректное значение суммы!", thrown.getMessage());
    }
}
