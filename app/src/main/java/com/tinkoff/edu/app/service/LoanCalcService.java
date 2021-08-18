package com.tinkoff.edu.app.service;

import com.tinkoff.edu.app.request.LoanRequest;
import com.tinkoff.edu.app.repository.LoanCalcRepository;

public class LoanCalcService {
    /**
     * TODO Loan calculation
     */
    public int createRequest(LoanRequest request) {
        LoanCalcRepository repository = new LoanCalcRepository();
        return repository.save(request);
    }
}
