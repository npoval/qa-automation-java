package com.tinkoff.edu.app.controller;


import com.tinkoff.edu.app.exception.AmountValidationException;
import com.tinkoff.edu.app.exception.FioDataLengthException;
import com.tinkoff.edu.app.exception.MonthsDataValidationException;
import com.tinkoff.edu.app.request.LoanRequest;
import com.tinkoff.edu.app.response.LoanResponse;
import com.tinkoff.edu.app.response.ResponseType;
import com.tinkoff.edu.app.service.BusinessService;


public class LoanCalcController {
    private BusinessService service;


    public LoanCalcController(BusinessService service) {
        this.service = service;
    }

    public LoanResponse createRequest(LoanRequest request) throws FioDataLengthException, MonthsDataValidationException, AmountValidationException {
        if (request != null && request.getFio() != null) {
            if (request.getFio().length() < 10 || request.getFio().length() > 100) {
                throw new FioDataLengthException("Некорректная длина FIO!");
            }
            if (request.getMonths() <= 0 || request.getMonths() > 100) {
                throw new MonthsDataValidationException("Некорректное значение месяца!");
            }
            if (request.getAmount() < 0.01 || request.getAmount() > 999999.99) {
                throw new AmountValidationException("Некорректное значение суммы!");
            }
            switch (request.getType()) {
                case PERSON:
                    if (request.getAmount() <= 10000 && request.getMonths() <= 12) {
                        return new LoanResponse(ResponseType.APPROVED, service.createRequest(request));
                    } else {
                        return new LoanResponse(ResponseType.DENIED, service.createRequest(request));
                    }
                case OOO:
                    if (request.getAmount() > 10000 && request.getMonths() < 12) {
                        return new LoanResponse(ResponseType.APPROVED, service.createRequest(request));
                    } else {
                        return new LoanResponse(ResponseType.DENIED, service.createRequest(request));
                    }
                case IP:
                    return new LoanResponse(ResponseType.DENIED, service.createRequest(request));
                default:
                    throw new IllegalArgumentException("Некорректные данные!");
            }
        } else {
            throw new NullPointerException("Пустые данные в запросе!");
        }
    }
}
