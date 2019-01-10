package com.gmail.tamyewwah.mywallet;

import java.util.Date;

public class Transaction {
    private String Name;
    private String Pay_Date;
    private Double Total;
    private String User;

    public Transaction(String Name,String Pay_Date,Double Total,String User){

        this.Name=Name;
        this.Pay_Date=Pay_Date;
        this.Total=Total;
        this.User=User;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getName() {
        return Name;
    }

    public void setPay_Date(String Pay_Date) {
        this.Pay_Date = Pay_Date;
    }

    public String getPay_Date() {
        return Pay_Date;
    }

    public void setTotal(Double Total) {
        this.Total = Total;
    }

    public Double getTotal() {
        return Total;
    }

    public void setUser(String User) {
        this.User = User;
    }

    public String getUser() {
        return User;
    }
}
