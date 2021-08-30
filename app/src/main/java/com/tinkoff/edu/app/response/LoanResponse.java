package com.tinkoff.edu.app.response;

import java.util.Objects;

/**
 * TODO добавить использование объекта LoanResponse
 */

public class LoanResponse {
    //private static int count = 0;
    private ResponseType responseType;
    private  int responseId;

    public LoanResponse(ResponseType responseType, int responseId) {
        this.responseType = responseType;
        this.responseId = responseId;
    }


    public ResponseType getResponse() {
        return responseType;
    }

    public int getResponseId() {
        return responseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanResponse response = (LoanResponse) o;
        return getResponseId() == response.getResponseId() && responseType == response.responseType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseType, getResponseId());
    }
}
