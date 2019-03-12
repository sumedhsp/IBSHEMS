package com.example.sumedhsp2.esep;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class about_us extends AppCompatActivity {
    TextView textView_aboutus2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        textView_aboutus2 = (TextView)findViewById(R.id.textView_aboutus2);
        textView_aboutus2.setText("As the theme of this competition is “disruptive technologies” i.e. replacing existing technologies, we have come across with the idea " +
                "of implementing IOT BASED SMART HOME ENERGY MANAGEMENT SYSTEM(IBSHEMS). " +
                "This project is divided into two parts: \n" + "The first objective is to use distributed generation technology in an economical and beneficial way. " +
                "Due to excessive usage of non-renewable sources of energy in India, fossil-fuels are decreasing in quantity at an alarming rate. In order to reduce the " +
                "dependency on " +
                "non-renewable sources of energy i.e. central generation, the homes locality can make use of distributed generation which will incorporate usage of" +
                " renewable sources of energy like solar, wind or biogas. \n"
                + "Suppose that in a society, one of the residents has a solar panel or any such power generating source. Now if the sources are not in use or the owner " +
                "is interested to sell his power, a simple app is created which is interfaced such that any resident of the society who wants to borrow the solar energy " +
                "can request to the owner through this software. If the owner accepts the request, then by making use of the system involved in this project, the power gets " +
                "passed to the lender’s house. The owner can even charge money based on per hour usage of the lender which will again be monitored by the clamp sensors which" +
                " is described in the second part of this project. Energy transferring part has been done by using relay modules connected to solar-panels. We are also using " +
                "solar charge controller based on maximum power-point tracking(hill-climbing techniques at perturbated and observational methods ) for obtaining maximum energy" +
                " from solar panels. Clearing price concept is involved when a greater number of customers participate in this process.\n" +
                "The second objective is to monitor energy consumption and analyse the day to day usage of each load/appliance from user by incorporating clamp sensors." +
                " These sensors can be attached as a clip to any wire, which will sense the current and voltage of the supply. Taking the current, voltage, power consumption" +
                " of each appliance for a period of 2 years and fitting a regression model on the data, a prediction is made for the optimum settings. These optimum settings" +
                " will be ensuring that minimum power is consumed with optimized output. This will be the most efficient setting for an appliance, and it will be displayed on" +
                " the application software so that the user can set appliance accordingly. Also, using previous data of appliance, energy consumption of the system can " +
                "determine the degradation in device performance over the years and if the user needs to buy a new device. These clamp sensors will have NodeMCU " +
                "(cloud-based microcontroller) installed so that the sensor can communicate to the cloud. " + "This data is processed in the cloud and will be retrieved/passed " +
                "to the mobile app. \n");
    }
}
