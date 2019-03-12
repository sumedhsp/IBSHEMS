package com.example.sumedhsp2.esep;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
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

public class energySharingActivity_Lender extends AppCompatActivity {
    ListView listView_lender;
    DatabaseReference mRef;
    DatabaseReference lender1;
    DatabaseReference lender_status;
    String username;
    String phone_num;
    String name_id;
    List<String> customer_Name = new ArrayList<String>();
    List<Integer> power_Requirement = new ArrayList<Integer>();
    ItemAdapter2 itemAdapter2;
    Context thisContext;
    ProgressBar progressBar_energySharing_Lender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy_sharing__lender);

       if (getIntent().hasExtra("username_lender")){
            username = getIntent().getExtras().getString("username_lender");
            name_id = getIntent().getExtras().getString("LENDER_NAME");
            phone_num = getIntent().getExtras().getString("LENDER_PHONE");
        }

        listView_lender = (ListView)findViewById(R.id.listView_lender);
        mRef = FirebaseDatabase.getInstance().getReference("connection");
        lender_status = FirebaseDatabase.getInstance().getReference("Lender");
        lender1 = FirebaseDatabase.getInstance().getReference("connection");
        progressBar_energySharing_Lender = (ProgressBar)findViewById(R.id.progressBar_energySharing_Lender);
        thisContext = this;

        listView_lender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                progressBar_energySharing_Lender.setVisibility(View.VISIBLE);
                String cust_temp = customer_Name.get(i);
                final String lender_name = name_id;
                int power_req_temp = power_Requirement.get(i);
                connectionClass c1 = new connectionClass(true, true, cust_temp, lender_name, power_req_temp);
                lender1.child(lender_name).setValue(c1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(thisContext, "Energy Transfer beginning....", Toast.LENGTH_SHORT).show();
                            EnergySharing_Lender e = new EnergySharing_Lender(name_id, username, phone_num, "Lender", 10, 1);
                            lender_status.child(name_id).setValue(e).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        //Toast.makeText(energySharingActivity_Lender.this, "Status changed....", Toast.LENGTH_SHORT).show();
                                        progressBar_energySharing_Lender.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar_energySharing_Lender.setVisibility(View.VISIBLE);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                customer_Name.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    String name_temp = ds.getValue(connectionClass.class).getLenderName();
                    //Toast.makeText(thisContext, name_temp, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(thisContext, name_id, Toast.LENGTH_SHORT).show();
                    if (name_temp.equals(name_id)){
                        int temp_power = ds.getValue(connectionClass.class).getPowerReq();
                        String Customer_temp = ds.getValue(connectionClass.class).getCustomerName();
                        customer_Name.add(Customer_temp);
                        power_Requirement.add(temp_power);
                    }
                }

                progressBar_energySharing_Lender.setVisibility(View.GONE);
                if (customer_Name.size() > 0) {
                    itemAdapter2 = new ItemAdapter2(thisContext, customer_Name, power_Requirement);
                    listView_lender.setAdapter(itemAdapter2);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
