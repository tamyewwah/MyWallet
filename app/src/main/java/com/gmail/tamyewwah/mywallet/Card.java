package com.gmail.tamyewwah.mywallet;

public class Card{

    private String cardname, goothru, cardType, userid;
    private int cardnum, cvcnum;
    private boolean isSelected;

    public Card(int cardnum, int cvcnum, String cardname, String goothru, String cardType, String userid) {
        this.cardnum = cardnum;
        this.cvcnum = cvcnum;
        this.cardname = cardname;
        this.goothru = goothru;
        this.cardType = cardType;
        this.userid = userid;
    }

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    public String getGoothru() {
        return goothru;
    }

    public void setGoothru(String goothru) {
        this.goothru = goothru;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getCardnum() {
        return cardnum;
    }

    public void setCardnum(int cardnum) {
        this.cardnum = cardnum;
    }

    public int getCvcnum() {
        return cvcnum;
    }

    public void setCvcnum(int cvcnum) {
        this.cvcnum = cvcnum;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
