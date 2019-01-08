package com.gmail.tamyewwah.mywallet;
import com.google.firebase.database.Exclude;
import java.io.Serializable;

public class User implements Serializable{
    @Exclude
    private String id, full_name, user_name, phone_no, email, password, pin;

    public User(){
        this.id = null;
        this.full_name = null;
        this.user_name = null;
        this.phone_no = null;
        this.email = null;
        this.password = null;
        this.pin = null;
    }

    User(String id, String full_name, String user_name, String phone_no, String email, String password, String pin) {
        this.id = id;
        this.full_name = full_name;
        this.user_name = user_name;
        this.phone_no = phone_no;
        this.email = email;
        this.password = password;
        this.pin = pin;
    }

    public void setId(String id){ this.id = id; }

    public String getId(){return id;}

    public void setFull_name (String full_name){ this.full_name = full_name; }

    public String getFull_name(){ return full_name; }

    public void setUser_name(String user_name){this.user_name = user_name;}

    public String getUser_name(){return user_name;}

    public void setPhone_no(String phone_no){this.phone_no = phone_no;}

    public String getPhone_no(){return phone_no;}

    public void setEmail(String email){this.email = email;}

    public String getEmail(){return email;}

    public void setPassword(String password){this.password = password;}

    public String getPassword(){return password;}

    public void setPin(String pin){this.pin = pin;}

    public String getPin(){return pin;}
}