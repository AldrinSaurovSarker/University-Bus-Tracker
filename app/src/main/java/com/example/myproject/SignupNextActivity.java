package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupNextActivity extends AppCompatActivity {


    EditText editTextUserName,editTextContactNo,editTextBusName,editTextBusDetails,editTextBusNo;
    Switch switchDriverCheck;
    Button saveButton;
    int flag =0;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef,driverRef;
    String idCurrentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_next);


        mAuth=FirebaseAuth.getInstance();
        idCurrentUser=mAuth.getCurrentUser().getUid();
        userRef= FirebaseDatabase.getInstance().getReference().child("users").child(idCurrentUser);
        driverRef= FirebaseDatabase.getInstance().getReference().child("drivers").child(idCurrentUser);


        editTextUserName=findViewById(R.id.editTextUserName);
        editTextContactNo=findViewById(R.id.editTextContactNo);
        editTextBusName=findViewById(R.id.editTextBusName);
        editTextBusNo=findViewById(R.id.editTextBusNo);
        editTextBusDetails=findViewById(R.id.editTextBusDetails);
        switchDriverCheck=findViewById(R.id.switchDriverCheck);
        saveButton=findViewById(R.id.buttonSave);

        switchDriverCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    editTextBusName.setVisibility(View.VISIBLE);
                    editTextBusNo.setVisibility(View.VISIBLE);
                    editTextBusDetails.setVisibility(View.VISIBLE);
                    flag=1;

                }else {
                    editTextBusName.setVisibility(View.INVISIBLE);
                    editTextBusNo.setVisibility(View.INVISIBLE);
                    editTextBusDetails.setVisibility(View.INVISIBLE);
                    flag=0;


                }

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==0){
                    String txtUserName=editTextUserName.getText().toString();
                    String txtContactNumber=editTextContactNo.getText().toString();
                    if(txtUserName.isEmpty()|txtContactNumber.isEmpty()){
                        Toast.makeText(SignupNextActivity.this, "Empty text fields", Toast.LENGTH_SHORT).show();
                    }else{
                        registerUserAccount(txtUserName,txtContactNumber);

                    }



                }else if(flag==1){

                    String txtDriverName=editTextUserName.getText().toString();
                    String txtDriverContactNumber=editTextContactNo.getText().toString();
                    String txtBusName=editTextBusName.getText().toString();
                    String txtBusNo=editTextBusNo.getText().toString();
                    String txtBusDetails=editTextBusDetails.getText().toString();

                    if(txtDriverName.isEmpty()|txtDriverContactNumber.isEmpty()|txtBusName.isEmpty()|txtBusNo.isEmpty()|txtBusDetails.isEmpty()){
                        Toast.makeText(SignupNextActivity.this, "Empty text fields", Toast.LENGTH_SHORT).show();
                    }else{
                        registerDriverAccount(txtDriverName,txtDriverContactNumber,txtBusName,txtBusNo,txtBusDetails);
                    }


                }
            }
        });



    }



    private void registerUserAccount(String txtUserName, String txtContactNumber) {
        HashMap hashMapUser=new HashMap();
        hashMapUser.put("username",txtUserName);
        hashMapUser.put("contact_no",txtContactNumber);
        userRef.updateChildren(hashMapUser).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignupNextActivity.this, "User Account Created Succcessfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignupNextActivity.this,HomepageActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(SignupNextActivity.this, "User Account Creation failed ", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void registerDriverAccount(String txtDriverName, String txtDriverContactNumber, String txtBusName, String txtBusNo, String txtBusDetails) {

        HashMap hashMapDriver=new HashMap();
        hashMapDriver.put("driver_name",txtDriverName);
        hashMapDriver.put("contact_no",txtDriverContactNumber);
        hashMapDriver.put("bus_name",txtBusName);
        hashMapDriver.put("bus_no",txtBusNo);
        hashMapDriver.put("bus_details",txtBusDetails);

        driverRef.updateChildren(hashMapDriver).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignupNextActivity.this, "Driver Account Created Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignupNextActivity.this,DriverHomepageActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(SignupNextActivity.this, "Driver Account Creation failed ", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}