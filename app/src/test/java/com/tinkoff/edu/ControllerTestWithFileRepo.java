package com.tinkoff.edu;

import com.tinkoff.edu.app.controller.LoanCalcController;
import com.tinkoff.edu.app.exception.AmountValidationException;
import com.tinkoff.edu.app.exception.FioDataLengthException;
import com.tinkoff.edu.app.exception.MonthsDataValidationException;
import com.tinkoff.edu.app.repository.FileLoanCalcRepo;
import com.tinkoff.edu.app.repository.LoanRequestResponse;
import com.tinkoff.edu.app.request.LoanRequest;
import com.tinkoff.edu.app.request.LoanType;
import com.tinkoff.edu.app.response.LoanResponse;
import com.tinkoff.edu.app.service.LoanCalcService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ControllerTestWithFileRepo {
    private LoanRequest request;
    private LoanCalcController controller;
    private FileLoanCalcRepo repository;
    private LoanRequestResponse loanRequestResponse;

    private static Stream<Arguments> provideValidRequestAndResponse() {
        return Stream.of(
                Arguments.of(LoanType.PERSON, 10, 1000, "Петров Иван Иванович", "APPROVED"),
                Arguments.of(LoanType.PERSON, 12, 10000, "Петров Иван Иванович", "APPROVED"),
                Arguments.of(LoanType.IP, 12, 10000, "Петров Иван Иванович", "DENIED"),
                Arguments.of(LoanType.OOO, 11, 10000, "Петров Иван Иванович", "DENIED"),
                Arguments.of(LoanType.OOO, 13, 10001, "Петров Иван Иванович", "DENIED"),
                Arguments.of(LoanType.OOO, 1, 10001, "Петров Иван Иванович", "APPROVED"),
                Arguments.of(LoanType.PERSON, 10, 12000, "Петров Иван Иванович", "DENIED"),
                Arguments.of(LoanType.PERSON, 15, 8000, "Петров Иван Иванович", "DENIED")
        );
    }

    private static Stream<Arguments> provideInValidMonthsRequestAndResponse() {
        return Stream.of(
                Arguments.of(LoanType.OOO, -2, 10000, "Петров Иван Иванович", "Некорректное значение месяца!"),
                Arguments.of(LoanType.PERSON, 0, 1000, "Петров Иван Иванович", "Некорректное значение месяца!"),
                Arguments.of(LoanType.PERSON, 101, 1000, "Петрова Ирина Михайловна", "Некорректное значение месяца!")
        );
    }

    private static Stream<Arguments> provideInValidAmountRequestAndResponse() {
        return Stream.of(
                Arguments.of(LoanType.PERSON, 5, -3000, "Петрова Ирина Михайловна", "Некорректное значение суммы!"),
                Arguments.of(LoanType.PERSON, 11, 0, "Петрова Ирина Михайловна", "Некорректное значение суммы!"),
                Arguments.of(LoanType.PERSON, 11, 1000000, "Петрова Ирина Михайловна", "Некорректное значение суммы!")
        );
    }

    private static Stream<Arguments> provideInValidFioRequestAndResponse() {
        return Stream.of(
                Arguments.of(LoanType.PERSON, 11, 0, "Петронн лавыавы лываваыв ывоаыоваоываоывавыаваываваывавыавыаываыва ваывавыаываваыв ывавыавыаывавы ыаввыавыаыв", "Некорректная длина FIO!"),
                Arguments.of(LoanType.PERSON, 11, 0, "Петро", "Некорректная длина FIO!")
        );
    }

    @BeforeEach
    public void init() {
        repository = new FileLoanCalcRepo();
    }

    @ParameterizedTest
    @MethodSource("provideValidRequestAndResponse")
    @DisplayName("Проверка бизнес логики контролера по запросам с корректными параметрами")
    public void shouldGetApprovePersonWhenValidRequestArrayRepo(LoanType LoanType, int months, int amount, String fio, String expectedResponseType) throws FioDataLengthException, MonthsDataValidationException, AmountValidationException, IOException {
        controller = new LoanCalcController(new LoanCalcService(repository));
        request = new LoanRequest(LoanType, months, amount, fio);
        LoanResponse response = controller.createRequest(request);
        loanRequestResponse = new LoanRequestResponse(response, request);
        repository.setLoanRequestResponseMap(response.getResponseId(), loanRequestResponse);
        repository.setLoanRequestResponseFile();
        String responseType = repository.getStatusByUuid(response.getResponseId());
        assertEquals(expectedResponseType, responseType);
    }

    @ParameterizedTest
    @MethodSource("provideInValidMonthsRequestAndResponse")
    @DisplayName("Проверка возврата ошибки Некорректное значение месяца! при некорретных значениях месяца")
    public void shouldGetErrorWhenNegativeMonthsRequest2(LoanType LoanType, int months, int amount, String fio, String expectedMessage) {
        request = new LoanRequest(LoanType, months, amount, fio);
        controller = new LoanCalcController(new LoanCalcService(repository));
        final MonthsDataValidationException thrown = assertThrows(MonthsDataValidationException.class,
                () -> controller.createRequest(request));
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideInValidAmountRequestAndResponse")
    @DisplayName("Проверка возврата ошибки Некорректное значение суммы! при некорректных значениях суммы")
    public void shouldGetErrorWhenNegativeAmountRequest2(LoanType LoanType, int months, int amount, String fio, String expectedMessage) {
        request = new LoanRequest(LoanType, months, amount, fio);
        controller = new LoanCalcController(new LoanCalcService(repository));
        final AmountValidationException thrown = assertThrows(AmountValidationException.class,
                () -> controller.createRequest(request));
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideInValidFioRequestAndResponse")
    @DisplayName("Проверка возврата ошибки Некорректная длина FIO! при невалидном FIO")
    public void shouldGetErrorWhenInvalidFioLong(LoanType LoanType, int months, int amount, String fio, String expectedMessage) {
        request = new LoanRequest(LoanType, months, amount, fio);
        controller = new LoanCalcController(new LoanCalcService(repository));
        final FioDataLengthException thrown = assertThrows(FioDataLengthException.class,
                () -> controller.createRequest(request));
        assertEquals("Некорректная длина FIO!", thrown.getMessage());
    }

    @Test
    @DisplayName("Проверка возврата ошибки Пустые данные в запросе! при длине больше 100")
    public void shouldGetErrorWhenInvalidFioNull() {
        controller = new LoanCalcController(new LoanCalcService(repository));
        final NullPointerException thrown = assertThrows(NullPointerException.class,
                () -> controller.createRequest(new LoanRequest(LoanType.PERSON, 11, 0, null)));
        assertEquals("Пустые данные в запросе!", thrown.getMessage());
    }

    @Test
    @DisplayName("Проверка возврата ошибки Пустые данные в запросе! при пустом реквесте")
    public void shouldGetErrorNullRequest() {
        controller = new LoanCalcController(new LoanCalcService(repository));
        final NullPointerException thrown = assertThrows(NullPointerException.class,
                () -> controller.createRequest(null));
        assertEquals("Пустые данные в запросе!", thrown.getMessage());
    }
}

