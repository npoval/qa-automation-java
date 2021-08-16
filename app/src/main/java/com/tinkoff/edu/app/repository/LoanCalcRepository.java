package com.tinkoff.edu.app.repository;

public class LoanCalcRepository {
    private static int requestId;

    /**
     * TODO persists request
     *
     * @return Request Id
     */
    public static int save() {
        return ++requestId;
    }
}


