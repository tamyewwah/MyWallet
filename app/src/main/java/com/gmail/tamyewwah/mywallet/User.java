package com.gmail.tamyewwah.mywallet;

public class User {
    private String id, full_name, user_name, phone_no, email, password;

    User(String id, String full_name, String user_name, String phone_no, String email, String password) {
        this.id = id;
        this.full_name = full_name;
        this.user_name = user_name;
        this.phone_no = phone_no;
        this.email = email;
        this.password = password;
    }
}