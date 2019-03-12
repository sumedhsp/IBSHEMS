package com.example.sumedhsp2.esep;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class energyPredictionActivity extends AppCompatActivity {
    ItemAdapter1 itemAdapter1;
    List<String> device_names = new ArrayList<String>();
    List<String> device_location = new ArrayList<String>();
    List<Integer> power_device = new ArrayList<Integer>();
    List<Float> powerUsage = new ArrayList<Float>();
    ListView predictionListView;
    DatabaseReference mRef;
    DatabaseReference retrieve_data;
    Context predictionContext;
    Spinner spinnerEnergyPrediction;
    EditText location_energyPrediction;
    EditText costs_energyPrediction;
    Button add_devices;
    String name_id = "";
    int counter;
    ProgressBar progressBar_predictionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy_prediction);

        if (getIntent().hasExtra("name_PREDICTION")){
            name_id = getIntent().getExtras().getString("name_PREDICTION");
        }
        predictionListView = (ListView)findViewById(R.id.prediction_list);
        predictionContext = this;
        location_energyPrediction  = (EditText)findViewById(R.id.editText_location_EP);
        costs_energyPrediction = (EditText)findViewById(R.id.editText_costs_EP);
        spinnerEnergyPrediction = (Spinner)findViewById(R.id.spinner_energyPrediction);
        retrieve_data = FirebaseDatabase.getInstance().getReference("devices/"+name_id);
        mRef = FirebaseDatabase.getInstance().getReference("devices");
        add_devices = (Button)findViewById(R.id.button_addDevice);
        progressBar_predictionID = (ProgressBar)findViewById(R.id.progressBar_predictionID);


        add_devices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar_predictionID.setVisibility(View.VISIBLE);
                int costs;
                String location_str = location_energyPrediction.getText().toString();
                String device_str = spinnerEnergyPrediction.getSelectedItem().toString();
                String costs_str = costs_energyPrediction.getText().toString();
                if (device_str.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Device cannot be empty", Toast.LENGTH_SHORT).show();
                }
                if (location_str.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Location cannot be empty....", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (costs_str.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Cost cannot be empty....", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    costs = Integer.valueOf(costs_str);
                }
                energyPrediction_Class e = new energyPrediction_Class(name_id, location_str, device_str, costs);
                mRef.child(name_id).child(device_str).setValue(e).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Device added....", Toast.LENGTH_SHORT).show();
                            progressBar_predictionID.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        /*device_names.add("Iron");
        device_location.add("Bedroom");
        power_device.add(400);
        device_names.add("Air Conditioner");
        device_location.add("Bedroom");
        power_device.add(800);

        if (device_names.size()>0) {
            itemAdapter1 = new ItemAdapter1(predictionContext, device_names, device_location, power_device);
            predictionListView.setAdapter(itemAdapter1);
        }*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar_predictionID.setVisibility(View.VISIBLE);
        retrieve_data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //counter = 0;
                //List <Float> copy_powerUsage = new ArrayList<>(powerUsage);
                powerUsage.clear();
                device_location.clear();
                device_names.clear();
                power_device.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    String device_name = ds.getValue(energyPrediction_Class.class).getDevice();
                    String location = ds.getValue(energyPrediction_Class.class).getLocation();
                    int powerThreshold = ds.getValue(energyPrediction_Class.class).getThresholdPower();
                    float powerUsage1 = ds.getValue(energyPrediction_Class.class).getPredictedPower();
                    powerUsage1 = powerUsage1/1000;
                    /*if (powerUsage1==copy_powerUsage.get(counter))
                    {
                        float temp_1 = 0;
                        powerUsage.add(temp_1);
                    }*/
                    //else{
                    powerUsage.add(powerUsage1);
                   // Toast.makeText(getApplicationContext(), counter, Toast.LENGTH_SHORT).show();
                    //}
                    device_location.add(location);
                    device_names.add(device_name);
                    power_device.add(powerThreshold);
                    //
                    // counter++;

                }
                progressBar_predictionID.setVisibility(View.GONE);
                if (device_names.size()>0) {
                    itemAdapter1 = new ItemAdapter1(predictionContext, device_names, device_location, powerUsage, power_device);
                    predictionListView.setAdapter(itemAdapter1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    /*public void refresh_data(){
        retrieve_data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                powerUsage.clear();
                device_location.clear();
                device_names.clear();
                power_device.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    String device_name = ds.getValue(energyPrediction_Class.class).getDevice();
                    String location = ds.getValue(energyPrediction_Class.class).getLocation();
                    int powerThreshold = ds.getValue(energyPrediction_Class.class).getThresholdPower();
                    int powerUsage1 = ds.getValue(energyPrediction_Class.class).getPredictedPower();
                    //powerUsage1 = powerUsage1*220;
                    powerUsage.add(powerUsage1);
                    device_location.add(location);
                    device_names.add(device_name);
                    power_device.add(powerThreshold);

                }
                progressBar_predictionID.setVisibility(View.GONE);
                if (device_names.size()>0) {
                    itemAdapter1 = new ItemAdapter1(predictionContext, device_names, device_location, powerUsage, power_device);
                    predictionListView.setAdapter(itemAdapter1);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/
}
