package com.gmail.tamyewwah.mywallet;

public class TransactionRecord {
    private String Name;
    private String Date;
    private Double Amount;
    private String UserID;

    public TransactionRecord(String Name, String Date, Double Amount, String UserID) {
        this.Name = Name;
        this.Date = Date;
        this.Amount = Amount;
        this.UserID = UserID;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getName() {
        return Name;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getDate() {
        return Date;
    }

    public void setAmount(Double amount) {
        this.Amount = amount;
    }

    public Double getAmount() {
        return Amount;
    }

    public void setUserID(String userID) {
        this.UserID = userID;
    }

    public String getUserID() {
        return UserID;
    }
}

