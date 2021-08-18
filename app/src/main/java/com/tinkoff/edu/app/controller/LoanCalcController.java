package com.tinkoff.edu.app.controller;


import com.tinkoff.edu.app.request.LoanRequest;
import com.tinkoff.edu.app.logger.LoanCalcLogger;
import com.tinkoff.edu.app.service.LoanCalcService;

public class LoanCalcController {
    /**
     * TODO Validates and logs request
     */
    public int createRequest(LoanRequest request) {
        LoanCalcService service = new LoanCalcService();

        //param validation
        //log request
        LoanCalcLogger.log(request);
        return service.createRequest(request);
    }
}
