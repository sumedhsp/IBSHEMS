package com.example.sumedhsp2.esep;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main2Activity extends AppCompatActivity {
    String username;
    String name_id = "";
    String phone_num = "";
    String type;
    DatabaseReference myRef;
    TextView textView_user_ID_name, textView6_main2activity;
    DatabaseReference lender_db;
    ProgressBar progressBar_main2activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        if (getIntent().hasExtra("name")){
            username = getIntent().getExtras().getString("username");
            name_id = getIntent().getExtras().getString("name");
            type = getIntent().getExtras().getString("type");
        }
        if (getIntent().hasExtra("username_main_activity")){
            username = getIntent().getExtras().getString("username_main_activity");
        }
        progressBar_main2activity = (ProgressBar)findViewById(R.id.progressBar_main2activity);
        myRef = FirebaseDatabase.getInstance().getReference("Customer");
        lender_db = FirebaseDatabase.getInstance().getReference("Lender");
        textView_user_ID_name = (TextView)findViewById(R.id.textView_user_ID_name);
        Button button_contact_us = (Button)findViewById(R.id.button_contact_us);
        button_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent notification_class = new Intent(getApplicationContext(), NotificationService.class);
                //notification_class.putExtra("username_main_activity", username);
                startService(notification_class);
                //Toast.makeText(Main2Activity.this, "Checked-16", Toast.LENGTH_SHORT).show();
                Intent contact_us = new Intent(getApplicationContext(), contact_us.class);
                startActivity(contact_us);
            }
        });
        Button button_about_us = (Button)findViewById(R.id.button_about_us);
        button_about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent about_us = new Intent(getApplicationContext(), about_us.class);
                startActivity(about_us);
            }
        });
        textView6_main2activity = (TextView)findViewById(R.id.textView6_main2activity);
        Button log_out = (Button)findViewById(R.id.log_out_ID);
        final Button energy_sharing_ID = (Button)findViewById(R.id.energy_sharing_ID);
        final Button energy_prediction_ID = (Button)findViewById(R.id.energy_prediction_ID);
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log_out = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(log_out);
            }
        });
        energy_sharing_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("Customer")) {
                    Intent energy_sharing_v = new Intent(getApplicationContext(), enerySharingActivity.class);
                    energy_sharing_v.putExtra("username", username);
                    startActivity(energy_sharing_v);
                }
                else{
                    Intent energy_sharing_v = new Intent(getApplicationContext(), energySharingActivity_Lender.class);
                    energy_sharing_v.putExtra("username_lender", username);
                    energy_sharing_v.putExtra("LENDER_NAME", name_id);
                    energy_sharing_v.putExtra("LENDER_PHONE", phone_num);
                    startActivity(energy_sharing_v);
                }
            }
        });
        energy_prediction_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent energy_prediction_v = new Intent(getApplicationContext(), energyPredictionActivity.class);
                energy_prediction_v.putExtra("name_PREDICTION", name_id);
                startActivity(energy_prediction_v);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        final Boolean[] flag = {true};
        progressBar_main2activity.setVisibility(View.VISIBLE);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dc: dataSnapshot.getChildren()){
                    String email_t = dc.getValue(Users.class).getUsername();
                    //Toast.makeText(Main2Activity.this, email_t, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(Main2Activity.this, name_id, Toast.LENGTH_SHORT).show();
                    if (email_t.equals(username)){
                        name_id = dc.getValue(Users.class).getName();
                        phone_num = dc.getValue(Users.class).getPhone_num();
                        type = dc.getValue(Users.class).getType();
                    }
                    if (name_id.isEmpty()){
                        flag[0] = true;
                        //Toast.makeText(getApplicationContext(), name_id, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        flag[0] = false;
                        textView_user_ID_name.setText("Welcome " + name_id);
                        progressBar_main2activity.setVisibility(View.GONE);
                        textView6_main2activity.setVisibility(View.VISIBLE);
                        //complete the progress bar here, so that the user can now start interacting....;
                    }
                   // Toast.makeText(Main2Activity.this, name_id, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (flag[0]) {
            lender_db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        String email_t = ds.getValue(EnergySharing_Lender.class).getUsername();
                       // Toast.makeText(Main2Activity.this, email_t, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(Main2Activity.this, name_id, Toast.LENGTH_SHORT).show();
                        if (email_t.equals(username)){
                            name_id = ds.getValue(EnergySharing_Lender.class).getName();
                            phone_num = ds.getValue(EnergySharing_Lender.class).getPhone_num();
                            type = ds.getValue(EnergySharing_Lender.class).getType();
                            progressBar_main2activity.setVisibility(View.GONE);
                            textView_user_ID_name.setText("Welcome " + name_id);
                            textView6_main2activity.setVisibility(View.VISIBLE);
                            //complete the progress bar here, so that the user can now start interacting....;
                        }
                        //Toast.makeText(Main2Activity.this, name_id, Toast.LENGTH_SHORT).show();
                       // Toast.makeText(Main2Activity.this, type, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
}
