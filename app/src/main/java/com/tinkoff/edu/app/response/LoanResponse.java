package com.tinkoff.edu.app.response;

/**
 * TODO добавить использование объекта LoanResponse
 */

public class LoanResponse {
    private static int count = 0;
    private ResponseType responseType;
    private int responseId;

    public LoanResponse(ResponseType responseType) {
        this.responseType = responseType;
        setResponseId(++count);
    }

    public ResponseType getResponse() {
        return responseType;
    }

    public int getResponseId() {
        return responseId;
    }

    public void setResponseId(int responseId) {
        this.responseId = responseId;
    }
}
