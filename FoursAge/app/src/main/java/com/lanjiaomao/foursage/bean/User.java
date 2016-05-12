package com.lanjiaomao.foursage.bean;

/**
 * Created by root on 2016/5/7.
 */
public class User {
    private String phoneNumber;
    private String passWord;

    public User(String phoneNumber, String passWord) {
        this.phoneNumber = phoneNumber;
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "User{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
