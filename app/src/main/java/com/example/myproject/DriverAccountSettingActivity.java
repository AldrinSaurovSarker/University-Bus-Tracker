package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DriverAccountSettingActivity extends AppCompatActivity {

    EditText editTextSettingDriverName, editTextSettingDriverNo, editTextSettingBusName, editTextSettingBusNo,
            editTextSettingBusDetails;
    Button buttonDriverSetting;

    private FirebaseAuth auth;
    private DatabaseReference driverSettingRef;
    private String driverKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_account_setting);

        editTextSettingDriverName = findViewById(R.id.editTextSettingDriverName);
        editTextSettingDriverNo = findViewById(R.id.editTextSettingDriverNo);
        editTextSettingBusName = findViewById(R.id.editTextSettingBusName);
        editTextSettingBusNo = findViewById(R.id.editTextSettingBusNo);
        editTextSettingBusDetails = findViewById(R.id.editTextSettingBusDetails);

        buttonDriverSetting=findViewById(R.id.buttonDriverSetting);

        auth=FirebaseAuth.getInstance();
        driverKey=auth.getCurrentUser().getUid();
        driverSettingRef= FirebaseDatabase.getInstance().getReference().child("drivers").child(driverKey);

        driverSettingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String settingDriverName=snapshot.child("driver_name").getValue().toString();
                    String settingDriverNo=snapshot.child("contact_no").getValue().toString();
                    String settingBusName=snapshot.child("bus_name").getValue().toString();
                    String settingBusNo=snapshot.child("bus_no").getValue().toString();
                    String settingBusDetails=snapshot.child("bus_details").getValue().toString();

                    editTextSettingDriverName.setText(settingDriverName);
                    editTextSettingDriverNo.setText(settingDriverNo);
                    editTextSettingBusName.setText(settingBusName);
                    editTextSettingBusNo.setText(settingBusNo);
                    editTextSettingBusDetails.setText(settingBusDetails);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        buttonDriverSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VallidateAccountInfo();
            }
        });

    }

    private void VallidateAccountInfo() {
        String drivername=editTextSettingDriverName.getText().toString();
        String driverno=editTextSettingDriverNo.getText().toString();
        String busname=editTextSettingBusName.getText().toString();
        String busno=editTextSettingBusNo.getText().toString();
        String busdetails=editTextSettingBusDetails.getText().toString();

        if(drivername.isEmpty()|driverno.isEmpty()|busname.isEmpty()|busno.isEmpty()|busdetails.isEmpty()){
            Toast.makeText(DriverAccountSettingActivity.this, "Empty textfields", Toast.LENGTH_SHORT).show();
        }else {
            UpdateAccount(drivername,driverno,busname,busno,busdetails);
        }
    }

    private void UpdateAccount(String drivername, String driverno, String busname, String busno, String busdetails) {
        HashMap hashMapDriverSetting = new HashMap();
        hashMapDriverSetting.put("driver_name",drivername);
        hashMapDriverSetting.put("contact_no",driverno);
        hashMapDriverSetting.put("bus_name",busname);
        hashMapDriverSetting.put("bus_no",busno);
        hashMapDriverSetting.put("bus_details",busdetails);

      driverSettingRef.updateChildren(hashMapDriverSetting).addOnCompleteListener(new OnCompleteListener() {
          @Override
          public void onComplete(@NonNull Task task) {
              if(task.isSuccessful()){
                  Toast.makeText(DriverAccountSettingActivity.this, "Account Updated", Toast.LENGTH_SHORT).show();
                  Intent intent=new Intent(DriverAccountSettingActivity.this,DriverHomepageActivity.class);
                  startActivity(intent);
                  finish();
              }else {
                  Toast.makeText(DriverAccountSettingActivity.this, "Failed", Toast.LENGTH_SHORT).show();
              }
          }
      });
    }

}