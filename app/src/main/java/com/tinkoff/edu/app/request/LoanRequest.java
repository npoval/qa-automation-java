package com.tinkoff.edu.app.request;

/**
 * Class, Type-> objects,instances
 */
public class LoanRequest {


    private final LoanType type;
    private final int months;
    private final int amount;
    private final String fio;

    public LoanRequest(LoanType type, int months, int amount, String fio) {
        this.type = type;
        this.months = months;
        this.amount = amount;
        this.fio = fio;
    }

    public int getMonths() {
        return months;
    }

    public int getAmount() {
        return amount;
    }

    public LoanType getType() {
        return type;
    }

    public String getFio() {
        return fio;
    }

    @Override
    public String toString() {
        return "RQ: {"
                + this.type + ", "
                + this.getAmount()
                + " for " + this.getMonths() +
                "}";
    }
}
