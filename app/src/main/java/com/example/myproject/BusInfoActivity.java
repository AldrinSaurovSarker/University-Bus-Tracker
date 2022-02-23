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


public class BusInfoActivity extends AppCompatActivity {

    String driverKey;
    TextView busInfoDriverName,busInfoDriverNo,busInfoBusName,busInfoBusNo,busInfoBusDetails;
    private FirebaseAuth auth;
    private DatabaseReference busInfoRef;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_info);

        driverKey=getIntent().getStringExtra("driverKey");

        busInfoDriverName=findViewById(R.id.busInfoDriverName);
        busInfoDriverNo=findViewById(R.id.busInfoDriverNo);
        busInfoBusName=findViewById(R.id.busInfoBusName);
        busInfoBusNo=findViewById(R.id.busInfoBusNo);
        busInfoBusDetails=findViewById(R.id.busInfoBusDetails);

        busInfoRef= FirebaseDatabase.getInstance().getReference().child("drivers").child(driverKey);

        busInfoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String DriverName=snapshot.child("driver_name").getValue().toString();
                String DriverNo=snapshot.child("contact_no").getValue().toString();
                String BusName=snapshot.child("bus_name").getValue().toString();
                String BusNo=snapshot.child("bus_no").getValue().toString();
                String BusDetails=snapshot.child("bus_details").getValue().toString();

                busInfoDriverName.setText("Driver Name: "+DriverName);
                busInfoDriverNo.setText("Driver Contact Number: "+DriverNo);
                busInfoBusName.setText("Bus Name: "+BusName);
                busInfoBusNo.setText("Bus Number: "+BusNo);
                busInfoBusDetails.setText("Bus Route: "+BusDetails);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}