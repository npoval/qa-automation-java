package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.request.LoanRequest;

import java.util.UUID;

public interface LoanCalcRepository {
    UUID save(LoanRequest request);
}
