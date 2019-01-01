package com.gmail.tamyewwah.mywallet;

public class MessageBill {
    private String BillCode;
    private String Company;
    private String Description;
    private String ExpireDate;
    private Double Amount;
    private String UserID;

    public MessageBill(String BillCode,String Company,String Description,String ExpireDate,Double Amount,String UserID)
    {
        this.BillCode=BillCode;
        this.Company=Company;
        this.Description=Description;
        this.ExpireDate=ExpireDate;
        this.Amount=Amount;
        this.UserID=UserID;
    }

    public void setBillCode(String billCode) {
        this.BillCode = billCode;
    }

    public String getBillCode() {
        return BillCode;
    }

    public void setCompany(String company) {
        this.Company = company;
    }

    public String getCompany() {
        return Company;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getDescription() {
        return Description;
    }

    public void setExpireDate(String expireDate) {
        this.ExpireDate = expireDate;
    }

    public String getExpireDate() {
        return ExpireDate;
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
