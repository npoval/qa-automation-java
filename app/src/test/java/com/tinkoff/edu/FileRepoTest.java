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

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FileRepoTest {
    private FileLoanCalcRepo repository;
    private LoanRequest request;
    private LoanCalcController controller;
    private LoanRequestResponse loanRequestResponse;
    private LoanResponse response;
    private Path fileRepo = Path.of("repo.txt");


    @BeforeEach
    public void init() throws IOException, FioDataLengthException, MonthsDataValidationException, AmountValidationException {
        request = new LoanRequest(LoanType.PERSON, 10, 1000, "Русаков Иван Иванович-Петрович");
        repository = new FileLoanCalcRepo();
        controller = new LoanCalcController(new LoanCalcService(repository));
        response = controller.createRequest(this.request);
        loanRequestResponse = new LoanRequestResponse(response, request);
        repository.setLoanRequestResponseMap(response.getResponseId(), loanRequestResponse);
        repository.setLoanRequestResponseFile();
    }

    @Test
    @DisplayName("Проверка записи в файл ")
    public void shouldWriteAndReadToFile() {
        assertThat(fileRepo).exists().isNotEmptyFile();
    }

    @Test
    @DisplayName("Проверка получения значения статуса по существующему uuid")
    public void shouldGetLoanStatusByUuid() throws IOException {
        String status = repository.getStatusByUuid(response.getResponseId());
        assertEquals("APPROVED", status);
    }

    @Test
    @DisplayName("Проверка получения значения статуса по несуществующему uuid")
    public void shouldGetLoanStatusByInvalidUuid() throws IOException {
        String status = repository.getStatusByUuid(UUID.fromString("123e4567-e89b-12d3-a456-426655440000"));
        assertNull(status);
    }

    @Test
    @DisplayName("Проверка получения количества заявок по их типу")
    public void shouldGetLoanCountByLoanType() throws IOException {
        long countLoan = repository.getCountLoanByLoanType(LoanType.PERSON);
        assertThat(countLoan).isNotZero().isNotNull();
        System.out.println(countLoan);
    }
}
