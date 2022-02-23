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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    Button buttonSignup;
    EditText editTextEmail,editTextPassword,editTextConfirmPassword;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        editTextConfirmPassword=(EditText)findViewById(R.id.editTextConfirmPassword);

        buttonSignup=(Button)findViewById(R.id.buttonSignup);
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail=editTextEmail.getText().toString();
                String txtPassword=editTextPassword.getText().toString();
                String txtConfirmPassword=editTextConfirmPassword.getText().toString();
                auth=FirebaseAuth.getInstance();

                if(txtEmail.isEmpty()|txtPassword.isEmpty()|txtConfirmPassword.isEmpty()){
                    Toast.makeText(SignupActivity.this, "Empty textfield", Toast.LENGTH_SHORT).show();
                }else if(txtPassword.length()<6){
                    Toast.makeText(SignupActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                }else if(! txtPassword.equals(txtConfirmPassword)){
                    Toast.makeText(SignupActivity.this, "Password didn't match", Toast.LENGTH_SHORT).show();
                }else {
                    registerUser(txtEmail,txtPassword);
                }

            }
        });
    }

    private void registerUser(String txtEmail, String txtPassword) {
        auth.createUserWithEmailAndPassword(txtEmail,txtPassword).addOnCompleteListener(SignupActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignupActivity.this, "Sign Up Completed", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignupActivity.this, SignupNextActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(SignupActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}