package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.request.LoanRequest;

public interface LoanCalcRepository {
    int save(LoanRequest request);
}
