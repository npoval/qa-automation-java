package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.request.LoanRequest;

public class VariableLoanCalcRepository implements LoanCalcRepository {
    private int requestId;

    public VariableLoanCalcRepository(int requestId) {
        this.requestId = requestId;
    }

    public VariableLoanCalcRepository() {
        this(0);
    }


    /**
     * TODO persists request
     *
     * @return Request Id
     */
    @Override
    public int save(LoanRequest request) {
        return ++requestId;
    }
}


