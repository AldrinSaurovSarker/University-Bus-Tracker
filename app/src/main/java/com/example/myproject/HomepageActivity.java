package com.example.myproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

public class HomepageActivity extends AppCompatActivity {

    CardView cardViewMap,cardViewCall,cardViewInfo,cardViewHelp,cardViewLogout,cardViewUserLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        cardViewMap=findViewById(R.id.cardviewMap);
        cardViewCall=findViewById(R.id.cardviewCall);
        cardViewInfo=findViewById(R.id.cardviewInfo);
        cardViewHelp=findViewById(R.id.carsviewHelp);
        cardViewLogout=findViewById(R.id.cardviewLogout);
        cardViewUserLocation=findViewById(R.id.cardviewUserLocation);

        cardViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomepageActivity.this,MapBusSelectActivity.class);
                startActivity(intent);
            }
        });

        cardViewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomepageActivity.this,InfoBusSelectActivity.class);
                startActivity(intent);
            }
        });
        cardViewCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomepageActivity.this,CallBusActivity.class);
                startActivity(intent);
            }
        });
        cardViewHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomepageActivity.this,HelpActivity.class);
                startActivity(intent);
            }
        });

        cardViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(HomepageActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(HomepageActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        cardViewUserLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomepageActivity.this,UserLocationActivity.class);
                startActivity(intent);
            }
        });



    }
    protected void onStart() {
        super.onStart();
        checkgps();
    }

    private void checkgps() {
        LocationManager locationManager=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if( !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {
            new AlertDialog.Builder(this)
                    .setTitle("Can Not Access Location")
                    .setMessage("Enable Location?")
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