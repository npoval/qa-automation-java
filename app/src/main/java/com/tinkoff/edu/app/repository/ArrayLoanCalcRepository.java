package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.request.LoanRequest;
import com.tinkoff.edu.app.response.LoanResponse;
import com.tinkoff.edu.app.response.ResponseType;

import java.util.Objects;
import java.util.UUID;

public class ArrayLoanCalcRepository implements LoanCalcRepository  {
    static LoanResponse[] responses = new LoanResponse[100000];
    static int counter;

    public ResponseType getStatus(UUID responseId) {
        for (int i = 0; i < responses.length; i++) {
            if (responses[i] != null) {
                if (Objects.equals(responses[i].getResponseId(), responseId))
                    return responses[i].getResponseType();
            }
        }
        return ResponseType.NOTFOUND;
    }

    public void writeResponse(LoanResponse response) {
        if (counter < responses.length - 1) {
            responses[counter] = response;
            counter++;
        }
    }

    public void setStatus(UUID responseId, ResponseType responseType) {
        for (int i = 0; i < responses.length; i++) {
            if (responses[i] != null) {
                if (Objects.equals(responses[i].getResponseId(), responseId)) {
                    responses[i].setResponseType(responseType);
                    break;
                }
            }
        }
    }

    @Override
    public UUID save(LoanRequest request) {
        UUID uuid = UUID.randomUUID();
        return uuid;
    }
}
