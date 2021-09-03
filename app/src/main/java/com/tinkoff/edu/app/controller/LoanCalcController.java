package com.tinkoff.edu.app.controller;


import com.tinkoff.edu.app.request.LoanRequest;
import com.tinkoff.edu.app.response.LoanResponse;
import com.tinkoff.edu.app.response.ResponseType;
import com.tinkoff.edu.app.service.BusinessService;


public class LoanCalcController {
    private BusinessService service;


    public LoanCalcController(BusinessService service) {
        this.service = service;
    }

    public LoanResponse createRequest(LoanRequest request) {
        if (request != null && request.getMonths() > 0 && request.getAmount() > 0) {
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
                    return new LoanResponse(ResponseType.ERROR, service.createRequest(request));
            }
        } else {
            return new LoanResponse(ResponseType.ERROR, service.createRequest(request));
        }
    }
}
