package com.example.myproject;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DriverHomepageActivity extends AppCompatActivity {

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==44){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location lastlocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if(lastlocation!=null){
                Double lat=lastlocation.getLatitude();
                Double lot=lastlocation.getLongitude();
                HashMap hashMapDriverLocation=new HashMap();
                hashMapDriverLocation.put("latitude",lat);
                hashMapDriverLocation.put("longitude",lot);
                referenceDriverLocation.updateChildren(hashMapDriverLocation).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Toast.makeText(DriverHomepageActivity.this, "Location sent ", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(DriverHomepageActivity.this, "Location sending failed ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });}


            }


        }
    }


    LocationManager locationManager;
    LocationListener locationListener;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference referenceDriverLocation;
    String idCurrentDriver;



    CardView cardViewDriverLogout,cardViewDriverSetting,cardViewDriverProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_homepage);

        firebaseAuth=FirebaseAuth.getInstance();
        idCurrentDriver=firebaseAuth.getCurrentUser().getUid();
        referenceDriverLocation= FirebaseDatabase.getInstance().getReference().child("Driver_Location").child(idCurrentDriver);

        cardViewDriverLogout=findViewById(R.id.cardviewDriverLogout);
        cardViewDriverSetting=findViewById(R.id.cardViewDriverSettings);
        cardViewDriverProfile=findViewById(R.id.cardViewDriverProfile);

        locationManager=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Double lat=location.getLatitude();
                Double lot=location.getLongitude();
                HashMap hashMapDriverLocation=new HashMap();
                hashMapDriverLocation.put("latitude",lat);
                hashMapDriverLocation.put("longitude",lot);
                referenceDriverLocation.updateChildren(hashMapDriverLocation).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                       if(task.isSuccessful()){
                           Toast.makeText(DriverHomepageActivity.this, "Updated Location sent ", Toast.LENGTH_SHORT).show();
                       } else{
                           Toast.makeText(DriverHomepageActivity.this, "Updated Location sending failed ", Toast.LENGTH_SHORT).show();
                       }
                    }
                });
            }
            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        };


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location lastlocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(lastlocation!=null){
            double lat=lastlocation.getLatitude();
            double lot=lastlocation.getLongitude();
            HashMap hashMapDriverLocation=new HashMap();
            hashMapDriverLocation.put("latitude",lat);
            hashMapDriverLocation.put("longitude",lot);
            referenceDriverLocation.updateChildren(hashMapDriverLocation).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        Toast.makeText(DriverHomepageActivity.this, "Last Location sent ", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(DriverHomepageActivity.this, "Location sending failed ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            }


        }


        cardViewDriverSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DriverHomepageActivity.this,DriverAccountSettingActivity.class);
                startActivity(intent);
            }
        });

        cardViewDriverProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DriverHomepageActivity.this,DriverProfileActivity.class);
                startActivity(intent);
            }
        });


        cardViewDriverLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(DriverHomepageActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(DriverHomepageActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        checkgps();
    }

    private void checkgps() {
        if( !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {
            new AlertDialog.Builder(this)
                    .setTitle("Can Not Access Location")
                    .setMessage("Enable Location")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }
}