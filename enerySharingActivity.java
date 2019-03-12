package com.example.sumedhsp2.esep;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class enerySharingActivity extends AppCompatActivity {
    ItemAdapter itemAdapter;
    List<String> status = new ArrayList<String>();
    Map <String, Integer> price_details = new LinkedHashMap<String, Integer>();
    Map <String, String> name_details = new LinkedHashMap<String, String>();
    ListView myListView;
    Context thisContext;
    EditText input;
    DatabaseReference databaseReference;
    DatabaseReference clicked_reference;
    DatabaseReference new_database;
    DatabaseReference customerName;
    String email_id;
    String name_id;
    String phone_num;
    String test1 = "";
    int position = 100;
    ProgressBar progressBar_energySharing_customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enery_sharing);
        if (getIntent().hasExtra("username")){
            email_id = getIntent().getExtras().getString("username");
        }

        myListView = (ListView)findViewById(R.id.listview_ID);
        thisContext = this;
        customerName = FirebaseDatabase.getInstance().getReference("Customer");
        databaseReference = FirebaseDatabase.getInstance().getReference("Lender");
        clicked_reference = FirebaseDatabase.getInstance().getReference("Lender");
        new_database = FirebaseDatabase.getInstance().getReference("connection");
        progressBar_energySharing_customer = (ProgressBar)findViewById(R.id.progressBar_energySharing_customer);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    progressBar_energySharing_customer.setVisibility(View.VISIBLE);

                    String status_temp = status.get(i);
                    List<String> name_list = new ArrayList<>(name_details.values());
                    List<Integer> price_list = new ArrayList<>(price_details.values());
                    List<String> username_list = new ArrayList<>(name_details.keySet());
                    final String name_temp = name_list.get(i);
                    final String username_temp = username_list.get(i);
                    int price_temp = price_list.get(i);
                    build_dialog(name_temp, username_temp, price_temp);
                    //update_requirement(name_temp, username_temp, price_temp, test);
                  /*  EnergySharing_Lender e = new EnergySharing_Lender(name_temp, username_temp, "Lender", price_temp, 1);
                    clicked_reference.child(name_temp).setValue(e).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Request sent to the Lender....", Toast.LENGTH_SHORT).show();
                                connectionClass c = new connectionClass(true, false, name_id, name_temp, test);//To be user_input....
                                new_database.child(name_temp).setValue(c).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Connection created....", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });*/

                }
            });
    }
    @Override
    protected void onStart() {
        super.onStart();
        progressBar_energySharing_customer.setVisibility(View.VISIBLE);
        customerName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dc: dataSnapshot.getChildren()){
                    String email_t = dc.getValue(Users.class).getUsername();
                    String name_t  = dc.getValue(Users.class).getName();
                    if (email_t.equals(email_id)){
                        name_id = dc.getValue(Users.class).getName();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        status.clear();
        price_details.clear();
        name_details.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    String name = ds.getValue(EnergySharing_Lender.class).getName();
                    phone_num = ds.getValue(EnergySharing_Lender.class).getPhone_num();
                    int price = ds.getValue(EnergySharing_Lender.class).getPrice();
                    String username = ds.getValue(EnergySharing_Lender.class).getUsername();
                    int status1 = ds.getValue(EnergySharing_Lender.class).getStatus();
                    String type = ds.getValue(EnergySharing_Lender.class).getType();
                    String temp = "";
                    if (status1==0){
                        temp = "Available";
                    }
                    else if(status1==1){
                        temp = "Requested";
                    }
                    else{
                        temp = "Transferring units";
                    }
                    if (username.equals(email_id)){
                        continue;
                    }else{
                        status.add(temp);
                        price_details.put(username, price);
                        name_details.put(username, name);
                    }

                }
                progressBar_energySharing_customer.setVisibility(View.GONE);
                if (price_details.size() > 0) {
                    itemAdapter = new ItemAdapter(thisContext, price_details, status, name_details);
                    myListView.setAdapter(itemAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void build_dialog(final String name_temp, final String username_temp, final int price_temp){
        //To build this tomorrow!!
        AlertDialog.Builder builder = new AlertDialog.Builder(enerySharingActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_dialog_energy_sharing, null);

        builder.setView(dialogView);

        builder.setTitle("Additional details");

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        final EditText editText_power_Req = (EditText)dialogView.findViewById(R.id.editText_power_Req);
        final Button button_alert_submit = (Button)dialogView.findViewById(R.id.btn_1);

        button_alert_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               test1 = editText_power_Req.getText().toString();
               if (test1.isEmpty()){
                   Toast.makeText(getApplicationContext(), "Requirement cannot be empty...", Toast.LENGTH_SHORT).show();
                   alertDialog.dismiss();
                   return;
               }
               else{
                   position = Integer.valueOf(test1);
                   update_requirement(name_temp, username_temp, price_temp, position);
                   alertDialog.dismiss();
               }
            }
        });

    }
    private void update_requirement(String name_temp, String username_temp, int price_temp, int test){
        EnergySharing_Lender e = new EnergySharing_Lender(name_temp, username_temp, phone_num, "Lender", price_temp, 1);
        final String name_temp1 = name_temp;
        final int test1 = test;
        clicked_reference.child(name_temp).setValue(e).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Request Sent....", Toast.LENGTH_SHORT).show();
                    connectionClass c = new connectionClass(true, false, name_id, name_temp1, test1);//To be user_input....
                    new_database.child(name_temp1).setValue(c).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                              //  Toast.makeText(getApplicationContext(), "Connection created....", Toast.LENGTH_SHORT).show();
                                progressBar_energySharing_customer.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });
    }
}
