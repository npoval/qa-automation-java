package com.tinkoff.edu.app.controller;


import com.tinkoff.edu.app.logger.LoanCalcLogger;
import com.tinkoff.edu.app.service.LoanCalcService;

public class LoanCalcController {
    /**
     * TODO Validates and logs request
     */
    public static int createRequest() {

        //param validation
        //log request
        LoanCalcLogger.log();
        return LoanCalcService.createRequest();
    }
}

