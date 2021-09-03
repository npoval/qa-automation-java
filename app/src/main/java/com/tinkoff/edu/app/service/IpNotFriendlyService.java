package com.tinkoff.edu.app.service;

import com.tinkoff.edu.app.repository.LoanCalcRepository;
import com.tinkoff.edu.app.request.LoanRequest;
import com.tinkoff.edu.app.request.LoanType;

import java.util.UUID;

public class IpNotFriendlyService extends LoanCalcService {
    public IpNotFriendlyService(LoanCalcRepository repository) {
        super(repository);
    }

    @Override
    public UUID createRequest(LoanRequest request) {
        if (request.getType().equals(LoanType.IP)) {
            return null;
        }
        return super.createRequest(request);
    }
}
