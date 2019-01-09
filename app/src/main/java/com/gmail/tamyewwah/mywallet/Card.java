package com.gmail.tamyewwah.mywallet;

public class Card{

    private String CVC, CardName, GoodThru, Type, Total, User;

    public Card(String CVC, String CardName, String GoodThru, String Total,String Type, String User) {
        this.CVC = CVC;
        this.CardName = CardName;
        this.GoodThru = GoodThru;
        this.Total = Total;
        this.Type = Type;
        this.User = User;
    }

    public String getCVC() { return CVC; }

    public void setCVC(String CVC) { this.CVC = CVC; }

    public String getCardName() { return CardName; }

    public void setCardName(String CardName) { this.CardName = CardName; }

    public String getGoodThru() { return GoodThru; }

    public void setGoodThru(String GoodThru) { this.GoodThru = GoodThru; }

    public String getType() { return Type; }

    public void setType(String Type) { this.Type = Type; }

    public String getUser() { return User; }

    public void setUser(String User) { this.User = User; }

    public String getTotal() { return Total; }

    public void setTotal(String Total) { this.Total = Total; }
}
