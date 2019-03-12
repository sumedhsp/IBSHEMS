package com.example.sumedhsp2.esep;

public class Users {
    String name;
    String username;
    String type;
    String phone_num;
    int status;
    public Users(){

    }
    public Users(String name, String username, String phone_num, String type){
        this.name = name;
        this.phone_num = phone_num;
        this.username = username;
        this.type = type;
        this.status = 0;
    }
    public  String getPhone_num(){
        return phone_num;
    }
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setType(String type) {
        this.type = type;
    }
}
