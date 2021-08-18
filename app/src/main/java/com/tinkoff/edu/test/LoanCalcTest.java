package com.tinkoff.edu.test;

import com.tinkoff.edu.app.request.LoanRequest;
import com.tinkoff.edu.app.request.LoanType;
import com.tinkoff.edu.app.controller.LoanCalcController;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Loan Calc Tests
 */
public class LoanCalcTest {
    @Test
    public void testEquals() {
        LoanRequest request = new LoanRequest(LoanType.IP, 10, 1000);
        LoanCalcController controller = new LoanCalcController();
        int requestId = controller.createRequest(request);
        System.out.println(request);
        assertEquals(requestId, 1);
    }
}


