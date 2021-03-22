package com.bihanitech.shikshyaprasasak.model;

public class Statements {
    int sN;
    String date;
    String particulars;
    String amounts;
    String balance;

    public Statements(int sN, String date, String particulars, String amounts, String balance) {
        this.sN = sN;
        this.date = date;
        this.particulars = particulars;
        this.amounts = amounts;
        this.balance = balance;
    }

    public int getsN() {
        return sN;
    }

    public void setsN(int sN) {
        this.sN = sN;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getAmounts() {
        return amounts;
    }

    public void setAmounts(String amounts) {
        this.amounts = amounts;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
