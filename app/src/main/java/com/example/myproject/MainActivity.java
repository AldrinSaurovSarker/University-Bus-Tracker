package com.example.myproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button buttonLogin;
    EditText editTextEmail, editTextPassword;
    private FirebaseAuth auth;
    private DatabaseReference referenceUser,referenceDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editTextEmail=findViewById(R.id.login_editTextEmail);
        editTextPassword=findViewById(R.id.login_editTextPassword);
        auth=FirebaseAuth.getInstance();
        referenceUser= FirebaseDatabase.getInstance().getReference().child("users");
        referenceDriver= FirebaseDatabase.getInstance().getReference().child("drivers");

        TextView signupTextview=(TextView)findViewById(R.id.textViewSignup);
        signupTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

        buttonLogin=(Button)findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail=editTextEmail.getText().toString();
                String txtPassword=editTextPassword.getText().toString();
                if(txtEmail.isEmpty()|txtPassword.isEmpty()){
                    Toast.makeText(MainActivity.this, "Empty Textfield", Toast.LENGTH_SHORT).show();

                }else {
                    logInUser(txtEmail, txtPassword);
                }


            }
        });
    }

    private void logInUser(String txtEmail, String txtPassword) {
        auth.signInWithEmailAndPassword(txtEmail,txtPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this, "Log In Successful", Toast.LENGTH_SHORT).show();

                final String userIdCurrent=auth.getCurrentUser().getUid();


                referenceUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(userIdCurrent)){
                            Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
                            startActivity(intent);
                            finish();


                        }else{
                            referenceDriver.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.hasChild(userIdCurrent)){

                                        Intent intent = new Intent(MainActivity.this, DriverHomepageActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        checkInternet();
        FirebaseUser user= auth.getCurrentUser();

        if(user != null){
            checkUserExistance();

        }

    }

    private void checkInternet() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
        if(!connected){


            new AlertDialog.Builder(this)
                    .setTitle("Can Not Access Internet")
                    .setMessage("Enable Internet")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent intent = new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }

    }

    private void checkUserExistance() {
        final String userIdCurrent=auth.getCurrentUser().getUid();

        referenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(! snapshot.hasChild(userIdCurrent)){
                    referenceDriver.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.hasChild(userIdCurrent)){

                                Intent intent=new Intent(MainActivity.this,SignupNextActivity.class);
                                startActivity(intent);
                                finish();
                            }else{

                                Intent intent=new Intent(MainActivity.this,DriverHomepageActivity.class);
                                startActivity(intent);
                                finish();

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    Intent intent=new Intent(MainActivity.this,HomepageActivity.class);
                    startActivity(intent);
                    finish();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }






}

