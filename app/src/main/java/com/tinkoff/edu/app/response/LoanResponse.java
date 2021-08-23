package com.tinkoff.edu.app.response;

/**
 * TODO добавить использование объекта LoanResponse
 */

public class LoanResponse {
    private ResponseType response;
    private int responseId;

    public LoanResponse(ResponseType response) {
        this.response = response;
        this.responseId = ++responseId;
    }

    public ResponseType getResponse() {
        return response;
    }

    public int getResponseId() {
        return responseId;
    }
}
