package com.example.sumedhsp2.esep;

public class connectionClass {
    String customerName;
    String lenderName;
    Boolean customerRequest;
    Boolean lenderAccept;
    Boolean ack;
    int powerReq;
    public connectionClass(){

    }
    public connectionClass(Boolean customerRequest, Boolean lenderAccept, String CustomerName, String LenderName, int powerReq){
            this.customerRequest = customerRequest;
            this.lenderAccept = lenderAccept;
            this.ack = false;
            this.customerName = CustomerName;
            this.lenderName = LenderName;
            this.powerReq = powerReq;
    }

    public int getPowerReq() {
        return powerReq;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getLenderName() {
        return lenderName;
    }

    public Boolean getCustomerRequest() {
        return customerRequest;
    }

    public Boolean getLenderAccept() {
        return lenderAccept;
    }

    public Boolean getAck() {
        return ack;
    }
}
