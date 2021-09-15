package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.request.LoanRequest;
import com.tinkoff.edu.app.response.LoanResponse;

public class LoanRequestResponse {
    LoanResponse loanResponse;
    LoanRequest loanRequest;

    public LoanResponse getLoanResponse() {
        return loanResponse;
    }

    public void setLoanResponse(LoanResponse loanResponse) {
        this.loanResponse = loanResponse;
    }

    public LoanRequest getLoanRequest() {
        return loanRequest;
    }

    public void setLoanRequest(LoanRequest loanRequest) {
        this.loanRequest = loanRequest;
    }

    public LoanRequestResponse(LoanResponse loanResponse, LoanRequest loanRequest) {
        this.loanResponse = loanResponse;
        this.loanRequest = loanRequest;
    }
}

