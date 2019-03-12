package com.example.sumedhsp2.esep;

public class energyPrediction_Class {
    String name;
    String location;
    String device;
    float predictedPower;
    int thresholdPower;
    public energyPrediction_Class(){

    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDevice() {
        return device;
    }

    public float getPredictedPower() {
        return predictedPower;
    }

    public int getThresholdPower() {
        return thresholdPower;
    }

    public energyPrediction_Class(String name, String location, String device, int thresholdPrice) {
        this.name = name;
        this.location = location;
        this.device = device;
        this.predictedPower = 0;
        int kwH = 5;
        int power_to_be_used = thresholdPrice/kwH;
        this.thresholdPower = power_to_be_used;
        // this.thresholdPower = thresholdPrice;
        //(P0+30*24*3600);
    }
}
