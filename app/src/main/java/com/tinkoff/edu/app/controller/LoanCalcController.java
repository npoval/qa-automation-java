package com.tinkoff.edu.app.controller;


import com.tinkoff.edu.app.request.LoanRequest;
import com.tinkoff.edu.app.request.LoanType;
import com.tinkoff.edu.app.response.LoanResponse;
import com.tinkoff.edu.app.response.ResponseType;
import com.tinkoff.edu.app.service.BusinessService;

public class LoanCalcController {
    private BusinessService service;


    public LoanCalcController(BusinessService service) {
        this.service = service;
    }

    /**
     * TODO Validates and logs request
     */
    public LoanResponse createRequest(LoanRequest request) {


        //param validation
        //log request
        //LoanCalcLogger.log(request);
        if ((request != null && request.getType().equals(LoanType.PERSON) && (request.getAmount() > 0 && request.getAmount() <= 10000) && ((request.getMonths() > 0) && request.getMonths() <= 12)) || ((request != null && request.getType().equals(LoanType.OOO)) && (request.getAmount() > 10000) && (request.getMonths() > 0 && request.getMonths() < 12))) {
            return new LoanResponse(ResponseType.APPROVED, service.createRequest(request));
        } else if (request == null || request.getAmount() <= 0 || request.getMonths() <= 0) {
            return new LoanResponse(ResponseType.ERROR, -1);
        } else
            return new LoanResponse(ResponseType.DENIED, service.createRequest(request));
    }
}

