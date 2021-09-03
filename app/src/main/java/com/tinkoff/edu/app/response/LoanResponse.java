package com.tinkoff.edu.app.response;

import java.util.UUID;

/**
 * TODO добавить использование объекта LoanResponse
 */

public class LoanResponse {
    private ResponseType responseType;
    private UUID responseId;

    public LoanResponse(ResponseType responseType, UUID responseId) {
        this.responseType = responseType;
        this.responseId = responseId;
    }

    public ResponseType getResponseType() {
        return responseType;
    }


    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public UUID getResponseId() {
        return responseId;
    }

}
