package com.example.sumedhsp2.esep;

public class EnergySharing_Lender {
    String name;
    String username;
    String type;
    String phone_num;
    int price;
    int status;
    public EnergySharing_Lender(){

    }
    public EnergySharing_Lender(String name, String username, String phone_num, String type, int price, int status){
        this.name = name;
        this.phone_num = phone_num;
        this.username = username;
        this.type = type;
        this.price = price;
        this.status = status;
    }
    public String getPhone_num(){
        return phone_num;
    }
    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public int getStatus() {
        return status;
    }
}
