package com.tinkoff.edu;


import com.tinkoff.edu.app.response.LoanResponse;
import com.tinkoff.edu.app.response.ResponseType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoanResponseTest {
    private LoanResponse response;

    @Test
    @DisplayName("Проверка инкремента responseId")
    public void getResponseIdAnyCall() {
        for (int i = 1; i < 5; i++) {
            response = new LoanResponse(ResponseType.APPROVED);
            assertEquals(i, response.getResponseId());
            System.out.println(response.getResponseId());
        }
    }
}
