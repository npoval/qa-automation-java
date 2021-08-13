package com.tinkoff.edu.app;

public class LoanCalcRepository {
    /**
     * TODO persists request
     *
     * @return Request Id
     */
    private static int requestId;

    public static int save() {
        return ++requestId;
    }
}


