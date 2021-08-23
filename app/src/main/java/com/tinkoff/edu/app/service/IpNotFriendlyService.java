package com.tinkoff.edu.app.service;

import com.tinkoff.edu.app.repository.LoanCalcRepository;
import com.tinkoff.edu.app.request.LoanRequest;
import com.tinkoff.edu.app.request.LoanType;

public class IpNotFriendlyService extends LoanCalcService {
    public IpNotFriendlyService(LoanCalcRepository repository) {
        super(repository);
    }

    @Override
    public int createRequest(LoanRequest request) {
        if (request.getType().equals(LoanType.IP)) return -1;
        return super.createRequest(request);
    }
}
