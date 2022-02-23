package com.example.myproject;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class BusOnMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    String driverkey;
    DatabaseReference buslocationref;
    GoogleMap mMap;
    TextView textViewAddress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_on_map);
        textViewAddress=findViewById(R.id.textViewAddress);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googleMapBus);
        mapFragment.getMapAsync(this);






    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap=googleMap;
        driverkey=getIntent().getStringExtra("driverKey");
        buslocationref= FirebaseDatabase.getInstance().getReference().child("Driver_Location").child(driverkey);

        ValueEventListener listener=buslocationref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Double lat,lot;
                lat=snapshot.child("latitude").getValue(Double.class);
                lot=snapshot.child("longitude").getValue(Double.class);

                LatLng buslocation=new LatLng(lat,lot);
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(buslocation).title("Bus is Here!"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(buslocation, 15));

                Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> listAddress =geocoder.getFromLocation(lat,lot,1);

                    if(listAddress!=null && listAddress.size()>0){
                        String address="LOCATION:";
                        if(listAddress.get(0).getSubThoroughfare()!=null){
                            address+=listAddress.get(0).getSubThoroughfare()+" ";
                        }
                        if(listAddress.get(0).getThoroughfare()!=null){
                            address+=listAddress.get(0).getThoroughfare()+", ";
                        }
                        if(listAddress.get(0).getSubLocality()!=null){
                            address+=listAddress.get(0).getSubLocality()+", ";
                        }
                        if(listAddress.get(0).getLocality()!=null){
                            address+=listAddress.get(0).getLocality()+", ";
                        }
                        if(listAddress.get(0).getCountryName()!=null){
                            address+=listAddress.get(0).getCountryName();
                        }

                         textViewAddress.setText(address);


                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}