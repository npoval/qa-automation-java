package com.tinkoff.edu.app.service;

import com.tinkoff.edu.app.repository.LoanCalcRepository;
import com.tinkoff.edu.app.request.LoanRequest;

public class LoanCalcService implements BusinessService {
    private LoanCalcRepository repository;

    public LoanCalcService(LoanCalcRepository repository) {
        this.repository = repository;
    }

    /**
     * TODO Loan calculation
     */
    public int createRequest(LoanRequest request) {
        return repository.save(request);
    }
}
