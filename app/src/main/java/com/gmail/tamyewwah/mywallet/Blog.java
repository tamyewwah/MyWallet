package com.gmail.tamyewwah.mywallet;

public class Blog {
    private String Company;
    private String Description;
    private Double Discount_rate;
    private String EndDate;
    private String Name;
    private String Picture;
    private String StartDate;

    public Blog(String Company, String Description, Double Discount_rate, String EndDate, String Name, String Picture, String StartDate)
    {
        this.Company = Company;
        this.Description = Description;
        this.Discount_rate = Discount_rate;
        this.EndDate = EndDate;
        this.Name = Name;
        this.Picture = Picture;
        this.StartDate = StartDate;
    }

    public void setCompany(String company){this.Company = company;}
    public String getCompany(){return Company;}

    public void setDescription(String description){ this.Description = description;}
    public String getDescription(){return Description;}

    public void setDiscount_rate(Double discount_rate){this.Discount_rate = discount_rate;}
    public Double getDiscount_rate(){return Discount_rate;}

    public void setEndDate(String endDate){ this.EndDate = endDate;}
    public String getEndDate(){return EndDate;}

    public void setName(String name){this.Name = name;}
    public String getName(){return Name;}

    public void setPicture(String picture){this.Picture = picture;}
    public String getPicture(){return Picture;}

    public void setStartDate(String startDate){this.StartDate = startDate;}
    public String getStartDate(){return StartDate;}}
