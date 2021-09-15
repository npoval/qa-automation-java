package com.tinkoff.edu.app.service;

import com.tinkoff.edu.app.request.LoanRequest;

import java.util.UUID;

public interface BusinessService {
    UUID createRequest(LoanRequest request);
}
