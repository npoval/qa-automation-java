package com.tinkoff.edu.app.service;

import com.tinkoff.edu.app.repository.LoanCalcRepository;
import com.tinkoff.edu.app.request.LoanRequest;

import java.util.UUID;

public class LoanCalcService implements BusinessService {
    private LoanCalcRepository repository;

    public LoanCalcService(LoanCalcRepository repository) {
        this.repository = repository;
    }

    /**
     * TODO Loan calculation
     */
    public UUID createRequest(LoanRequest request) {
        return repository.save(request);
    }
}
