package com.example.sumedhsp2.esep;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    FirebaseAuth mAuth;
    boolean flag = true;
    ProgressBar progressBar_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        Button sign_up = (Button)findViewById(R.id.sign_up);
        Button login = (Button)findViewById(R.id.login);
        progressBar_login = (ProgressBar)findViewById(R.id.progressBar_login);


        login.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View view) {
                progressBar_login.setVisibility(View.VISIBLE);
                flag = true;
                final String username = email.getText().toString();
                final String pass = password.getText().toString();
                if (username.isEmpty() || pass.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please fill the details..", Toast.LENGTH_SHORT).show();
                    flag = false;
                    progressBar_login.setVisibility(View.GONE);
                }
                else{
                    if (flag) {
                        mAuth.signInWithEmailAndPassword(username, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    //Toast.makeText(getApplicationContext(), "Logging In", Toast.LENGTH_SHORT).show();
                                    flag = true;
                                    String uid = mAuth.getCurrentUser().getUid();
                                    //Toast.makeText(getApplicationContext(), uid, Toast.LENGTH_SHORT).show();
                                    Intent main2activity = new Intent(getApplicationContext(), Main2Activity.class);
                                    main2activity.putExtra("username_main_activity", username);
                                    main2activity.putExtra("UID", uid);
                                    progressBar_login.setVisibility(View.GONE);
                                    startActivity(main2activity);
                            }
                                else{
                                    flag = false;
                                    Toast.makeText(getApplicationContext(), "Invalid Credentials....", Toast.LENGTH_SHORT).show();
                                    progressBar_login.setVisibility(View.GONE);
                                }
                            }
                        });
                        /*if (flag){
                        String uid = mAuth.getCurrentUser().getUid();
                        //Toast.makeText(getApplicationContext(), uid, Toast.LENGTH_SHORT).show();
                        Intent main2activity = new Intent(getApplicationContext(), Main2Activity.class);
                        main2activity.putExtra("username_main_activity", username);
                        main2activity.putExtra("UID", uid);
                        progressBar_login.setVisibility(View.GONE);
                        startActivity(main2activity);}*/

                    }
                }
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sign_up_intent = new Intent(getApplicationContext(), sign_up.class);
                startActivity(sign_up_intent);
            }
        });

    }

}
