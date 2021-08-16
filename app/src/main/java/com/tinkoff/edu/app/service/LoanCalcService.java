package com.tinkoff.edu.app.service;

import com.tinkoff.edu.app.repository.LoanCalcRepository;

public class LoanCalcService {
    /**
     * TODO Loan calculation
     */
    public static int createRequest() {
        return LoanCalcRepository.save();
    }
}

