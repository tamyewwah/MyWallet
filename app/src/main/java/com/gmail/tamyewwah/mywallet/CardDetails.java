package com.gmail.tamyewwah.mywallet;

public class CardDetails {
    private String CardName;
    private String GoodTill;
    private String CardType;
    private Integer CVC;

    public CardDetails(String CardName, String GoodTill, String CardType, Integer CVC){
        this.CardName = CardName;
        this.GoodTill = GoodTill;
        this.CardType = CardType;
        this.CVC = CVC;
    }

    public void setCardName(String CardName) {
        this.CardName = CardName;
    }

    public String getCardName() {
        return CardName;
    }

    public void setGoodTill(String GoodTill) {
        this.GoodTill = GoodTill;
    }

    public String getGoodTill() { return GoodTill; }

    public void setCardType(String CardType) {
        this.CardType = CardType;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCVC(int CVC) {
        this.CVC = CVC;
    }

    public int getCVC() {
        return CVC;
    }
}
