package com.example.myproject;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DriverProfileActivity extends AppCompatActivity {

    TextView driverProfileName,driverProfileNo,driverProfileBusName,driverProfileBusNo,getDriverProfileBusDetails;
    private FirebaseAuth auth;
    private DatabaseReference driverProfileRef;
    private String driverKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);


        driverProfileName=findViewById(R.id.driverProfileName);
        driverProfileNo=findViewById(R.id.driverProfileNo);
        driverProfileBusName=findViewById(R.id.driverProfileBusName);
        driverProfileBusNo=findViewById(R.id.driverProfileBusNo);
        getDriverProfileBusDetails=findViewById(R.id.driverProfileBusDetails);

        auth=FirebaseAuth.getInstance();
        driverKey=auth.getCurrentUser().getUid();
        driverProfileRef= FirebaseDatabase.getInstance().getReference().child("drivers").child(driverKey);

        driverProfileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String DriverName=snapshot.child("driver_name").getValue().toString();
                String DriverNo=snapshot.child("contact_no").getValue().toString();
                String BusName=snapshot.child("bus_name").getValue().toString();
                String BusNo=snapshot.child("bus_no").getValue().toString();
                String BusDetails=snapshot.child("bus_details").getValue().toString();

                driverProfileName.setText("Name: "+DriverName);
                driverProfileNo.setText("Contact Number: "+DriverNo);
                driverProfileBusName.setText("Bus Name: "+BusName);
                driverProfileBusNo.setText("Bus Number: "+BusNo);
                getDriverProfileBusDetails.setText("Bus Route: "+BusDetails);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}