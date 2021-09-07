package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.request.LoanRequest;
import com.tinkoff.edu.app.request.LoanType;
import com.tinkoff.edu.app.response.ResponseType;

import java.util.*;

public class MapLoanCalcRepository implements LoanCalcRepository {

    Map<UUID, LoanRequestResponse> loanRequestResponseMap = new HashMap<>();

    public void setLoanRequestResponseMap(UUID uuid, LoanRequestResponse loanRequestResponse) {
        this.loanRequestResponseMap.put(uuid, loanRequestResponse);
    }

    public void setStatus(UUID uuid, ResponseType responseType) {
        if (this.loanRequestResponseMap.containsKey(uuid)) {
            this.loanRequestResponseMap.get(uuid).getLoanResponse().setResponseType(responseType);
        }
    }

    public ResponseType getStatus(UUID uuid) {
        if (this.loanRequestResponseMap.containsKey(uuid)) {
            return this.loanRequestResponseMap.get(uuid).getLoanResponse().getResponseType();
        } else {
            return ResponseType.NOTFOUND;
        }
    }

    public List<LoanRequest> getLoanRequestByLoanType(LoanType loanType) {
        List<LoanRequest> loanRequestsList = new ArrayList<>();
        for (Map.Entry<UUID, LoanRequestResponse> entry : this.loanRequestResponseMap.entrySet()) {
            if (entry.getValue().getLoanRequest().getType() == loanType) {
                loanRequestsList.add(entry.getValue().getLoanRequest());
            }
        }
        return loanRequestsList;
    }

    public int getCountLoanRequestByLoanType(LoanType loanType) {
        List<LoanRequest> loanRequestList = getLoanRequestByLoanType(loanType);
        return loanRequestList.size();
    }

    @Override
    public UUID save(LoanRequest request) {
        return UUID.randomUUID();
    }
}

