package com.example.sumedhsp2.esep;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sign_up extends AppCompatActivity {
    EditText email_signup;
    EditText pass_signup;
    EditText cpass_signup;
    EditText name_signup;
    EditText editText_phone_num;
    RadioGroup type_signup;
    boolean flag = true;
    ProgressBar progressBar_signUP;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email_signup = (EditText)findViewById(R.id.email_signup_ID);
        editText_phone_num = (EditText)findViewById(R.id.editText_phone_num);
        pass_signup = (EditText)findViewById(R.id.pass_signup_ID);
        cpass_signup = (EditText)findViewById(R.id.cpass_signup_ID);
        name_signup = (EditText)findViewById(R.id.name_signup_ID);
        type_signup = (RadioGroup)findViewById(R.id.radioGroup_signup);
        progressBar_signUP = (ProgressBar)findViewById(R.id.progressBar_signUP);
        mAuth = FirebaseAuth.getInstance();
        Button signup = (Button)findViewById(R.id.sign_up_ID);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar_signUP.setVisibility(View.VISIBLE);
                flag = true;
                final String email_str = email_signup.getText().toString();
                if (email_str.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please type Email....", Toast.LENGTH_SHORT).show();
                    flag = false;
                    progressBar_signUP.setVisibility(View.GONE);
                }
                final String phone_num = editText_phone_num.getText().toString();
                if (phone_num.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Mobile field cannot be empty....", Toast.LENGTH_SHORT).show();
                    flag = false;
                    progressBar_signUP.setVisibility(View.GONE);
                }
                else if(phone_num.length()!=10){
                    Toast.makeText(getApplicationContext(), "Invalid phone number....", Toast.LENGTH_SHORT).show();
                    flag = false;
                    progressBar_signUP.setVisibility(View.GONE);
                }
                String pass_str = "";
                if (pass_signup.getText().toString().equals(cpass_signup.getText().toString()) && !(pass_signup.getText().toString().isEmpty())){
                    pass_str = pass_signup.getText().toString();

                }else{
                    Toast.makeText(getApplicationContext(), "Please type password properly....", Toast.LENGTH_SHORT).show();
                    flag = false;
                    progressBar_signUP.setVisibility(View.GONE);
                }
                final String name_str = name_signup.getText().toString();
                if (name_str.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please type name....", Toast.LENGTH_SHORT).show();
                    flag = false;
                    progressBar_signUP.setVisibility(View.GONE);
                }
                int selectedId = -1;
                selectedId = type_signup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton)findViewById(selectedId);
                final String type_str = radioButton.getText().toString();
                if (selectedId == -1){
                    Toast.makeText(getApplicationContext(), "Please select type of account....", Toast.LENGTH_SHORT).show();
                    flag = false;
                    progressBar_signUP.setVisibility(View.GONE);
                }
                if (flag) {
                    mAuth.createUserWithEmailAndPassword(email_str, pass_str).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String id = FirebaseAuth.getInstance().getUid();
                                if (type_str.equals("Lender")){
                                    EnergySharing_Lender u = new EnergySharing_Lender(name_str, email_str, phone_num, type_str, 10, 0);
                                    FirebaseDatabase.getInstance().getReference(type_str).child(name_str).setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                //Toast.makeText(getApplicationContext(), "Registration is successful....", Toast.LENGTH_SHORT).show();
                                                flag = true;

                                            } else {
                                                flag = false;
                                                Toast.makeText(getApplicationContext(), "Error inside....", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                                else {
                                    Users u = new Users(name_str, email_str, phone_num, type_str);

                                    FirebaseDatabase.getInstance().getReference(type_str).child(name_str).setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                //Toast.makeText(getApplicationContext(), "Registration is successful....", Toast.LENGTH_SHORT).show();
                                                flag = true;

                                            } else {
                                                flag = false;
                                                Toast.makeText(getApplicationContext(), "Error inside....", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                                if (flag){
                                    String uid = mAuth.getCurrentUser().getUid();
                                    Intent newintent = new Intent(getApplicationContext(), Main2Activity.class);
                                    newintent.putExtra("username", email_str);
                                    newintent.putExtra("type", type_str);
                                    progressBar_signUP.setVisibility(View.GONE);
                                    startActivity(newintent);
                                }

                            }
                            else{
                                if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                    Toast.makeText(getApplicationContext(), "User already exists....", Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(getApplicationContext(), "Error occured....", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
