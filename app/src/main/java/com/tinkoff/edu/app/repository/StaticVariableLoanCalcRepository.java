package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.request.LoanRequest;

public class StaticVariableLoanCalcRepository implements LoanCalcRepository {
    private static int requestId;

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


