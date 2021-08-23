package com.tinkoff.edu.app.controller;


import com.tinkoff.edu.app.request.LoanRequest;
import com.tinkoff.edu.app.service.BusinessService;

public class LoanCalcController {
    private BusinessService service;


    public LoanCalcController(BusinessService service) {
        this.service = service;
    }

    /**
     * TODO Validates and logs request
     */
    public int createRequest(LoanRequest request) {


        //param validation
        //log request
        //LoanCalcLogger.log(request);
        return service.createRequest(request);
    }
}
